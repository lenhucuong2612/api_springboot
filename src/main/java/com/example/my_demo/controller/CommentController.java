package com.example.my_demo.controller;

import com.example.my_demo.payloads.ApiResponse;
import com.example.my_demo.payloads.CommentDto;
import com.example.my_demo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;
    //create
    @PostMapping("/comment/create/user/{userId}/post/{postId}")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable Integer userId,
                                                    @PathVariable Integer postId
                                                    ){
        CommentDto createComment=this.commentService.createComment(commentDto,userId,postId);
        return new ResponseEntity<>(createComment, HttpStatus.CREATED);
    }
    //find comment by comment_id
    @GetMapping("/comment/find/{comment_id}")
    public ResponseEntity<CommentDto> findCommentById(@PathVariable Integer comment_id){
        CommentDto findComment=this.commentService.getOneComment(comment_id);
        return new ResponseEntity<>(findComment, HttpStatus.OK);
    }
    //find all comment
    @GetMapping("/comment/getALl")
    public ResponseEntity<List<CommentDto>> findAllComment(){
        List<CommentDto> commentDtoList=this.commentService.listComment();
        return new ResponseEntity<>(commentDtoList,HttpStatus.OK);
    }
    @GetMapping("/comment/find/post/{post_id}")
    public ResponseEntity<List<String>> findAllCommentByPost(@PathVariable Integer post_id){
        List<String> commentDtoList=this.commentService.ListCommentByPost(post_id);
        return new ResponseEntity<>(commentDtoList,HttpStatus.OK);
    }
    @DeleteMapping("/comment/delete/{comment_id}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer comment_id){
        this.commentService.deleteComment(comment_id);
        return new ResponseEntity<>(new ApiResponse("Delete successfully ",true),HttpStatus.OK);
    }
}
