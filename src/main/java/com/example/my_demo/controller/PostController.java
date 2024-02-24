package com.example.my_demo.controller;

import com.example.my_demo.config.AppConstants;
import com.example.my_demo.payloads.ApiResponse;
import com.example.my_demo.payloads.PostDto;
import com.example.my_demo.payloads.PostResponse;
import com.example.my_demo.services.FileService;
import com.example.my_demo.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    //create
    @PostMapping("/user/{user_id}/category/{category_id}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer user_id,@PathVariable Integer category_id){
        PostDto createPost=this.postService.createPost(postDto,user_id,category_id);
        return new ResponseEntity<>(createPost, HttpStatus.CREATED);
    }
    @PutMapping("/post/update/{post_id}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer post_id){
        PostDto updatePost=this.postService.updatePost(post_id,postDto);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }
    //find one post
    @GetMapping("/post/{id}")
    public ResponseEntity<PostDto> findOnePost(@PathVariable Integer id){
        PostDto findPost=this.postService.getOnePost(id);
        return new ResponseEntity<>(findPost,HttpStatus.OK);
    }

    //find all post
    @GetMapping("/posts/all")
    public ResponseEntity<PostResponse> findALlPost(@RequestParam(value="pageNumber",defaultValue = AppConstants.PAGE_NUMBER,required = false) Integer pageNumber,
                                                    @RequestParam(value="pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                    @RequestParam(value="sortBy",defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
                                                    @RequestParam(value="sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir){
        PostResponse resultPost=this.postService.listPost(pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(resultPost,HttpStatus.OK);
    }
    //delete post by id
    @DeleteMapping("/post/delete/{id}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer id){
        this.postService.deletePost(id);
        return new ResponseEntity<>(new ApiResponse("Delete successfully",true),HttpStatus.OK);
    }
    //find post by category id
    @GetMapping("/posts/category/{category_id}")
    public ResponseEntity<List<PostDto>> findPostByCategory(@PathVariable Integer category_id){
        List<PostDto> resultPost=this.postService.listPostByCategory(category_id);
        return new ResponseEntity<>(resultPost,HttpStatus.OK);
    }

    //find post by user id
    @GetMapping("/posts/user/{user_id}")
    public ResponseEntity<List<PostDto>> findPostByUser(@PathVariable Integer user_id){
        List<PostDto> resultPost=this.postService.listPostByUser(user_id);
        return new ResponseEntity<>(resultPost,HttpStatus.OK);
    }
    //find post by keyword
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> findPostByKeyword(@RequestParam(value="keyword") String keyword){
        List<PostDto> resultPost=this.postService.findPostByKeyword(keyword);
        return new ResponseEntity<>(resultPost,HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image" )MultipartFile image,
            @PathVariable Integer postId
    ) throws IOException {
        String fileName= this.fileService.uploadImage(path,image);
        PostDto postDto=this.postService.getOnePost(postId);
        postDto.setImageName(fileName);
        PostDto updatePost=this.postService.updatePost(postId,postDto);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }
}
