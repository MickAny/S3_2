package tests;

import tests.annotations.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestRunner {

    public static void run(Class<?> testClass) throws InvocationTargetException, IllegalAccessException {

        final Object testObj = initTestObj(testClass);




        for (Method testMethod : testClass.getDeclaredMethods()) {

            if(testMethod.getAnnotation(BeforeAll.class) != null){
                try {
                    testMethod.invoke(testObj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }



        for (Method testMethod : testClass.getDeclaredMethods()) {



            if(testMethod.getAnnotation(Test.class) != null){

                for (Method before: testClass.getDeclaredMethods()) {
                    if(before.getAnnotation(BeforeEach.class) != null){
                        before.invoke(testObj);
                    }
                }

                try {
                    testMethod.invoke(testObj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }

                for (Method after: testClass.getDeclaredMethods()) {
                    if(after.getAnnotation(AfterEach.class) != null){
                        after.invoke(testObj);
                    }
                }
            }

        }



        for (Method testMethod : testClass.getDeclaredMethods()) {

            if(testMethod.getAnnotation(AfterAll.class) != null){
                try {
                    testMethod.invoke(testObj);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }

    private static Object initTestObj(Class<?> testClass) {
        try {
            Constructor<?> noArgsConstructor = testClass.getConstructor();
            return noArgsConstructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Нет конструктора по умолчанию");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException("Не удалось создать объект тест класса");
        }
    }

}
