package com.example.my_demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="user_name", nullable = false, length=100)
    private String name;
    private String email;
    private String password;
    private String about;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> postList=new ArrayList<>();
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> commentList=new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
    joinColumns=@JoinColumn(name="users",referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name="roles",referencedColumnName = "id"))
    private Set<Roles> roles=new HashSet<>();
}
