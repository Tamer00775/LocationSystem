package kz.kartayev.LocationSystem.dto;

import javax.persistence.Column;
import javax.validation.constraints.*;

public class UserDTO {
    @NotEmpty(message = "name should be not null")
    @Size(min = 2, max = 30, message = "size should be between 2 and 30")
    String name;

    @NotNull
    @Min(value = 0, message = "Age should be greater than 2")
    int age;

    @NotEmpty
    @Email(message = "EX: tamerlan@mail.ru | tamerlan@gmail.ru")
    String email;


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
}
