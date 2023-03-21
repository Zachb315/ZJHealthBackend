package com.ZJHealth.ZJHealth.dto;

import com.ZJHealth.ZJHealth.model.ZJUser;
import lombok.Data;

@Data
public class DirectoryDto {
    private ZJUser user;
    private byte[] photo;
}
