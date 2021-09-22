package com.itsmv.api.enums;

import lombok.Getter;

@Getter
public class EnumDTO {
    private final String value;
    private final String label;

    public EnumDTO(EnumModel enumModel) {
        this.value = enumModel.getKey();
        this.label = enumModel.getValue();
    }
}
