package com.frogak;
import org.junit.jupiter.api.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Test2 {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Kaixo");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Agur");
    }

    @org.junit.jupiter.api.Test
    @Order(2)
    void test1() {
        System.out.println("Hau da test1");
    }

    @org.junit.jupiter.api.Test
    @Order(1)
    void test2() {
        System.out.println("Hau da test2");
    }

}
