package com.appointmentapp.manager.service;

import com.appointmentapp.manager.models.Appointment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock
    private AppointmentService appointmentService;

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
    void testFindAll_returnsListOfAppointments() {
        // Arrange
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        when(appointmentService.findAll()).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(appointmentService, times(1)).findAll();
    }

    @Test
    void testFindById_withValidId_returnsAppointment() {
        // Arrange
        when(appointmentService.findById(1L)).thenReturn(appointment);

        // Act
        Appointment result = appointmentService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(appointmentService, times(1)).findById(1L);
    }

    @Test
    void testFindById_withInvalidId_returnsNull() {
        // Arrange
        when(appointmentService.findById(999L)).thenReturn(null);

        // Act
        Appointment result = appointmentService.findById(999L);

        // Assert
        assertNull(result);
        verify(appointmentService, times(1)).findById(999L);
    }

    @Test
    void testSave_savesAppointment() {
        // Arrange
        when(appointmentService.save(appointment)).thenReturn(appointment);

        // Act
        Appointment result = appointmentService.save(appointment);

        // Assert
        assertNotNull(result);
        assertEquals("Team Meeting", result.getNameAppointment());
        verify(appointmentService, times(1)).save(appointment);
    }

    @Test
    void testDelete_deletesAppointment() {
        // Arrange
        doNothing().when(appointmentService).delete(1L);

        // Act
        appointmentService.delete(1L);

        // Assert
        verify(appointmentService, times(1)).delete(1L);
    }

    @Test
    void testFindAll_withEmptyList_returnsEmptyList() {
        // Arrange
        when(appointmentService.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Appointment> result = appointmentService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(appointmentService, times(1)).findAll();
    }

    @Test
    void testFindAll_withMultipleAppointments_returnsAllAppointments() {
        // Arrange
        List<Appointment> appointments = new ArrayList<>();
        appointments.add(appointment);
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        appointment2.setNameAppointment("Project Review");
        appointments.add(appointment2);
        when(appointmentService.findAll()).thenReturn(appointments);

        // Act
        List<Appointment> result = appointmentService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(appointmentService, times(1)).findAll();
    }

    @Test
    void testSave_withNewAppointment_savesSuccessfully() {
        // Arrange
        Appointment newAppointment = new Appointment();
        newAppointment.setNameAppointment("New Meeting");
        when(appointmentService.save(newAppointment)).thenReturn(newAppointment);

        // Act
        Appointment result = appointmentService.save(newAppointment);

        // Assert
        assertNotNull(result);
        assertEquals("New Meeting", result.getNameAppointment());
        verify(appointmentService, times(1)).save(newAppointment);
    }

    @Test
    void testDelete_withDifferentId_deletesCorrectAppointment() {
        // Arrange
        doNothing().when(appointmentService).delete(5L);

        // Act
        appointmentService.delete(5L);

        // Assert
        verify(appointmentService, times(1)).delete(5L);
    }

    @Test
    void testFindById_withZeroId_handlesZeroId() {
        // Arrange
        when(appointmentService.findById(0L)).thenReturn(null);

        // Act
        Appointment result = appointmentService.findById(0L);

        // Assert
        assertNull(result);
        verify(appointmentService, times(1)).findById(0L);
    }

    @Test
    void testFindById_withNegativeId_handlesNegativeId() {
        // Arrange
        when(appointmentService.findById(-1L)).thenReturn(null);

        // Act
        Appointment result = appointmentService.findById(-1L);

        // Assert
        assertNull(result);
        verify(appointmentService, times(1)).findById(-1L);
    }

    @Test
    void testSave_withUpdatedAppointment_updatesSuccessfully() {
        // Arrange
        appointment.setNameAppointment("Updated Meeting");
        when(appointmentService.save(appointment)).thenReturn(appointment);

        // Act
        Appointment result = appointmentService.save(appointment);

        // Assert
        assertNotNull(result);
        assertEquals("Updated Meeting", result.getNameAppointment());
        verify(appointmentService, times(1)).save(appointment);
    }
}
