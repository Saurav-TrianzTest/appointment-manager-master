package com.appointmentapp.manager.controller;

import com.appointmentapp.manager.models.Appointment;
import com.appointmentapp.manager.models.Invited;
import com.appointmentapp.manager.repository.InvitedRepository;
import com.appointmentapp.manager.service.AppointmentService;
import com.appointmentapp.manager.service.InvitedService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentControllerTest {

    @Mock
    private AppointmentService appointService;

    @Mock
    private InvitedService invitService;

    @Mock
    private InvitedRepository invitRepository;

    @Mock
    private BindingResult bindingResult;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private AppointmentController appointmentController;

    private Appointment appointment;
    private Invited invited;
    private List<Appointment> appointments;
    private List<Invited> invitedList;

    @BeforeEach
    void setUp() {
        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setNameAppointment("Team Meeting");
        appointment.setLocal("Conference Room");
        appointment.setDate("2024-01-15");
        appointment.setTime("14:00");

        invited = new Invited();
        invited.setId(1L);
        invited.setInvitedName("John Doe");
        invited.setCellNumber("555-1234");
        invited.setAppointment(appointment);

        appointments = new ArrayList<>();
        appointments.add(appointment);

        invitedList = new ArrayList<>();
        invitedList.add(invited);
    }

    @Test
    void testNewAppmt_returnsFormAppointmentView() {
        // Act
        String viewName = appointmentController.newAppmt();

        // Assert
        assertEquals("formAppointment", viewName);
    }

    @Test
    void testListAll_returnsModelAndViewWithAppointments() {
        // Arrange
        when(appointService.findAll()).thenReturn(appointments);

        // Act
        ModelAndView result = appointmentController.listAll(appointment);

        // Assert
        assertNotNull(result);
        assertEquals("appointmentsList", result.getViewName());
        assertNotNull(result.getModel().get("appointments"));
        verify(appointService, times(1)).findAll();
    }

    @Test
    void testListAll_withEmptyList_returnsEmptyList() {
        // Arrange
        when(appointService.findAll()).thenReturn(new ArrayList<>());

        // Act
        ModelAndView result = appointmentController.listAll(appointment);

        // Assert
        assertNotNull(result);
        assertEquals("appointmentsList", result.getViewName());
        verify(appointService, times(1)).findAll();
    }

    @Test
    void testPostAppmt_withValidAppointment_savesAndRedirects() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(appointService.save(appointment)).thenReturn(appointment);

        // Act
        String result = appointmentController.postAppmt(appointment, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/new", result);
        verify(appointService, times(1)).save(appointment);
        verify(redirectAttributes, times(1)).addFlashAttribute("messageSuccess", "Appointment saved with success!");
    }

    @Test
    void testPostAppmt_withValidationErrors_redirectsWithErrorMessage() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = appointmentController.postAppmt(appointment, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/new", result);
        verify(appointService, never()).save(any());
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Please, check all fields!");
    }

    @Test
    void testPostAppmt_withNullAppointment_handlesValidationError() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = appointmentController.postAppmt(null, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/new", result);
        verify(appointService, never()).save(any());
    }

    @Test
    void testDetails_withValidId_returnsDetailsView() {
        // Arrange
        when(appointService.findById(1L)).thenReturn(appointment);
        when(invitRepository.findByAppointment(appointment)).thenReturn(invitedList);

        // Act
        ModelAndView result = appointmentController.Details(1L);

        // Assert
        assertNotNull(result);
        assertEquals("appointmentDetails", result.getViewName());
        assertNotNull(result.getModel().get("appointment"));
        assertNotNull(result.getModel().get("inviteds"));
        verify(appointService, times(1)).findById(1L);
        verify(invitRepository, times(1)).findByAppointment(appointment);
    }

    @Test
    void testDetails_withInvalidId_handlesNull() {
        // Arrange
        when(appointService.findById(999L)).thenReturn(null);
        when(invitRepository.findByAppointment(null)).thenReturn(new ArrayList<>());

        // Act
        ModelAndView result = appointmentController.Details(999L);

        // Assert
        assertNotNull(result);
        assertEquals("appointmentDetails", result.getViewName());
        verify(appointService, times(1)).findById(999L);
    }

    @Test
    void testSetInviteds_withValidInvited_savesAndRedirects() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(appointService.findById(1L)).thenReturn(appointment);
        when(invitService.save(invited)).thenReturn(invited);

        // Act
        String result = appointmentController.setInviteds(1L, invited, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/details/{id}", result);
        verify(appointService, times(1)).findById(1L);
        verify(invitService, times(1)).save(invited);
        verify(redirectAttributes, times(1)).addFlashAttribute("messageSucces", "Invited added");
    }

    @Test
    void testSetInviteds_withValidationErrors_redirectsWithErrorMessage() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(true);

        // Act
        String result = appointmentController.setInviteds(1L, invited, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/details/{id}", result);
        verify(invitService, never()).save(any());
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Please, check all fields!");
    }

    @Test
    void testSetInviteds_associatesInvitedWithAppointment() {
        // Arrange
        when(bindingResult.hasErrors()).thenReturn(false);
        when(appointService.findById(1L)).thenReturn(appointment);
        when(invitService.save(invited)).thenReturn(invited);

        // Act
        appointmentController.setInviteds(1L, invited, bindingResult, redirectAttributes);

        // Assert
        assertEquals(appointment, invited.getAppointment());
        verify(invitService, times(1)).save(invited);
    }

    @Test
    void testDeleteAppointment_withValidId_deletesAndRedirects() {
        // Arrange
        doNothing().when(appointService).delete(1L);

        // Act
        String result = appointmentController.deleteAppointment(1L);

        // Assert
        assertEquals("redirect:/list", result);
        verify(appointService, times(1)).delete(1L);
    }

    @Test
    void testDeleteAppointment_withDifferentId_deletesCorrectAppointment() {
        // Arrange
        doNothing().when(appointService).delete(5L);

        // Act
        String result = appointmentController.deleteAppointment(5L);

        // Assert
        assertEquals("redirect:/list", result);
        verify(appointService, times(1)).delete(5L);
    }

    @Test
    void testRemoveInvited_withValidId_removesAndRedirects() {
        // Arrange
        doNothing().when(invitService).delete(1L);

        // Act
        String result = appointmentController.removeInvited(1L, appointment, invited, redirectAttributes);

        // Assert
        assertEquals("redirect:/list", result);
        verify(invitService, times(1)).delete(1L);
        verify(redirectAttributes, times(1)).addFlashAttribute("message", "Invited removed with success!");
    }

    @Test
    void testRemoveInvited_withDifferentId_removesCorrectInvited() {
        // Arrange
        doNothing().when(invitService).delete(10L);

        // Act
        String result = appointmentController.removeInvited(10L, appointment, invited, redirectAttributes);

        // Assert
        assertEquals("redirect:/list", result);
        verify(invitService, times(1)).delete(10L);
    }

    @Test
    void testListAll_withMultipleAppointments_returnsAllAppointments() {
        // Arrange
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        appointment2.setNameAppointment("Project Review");
        appointments.add(appointment2);
        when(appointService.findAll()).thenReturn(appointments);

        // Act
        ModelAndView result = appointmentController.listAll(appointment);

        // Assert
        assertNotNull(result);
        assertEquals("appointmentsList", result.getViewName());
        verify(appointService, times(1)).findAll();
    }

    @Test
    void testDetails_withMultipleInviteds_returnsAllInviteds() {
        // Arrange
        Invited invited2 = new Invited();
        invited2.setId(2L);
        invited2.setInvitedName("Jane Smith");
        invitedList.add(invited2);
        when(appointService.findById(1L)).thenReturn(appointment);
        when(invitRepository.findByAppointment(appointment)).thenReturn(invitedList);

        // Act
        ModelAndView result = appointmentController.Details(1L);

        // Assert
        assertNotNull(result);
        assertEquals("appointmentDetails", result.getViewName());
        verify(invitRepository, times(1)).findByAppointment(appointment);
    }

    @Test
    void testPostAppmt_savesAppointmentWithAllFields() {
        // Arrange
        appointment.setNameAppointment("Annual Meeting");
        appointment.setLocal("Main Hall");
        appointment.setDate("2024-12-31");
        appointment.setTime("18:00");
        when(bindingResult.hasErrors()).thenReturn(false);
        when(appointService.save(appointment)).thenReturn(appointment);

        // Act
        String result = appointmentController.postAppmt(appointment, bindingResult, redirectAttributes);

        // Assert
        assertEquals("redirect:/new", result);
        verify(appointService, times(1)).save(appointment);
    }
}
