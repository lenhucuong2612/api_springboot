package com.example.my_demo.repositories;

import com.example.my_demo.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepo extends JpaRepository<Roles, Integer> {
    Roles findByName(String name);
}
