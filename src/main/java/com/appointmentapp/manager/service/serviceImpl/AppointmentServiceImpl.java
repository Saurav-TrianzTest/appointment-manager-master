package com.appointmentapp.manager.service.serviceImpl;

import java.util.List;

import com.appointmentapp.manager.models.Appointment;
import com.appointmentapp.manager.repository.AppointmentRepository;
import com.appointmentapp.manager.service.AppointmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository repository;

    @Override
    public List<Appointment> findAll() {       
        return repository.findAll();
    }

    @Override
    public Appointment findById(long id) {        
        return repository.findById(id).get();
    }

    @Override
    public Appointment save(Appointment appointment) {      
        return repository.save(appointment);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);
    }

}