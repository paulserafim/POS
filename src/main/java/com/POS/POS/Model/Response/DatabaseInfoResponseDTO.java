package com.POS.POS.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatabaseInfoResponseDTO {

    private String name;
    private String inquiry;
    private boolean value;
    private long size;

    public DatabaseInfoResponseDTO(String name, long size) {
        this.name = name;
        this.size = size;
    }

    public DatabaseInfoResponseDTO(String inquiry, boolean value) {
        this.inquiry = inquiry;
        this.value = value;
    }
}
