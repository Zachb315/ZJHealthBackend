package com.ZJHealth.ZJHealth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Treatment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String treatmentName;
    private String treatmentReason;

    @ManyToOne
    private Appointment appointment;

    @ManyToOne
    @JsonBackReference(value = "record_treatment")
    private MedicalRecord record;
}
