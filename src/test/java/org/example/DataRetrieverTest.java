package org.example;

import org.example.entity.Category;
import org.example.entity.Product;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.time.Instant;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class DataRetrieverTest {

    private DBConnection dbConnection;
    private DataRetriever dataRetriever;

    private Connection connection;
    private PreparedStatement statement;
    private ResultSet resultSet;


    @Before
    public void setUp() throws Exception {
        dbConnection = mock(DBConnection.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        resultSet = mock(ResultSet.class);

        when(dbConnection.getConnection()).thenReturn(connection);

        dataRetriever = new DataRetriever(dbConnection);
    }

    @Test
    public void getCategories() throws SQLException {
        when(connection.prepareStatement("SELECT id, name FROM product_category"))
                .thenReturn(statement);

        when(statement.executeQuery()).thenReturn(resultSet);

        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("name")).thenReturn("Informatique", "Audio");

        List<Category> list = dataRetriever.getCategories();

        assertEquals(2, list.size());
        assertEquals("Informatique", list.get(0).getName());
        assertEquals("Audio", list.get(1).getName());
    }

    @Test
    public void getProductList() throws SQLException {

        String sql = """
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

        when(connection.prepareStatement(sql)).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);

        Timestamp now = Timestamp.from(Instant.now());

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("product_id")).thenReturn(1);
        when(resultSet.getString("product_name")).thenReturn("Laptop Dell XPS");
        when(resultSet.getTimestamp("creation_datetime")).thenReturn(now);
        when(resultSet.getInt("category_id")).thenReturn(1);
        when(resultSet.getString("category_name")).thenReturn("Informatique");

        List<Product> list = dataRetriever.getProductList(1, 5);

        assertEquals(1, list.size());
        assertEquals("Laptop Dell XPS", list.get(0).getName());
        assertEquals("Informatique", list.get(0).getCategory().getName());
    }

    @Test
    public void getProductsByCriteria() throws SQLException {
        String expectedSql =
                "SELECT p.id, p.name, p.price, p.creation_datetime," +
                        "               c.id AS category_id, c.name AS category_name" +
                        "        FROM product p" +
                        "        JOIN product_category c ON c.product_id = p.id" +
                        "        WHERE 1=1  AND p.name ILIKE ?  AND c.name ILIKE ? ";

        when(connection.prepareStatement(anyString())).thenReturn(statement);

        when(statement.executeQuery()).thenReturn(resultSet);

        Timestamp now = Timestamp.from(Instant.now());

        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt("id")).thenReturn(4);
        when(resultSet.getString("name")).thenReturn("Clavier Logitech");
        when(resultSet.getTimestamp("creation_datetime")).thenReturn(now);
        when(resultSet.getInt("category_id")).thenReturn(1);
        when(resultSet.getString("category_name")).thenReturn("Accessoires");

        List<Product> products = dataRetriever.getProductsByCriteria(
                "Clavier", "Accessoires", null, null
        );

        assertEquals(1, products.size());
        assertEquals("Clavier Logitech", products.get(0).getName());
        assertEquals("Accessoires", products.get(0).getCategory().getName());
    }
}