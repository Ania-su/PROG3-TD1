package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class Product {
    private int  id;
    private String name;
    private Instant createDateTime;
    private Category category;

    public String getCategoryName(){
        return category.getName();
    }
}
