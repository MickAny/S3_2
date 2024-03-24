package S3_3sem.S3_3hw;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Reflection reflection = new Reflection();
        reflection.createTable(Person.class);
    }
}
