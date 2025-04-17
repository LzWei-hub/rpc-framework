package com.ciwei.extension;

import java.time.Period;

/**
 * Create by LzWei on 2025/4/12
 */
public class Holder<T> {
    private volatile T value;

    public T get() {
        return value;
    }

    public void  set(T value) {
        this.value = value;
    }
}
