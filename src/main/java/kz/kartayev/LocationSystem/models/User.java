package kz.kartayev.LocationSystem.models;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    int id;

    @Column(name="name")
    @NotEmpty(message = "name should be not null")
    @Size(min = 2, max = 30, message = "size should be between 2 and 30")
    String name;

    @Column(name="age")
    @NotNull
    @Min(value = 0, message = "Age should be greater than 2")
    int age;

    @Column(name = "email")
    @NotEmpty
    String email;

    @OneToMany(mappedBy = "owner")
    private List<Location> locationList;

    public User(String name, int age, String email){
        this.name = name;
        this.age = age;
        this.email = email;
    }
    public User(){

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Location> getLocationList() {
        return locationList;
    }

    public void setLocationList(List<Location> locationList) {
        this.locationList = locationList;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", locationList=" + locationList +
                '}';
    }
}
