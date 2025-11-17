package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.admin.HealthCenterResponseDTO;
import com.turnofacil.salta.dto.admin.SpecialityResponseDTO;
import com.turnofacil.salta.dto.toPublic.ProfessionalResponseDTO;
import com.turnofacil.salta.entity.HealthCenter;
import com.turnofacil.salta.entity.Schedule;
import com.turnofacil.salta.entity.Speciality;
import com.turnofacil.salta.entity.SpecialityDetail;
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.mapper.HealthCenterMapper;
import com.turnofacil.salta.mapper.ProfessionalMapper;
import com.turnofacil.salta.mapper.SpecialityMapper;
import com.turnofacil.salta.repository.AppointmentRepository;
import com.turnofacil.salta.repository.HealthCenterRepository;
import com.turnofacil.salta.repository.SpecialityDetailRepository;
import com.turnofacil.salta.repository.SpecialityRepository;
import com.turnofacil.salta.service.IPublicSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PublicSearchServiceImpl implements IPublicSearchService {
    private final SpecialityDetailRepository specialityDetailRepository;
    private final HealthCenterRepository healthCenterRepository;
    private final SpecialityRepository specialityRepository;
    private final AppointmentRepository appointmentRepository;

    public PublicSearchServiceImpl(SpecialityDetailRepository specialityDetailRepository,
                                   HealthCenterRepository healthCenterRepository,
                                   SpecialityRepository specialityRepository,
                                   AppointmentRepository appointmentRepository
    ) {
        this.specialityDetailRepository = specialityDetailRepository;
        this.healthCenterRepository = healthCenterRepository;
        this.specialityRepository = specialityRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProfessionalResponseDTO> findProfessionalByCenterAndSpeciality(Long centerId, Integer specialityId) {
        HealthCenter center = healthCenterRepository.findById(centerId)
                .orElseThrow(() -> new ResourceNotFoundException("HealthCenter", "id", centerId));

        Speciality speciality = specialityRepository.findById(specialityId)
                .orElseThrow(() -> new ResourceNotFoundException("Speciality", "id", specialityId));

        List<SpecialityDetail> details = specialityDetailRepository.findActiveDetailsByCenterAndSpeciality(center, speciality);

        return details.stream()
                .map(ProfessionalMapper :: toProfessionalResponseDTO)
                .distinct()
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<String> findAvailableSlots(Long specialityDetailId, LocalDate date) {
        //obtener horarrios
        SpecialityDetail detail = this.specialityDetailRepository.findById(specialityDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("SpecialityDetail", "id", specialityDetailId));

        Schedule schedule = detail.getSchedule();
        if (schedule == null) {
            return new ArrayList<>();
        }

        //obtener el dia en ingles "SUNDAY, MONDAY, TUESDAY, etc"
        String requestedDayOfWeek = date.getDayOfWeek().toString();

        /*
         *usar cuando la funcion de traduccion esté lista
         * boolean dayIsValid schedule.getDays().contains(dia_traducido);
         * if(dayIsValid) {
         *      return new ArrayList<>();
         * }
         */

        //generar los horarios posibles
        List<LocalTime> allPossibleSlots = generateSlots(
            schedule.getStartTime(),
            schedule.getFinishTime(),
            schedule.getNumberOfAppointment()
        );

        //obtener los horarios reservados
        Set<LocalTime> bookedSlots = Set.copyOf(
                appointmentRepository.findBookedSlotsByDetailAndDate(specialityDetailId, date)
        );

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        return allPossibleSlots.stream()
                .filter(slot -> !bookedSlots.contains(slot))
                .map(slot -> slot.format(timeFormatter))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<HealthCenterResponseDTO> findCentersBySpeciality(Integer specialityId) {
        List<HealthCenter> centers = healthCenterRepository.findDistinctBySpecialityId(specialityId);

        return centers.stream()
                .map(HealthCenterMapper::toHealthCenterResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecialityResponseDTO> findSpecialitiesByCenter(Long centerId) {
        List<Speciality> specialities = specialityRepository.findDistinctByHealthCenterId(centerId);

        return specialities.stream()
                .map(SpecialityMapper::toSpecialityResponseDTO)
                .collect(Collectors.toList());
    }


    private List<LocalTime> generateSlots(LocalTime start, LocalTime finish, int numberOfAppointments){
        List<LocalTime> slots = new ArrayList<>();
        long totalMinutes = start.until(finish, ChronoUnit.MINUTES);

        if (numberOfAppointments <= 0) return slots;    //retornamos vacio

        long slotDuration = totalMinutes / numberOfAppointments;    //quizas deberia ser un double :/

        LocalTime currentSlot = start;
        for (int i = 0; i < numberOfAppointments; i++){
            slots.add(currentSlot);
            currentSlot = currentSlot.plusMinutes(slotDuration);    //como decir horario = horario + duracion
        }

        return slots;
    }
}









