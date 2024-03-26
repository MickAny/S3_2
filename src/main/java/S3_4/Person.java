package S3_4;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "people")
public class Person {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    //Конструктор без аргументов обязательно должен быть, чтобы Hibernate создавал объекты через Reflection
    //То же самое с get и set (если нет Lombok)
    public Person() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ID:" + id + " - " + "Name: " + name;
    }
}
