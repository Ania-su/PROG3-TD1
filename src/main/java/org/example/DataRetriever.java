package org.example;

import org.example.entity.Category;
import org.example.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {

    private final DBConnection dbconnection;

    public DataRetriever(DBConnection dbconnection) {
        this.dbconnection = dbconnection;
    }

    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT id, name FROM product_category";

        try (Connection conn = dbconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    public List<Product> getProductList( int page, int size) {
        List<Product> products = new ArrayList<>();

        int offset = (page - 1) * size;

        String sql =  """
        SELECT 
            p.id AS product_id,
            p.name AS product_name,
            p.price AS product_price,
            p.creation_datetime AS creation_datetime,
            c.id AS category_id,
            c.name AS category_name
        FROM product p
        JOIN product_category c ON p.id = c.product_id
        ORDER BY p.id
        LIMIT ? OFFSET ?
        """;

        try (Connection connection = dbconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, size);
            stmt.setInt(2, offset);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category(
                            rs.getInt("category_id"),
                            rs.getString("category_name")
                    );

                    Product product = new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getTimestamp("creation_datetime").toInstant(),
                            category
                    );

                    products.add(product);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

//    public List<Product> getProductByCriteria(String productName, String categoryName, Instant creationMin, Instant creationMax) {
//
//    }
}
