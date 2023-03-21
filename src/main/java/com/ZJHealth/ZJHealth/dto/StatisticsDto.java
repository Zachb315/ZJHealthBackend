package com.ZJHealth.ZJHealth.dto;

import com.ZJHealth.ZJHealth.model.Diagnosis;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StatisticsDto {
    private List<String> states = new ArrayList<>();
    private List<Integer> ages = new ArrayList<>();
    private List<String> diagnoses = new ArrayList<>();


    public void addState(String state) {
        states.add(state);
    }

    public void addAge(Integer age) {
        ages.add(age);
    }

    public void addDiagnosis(String diagnosis) {
        diagnoses.add(diagnosis);
    }
}
