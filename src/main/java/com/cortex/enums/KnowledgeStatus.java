package com.cortex.enums;

import lombok.Getter;

@Getter
public enum KnowledgeStatus {
    UNKNOWN("UNKNOWN", 0),
    KNOWN("KNOWN", 1);

    private final String label;  // "UNKNOWN" o "KNOWN"
    private final int value;     // 0 o 1

    KnowledgeStatus(String label, int value) {
        this.label = label;
        this.value = value;
    }

    public static KnowledgeStatus fromInt(int value) {
        return switch (value) {
            case 0 -> UNKNOWN;
            case 1 -> KNOWN;
            default -> throw new IllegalArgumentException("Invalid value for KnowledgeStatus: " + value);
        };
    }

}