package com.appointmentapp.manager.service;

import java.util.List;

import com.appointmentapp.manager.models.Appointment;

public interface AppointmentService {

    List<Appointment> findAll(); 

    Appointment findById(long id); 

    Appointment save(Appointment appointment); 

    void delete(long id); 

}