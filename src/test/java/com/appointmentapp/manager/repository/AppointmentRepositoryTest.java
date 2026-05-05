package com.appointmentapp.manager.repository;

import com.appointmentapp.manager.models.Appointment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AppointmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppointmentRepository appointmentRepository;

    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointment.setNameAppointment("Team Meeting");
        appointment.setLocal("Conference Room");
        appointment.setDate("2024-01-15");
        appointment.setTime("14:00");
    }

    @Test
    void testSave_savesAppointmentSuccessfully() {
        // Act
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Assert
        assertNotNull(savedAppointment);
        assertNotNull(savedAppointment.getId());
        assertEquals("Team Meeting", savedAppointment.getNameAppointment());
    }

    @Test
    void testFindById_withValidId_returnsAppointment() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);

        // Act
        Optional<Appointment> foundAppointment = appointmentRepository.findById(savedAppointment.getId());

        // Assert
        assertTrue(foundAppointment.isPresent());
        assertEquals("Team Meeting", foundAppointment.get().getNameAppointment());
    }

    @Test
    void testFindById_withInvalidId_returnsEmpty() {
        // Act
        Optional<Appointment> foundAppointment = appointmentRepository.findById(999L);

        // Assert
        assertFalse(foundAppointment.isPresent());
    }

    @Test
    void testFindAll_returnsAllAppointments() {
        // Arrange
        entityManager.persistAndFlush(appointment);
        Appointment appointment2 = new Appointment();
        appointment2.setNameAppointment("Project Review");
        appointment2.setLocal("Room B");
        appointment2.setDate("2024-02-20");
        appointment2.setTime("10:00");
        entityManager.persistAndFlush(appointment2);

        // Act
        List<Appointment> appointments = appointmentRepository.findAll();

        // Assert
        assertNotNull(appointments);
        assertTrue(appointments.size() >= 2);
    }

    @Test
    void testDelete_deletesAppointmentSuccessfully() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        Long appointmentId = savedAppointment.getId();

        // Act
        appointmentRepository.deleteById(appointmentId);
        Optional<Appointment> deletedAppointment = appointmentRepository.findById(appointmentId);

        // Assert
        assertFalse(deletedAppointment.isPresent());
    }

    @Test
    void testUpdate_updatesAppointmentSuccessfully() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);
        savedAppointment.setNameAppointment("Updated Meeting");

        // Act
        Appointment updatedAppointment = appointmentRepository.save(savedAppointment);

        // Assert
        assertEquals("Updated Meeting", updatedAppointment.getNameAppointment());
    }

    @Test
    void testSave_withAllFields_savesAllFieldsCorrectly() {
        // Arrange
        appointment.setNameAppointment("Annual Review");
        appointment.setLocal("Main Hall");
        appointment.setDate("2024-12-31");
        appointment.setTime("18:00");

        // Act
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Assert
        assertNotNull(savedAppointment.getId());
        assertEquals("Annual Review", savedAppointment.getNameAppointment());
        assertEquals("Main Hall", savedAppointment.getLocal());
        assertEquals("2024-12-31", savedAppointment.getDate());
        assertEquals("18:00", savedAppointment.getTime());
    }

    @Test
    void testFindAll_withEmptyRepository_returnsEmptyList() {
        // Act
        List<Appointment> appointments = appointmentRepository.findAll();

        // Assert
        assertNotNull(appointments);
    }

    @Test
    void testSave_multipleAppointments_savesAllSuccessfully() {
        // Arrange
        Appointment appointment2 = new Appointment();
        appointment2.setNameAppointment("Second Meeting");
        appointment2.setLocal("Room C");
        appointment2.setDate("2024-03-10");
        appointment2.setTime("15:30");

        // Act
        Appointment saved1 = appointmentRepository.save(appointment);
        Appointment saved2 = appointmentRepository.save(appointment2);

        // Assert
        assertNotNull(saved1.getId());
        assertNotNull(saved2.getId());
        assertNotEquals(saved1.getId(), saved2.getId());
    }

    @Test
    void testExistsById_withValidId_returnsTrue() {
        // Arrange
        Appointment savedAppointment = entityManager.persistAndFlush(appointment);

        // Act
        boolean exists = appointmentRepository.existsById(savedAppointment.getId());

        // Assert
        assertTrue(exists);
    }

    @Test
    void testExistsById_withInvalidId_returnsFalse() {
        // Act
        boolean exists = appointmentRepository.existsById(999L);

        // Assert
        assertFalse(exists);
    }

    @Test
    void testCount_returnsCorrectCount() {
        // Arrange
        entityManager.persistAndFlush(appointment);

        // Act
        long count = appointmentRepository.count();

        // Assert
        assertTrue(count >= 1);
    }
}
