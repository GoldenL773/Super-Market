package org.example.spring.supermarket.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Staffs")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id", nullable = false)
    private int id;
    @Column(name = "username", nullable = false, columnDefinition = "varchar(50)")
    private String username;

    @Column(name = "password", nullable = false, columnDefinition = "varchar(255)")
    private String password;

    @Column(name = "full_name", nullable = true, columnDefinition = "nvarchar(100)")
    private String fullName;

    @Column(name = "image", nullable = true, columnDefinition = "nvarchar(max)")
    private String image;

    @Column(name = "email", nullable = false, columnDefinition = "varchar(100)")
    private String email;

    @Column(name = "phone_number", nullable = true,  columnDefinition = "varchar(10)")
    private String phoneNumber;

    @Column(name = "gender", nullable = false, columnDefinition = "bit")
    private boolean gender;

    @Column(name = "address", nullable = true,  columnDefinition = "nvarchar(255)")
    private String address;

    @Column(name = "role_id", nullable = true)
    private int roleId;

    @Column(name = "created_at", nullable = true, columnDefinition = "datetime")
    private Date createdAt;


}
