package com.ciwei.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Create by LzWei on 2025/4/15
 */
@AllArgsConstructor
@Getter
public enum SerializationTypeEnum {
    KRYO((byte) 0x01,"kryo");

    private final byte code;
    private final String name;

    public static String getName(byte code) {
        for (SerializationTypeEnum c : SerializationTypeEnum.values()) {
            if (c.getCode() == code) {
                return c.name;
            }
        }
        return null;
    }
}
