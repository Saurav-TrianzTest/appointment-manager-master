package com.appointmentapp.manager.service.serviceImpl;

import com.appointmentapp.manager.models.Appointment;
import com.appointmentapp.manager.repository.AppointmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository repository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setNameAppointment("Team Meeting");
        appointment.setLocal("Conference Room");
        appointment.setDate("2024-01-15");
        appointment.setTime("14:00");
    }

    @Test
    void testFindAll_returnsAllAppointments() {
        // Arrange
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        appointment2.setNameAppointment("Project Review");
        appointments.add(appointment2);
        when(repository.findAll()).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Team Meeting", result.get(0).getNameAppointment());
        assertEquals("Project Review", result.get(1).getNameAppointment());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindAll_withEmptyList_returnsEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Appointment> result = appointmentService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById_withValidId_returnsAppointment() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(appointment));

        // Act
        Appointment result = appointmentService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Team Meeting", result.getNameAppointment());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindById_withInvalidId_returnsNull() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Appointment result = appointmentService.findById(999L);

        // Assert
        assertNull(result);
        verify(repository, times(1)).findById(999L);
    }

    @Test
    void testFindById_withZeroId_returnsNull() {
        // Arrange
        when(repository.findById(0L)).thenReturn(Optional.empty());

        // Act
        Appointment result = appointmentService.findById(0L);

        // Assert
        assertNull(result);
        verify(repository, times(1)).findById(0L);
    }

    @Test
    void testFindById_withNegativeId_returnsNull() {
        // Arrange
        when(repository.findById(-1L)).thenReturn(Optional.empty());

        // Act
        Appointment result = appointmentService.findById(-1L);

        // Assert
        assertNull(result);
        verify(repository, times(1)).findById(-1L);
    }

    @Test
    void testSave_withValidAppointment_savesAndReturnsAppointment() {
        // Arrange
        when(repository.save(appointment)).thenReturn(appointment);

        // Act
        Appointment result = appointmentService.save(appointment);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Team Meeting", result.getNameAppointment());
        verify(repository, times(1)).save(appointment);
    }

    @Test
    void testSave_withNewAppointment_savesSuccessfully() {
        // Arrange
        Appointment newAppointment = new Appointment();
        newAppointment.setNameAppointment("New Meeting");
        newAppointment.setLocal("Room B");
        newAppointment.setDate("2024-02-20");
        newAppointment.setTime("10:00");
        when(repository.save(newAppointment)).thenReturn(newAppointment);

        // Act
        Appointment result = appointmentService.save(newAppointment);

        // Assert
        assertNotNull(result);
        assertEquals("New Meeting", result.getNameAppointment());
        verify(repository, times(1)).save(newAppointment);
    }

    @Test
    void testSave_withUpdatedAppointment_updatesSuccessfully() {
        // Arrange
        appointment.setNameAppointment("Updated Meeting");
        when(repository.save(appointment)).thenReturn(appointment);

        // Act
        Appointment result = appointmentService.save(appointment);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Meeting", result.getNameAppointment());
        verify(repository, times(1)).save(appointment);
    }

    @Test
    void testDelete_withValidId_deletesAppointment() {
        // Arrange
        doNothing().when(repository).deleteById(1L);

        // Act
        appointmentService.delete(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_withDifferentId_callsDeleteWithCorrectId() {
        // Arrange
        doNothing().when(repository).deleteById(5L);

        // Act
        appointmentService.delete(5L);

        // Assert
        verify(repository, times(1)).deleteById(5L);
    }

    @Test
    void testDelete_withZeroId_callsDelete() {
        // Arrange
        doNothing().when(repository).deleteById(0L);

        // Act
        appointmentService.delete(0L);

        // Assert
        verify(repository, times(1)).deleteById(0L);
    }

    @Test
    void testFindAll_multipleInvocations_callsRepositoryEachTime() {
        // Arrange
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        when(repository.findAll()).thenReturn(appointments);

        // Act
        appointmentService.findAll();
        appointmentService.findAll();

        // Assert
        verify(repository, times(2)).findAll();
    }

    @Test
    void testSave_withNullFields_savesAppointment() {
        // Arrange
        Appointment appointmentWithNulls = new Appointment();
        when(repository.save(appointmentWithNulls)).thenReturn(appointmentWithNulls);

        // Act
        Appointment result = appointmentService.save(appointmentWithNulls);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(appointmentWithNulls);
    }
}
