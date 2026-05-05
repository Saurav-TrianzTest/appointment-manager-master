package com.appointmentapp.manager.service;

import com.appointmentapp.manager.models.Invited;
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
class InvitedServiceTest {

    @Mock
    private InvitedService invitedService;

    private Invited invited;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setNameAppointment("Team Meeting");

        invited = new Invited();
        invited.setId(1L);
        invited.setInvitedName("John Doe");
        invited.setCellNumber("555-1234");
        invited.setAppointment(appointment);
    }

    @Test
    void testFindAll_returnsListOfInvited() {
        // Arrange
        List<Invited> invitedList = new ArrayList<>();
        invitedList.add(invited);
        when(invitedService.findAll()).thenReturn(invitedList);

        // Act
        List<Invited> result = invitedService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(invitedService, times(1)).findAll();
    }

    @Test
    void testFindById_withValidId_returnsInvited() {
        // Arrange
        when(invitedService.findById(1L)).thenReturn(invited);

        // Act
        Invited result = invitedService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getInvitedName());
        verify(invitedService, times(1)).findById(1L);
    }

    @Test
    void testFindById_withInvalidId_returnsNull() {
        // Arrange
        when(invitedService.findById(999L)).thenReturn(null);

        // Act
        Invited result = invitedService.findById(999L);

        // Assert
        assertNull(result);
        verify(invitedService, times(1)).findById(999L);
    }

    @Test
    void testSave_savesInvited() {
        // Arrange
        when(invitedService.save(invited)).thenReturn(invited);

        // Act
        Invited result = invitedService.save(invited);

        // Assert
        assertNotNull(result);
        assertEquals("John Doe", result.getInvitedName());
        verify(invitedService, times(1)).save(invited);
    }

    @Test
    void testDelete_deletesInvited() {
        // Arrange
        doNothing().when(invitedService).delete(1L);

        // Act
        invitedService.delete(1L);

        // Assert
        verify(invitedService, times(1)).delete(1L);
    }

    @Test
    void testFindAll_withEmptyList_returnsEmptyList() {
        // Arrange
        when(invitedService.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Invited> result = invitedService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(invitedService, times(1)).findAll();
    }

    @Test
    void testFindAll_withMultipleInvited_returnsAllInvited() {
        // Arrange
        List<Invited> invitedList = new ArrayList<>();
        invitedList.add(invited);
        Invited invited2 = new Invited();
        invited2.setId(2L);
        invited2.setInvitedName("Jane Smith");
        invited2.setCellNumber("555-5678");
        invitedList.add(invited2);
        when(invitedService.findAll()).thenReturn(invitedList);

        // Act
        List<Invited> result = invitedService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(invitedService, times(1)).findAll();
    }

    @Test
    void testSave_withNewInvited_savesSuccessfully() {
        // Arrange
        Invited newInvited = new Invited();
        newInvited.setInvitedName("Bob Johnson");
        newInvited.setCellNumber("555-9999");
        when(invitedService.save(newInvited)).thenReturn(newInvited);

        // Act
        Invited result = invitedService.save(newInvited);

        // Assert
        assertNotNull(result);
        assertEquals("Bob Johnson", result.getInvitedName());
        verify(invitedService, times(1)).save(newInvited);
    }

    @Test
    void testDelete_withDifferentId_deletesCorrectInvited() {
        // Arrange
        doNothing().when(invitedService).delete(10L);

        // Act
        invitedService.delete(10L);

        // Assert
        verify(invitedService, times(1)).delete(10L);
    }

    @Test
    void testFindById_withZeroId_handlesZeroId() {
        // Arrange
        when(invitedService.findById(0L)).thenReturn(null);

        // Act
        Invited result = invitedService.findById(0L);

        // Assert
        assertNull(result);
        verify(invitedService, times(1)).findById(0L);
    }

    @Test
    void testFindById_withNegativeId_handlesNegativeId() {
        // Arrange
        when(invitedService.findById(-1L)).thenReturn(null);

        // Act
        Invited result = invitedService.findById(-1L);

        // Assert
        assertNull(result);
        verify(invitedService, times(1)).findById(-1L);
    }

    @Test
    void testSave_withUpdatedInvited_updatesSuccessfully() {
        // Arrange
        invited.setInvitedName("John Updated");
        when(invitedService.save(invited)).thenReturn(invited);

        // Act
        Invited result = invitedService.save(invited);

        // Assert
        assertNotNull(result);
        assertEquals("John Updated", result.getInvitedName());
        verify(invitedService, times(1)).save(invited);
    }

    @Test
    void testSave_withAppointmentAssociation_savesWithAppointment() {
        // Arrange
        invited.setAppointment(appointment);
        when(invitedService.save(invited)).thenReturn(invited);

        // Act
        Invited result = invitedService.save(invited);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getAppointment());
        assertEquals(1L, result.getAppointment().getId());
        verify(invitedService, times(1)).save(invited);
    }

    @Test
    void testFindById_withLargeId_handlesLargeId() {
        // Arrange
        long largeId = Long.MAX_VALUE;
        when(invitedService.findById(largeId)).thenReturn(null);

        // Act
        Invited result = invitedService.findById(largeId);

        // Assert
        assertNull(result);
        verify(invitedService, times(1)).findById(largeId);
    }
}
