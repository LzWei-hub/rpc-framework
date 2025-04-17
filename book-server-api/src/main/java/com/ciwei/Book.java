package com.ciwei;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Create by LzWei on 2025/4/9
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book implements Serializable {
    private String name;
    private String author;
}
