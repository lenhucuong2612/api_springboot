package com.example.my_demo.services.impl;

import com.example.my_demo.entity.Category;
import com.example.my_demo.entity.Comment;
import com.example.my_demo.entity.Post;
import com.example.my_demo.entity.User;
import com.example.my_demo.exceptions.ResourceNotFoundException;
import com.example.my_demo.payloads.CommentDto;
import com.example.my_demo.payloads.PostDto;
import com.example.my_demo.payloads.PostResponse;
import com.example.my_demo.repositories.CateRepo;
import com.example.my_demo.repositories.CommentRepo;
import com.example.my_demo.repositories.PostRepo;
import com.example.my_demo.repositories.UserRepo;
import com.example.my_demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CateRepo cateRepo;
    @Autowired
    private UserImpl userImpl;
    @Autowired
    private CategoryImpl categoryImpl;
    @Autowired
    private CommentRepo commentRepo;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User ","User id", userId));
        Category category=this.cateRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));
        postDto.setUser(this.userImpl.UserToDo(user));
        postDto.setCategory(this.categoryImpl.categoryToDo(category));
        Post postSave=this.postRepo.save(this.postDto(postDto));
        PostDto post=this.postToDO(postSave);
        return post;
    }

    @Override
    public PostDto updatePost(Integer id, PostDto postDto) {
        Post findPost=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Post Id", id));
        findPost.setContent(postDto.getContent());
        findPost.setTitle(postDto.getTitle());
        findPost.setImage(postDto.getImageName());
        Post postSave=this.postRepo.save(findPost);
        return this.postToDO(postSave);
    }

    @Override
    public void deletePost(Integer id) {
        Post deletePost=this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",id));
        this.postRepo.delete(deletePost);
    }

    @Override
    public PostDto getOnePost(Integer id) {
        PostDto findPost=this.postToDO(this.postRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","Post Id", id)));
        return findPost;
    }

    @Override
    public PostResponse listPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Sort sort=null;
        if(sortDir.equalsIgnoreCase("asc")){
            sort=Sort.by(sortBy).ascending();
        }
        else{
            sort=Sort.by(sortBy).descending();
        }
        PageRequest p=PageRequest.of(pageNumber,pageSize, sort);
        Page<Post> pagePost=this.postRepo.findAll(p);
        List<Post> allPosts=pagePost.getContent();
        List<PostDto> allPostDto=new ArrayList<>();
        for(Post a: allPosts){
            allPostDto.add(this.postToDO(a));
        }
        PostResponse postResponse=new PostResponse();
        postResponse.setContent(allPostDto);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setPageLast(pagePost.isLast());
        return postResponse;
    }

    /**
    @Override
    public List<PostDto> listPost(Integer pageNumber, Integer pageSize) {

        PageRequest p= PageRequest.of(pageNumber,pageSize);
        Page<Post> findPost=this.postRepo.findAll(p);
        List<Post> allPosts=findPost.getContent();

        List<PostDto> resultPost=new ArrayList<>();
        for(Post a: allPosts){
            resultPost.add(this.postToDO(a));
        }
        return resultPost;
    }
*/
    @Override
    public List<PostDto> listPostByCategory(Integer categoryId) {
        Category category=this.cateRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id",categoryId));
        List<Post> findPost=this.postRepo.findAllByCategory(category);
        List<PostDto> resultPost=new ArrayList<>();
        for(Post a: findPost){
            resultPost.add(this.postToDO(a));
        }
        return resultPost;
    }

    @Override
    public List<PostDto> listPostByUser(Integer userId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id", userId));
        List<Post> findUser=this.postRepo.findAllByUser(user);
        List<PostDto> resultPost=new ArrayList<>();
        for(Post a: findUser){
            resultPost.add(this.postToDO(a));
        }
        return resultPost;
    }

    @Override
    public List<PostDto> findPostByKeyword(String keyword) {
        List<Post> findPost=this.postRepo.findPostByKeyword(keyword);
        List<PostDto> resultPost=new ArrayList<>();
        for(Post a: findPost){
            resultPost.add(this.postToDO(a));
        }
        return resultPost;
    }



    public Post postDto(PostDto postDto){
        Post post=new Post();
        post.setPostId(postDto.getPostDtoId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImage("default.png");
        post.setAddedDate(new Date());
        post.setCategory(this.categoryImpl.dtoCategory(postDto.getCategory()));
        post.setUser(this.userImpl.dtoToUser(postDto.getUser()));
        return post;
    }
    public PostDto postToDO(Post post){

        PostDto postDto=new PostDto();
        postDto.setPostDtoId(post.getPostId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setImageName(post.getImage());
        postDto.setAddedDate(post.getAddedDate());
        postDto.setUser(this.userImpl.UserToDo(post.getUser()));
        postDto.setCategory(this.categoryImpl.categoryToDo(post.getCategory()));
        Set<CommentDto> commentDtoSet = post.getComments().stream()
                .map(comment -> commentToDO(comment))
                .collect(Collectors.toSet());
        postDto.setCommentDto(commentDtoSet);
        System.out.println(postDto.getPostDtoId());
        return postDto;
    }
    public CommentDto commentToDO(Comment comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setCommentDtoId(comment.getCommentId());
        commentDto.setContentDto(comment.getContent());
        return commentDto;
    }
}
