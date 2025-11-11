package com.appointmentapp.manager.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name = "tb_appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;  

    @NotBlank
    private String nameAppointment;
    @NotBlank
    private String local;
    @NotBlank
    private String date;
    @NotBlank
    private String time;

    @OneToMany
    private List<Invited> inited;


    public String getNameAppointment() {
        return this.nameAppointment;
    }

    public void setNameAppointment(String nameAppointment) {
        this.nameAppointment = nameAppointment;
    }

    public String getLocal() {
        return this.local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Invited> getInited() {
        return this.inited;
    }

    public void setInited(List<Invited> inited) {
        this.inited = inited;
    }

}