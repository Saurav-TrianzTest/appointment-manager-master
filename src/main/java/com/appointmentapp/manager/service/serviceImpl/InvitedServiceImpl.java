package com.appointmentapp.manager.service.serviceImpl;

import java.util.List;

import com.appointmentapp.manager.models.Invited;
import com.appointmentapp.manager.repository.InvitedRepository;
import com.appointmentapp.manager.service.InvitedService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvitedServiceImpl implements InvitedService {

    @Autowired
    InvitedRepository repository; 

    @Override
    public List<Invited> findAll() {        
        return repository.findAll();
    }

    @Override
    public Invited findById(long id) {
        return repository.findById(id).get();
    }

    @Override
    public Invited save(Invited invited) {        
        return repository.save(invited);
    }

    @Override
    public void delete(long id) {
        repository.deleteById(id);

    }

}