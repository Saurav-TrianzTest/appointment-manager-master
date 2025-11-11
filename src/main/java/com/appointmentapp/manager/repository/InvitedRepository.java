package com.appointmentapp.manager.repository;

import com.appointmentapp.manager.models.Appointment;
import com.appointmentapp.manager.models.Invited;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitedRepository extends JpaRepository<Invited, Long> {

    Iterable<Invited> findByAppointment(Appointment appointment); 
    
}