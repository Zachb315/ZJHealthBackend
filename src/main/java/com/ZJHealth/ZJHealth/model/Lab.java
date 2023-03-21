package com.ZJHealth.ZJHealth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Lab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String testName;
    private LocalDateTime date;
    private String result;
    @ManyToOne
    @JsonBackReference(value = "doctor_lab")
    private Doctor doctor;

    @ManyToOne
    @JsonIgnore
    private ZJUser patient;

    @ManyToOne
    @JsonBackReference(value = "record_lab")
    private MedicalRecord record;


}
