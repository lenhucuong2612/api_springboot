package com.example.my_demo.services.impl;

import com.example.my_demo.entity.Comment;
import com.example.my_demo.entity.Post;
import com.example.my_demo.entity.User;
import com.example.my_demo.exceptions.ResourceNotFoundException;
import com.example.my_demo.payloads.CommentDto;
import com.example.my_demo.repositories.CommentRepo;
import com.example.my_demo.repositories.PostRepo;
import com.example.my_demo.repositories.UserRepo;
import com.example.my_demo.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CommentImpl implements CommentService {
    @Autowired
    private UserImpl user;
    @Autowired
    private PostImpl post;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer userId, Integer postId) {
        User user=this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User id",userId));
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Post id",postId));

        Comment createComment=this.commentRepo.save(this.commentDto(commentDto));
        createComment.setPost(post);
        createComment.setUser(user);
        this.commentRepo.save(createComment);
        CommentDto commentSave=this.commentToDO(createComment);
        return commentSave;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id", commentId));
        this.commentRepo.delete(comment);
    }

    @Override
    public CommentDto updateComment(Integer commentId, CommentDto commentDto) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id", commentId));
        comment.setContent(commentDto.getContentDto());
        Comment commentSave=this.commentRepo.save(comment);
        return this.commentToDO(commentSave);
    }

    @Override
    public CommentDto getOneComment(Integer commentId) {
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment","Comment Id", commentId));
        return this.commentToDO(comment);
    }

    @Override
    public List<CommentDto> listComment() {
        List<Comment> listComment=this.commentRepo.findAll();
        List<CommentDto> resultComment=new ArrayList<>();
        for(Comment a: listComment){
            resultComment.add(this.commentToDO(a));
        }
        return resultComment;
    }

    @Override
    public List<String> ListCommentByPost(Integer post_id) {
        Post post=this.postRepo.findById(post_id).orElseThrow(()->new ResourceNotFoundException("Post","Post Id",post_id));
        List<Comment> listComment=this.commentRepo.findCommentByPost(post);
        List<String> resultComment=new ArrayList<>();
        for(Comment a: listComment){
            resultComment.add(this.commentToDO(a).getContentDto());
        }
        return resultComment;
    }

    public Comment commentDto(CommentDto commentDto){
        Comment comment=new Comment();
        comment.setCommentId(commentDto.getCommentDtoId());
        comment.setContent(commentDto.getContentDto());
        return comment;
    }
    public CommentDto commentToDO(Comment comment){
        CommentDto commentDto=new CommentDto();
        commentDto.setCommentDtoId(comment.getCommentId());
        commentDto.setContentDto(comment.getContent());
        return commentDto;
    }
}
