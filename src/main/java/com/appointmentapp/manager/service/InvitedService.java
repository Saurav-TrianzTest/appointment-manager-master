package com.appointmentapp.manager.service;

import java.util.List;

import com.appointmentapp.manager.models.Invited;

public interface InvitedService {

    List<Invited> findAll(); 

    Invited findById(long id); 

    Invited save(Invited invited); 

    void delete(long id); 

}