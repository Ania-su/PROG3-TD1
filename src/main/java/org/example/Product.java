package org.example;

import java.time.Instant;

public class Product {
    private int  id;
    private String name;
    private Instant createDateTime;
    private Category category;

    public String getCategoryName(){
        return category.getName();
    }
}
