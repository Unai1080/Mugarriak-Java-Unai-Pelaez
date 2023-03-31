package com.frogak;

import com.example.demo.service.SmartPhoneService;
import com.example.demo.service.SmartPhoneServiceImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class Test {
    @org.junit.jupiter.api.Test
    @DisplayName("Nire lehenengo testa")
    void name() {
        SmartPhoneService service = new SmartPhoneServiceImpl();
        int count = service.count();

        assertAll(
                () -> assertNotNull(count),
                () -> assertTrue(count > 0),
                () -> assertEquals(3, count),
                () -> assertEquals(4, count, "Hiru espero zen")
        );
    }

    @org.junit.jupiter.api.Test
    @DisplayName("Nire bigarren testa")
    void name2() {
        SmartPhoneService service = new SmartPhoneServiceImpl();

        assertAll(
                () -> assertThrows(IllegalArgumentException.class, () -> service.findOne(null))

        );
    }

        @BeforeEach
        void setUp() {
            System.out.println("Kaixo");
        }

        @AfterEach
        void tearDown() {
            System.out.println("Agur");
        }

        @org.junit.jupiter.api.Test
        void test1() {
            System.out.println("Hau da test1");
        }

        @org.junit.jupiter.api.Test
        void test2() {
            System.out.println("Hau da test2");
        }
}


