package com.example.my_demo.repositories;

import com.example.my_demo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CateRepo extends JpaRepository<Category,Integer> {
}
