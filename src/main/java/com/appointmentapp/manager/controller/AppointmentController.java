package com.appointmentapp.manager.controller;

import java.util.List;

import javax.validation.Valid;

import com.appointmentapp.manager.models.Appointment;
import com.appointmentapp.manager.models.Invited;
import com.appointmentapp.manager.repository.InvitedRepository;
import com.appointmentapp.manager.service.AppointmentService;
import com.appointmentapp.manager.service.InvitedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AppointmentController {
    @Autowired
    AppointmentService appointService;
    @Autowired
    InvitedService invitService;
    @Autowired
    InvitedRepository invitRepository;

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String newAppmt() {
        return "formAppointment";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView listAll(Appointment appointment) {
        ModelAndView mv = new ModelAndView("appointmentsList");
        List<Appointment> appointments = appointService.findAll();
        mv.addObject("appointments", appointments);
        return mv;
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String postAppmt(@Valid Appointment appointment, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Please, check all fields!");
            return "redirect:/new";
        }
        appointService.save(appointment);
        attributes.addFlashAttribute("messageSuccess", "Appointment saved with success!");
        return "redirect:/new";
    }

    @RequestMapping(value = "details/{id}", method = RequestMethod.GET)
    public ModelAndView Details(@PathVariable("id") long id) {
        Appointment appointment = appointService.findById(id);
        ModelAndView mv = new ModelAndView("appointmentDetails");
        Iterable<Invited> inviteds = invitRepository.findByAppointment(appointment);
        mv.addObject("appointment", appointment);
        mv.addObject("inviteds", inviteds);
        return mv;
    }

    @RequestMapping(value = "details/{id}", method = RequestMethod.POST)
    public String setInviteds(@PathVariable("id") long id, @Valid Invited invited, BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("message", "Please, check all fields!");
            return "redirect:/details/{id}";
        }
        Appointment appt = appointService.findById(id);
        invited.setAppointment(appt);
        invitService.save(invited);
        attributes.addFlashAttribute("messageSucces", "Invited added");
        return "redirect:/details/{id}";
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deleteAppointment(@PathVariable("id") long id) {
        appointService.delete(id);
        return "redirect:/list";
    }

    @RequestMapping(value = "/details/remove/{id}", method = RequestMethod.GET)
    public String removeInvited(@PathVariable("id") long id, Appointment appt, Invited invited, RedirectAttributes attributes) {
        invitService.delete(id);
        attributes.addFlashAttribute("message", "Invited removed with success!");
        return "redirect:/list";
    }

}