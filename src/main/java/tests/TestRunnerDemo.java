package tests;

import tests.annotations.*;

import java.lang.reflect.InvocationTargetException;

public class TestRunnerDemo {

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        TestRunner.run(TestRunnerDemo.class);
    }

    @Test
    void test1() {
        System.out.println("test1");
    }

    @BeforeEach
    void BeforeEach1() {
        System.out.println("BeforeEach1");
    }

    @BeforeAll()
    void BeforeAll1() {
        System.out.println("BeforeAll1");
    }
    @BeforeAll()
    void BeforeAll2() {
        System.out.println("BeforeAll2");
    }
    @AfterEach
    void AfterEach1() {
        System.out.println("AfterEach1");
    }
    @AfterEach
    void AfterEach2() {
        System.out.println("AfterEach2");
    }
    @Test
    void test2() {
        System.out.println("test2");
    }
    @BeforeAll()
    void BeforeAll3() {
        System.out.println("BeforeAll3");
    }
    @AfterAll
    void AfterAll1() {
        System.out.println("AfterAll1");
    }


}
