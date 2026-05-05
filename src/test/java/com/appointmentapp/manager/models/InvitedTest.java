package com.appointmentapp.manager.models;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class InvitedTest {

    private Invited invited;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        invited = new Invited();
        appointment = new Appointment();
    }

    @Test
    void testGetId_returnsId() {
        // Arrange
        long expectedId = 1L;
        invited.setId(expectedId);

        // Act
        long actualId = invited.getId();

        // Assert
        assertEquals(expectedId, actualId);
    }

    @Test
    void testSetId_setsIdCorrectly() {
        // Arrange
        long expectedId = 50L;

        // Act
        invited.setId(expectedId);

        // Assert
        assertEquals(expectedId, invited.getId());
    }

    @Test
    void testGetInvitedName_returnsName() {
        // Arrange
        String expectedName = "John Doe";
        invited.setInvitedName(expectedName);

        // Act
        String actualName = invited.getInvitedName();

        // Assert
        assertEquals(expectedName, actualName);
    }

    @Test
    void testSetInvitedName_setsNameCorrectly() {
        // Arrange
        String expectedName = "Jane Smith";

        // Act
        invited.setInvitedName(expectedName);

        // Assert
        assertEquals(expectedName, invited.getInvitedName());
    }

    @Test
    void testGetCellNumber_returnsCellNumber() {
        // Arrange
        String expectedCellNumber = "555-1234";
        invited.setCellNumber(expectedCellNumber);

        // Act
        String actualCellNumber = invited.getCellNumber();

        // Assert
        assertEquals(expectedCellNumber, actualCellNumber);
    }

    @Test
    void testSetCellNumber_setsCellNumberCorrectly() {
        // Arrange
        String expectedCellNumber = "555-9876";

        // Act
        invited.setCellNumber(expectedCellNumber);

        // Assert
        assertEquals(expectedCellNumber, invited.getCellNumber());
    }

    @Test
    void testGetAppointment_returnsAppointment() {
        // Arrange
        appointment.setId(1L);
        appointment.setNameAppointment("Team Meeting");
        invited.setAppointment(appointment);

        // Act
        Appointment actualAppointment = invited.getAppointment();

        // Assert
        assertNotNull(actualAppointment);
        assertEquals(1L, actualAppointment.getId());
        assertEquals("Team Meeting", actualAppointment.getNameAppointment());
    }

    @Test
    void testSetAppointment_setsAppointmentCorrectly() {
        // Arrange
        appointment.setId(2L);
        appointment.setNameAppointment("Project Review");

        // Act
        invited.setAppointment(appointment);

        // Assert
        assertNotNull(invited.getAppointment());
        assertEquals(2L, invited.getAppointment().getId());
    }

    @Test
    void testInvited_defaultConstructor_createsInstance() {
        // Act
        Invited newInvited = new Invited();

        // Assert
        assertNotNull(newInvited);
    }

    @Test
    void testInvited_withNullAppointment_handlesNull() {
        // Act
        invited.setAppointment(null);

        // Assert
        assertNull(invited.getAppointment());
    }

    @Test
    void testInvited_allFieldsSet_returnsAllValues() {
        // Arrange
        invited.setId(10L);
        invited.setInvitedName("Alice Johnson");
        invited.setCellNumber("555-4321");
        appointment.setId(5L);
        invited.setAppointment(appointment);

        // Assert
        assertEquals(10L, invited.getId());
        assertEquals("Alice Johnson", invited.getInvitedName());
        assertEquals("555-4321", invited.getCellNumber());
        assertNotNull(invited.getAppointment());
        assertEquals(5L, invited.getAppointment().getId());
    }

    @Test
    void testSetInvitedName_withEmptyString_setsEmptyString() {
        // Arrange
        String emptyName = "";

        // Act
        invited.setInvitedName(emptyName);

        // Assert
        assertEquals("", invited.getInvitedName());
    }

    @Test
    void testSetCellNumber_withEmptyString_setsEmptyString() {
        // Arrange
        String emptyCellNumber = "";

        // Act
        invited.setCellNumber(emptyCellNumber);

        // Assert
        assertEquals("", invited.getCellNumber());
    }

    @Test
    void testInvited_withLongName_handlesLongString() {
        // Arrange
        String longName = "A".repeat(100);

        // Act
        invited.setInvitedName(longName);

        // Assert
        assertEquals(100, invited.getInvitedName().length());
    }

    @Test
    void testInvited_withSpecialCharactersInName_handlesSpecialCharacters() {
        // Arrange
        String specialName = "John O'Brien-Smith";

        // Act
        invited.setInvitedName(specialName);

        // Assert
        assertEquals(specialName, invited.getInvitedName());
    }

    @Test
    void testInvited_withInternationalPhoneNumber_handlesInternationalFormat() {
        // Arrange
        String internationalNumber = "+1-555-123-4567";

        // Act
        invited.setCellNumber(internationalNumber);

        // Assert
        assertEquals(internationalNumber, invited.getCellNumber());
    }
}
