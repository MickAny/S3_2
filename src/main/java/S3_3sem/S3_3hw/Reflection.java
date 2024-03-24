package S3_3sem.S3_3hw;

import S3_3sem.S3_3hw.annotations2.Column;
import S3_3sem.S3_3hw.annotations2.ID;
import S3_3sem.S3_3hw.annotations2.Table;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reflection {

    public void createTable(Class<?> testClass) throws SQLException {
        final Object testObj = initTestObj(testClass);


        String tableName = testObj.getClass().getAnnotation(Table.class).name();
        String idField = null;
        String nameField = null;

        ArrayList<Person> people = new ArrayList<>(List.of(
                new Person(1, "Egor1"),
                new Person(2, "Egor2"),
                new Person(3, "Egor3"),
                new Person(4, "Egor4")
        ));


        for (Field field : testClass.getDeclaredFields()) {
            if(field.getAnnotation(Column.class) != null && field.getAnnotation(ID.class) != null){
                idField = field.getAnnotation(Column.class).name();
            }
            if(field.getAnnotation(Column.class) != null && field.getAnnotation(Column.class).name() != null){
                nameField = field.getAnnotation(Column.class).name();
            }

        }
        System.out.println( tableName + idField + nameField);



        Connection connection = DriverManager.getConnection("jdbc:h2:mem:test");
        tableInit(connection, tableName, idField, nameField);
        insertData(connection, tableName, idField, nameField, people);

        save(connection, tableName, idField, nameField, new Person(76, "John"));
        update(connection, 3, "NewName", tableName);


        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select id, name from person");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                System.out.println("id: " + id + ", name:" + name);
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
    private static void tableInit(Connection connection, String tableName, String col1, String col2) throws SQLException {
        try(Statement statement = connection.createStatement()){

/*            statement.execute("""
                    create table tableName (
                    col1 bigint,
                    col2 varchar(256)
                )
                    """);*/

            statement.execute("""
                create table person (
                    id bigint,
                    name varchar(256)
                )
                """);
        }
    }
    private static void insertData(Connection connection, String tableName, String col1, String col2, List<Person> people) throws SQLException {
        try (Statement statement = connection.createStatement()) {

/*            for (Person person: people) {
                statement.executeUpdate("insert into tableName(col1, col2) values (person.getId(), person.getName())");
            }  */



            int affectedRows = statement.executeUpdate("""
        insert into person(id, name) values
        (1, 'Igor'), 
        (2, 'Person #2'), 
        (3, 'John'), 
        (4, 'Alex'), 
        (5, 'Peter') 
        """);

            System.out.println("INSERT: affected rows: " + affectedRows);
        }
    }


    private static void save(Connection connection, String tableName, String col1, String col2, Person person) throws SQLException {

/*        Statement statement = connection.createStatement();
        statement.executeUpdate("insert into tableName(col1, col2) values (person.getId(), person.getName())");*/
    }
    private static void update(Connection connection, int id, String name, String tableName) throws SQLException {
/*        PreparedStatement stmt = connection.prepareStatement("update tableName set name = $2 where id = $1");
        stmt.setInt(1, id);
        stmt.setString(2, name);*/
    }


}


