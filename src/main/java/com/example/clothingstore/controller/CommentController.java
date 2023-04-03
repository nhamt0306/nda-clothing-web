package com.example.clothingstore.controller;

import com.example.clothingstore.dto.CommentDTO;
import com.example.clothingstore.config.mapper.CommentMapper;
import com.example.clothingstore.model.CommentEntity;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.model.TransactionEntity;
import com.example.clothingstore.model.UserEntity;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    TransactionServiceImpl transactionService;

    @GetMapping("/comment/product/{id}")
    public ResponseEntity<?> getAllCommentByProduct(@PathVariable long id){
        List<CommentMapper> commentMappers = new ArrayList<>();
        for(CommentEntity commentEntity : commentService.findByProductId(id))
        {
            UserEntity user= userService.findById(commentEntity.getUserId()).get();
            CommentMapper commentMapper = new CommentMapper(commentEntity.getId(), commentEntity.getContent(), commentEntity.getRating(), user.getFullname(), commentEntity.getCreate_at());
            commentMapper.setAvatar(user.getAvatar());
            commentMapper.setComImage(commentEntity.getImage());
            commentMappers.add(commentMapper);
        }
        return ResponseEntity.ok(commentMappers);
    }
    // Tạo comment --> Tính lại avg Rating của product;
    @PostMapping("/user/comment/create")
    public Object createComment(@ModelAttribute CommentDTO commentDTO) throws ParseException {
        // set isComment to true
        TransactionEntity transactionEntity = transactionService.getById(commentDTO.getTransactionId());

        if (transactionEntity.getCommented() == true) {
            return new ResponseEntity<>("Transaction has already been commented", HttpStatus.CONFLICT);
        }
        transactionEntity.setCommented(true);
        if(!commentDTO.getImage().getContentType().equals("image/png") && !commentDTO.getImage().getContentType().equals("image/jpeg") && !commentDTO.getImage().getContentType().equals("image/jpg")) {
            return new ResponseEntity<>("File khong hop le!", HttpStatus.BAD_REQUEST);
        }
        CommentEntity commentEntity1 = new CommentEntity();
        commentEntity1.setContent(commentDTO.getComContent());
        commentEntity1.setRating(commentDTO.getComRating());
        commentEntity1.setUserId(userDetailService.getCurrentUser().getId());
        commentEntity1.setProductEntity(productService.findProductById(commentDTO.getProductId()));
        commentEntity1.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        commentEntity1.setCreate_at(new Timestamp(System.currentTimeMillis()));
        // upload image:
        String imageUrl = cloudinaryService.uploadFile(commentDTO.getImage(),String.valueOf(commentDTO.getId()),
                "ClothingStore"+ "/" + "Comment");
        if(!imageUrl.equals("-1")) {
            commentEntity1.setImage(imageUrl);
        }
        else if(commentEntity1.getImage().equals("") || commentEntity1.getImage().equals("-1"))
            commentEntity1.setImage("");

        commentService.save(commentEntity1);
        // Tính lại avgRating của Product;
        Long totalRating = Long.valueOf(0);
        Long totalComment = Long.valueOf(0);
        for(CommentEntity commentEntity : commentService.findByProductId(commentDTO.getProductId()))
        {
            totalRating = totalRating + commentEntity.getRating();
            totalComment = totalComment + 1;
        }
        Double avgRating = Double.valueOf(totalRating) / Double.valueOf(totalComment);
        // Set vào Product;
        ProductEntity productEntity = productService.findProductById(commentDTO.getProductId());
        productEntity.setAvgRating(avgRating.longValue());
        productService.save(productEntity);
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
