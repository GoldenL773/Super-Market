package org.example.spring.supermarket.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "Permissions")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Integer permissionId;

    @Column(name = "permission_name", nullable = false, length = 50)
    private String permissionName;

    @Column(name = "url", length = 50)
    private String url;

    @OneToMany(mappedBy = "permission")
    private Set<RolePermission> rolePermissions;

    // Constructors
    public Permission() {
    }

    public Permission(String permissionName, String url) {
        this.permissionName = permissionName;
        this.url = url;
    }

    // Getters and Setters
    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<RolePermission> getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(Set<RolePermission> rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}
