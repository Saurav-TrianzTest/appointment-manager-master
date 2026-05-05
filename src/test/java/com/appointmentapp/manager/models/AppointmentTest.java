package com.appointmentapp.manager.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class AppointmentTest {

    private Appointment appointment;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
    }

    @Test
    void testGetId_returnsId() {
        // Arrange
        long expectedId = 1L;
        appointment.setId(expectedId);

        // Act
        long actualId = appointment.getId();

        // Assert
        assertEquals(expectedId, actualId);
    }

    @Test
    void testSetId_setsIdCorrectly() {
        // Arrange
        long expectedId = 100L;

        // Act
        appointment.setId(expectedId);

        // Assert
        assertEquals(expectedId, appointment.getId());
    }

    @Test
    void testGetNameAppointment_returnsName() {
        // Arrange
        String expectedName = "Team Meeting";
        appointment.setNameAppointment(expectedName);

        // Act
        String actualName = appointment.getNameAppointment();

        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void testSetNameAppointment_setsNameCorrectly() {
        // Arrange
        String expectedName = "Project Review";

        // Act
        appointment.setNameAppointment(expectedName);

        // Assert
        assertEquals(expectedName, appointment.getNameAppointment());
    }

    @Test
    void testGetLocal_returnsLocal() {
        // Arrange
        String expectedLocal = "Conference Room A";
        appointment.setLocal(expectedLocal);

        // Act
        String actualLocal = appointment.getLocal();

        // Assert
        assertEquals(expectedLocal, actualLocal);
    }

    @Test
    void testSetLocal_setsLocalCorrectly() {
        // Arrange
        String expectedLocal = "Office Building 2";

        // Act
        appointment.setLocal(expectedLocal);

        // Assert
        assertEquals(expectedLocal, appointment.getLocal());
    }

    @Test
    void testGetDate_returnsDate() {
        // Arrange
        String expectedDate = "2024-01-15";
        appointment.setDate(expectedDate);

        // Act
        String actualDate = appointment.getDate();

        // Assert
        assertEquals(expectedDate, actualDate);
    }

    @Test
    void testSetDate_setsDateCorrectly() {
        // Arrange
        String expectedDate = "2024-12-31";

        // Act
        appointment.setDate(expectedDate);

        // Assert
        assertEquals(expectedDate, appointment.getDate());
    }

    @Test
    void testGetTime_returnsTime() {
        // Arrange
        String expectedTime = "14:30";
        appointment.setTime(expectedTime);

        // Act
        String actualTime = appointment.getTime();

        // Assert
        assertEquals(expectedTime, actualTime);
    }

    @Test
    void testSetTime_setsTimeCorrectly() {
        // Arrange
        String expectedTime = "09:00";

        // Act
        appointment.setTime(expectedTime);

        // Assert
        assertEquals(expectedTime, appointment.getTime());
    }

    @Test
    void testGetInited_returnsInvitedList() {
        // Arrange
        List<Invited> expectedList = new ArrayList<>();
        Invited invited = new Invited();
        invited.setInvitedName("John Doe");
        expectedList.add(invited);
        appointment.setInited(expectedList);

        // Act
        List<Invited> actualList = appointment.getInited();

        // Assert
        assertNotNull(actualList);
        assertEquals(1, actualList.size());
        assertEquals("John Doe", actualList.get(0).getInvitedName());
    }

    @Test
    void testSetInited_setsInvitedListCorrectly() {
        // Arrange
        List<Invited> expectedList = new ArrayList<>();
        Invited invited1 = new Invited();
        invited1.setInvitedName("Jane Smith");
        Invited invited2 = new Invited();
        invited2.setInvitedName("Bob Johnson");
        expectedList.add(invited1);
        expectedList.add(invited2);

        // Act
        appointment.setInited(expectedList);

        // Assert
        assertEquals(2, appointment.getInited().size());
    }

    @Test
    void testAppointment_defaultConstructor_createsInstance() {
        // Act
        Appointment newAppointment = new Appointment();

        // Assert
        assertNotNull(newAppointment);
    }

    @Test
    void testAppointment_withNullInvitedList_handlesNull() {
        // Act
        appointment.setInited(null);

        // Assert
        assertNull(appointment.getInited());
    }

    @Test
    void testAppointment_withEmptyInvitedList_handlesEmpty() {
        // Arrange
        List<Invited> emptyList = new ArrayList<>();

        // Act
        appointment.setInited(emptyList);

        // Assert
        assertNotNull(appointment.getInited());
        assertTrue(appointment.getInited().isEmpty());
    }

    @Test
    void testAppointment_allFieldsSet_returnsAllValues() {
        // Arrange
        appointment.setId(1L);
        appointment.setNameAppointment("Annual Review");
        appointment.setLocal("Main Office");
        appointment.setDate("2024-06-15");
        appointment.setTime("10:00");
        List<Invited> invitedList = new ArrayList<>();
        appointment.setInited(invitedList);

        // Assert
        assertEquals(1L, appointment.getId());
        assertEquals("Annual Review", appointment.getNameAppointment());
        assertEquals("Main Office", appointment.getLocal());
        assertEquals("2024-06-15", appointment.getDate());
        assertEquals("10:00", appointment.getTime());
        assertNotNull(appointment.getInited());
    }
}
