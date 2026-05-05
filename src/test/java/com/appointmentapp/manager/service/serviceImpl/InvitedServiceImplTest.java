package com.appointmentapp.manager.service.serviceImpl;

import com.appointmentapp.manager.models.Invited;
import com.appointmentapp.manager.models.Appointment;
import com.appointmentapp.manager.repository.InvitedRepository;
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
class InvitedServiceImplTest {

    @Mock
    private InvitedRepository repository;

    @InjectMocks
    private InvitedServiceImpl invitedService;

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
    void testFindAll_returnsAllInvited() {
        // Arrange
        List<Invited> invitedList = new ArrayList<>();
        invitedList.add(invited);
        Invited invited2 = new Invited();
        invited2.setId(2L);
        invited2.setInvitedName("Jane Smith");
        invited2.setCellNumber("555-5678");
        invitedList.add(invited2);
        when(repository.findAll()).thenReturn(invitedList);

        // Act
        List<Invited> result = invitedService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getInvitedName());
        assertEquals("Jane Smith", result.get(1).getInvitedName());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindAll_withEmptyList_returnsEmptyList() {
        // Arrange
        when(repository.findAll()).thenReturn(new ArrayList<>());

        // Act
        List<Invited> result = invitedService.findAll();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testFindById_withValidId_returnsInvited() {
        // Arrange
        when(repository.findById(1L)).thenReturn(Optional.of(invited));

        // Act
        Invited result = invitedService.findById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getInvitedName());
        assertEquals("555-1234", result.getCellNumber());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindById_withInvalidId_returnsNull() {
        // Arrange
        when(repository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Invited result = invitedService.findById(999L);

        // Assert
        assertNull(result);
        verify(repository, times(1)).findById(999L);
    }

    @Test
    void testFindById_withZeroId_returnsNull() {
        // Arrange
        when(repository.findById(0L)).thenReturn(Optional.empty());

        // Act
        Invited result = invitedService.findById(0L);

        // Assert
        assertNull(result);
        verify(repository, times(1)).findById(0L);
    }

    @Test
    void testFindById_withNegativeId_returnsNull() {
        // Arrange
        when(repository.findById(-1L)).thenReturn(Optional.empty());

        // Act
        Invited result = invitedService.findById(-1L);

        // Assert
        assertNull(result);
        verify(repository, times(1)).findById(-1L);
    }

    @Test
    void testSave_withValidInvited_savesAndReturnsInvited() {
        // Arrange
        when(repository.save(invited)).thenReturn(invited);

        // Act
        Invited result = invitedService.save(invited);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getInvitedName());
        assertEquals("555-1234", result.getCellNumber());
        verify(repository, times(1)).save(invited);
    }

    @Test
    void testSave_withNewInvited_savesSuccessfully() {
        // Arrange
        Invited newInvited = new Invited();
        newInvited.setInvitedName("Bob Johnson");
        newInvited.setCellNumber("555-9999");
        when(repository.save(newInvited)).thenReturn(newInvited);

        // Act
        Invited result = invitedService.save(newInvited);

        // Assert
        assertNotNull(result);
        assertEquals("Bob Johnson", result.getInvitedName());
        verify(repository, times(1)).save(newInvited);
    }

    @Test
    void testSave_withUpdatedInvited_updatesSuccessfully() {
        // Arrange
        invited.setInvitedName("John Updated");
        when(repository.save(invited)).thenReturn(invited);

        // Act
        Invited result = invitedService.save(invited);

        // Assert
        assertNotNull(result);
        assertEquals("John Updated", result.getInvitedName());
        verify(repository, times(1)).save(invited);
    }

    @Test
    void testSave_withAppointmentAssociation_savesWithAppointment() {
        // Arrange
        invited.setAppointment(appointment);
        when(repository.save(invited)).thenReturn(invited);

        // Act
        Invited result = invitedService.save(invited);

        // Assert
        assertNotNull(result);
        assertNotNull(result.getAppointment());
        assertEquals(1L, result.getAppointment().getId());
        verify(repository, times(1)).save(invited);
    }

    @Test
    void testDelete_withValidId_deletesInvited() {
        // Arrange
        doNothing().when(repository).deleteById(1L);

        // Act
        invitedService.delete(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_withDifferentId_callsDeleteWithCorrectId() {
        // Arrange
        doNothing().when(repository).deleteById(10L);

        // Act
        invitedService.delete(10L);

        // Assert
        verify(repository, times(1)).deleteById(10L);
    }

    @Test
    void testDelete_withZeroId_callsDelete() {
        // Arrange
        doNothing().when(repository).deleteById(0L);

        // Act
        invitedService.delete(0L);

        // Assert
        verify(repository, times(1)).deleteById(0L);
    }

    @Test
    void testFindAll_multipleInvocations_callsRepositoryEachTime() {
        // Arrange
        List<Invited> invitedList = new ArrayList<>();
        invitedList.add(invited);
        when(repository.findAll()).thenReturn(invitedList);

        // Act
        invitedService.findAll();
        invitedService.findAll();

        // Assert
        verify(repository, times(2)).findAll();
    }

    @Test
    void testSave_withNullFields_savesInvited() {
        // Arrange
        Invited invitedWithNulls = new Invited();
        when(repository.save(invitedWithNulls)).thenReturn(invitedWithNulls);

        // Act
        Invited result = invitedService.save(invitedWithNulls);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).save(invitedWithNulls);
    }

    @Test
    void testFindById_withLargeId_handlesLargeId() {
        // Arrange
        long largeId = Long.MAX_VALUE;
        when(repository.findById(largeId)).thenReturn(Optional.empty());

        // Act
        Invited result = invitedService.findById(largeId);

        // Assert
        assertNull(result);
        verify(repository, times(1)).findById(largeId);
    }
}
