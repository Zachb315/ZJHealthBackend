package com.ZJHealth.ZJHealth.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int quantity;
    private String pharmacy;
    private String condition;
    @ManyToOne
    private ZJUser doctor;
    @ManyToOne
    private ZJUser patient;

}
