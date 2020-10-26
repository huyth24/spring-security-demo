package com.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user", schema = "public")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String username;

    @JsonIgnore
    private String password;

    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    List<Role> roles;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {
    }
}
