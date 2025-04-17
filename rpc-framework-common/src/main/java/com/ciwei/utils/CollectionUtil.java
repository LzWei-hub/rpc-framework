package com.ciwei.utils;

import java.util.Collection;

/**
 * Create by LzWei on 2025/4/12
 */
public class CollectionUtil {
    public static boolean isEmpty(Collection<?> c) {
        return c == null || c.isEmpty();
    }
}
