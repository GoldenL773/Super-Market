package org.example.spring.supermarket.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id", nullable = false, columnDefinition = "int")
    private int id;

    @Column(name = "permission_name", nullable = false, length = 50, columnDefinition = "nvarchar(50)")
    private String permissionName;

    @Column(name = "url", nullable = true, length = 50, columnDefinition = "varchar(50)")
    private String url;
}
