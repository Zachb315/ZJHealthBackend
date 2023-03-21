package com.ZJHealth.ZJHealth.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jdk.jshell.Diag;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "record")
    @JsonManagedReference(value = "record_diag")
    private List<Diagnosis> diagnosis = new ArrayList<>();

    @OneToMany(mappedBy = "record")
    @JsonManagedReference(value = "record_treatment")
    private List<Treatment> treatment = new ArrayList<>();

    @OneToMany(mappedBy = "record")
    private List<Lab> labs = new ArrayList<>();

    @OneToOne
    @JsonBackReference(value = "user_record")
    private ZJUser patient;


    public void addTreatment(Treatment newTreatment) {
        treatment.add(newTreatment);
    }

    public void addDiagnosis(Diagnosis newDiagnosis) {
        diagnosis.add(newDiagnosis);
    }

    public void addLab(Lab newLab) {
        labs.add(newLab);
    }

}
