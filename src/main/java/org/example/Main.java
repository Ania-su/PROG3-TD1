package org.example;

import org.example.entity.Category;
import org.example.entity.Product;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        DataRetriever datas = new DataRetriever(new DBConnection());

        List<Category> categories = datas.getCategories();
        System.out.println("-Categories :");
        categories.forEach(System.out::println);

        List<Product> productPage1 = datas.getProductList(1,10);
        List<Product> productPage2 = datas.getProductList(1,5);
        List<Product> productPage3 = datas.getProductList(1,3);
        List<Product> productPage4 = datas.getProductList(2,2);

        System.out.println("\n-Products :");
        productPage1.forEach(System.out::println);
        productPage2.forEach(System.out::println);
        productPage3.forEach(System.out::println);
        productPage4.forEach(System.out::println);

        List<Product> filteredProduct1 = datas.getProductsByCriteria(
                "Dell",
                null,
                null,
                null
        );
        List<Product> filteredProduct2 = datas.getProductsByCriteria(
                null,
                "info",
                null,
                null
        );
        List<Product> filteredProduct3 = datas.getProductsByCriteria(
                "iPhone",
                "mobile",
                null,
                null
        );
        List<Product> filteredProduct4 = datas.getProductsByCriteria(
                null,
                null,
                Instant.parse("2024-02-01T00:00:00Z"),
                Instant.parse("2024-03-01T00:00:00Z")
        );
        List<Product> filteredProduct5 = datas.getProductsByCriteria(
                "Samsung",
                "bureau",
                null,
                null
        );
        List<Product> filteredProduct6 = datas.getProductsByCriteria(
                "Sony",
                "informatique",
                null,
                null
        );
        List<Product> filteredProduct7 = datas.getProductsByCriteria(
                null,
                "audio",
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2024-12-01T00:00:00Z")
        );

        System.out.println("\n-Filtered Products :");
        filteredProduct1.forEach(System.out::println);
        filteredProduct2.forEach(System.out::println);
        filteredProduct3.forEach(System.out::println);
        filteredProduct4.forEach(System.out::println);
        filteredProduct5.forEach(System.out::println);
        filteredProduct6.forEach(System.out::println);
        filteredProduct7.forEach(System.out::println);

        List<Product> filterPagedProduct1 = datas.getProductsByCriteria(
                null,
                null,
                null,
                null,
                1,
                10
        );
        List<Product> filterPagedProduct2 = datas.getProductsByCriteria(
                "Dell",
                null,
                null,
                null,
                1,
                5
        );
        List<Product> filterPaged3 = datas.getProductsByCriteria(
                null,
                "informatique",
                null,
                null,
                1,
                10
        );

        System.out.println("\n-Filtered Products with pagination :");
        filterPagedProduct1.forEach(System.out::println);
        filterPagedProduct2.forEach(System.out::println);
        filteredProduct3.forEach(System.out::println);

    }
}