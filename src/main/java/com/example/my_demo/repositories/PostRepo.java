package com.example.my_demo.repositories;

import com.example.my_demo.entity.Category;
import com.example.my_demo.entity.Comment;
import com.example.my_demo.entity.Post;
import com.example.my_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findAllByCategory(Category category);
    List<Post> findAllByUser(User user);
    @Query("SELECT p FROM Post p where CONCAT(p.user.name,p.category.categoryTitle,p.content,p.title) like %?1%")
    List<Post> findPostByKeyword(String keyword);

}
