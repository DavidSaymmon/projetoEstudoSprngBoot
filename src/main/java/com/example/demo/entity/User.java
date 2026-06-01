package com.example.demo.entity;
import java.time.LocalDateTime;

import com.example.demo.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name="users")
public class User{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;
   
    @Column(nullable = false)
    @NotBlank(message = "O nome é obrigatório")
    private String name;
   
    @Column(nullable = false, unique = true)
    @NotBlank(message = "O email é obrigatório")
    private String email;
    
    @Column(nullable = false)
    @NotBlank(message = "A senha é obrigatória") 
    private String password;

    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @Column(nullable = false, updatable=false)
    private LocalDateTime createdAt; 
    
    @Enumerated(EnumType.STRING)
    @Column(updatable=false)
    private Role role=Role.USER;

    @PrePersist
    public void onCreate(){
        LocalDateTime now =  LocalDateTime.now(); 
        createdAt= now;
        updatedAt = now;
    }
    @PreUpdate
    public void onUpdate(){
        updatedAt= LocalDateTime.now();
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public User(Long id, String name,
            String email,
            String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
        public User(Long id, String name,
            String email,
            String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role=role;
    }
    public User(String name,
            String email,
           String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public User(){}
    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", updatedAt="
                + updatedAt + ", createdAt=" + createdAt + "]";
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role){
        this.role = role;
    }
}
