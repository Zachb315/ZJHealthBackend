package com.ZJHealth.ZJHealth.dto;

import com.ZJHealth.ZJHealth.model.Insurance;
import com.ZJHealth.ZJHealth.model.ZJUser;
import lombok.Data;

@Data
public class RegistrationDto {
    private ZJUser user;
    private Insurance insurance;

}
