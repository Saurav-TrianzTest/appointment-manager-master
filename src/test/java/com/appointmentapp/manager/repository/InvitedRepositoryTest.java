package com.appointmentapp.manager.repository;

import com.appointmentapp.manager.models.Appointment;
import com.appointmentapp.manager.models.Invited;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InvitedRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InvitedRepository invitedRepository;

    private Invited invited;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointment.setNameAppointment("Team Meeting");
        appointment.setLocal("Conference Room");
        appointment.setDate("2024-01-15");
        appointment.setTime("14:00");

        invited = new Invited();
        invited.setInvitedName("John Doe");
        invited.setCellNumber("555-1234");
    }

    @Test
    void testSave_savesInvitedSuccessfully() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        invited.setAppointment(savedAppointment);

        // Act
        Invited savedInvited = invitedRepository.save(invited);

        // Assert
        assertNotNull(savedInvited);
        assertNotNull(savedInvited.getId());
        assertEquals("John Doe", savedInvited.getInvitedName());
    }

    @Test
    void testFindById_withValidId_returnsInvited() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        invited.setAppointment(savedAppointment);
        Invited savedInvited = entityManager.persistAndFlush(invited);

        // Act
        Optional<Invited> foundInvited = invitedRepository.findById(savedInvited.getId());

        // Assert
        assertTrue(foundInvited.isPresent());
        assertEquals("John Doe", foundInvited.get().getInvitedName());
    }

    @Test
    void testFindById_withInvalidId_returnsEmpty() {
        // Act
        Optional<Invited> foundInvited = invitedRepository.findById(999L);

        // Assert
        assertFalse(foundInvited.isPresent());
    }

    @Test
    void testFindAll_returnsAllInvited() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        invited.setAppointment(savedAppointment);
        entityManager.persistAndFlush(invited);
        
        Invited invited2 = new Invited();
        invited2.setInvitedName("Jane Smith");
        invited2.setCellNumber("555-5678");
        invited2.setAppointment(savedAppointment);
        entityManager.persistAndFlush(invited2);

        // Act
        List<Invited> invitedList = invitedRepository.findAll();

        // Assert
        assertNotNull(invitedList);
        assertTrue(invitedList.size() >= 2);
    }

    @Test
    void testFindByAppointment_returnsInvitedForAppointment() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        invited.setAppointment(savedAppointment);
        entityManager.persistAndFlush(invited);
        
        Invited invited2 = new Invited();
        invited2.setInvitedName("Jane Smith");
        invited2.setCellNumber("555-5678");
        invited2.setAppointment(savedAppointment);
        entityManager.persistAndFlush(invited2);

        // Act
        Iterable<Invited> invitedList = invitedRepository.findByAppointment(savedAppointment);

        // Assert
        assertNotNull(invitedList);
        long count = 0;
        for (Invited inv : invitedList) {
            count++;
            assertEquals(savedAppointment.getId(), inv.getAppointment().getId());
        }
        assertTrue(count >= 2);
    }

    @Test
    void testFindByAppointment_withNoInvited_returnsEmptyList() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);

        // Act
        Iterable<Invited> invitedList = invitedRepository.findByAppointment(savedAppointment);

        // Assert
        assertNotNull(invitedList);
        assertFalse(invitedList.iterator().hasNext());
    }

    @Test
    void testFindByAppointment_withDifferentAppointments_returnsCorrectInvited() {
        // Arrange
        Appointment savedAppointment1 = entityManager.persistAndFlush(appointment);
        
        Appointment appointment2 = new Appointment();
        appointment2.setNameAppointment("Project Review");
        appointment2.setLocal("Room B");
        appointment2.setDate("2024-02-20");
        appointment2.setTime("10:00");
        Appointment savedAppointment2 = entityManager.persistAndFlush(appointment2);

        invited.setAppointment(savedAppointment1);
        entityManager.persistAndFlush(invited);
        
        Invited invited2 = new Invited();
        invited2.setInvitedName("Jane Smith");
        invited2.setCellNumber("555-5678");
        invited2.setAppointment(savedAppointment2);
        entityManager.persistAndFlush(invited2);

        // Act
        Iterable<Invited> invitedList1 = invitedRepository.findByAppointment(savedAppointment1);
        Iterable<Invited> invitedList2 = invitedRepository.findByAppointment(savedAppointment2);

        // Assert
        long count1 = 0;
        for (Invited inv : invitedList1) {
            count1++;
            assertEquals(savedAppointment1.getId(), inv.getAppointment().getId());
        }
        assertEquals(1, count1);

        long count2 = 0;
        for (Invited inv : invitedList2) {
            count2++;
            assertEquals(savedAppointment2.getId(), inv.getAppointment().getId());
        }
        assertEquals(1, count2);
    }

    @Test
    void testDelete_deletesInvitedSuccessfully() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        invited.setAppointment(savedAppointment);
        Invited savedInvited = entityManager.persistAndFlush(invited);
        Long invitedId = savedInvited.getId();

        // Act
        invitedRepository.deleteById(invitedId);
        Optional<Invited> deletedInvited = invitedRepository.findById(invitedId);

        // Assert
        assertFalse(deletedInvited.isPresent());
    }

    @Test
    void testUpdate_updatesInvitedSuccessfully() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        invited.setAppointment(savedAppointment);
        Invited savedInvited = entityManager.persistAndFlush(invited);
        savedInvited.setInvitedName("John Updated");

        // Act
        Invited updatedInvited = invitedRepository.save(savedInvited);

        // Assert
        assertEquals("John Updated", updatedInvited.getInvitedName());
    }

    @Test
    void testSave_withAllFields_savesAllFieldsCorrectly() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        invited.setInvitedName("Alice Johnson");
        invited.setCellNumber("555-9999");
        invited.setAppointment(savedAppointment);

        // Act
        Invited savedInvited = invitedRepository.save(invited);

        // Assert
        assertNotNull(savedInvited.getId());
        assertEquals("Alice Johnson", savedInvited.getInvitedName());
        assertEquals("555-9999", savedInvited.getCellNumber());
        assertEquals(savedAppointment.getId(), savedInvited.getAppointment().getId());
    }

    @Test
    void testExistsById_withValidId_returnsTrue() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        invited.setAppointment(savedAppointment);
        Invited savedInvited = entityManager.persistAndFlush(invited);

        // Act
        boolean exists = invitedRepository.existsById(savedInvited.getId());

        // Assert
        assertTrue(exists);
    }

    @Test
    void testExistsById_withInvalidId_returnsFalse() {
        // Act
        boolean exists = invitedRepository.existsById(999L);

        // Assert
        assertFalse(exists);
    }

    @Test
    void testCount_returnsCorrectCount() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        invited.setAppointment(savedAppointment);
        entityManager.persistAndFlush(invited);

        // Act
        long count = invitedRepository.count();

        // Assert
        assertTrue(count >= 1);
    }
}
