package com.ZJHealth.ZJHealth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


@Entity
@Data
@NoArgsConstructor
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int salary;
    private String specialty;
    private String photoPath;


    @JsonBackReference(value = "user_doctor")
    @OneToOne(mappedBy = "doctor")
    private ZJUser user;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference(value = "doctor_lab")
    private List<Lab> labs = new ArrayList<>();

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference(value = "doctor_appt")
    private List<Appointment> appointments = new ArrayList<>();


    public void addLab(Lab lab) {
        labs.add(lab);
    }

    public void addAppointment(Appointment appt) {
        appointments.add(appt);
    }

}
