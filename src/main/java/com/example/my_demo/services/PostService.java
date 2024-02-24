package com.example.my_demo.services;

import com.example.my_demo.entity.Post;
import com.example.my_demo.payloads.PostDto;
import com.example.my_demo.payloads.PostResponse;

import java.util.List;

public interface PostService {
    //create
    PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    //update
    PostDto updatePost(Integer id, PostDto postDto);
    //delete
    void deletePost(Integer id);
    //getOne
    PostDto getOnePost(Integer id);

    //getAll
    PostResponse listPost(Integer pageNumber, Integer pageSize, String sortBy,String sortDir);
    //get all posts by category
    List<PostDto> listPostByCategory(Integer categoryId);
    //get all users by users
    List<PostDto> listPostByUser(Integer userId);
    //find post by keyword
    List<PostDto> findPostByKeyword(String keyword);
}
