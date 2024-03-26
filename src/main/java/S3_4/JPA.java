package S3_4;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.List;

public class JPA {

    public static void main(String[] args) throws SQLException {

        Configuration configuration = new Configuration().configure();

        try(SessionFactory sessionFactory = configuration.buildSessionFactory()){

            insertNewPerson(sessionFactory);

            try(Session session = sessionFactory.openSession()){
                Person person = session.find(Person.class, 1L);
                System.out.println(person);

                Transaction tx = session.beginTransaction();
                person.setName("NEW NAME");
                session.merge(person);
                tx.commit();


            }

            try(Session session = sessionFactory.openSession()) {
                Person person = session.find(Person.class, 1L);
                System.out.println(person);

                Transaction tx = session.beginTransaction();
                session.remove(person);
                tx.commit();


            }

            try(Session session = sessionFactory.openSession()) {
                Person person = session.find(Person.class, 1L);
                System.out.println(person);
            }

            try(Session session = sessionFactory.openSession()){
                Query<Person> query = session.createQuery("select p from Person p where name like 'NEW NAME' ", Person.class);
                List<Person> resultList = query.getResultList();
                System.out.println("Select by QUERY");
                System.out.println(resultList);
            }

            try(Session session = sessionFactory.openSession()){
                // SQL
                // JQL

                // select полей
                System.out.println("Field select");
                Query<Long> _query = session.createQuery("select p.id from Person p where name like '%NAME'", Long.class);
                System.out.println(_query.getSingleResult());

                Query<Person> query = session.createQuery("select p from Person where name like '%NAME'", Person.class);
                List<Person> resultList = query.getResultList();
                System.out.println(resultList);
            }

        }
    }

    private static void insertNewPerson(SessionFactory sessionFactory){
        Person person = new Person();
        person.setId(1L);
        person.setName("Ed");

        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction(); // механизм манипуляции с данными бд


            session.persist(person); // сохранить объект
            tx.commit();
        }
    }
}
