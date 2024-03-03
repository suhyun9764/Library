package com.sparta.library.entity.enums;

public enum Language {
    KOR("한국어"),
    ENG("영어"),
    JAP("일본어"),
    CH("중국어");

    private String displayName;
    Language(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
