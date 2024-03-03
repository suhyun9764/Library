package com.sparta.library.entity.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("남"),
    FEMALE("여");

    private String displayName;

    Gender(String displayName) {
        this.displayName = displayName;
    }

}
