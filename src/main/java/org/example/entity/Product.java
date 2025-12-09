package org.example.entity;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class Product {
    private int id;
    private String name;
    private Instant createDateTime;
    private Category category;

    public String getCategoryName(){
        return category.getName();
    }
}
