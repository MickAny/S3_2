package S3_3sem;

import java.sql.*;

public class JDBC {

    public static void main(String[] args) {

        try(Connection connection = DriverManager.getConnection("jdbc:h2:mem:test")){
            acceptConnection(connection);
        }catch (SQLException e){
            throw new RuntimeException();
        }

    }

    private static void acceptConnection(Connection connection) throws SQLException {
        createTable(connection);
        insertData(connection);
        deleteRandomRow(connection);
        updateRandomRow(connection);
        updateRow(connection, 1, "NewName");


        try(Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("select id, name from person");
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                System.out.println("id: " + id + ", name:" + name);
            }
        }


    }



    private static void createTable(Connection connection) throws SQLException {
        try(Statement statement = connection.createStatement()){
            statement.execute("""
                create table person (
                    id bigint,
                    name varchar(256)
                )
                """);
        }
    }

    private static void insertData(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
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

    private static void deleteRandomRow(Connection connection) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            System.out.println("DELETED: " + statement.executeUpdate("delete from person where id = 4"));
        }
    }

    private static void updateRandomRow(Connection connection) throws SQLException {
        try(Statement statement = connection.createStatement()) {
            System.out.println("UPDATED: " + statement.executeUpdate("update person set name = 'UPDATED' where id = 3"));
        }
    }

    private static void updateRow(Connection connection, int id, String name) throws SQLException{
        PreparedStatement stmt = connection.prepareStatement("update person set name = $2 where id = $1");
        stmt.setInt(1, id);
        stmt.setString(2, name);

        System.out.println("AFFECTED: " + stmt.executeUpdate());
    }
}



