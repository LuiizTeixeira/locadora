package com.LuiizTeixeira.locadora.model;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {

    static Connection connection;

    @BeforeAll
    static void setUpDataBase() throws Exception {
        connection = DriverManager
                .getConnection("jdbc:h2:mem:testdb", "sa", "");
        connection.createStatement().execute("CREATE TABLE users (id INT, name VARCHAR)");

    }

    @BeforeEach
    void insertUserTest() throws Exception {
        connection.createStatement().execute("INSERT INTO users (id, name) VALUES (1,'José')");
    }

    @Test
    void testeUserExists() throws Exception {
        var result = connection
                .createStatement()
                .execute("SELECT * FROM users where id = 1");
        Assertions.assertTrue(result);
    }

    @AfterAll
    static void closeDatdaBase() throws Exception {
        connection.close();
    }

}

