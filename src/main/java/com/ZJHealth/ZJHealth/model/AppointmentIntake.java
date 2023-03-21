package com.ZJHealth.ZJHealth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class AppointmentIntake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String emergencyFirstName;
    private String emergencyLastName;
    private String emergencyNum;
    private String emergencyRelation;
    private String primarySymptoms;
    private Date symptomOnset;
    private String symptomDesc;
    private String currentTemp;
    private String medicationName;
    private String doseage;
    private String priorCondition;
    private Date diagDate;
    private String allergyName;
    private String allergyDesc;

    @OneToOne
    private Insurance insurance;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference(value = "appt_intake")
    private Appointment appointment;

}
