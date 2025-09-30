package com.OrderServiceBootApp.com.OrderServiceBootApp.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Entity
@Data
@Table(name = "customers")
/*@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")*/
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    @Min(value = 0, message = "Age should be more than 0")
    @Max(value = 150, message = "Agw should be less than 150")
    private int age;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "createdat")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @Past(message = "Date should be in past")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;

    @Setter
    @Column(name = "gender")
    @NotEmpty(message = "Gender can't be empty")
    private String gender;

    @Setter
    @Column(name = "password", length = 100)  // шифрование bCrypt
    private String password;



    @Column(name = "role")
    @NotEmpty(message = "Role can't be empty")
    private String role;


    public Customer(){
        createdAt = LocalDateTime.now();
    }

    public String getFormattedCreatedAt() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        return createdAt.format(dateTimeFormatter);
    }

    @Override
    public String toString() {
        return "User  {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Age='" + age + '\'' +
                ", email='" + email + '\'' +
                ", createdAt='" + getFormattedCreatedAt() + '\'' +
                ", Date of Birth='" + dateOfBirth.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + '\'' +
                ", Gender='" + gender + '\'' +
                ", Password='" + password + '\'' +
                ", Role='" + role +
                ", Orders='" + orders +
                '}';
    }




    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer user = (Customer) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}