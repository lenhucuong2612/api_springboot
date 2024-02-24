package com.example.my_demo.services;


import com.example.my_demo.payloads.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentDto commentDto,Integer userId, Integer postId);
    void deleteComment(Integer commentId);
    CommentDto updateComment(Integer id, CommentDto commentDto);
    CommentDto getOneComment(Integer id);
    List<CommentDto> listComment();
    List<String> ListCommentByPost(Integer post_id);
}
