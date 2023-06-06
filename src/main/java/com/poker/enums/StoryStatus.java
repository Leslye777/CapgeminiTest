package com.poker.enums;

public enum StoryStatus {
    PENDING("Pendente"),
    VOTING("Votação"),
    VOTED("Votado");

    private String displayName;

    StoryStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
