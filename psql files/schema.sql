--create product table:
CREATE TABLE product(
    id INT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    price DECIMAL NOT NULL,
    creation_datetime TIMESTAMP
);

--create product_category table:
create table product_category(
    id INT PRIMARY KEY UNIQUE ,
    name VARCHAR(200) NOT NULL ,
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES product(id)
);