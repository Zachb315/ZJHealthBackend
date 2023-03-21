package com.ZJHealth.ZJHealth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String diagnosisName;

    @OneToOne
    @JsonManagedReference(value = "dia_appt")
    private Appointment appointment;

    @ManyToOne
    @JsonBackReference(value = "record_diag")
    private MedicalRecord record;

}
