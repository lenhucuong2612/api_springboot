package com.example.my_demo.repositories;

import com.example.my_demo.entity.Comment;
import com.example.my_demo.entity.Post;
import com.example.my_demo.payloads.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
    List<Comment> findCommentByPost(Post post);
}
