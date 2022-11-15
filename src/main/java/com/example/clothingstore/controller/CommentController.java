package com.example.clothingstore.controller;

import com.example.clothingstore.dto.CommentDTO;
import com.example.clothingstore.mapper.CommentMapper;
import com.example.clothingstore.model.CommentEntity;
import com.example.clothingstore.model.UserEntity;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.impl.CommentServiceImpl;
import com.example.clothingstore.service.impl.ProductServiceImpl;
import com.example.clothingstore.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentServiceImpl commentService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/comment/product/{id}")
    public ResponseEntity<?> getAllCommentByProduct(@PathVariable long id){
        List<CommentMapper> commentMappers = new ArrayList<>();
        for(CommentEntity commentEntity : commentService.findByProductId(id))
        {
            UserEntity user= userService.findById(commentEntity.getUserId()).get();
            CommentMapper commentMapper = new CommentMapper(commentEntity.getId(), commentEntity.getContent(), commentEntity.getRating(), user.getFullname());
            commentMappers.add(commentMapper);
        }
        return ResponseEntity.ok(commentMappers);
    }

    @PostMapping("/user/comment/create")
    public Object createComment(@RequestBody CommentDTO commentDTO) throws ParseException {
        CommentEntity commentEntity1 = new CommentEntity();
        commentEntity1.setContent(commentDTO.getComContent());
        commentEntity1.setRating(commentDTO.getComRating());
        commentEntity1.setUserId(userDetailService.getCurrentUser().getId());
        commentEntity1.setProductEntity(productService.findProductById(commentDTO.getProductId()));
        commentEntity1.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        commentEntity1.setCreate_at(new Timestamp(System.currentTimeMillis()));
        commentService.save(commentEntity1);
        return "Create comment success!";
    }

    @DeleteMapping("/user/comment/{id}")
    public ResponseEntity<?> deleteCommentById(@PathVariable long id)
    {
        try {
            if (commentService.checkCommentIncludeByUser(userDetailService.getCurrentUser().getId(), id))
            {
                commentService.delete(id);
                return ResponseEntity.ok("Delete comment success!");
            }
            return ResponseEntity.ok("This comment is not yours!");
        }
        catch (Exception e)
        {
            return ResponseEntity.ok("Comment is invalid!");
        }
    }
}