package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.UpdateProfessionalStatusDTO;
import com.turnofacil.salta.dto.appointment.AppointmentResponseDTO;
import com.turnofacil.salta.entity.*;
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.mapper.AppointmentMapper;
import com.turnofacil.salta.repository.AppointmentRepository;
import com.turnofacil.salta.repository.ProfessionalRepository;
import com.turnofacil.salta.repository.SpecialityDetailRepository;
import com.turnofacil.salta.repository.UserRepository;
import com.turnofacil.salta.service.IProfessionalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfessionalServiceImpl implements IProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final SpecialityDetailRepository specialityDetailRepository;
    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;

    public ProfessionalServiceImpl(
            ProfessionalRepository professionalRepository,
            SpecialityDetailRepository specialityDetailRepository,
            UserRepository userRepository,
            AppointmentRepository appointmentRepository) {
        this.professionalRepository = professionalRepository;
        this.specialityDetailRepository = specialityDetailRepository;
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional
    public MessageResponse updateProfessionalStatus(Long professionalId, UpdateProfessionalStatusDTO request) {
        // 1. Encontrar al profesional
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional", "id", professionalId));

        // 2. Actualizar su estado
        professional.setStatus(request.getStatus());
        professionalRepository.save(professional);

        // 3. Determinar si sus horarios deben estar activos
        // Solo están activos si el profesional está ACTIVO.
        boolean areSchedulesActive = request.getStatus() == ProfessionalStatus.ACTIVO;

        // 4. Encontrar TODAS sus asignaciones (SpecialityDetail)
        List<SpecialityDetail> details = specialityDetailRepository.findByProfessional(professional);

        // 5. Propagar el cambio de estado a todas sus asignaciones
        for (SpecialityDetail detail : details) {
            detail.setStatus(areSchedulesActive);
            specialityDetailRepository.save(detail);
        }

        return new MessageResponse(
                "Estado del profesional actualizado a " + request.getStatus() +
                        ". " + details.size() + " asignaciones de horarios han sido " +
                        (areSchedulesActive ? "activadas." : "desactivadas.")
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppointmentResponseDTO> getMyAgenda(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado para el email: " + userEmail));

        Professional professional = user.getProfessional();
        //si un paciente o admin accede a la ruta
        if (professional == null) {
            throw new ResourceNotFoundException("El usuario no es un profesional...");
        }

        List<SpecialityDetail> details = specialityDetailRepository.findByProfessional(professional);
        //si tiene la agenda vacia
        if (details.isEmpty()) {
            return new ArrayList<>();
        }

        List<Appointment> appointments = appointmentRepository.findActiveBySpecialityDetailInWithDetails(details);

        return appointments.stream()
                .map(AppointmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    private Appointment verifyAppointmentOwnership(Long appointmentId, String professionalEmail) {
        User user = userRepository.findByEmail(professionalEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", professionalEmail));

        Professional professional = user.getProfessional();
        if (professional == null) {
            throw new org.springframework.security.access.AccessDeniedException("El usuario autenticado no es un profesional.");
        }
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));
        Long appointmentProfessionalId = appointment.getSpecialityDetail().getProfessional().getProfesionalId();
        Long loggedInProfessionalId = professional.getProfesionalId();

        if (!appointmentProfessionalId.equals(loggedInProfessionalId)) {
            throw new org.springframework.security.access.AccessDeniedException("No tiene permisos para modificar este turno.");
        }

        return appointment;
    }

    @Override
    @Transactional
    public MessageResponse professionalCancelAppointment(Long appointmentId, String professionalEmail) {
        Appointment appointment = verifyAppointmentOwnership(appointmentId, professionalEmail);

        if (appointment.getStatus() != AppointmentStatus.RESERVADO) {
            throw new IllegalArgumentException("Solo se pueden cancelar turnos 'RESERVADOS'.");
        }

        appointment.setStatus(AppointmentStatus.CANCELADO_PROFESIONAL);
        appointmentRepository.save(appointment);
        return new MessageResponse("El turno " + appointmentId + " ha sido cancelado por el profesional.");
    }

    @Override
    @Transactional
    public MessageResponse completeAppointment(Long appointmentId, String professionalEmail) {
        Appointment appointment = verifyAppointmentOwnership(appointmentId, professionalEmail);

        if (appointment.getStatus() != AppointmentStatus.RESERVADO) {
            throw new IllegalArgumentException("Solo se pueden completar turnos 'RESERVADOS'.");
        }

        appointment.setStatus(AppointmentStatus.COMPLETADO);
        appointmentRepository.save(appointment);
        return new MessageResponse("El turno " + appointmentId + " ha sido marcado como completado.");
    }
}