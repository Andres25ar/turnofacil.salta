package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.appointment.AppointmentRequestDTO;
import com.turnofacil.salta.dto.appointment.AppointmentResponseDTO;
import com.turnofacil.salta.entity.Appointment;
import com.turnofacil.salta.entity.SpecialityDetail;
import com.turnofacil.salta.entity.User;
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.mapper.AppointmentMapper;
import com.turnofacil.salta.repository.AppointmentRepository;
import com.turnofacil.salta.repository.SpecialityDetailRepository;
import com.turnofacil.salta.repository.UserRepository;
import com.turnofacil.salta.service.IAppointmentService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements IAppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final SpecialityDetailRepository specialityDetailRepository;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            UserRepository userRepository,
            SpecialityDetailRepository specialityDetailRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
        this.specialityDetailRepository = specialityDetailRepository;
    }

    @Override
    @Transactional
    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO requestDTO, String userEmail) {
        User patient = userRepository.findByEmail(userEmail)
                .orElseThrow(()-> new ResourceNotFoundException("Usuario no encontrado"));

        SpecialityDetail detail = specialityDetailRepository.findById(requestDTO.getSpecialityDetailId())
                .orElseThrow(() -> new ResourceNotFoundException("SpecialityDetail", "id", requestDTO.getSpecialityDetailId()));

        String dayOfWeek = requestDTO.getAppointmentDate().getDayOfWeek().toString();

        boolean isOccupied = appointmentRepository.existsBySpecialityDetailAndAppointmentDateAndStartTime(
                detail, requestDTO.getAppointmentDate(), requestDTO.getStartTime()
        );

        if(isOccupied){
            throw new IllegalArgumentException("Este horario ya no está disponible.");
        }

        Appointment appointment = new Appointment();
        appointment.setUser(patient);
        appointment.setSpecialityDetail(detail);
        appointment.setAppointmentDate(requestDTO.getAppointmentDate());
        appointment.setStartTime(requestDTO.getStartTime());
        appointment.setStatus(true);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return AppointmentMapper.toResponseDTO(savedAppointment);
    }

    @Override
    @Transactional
    public List<AppointmentResponseDTO> getMyAppointmentsByEmail(String userEmail) {
        User patient = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        List<Appointment> appointments = appointmentRepository.findByUserWithDetails(patient);

        return appointments.stream()
                .map(AppointmentMapper :: toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AppointmentResponseDTO> getMyAppointmentsByCuil(String cuil) {
        User patient = userRepository.findByCuil(cuil)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        List<Appointment> appointments = appointmentRepository.findByUserWithDetails(patient);

        return appointments.stream()
                .map(AppointmentMapper :: toResponseDTO)
                .collect(Collectors.toList());
    }

    @SneakyThrows //si no funciona descomentar y probar
    @Override
    @Transactional
    public MessageResponse cancelAppointment(Long appointmentId, String userEmail) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", appointmentId));

        if(!appointment.getUser().getEmail().equals(userEmail)){
            throw new AccessDeniedException("No tiene permisos para cancelar este turno.");
        }

        appointment.setStatus(false);
        appointmentRepository.save(appointment);

        return new MessageResponse("Se ha cancelado el turno correctamente.");
    }
}
