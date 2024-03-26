package S3_4.hw;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args){
        Configuration configuration = new Configuration().configure();

        try(SessionFactory sessionFactory = configuration.buildSessionFactory()){

            Student existedStudent = new Student(2, "Ann", "Garison", 22);
            Student newStudent = new Student(8, "Mitchel", "Cartman", 38);
            Student mergeStudent = new Student(4, "Merge_Missy", "Merge_Miller", 41);

            InsertStudents(sessionFactory);
            ShowStudents(sessionFactory);

            FindById(sessionFactory, 5);

            //Persist(sessionFactory, existedStudent); Упала с ошибкой)
            Persist(sessionFactory, newStudent);
            ShowStudents(sessionFactory);

            Merge(sessionFactory, mergeStudent);
            ShowStudents(sessionFactory);

            Remove(sessionFactory, existedStudent);
            ShowStudents(sessionFactory);

            StudentsElderThan20(sessionFactory);



        }
    }

    private static void ShowStudents(SessionFactory sessionFactory) {
        try(Session session = sessionFactory.openSession()){
            System.out.println("Table created");
            Query<Student> studentQuery = session.createQuery("select s from Student s", Student.class);
            List<Student> resultList = studentQuery.getResultList();
            System.out.println(resultList);
        }
    }
    private static void InsertStudents(SessionFactory sessionFactory) {

        List<Student> students = new ArrayList<>(List.of(
                new Student(1, "Alex", "Mann", 33),
                new Student(2, "Ann", "Garison", 22),
                new Student(3, "Daniel", "Pat", 18),
                new Student(4, "Missy", "Miller", 41),
                new Student(5, "Andrew", "Johnson", 29),
                new Student(6, "Greg", "Den", 25),
                new Student(7, "Sam", "Serious", 19)
        ));

        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();

            for (Student student : students) {
                session.persist(student);
            }

            tx.commit();
        }
    }
    private static void FindById(SessionFactory sessionFactory, int ID){
        try(Session session = sessionFactory.openSession()){

            Student student = session.find(Student.class, ID);
            System.out.println("Student was found by ID(" + ID + ") " + student);
        }
    }
    private static void Persist(SessionFactory sessionFactory, Student student){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            session.persist(student);
            tx.commit();
        }
    }
    private static void Merge(SessionFactory sessionFactory, Student student){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            session.merge(student);
            tx.commit();
        }
    }
    private static void Remove(SessionFactory sessionFactory, Student student){
        try(Session session = sessionFactory.openSession()){
            Transaction tx = session.beginTransaction();
            session.remove(student);
            tx.commit();
            System.out.println("Student ID(" + student.getId() + ") was removed");
        }
    }
    private static void StudentsElderThan20(SessionFactory sessionFactory){

        try(Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("select s from Student s where age > 20", Student.class);
            List<Student> resultList = query.getResultList();
            System.out.println(resultList);
        }
    }

}
