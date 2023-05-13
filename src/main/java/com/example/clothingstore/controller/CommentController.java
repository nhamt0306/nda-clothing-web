package com.example.clothingstore.controller;

import com.example.clothingstore.config.mapper.*;
import com.example.clothingstore.dto.CommentDTO;
import com.example.clothingstore.dto.CommentDTOV1;
import com.example.clothingstore.model.*;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.stream.events.Comment;
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
    @PostMapping("/user/comment/create-v2")
    public Object createCommentV2(@ModelAttribute CommentDTO commentDTO) throws ParseException {
        // set isComment to true
        TransactionEntity transactionEntity = transactionService.getById(commentDTO.getTransactionId());

        if (transactionEntity.getCommented() == true) {
            return new ResponseEntity<>("Transaction has already been commented", HttpStatus.CONFLICT);
        }
        transactionEntity.setCommented(true);

        CommentEntity commentEntity1 = new CommentEntity();
        commentEntity1.setContent(commentDTO.getComContent());
        commentEntity1.setRating(commentDTO.getComRating());
        commentEntity1.setUserId(userDetailService.getCurrentUser().getId());
        commentEntity1.setProductEntity(productService.findProductById(commentDTO.getProductId()));
        commentEntity1.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        commentEntity1.setCreate_at(new Timestamp(System.currentTimeMillis()));
        if (commentDTO.getImage().isEmpty()){
            commentEntity1.setImage("");
        }
        else if(!commentDTO.getImage().getContentType().equals("image/png") && !commentDTO.getImage().getContentType().equals("image/jpeg") && !commentDTO.getImage().getContentType().equals("image/jpg")) {
            return new ResponseEntity<>("File khong hop le!", HttpStatus.BAD_REQUEST);
        }
        else {
            String imageUrl = cloudinaryService.uploadFile(commentDTO.getImage(), String.valueOf(commentDTO.getId()),
                    "ClothingStore" + "/" + "Comment");
            if (!imageUrl.equals("-1")) {
                commentEntity1.setImage(imageUrl);
            } else if (commentEntity1.getImage().equals("") || commentEntity1.getImage().equals("-1") || commentEntity1.getImage().equals(null))
                commentEntity1.setImage("");
        }
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


    @PostMapping("/user/comment/create")
    public Object createComment(@RequestBody CommentDTOV1 commentDTO) throws ParseException {
        // set isComment to true
        TransactionEntity transactionEntity = transactionService.getById(commentDTO.getTransactionId());

        if (transactionEntity.getCommented() == true) {
            return new ResponseEntity<>("Transaction has already been commented", HttpStatus.CONFLICT);
        }
        transactionEntity.setCommented(true);

        CommentEntity commentEntity1 = new CommentEntity();
        commentEntity1.setContent(commentDTO.getComContent());
        commentEntity1.setRating(commentDTO.getComRating());
        commentEntity1.setUserId(userDetailService.getCurrentUser().getId());
        commentEntity1.setProductEntity(productService.findProductById(commentDTO.getProductId()));
        commentEntity1.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        commentEntity1.setCreate_at(new Timestamp(System.currentTimeMillis()));

        commentService.save(commentEntity1);
        transactionService.save(transactionEntity);
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

//  - Comment pagination
    @GetMapping("/comment")
    public Object getAllProducts(@RequestParam(defaultValue = "1") Integer pageNo,
                                 @RequestParam(defaultValue = "8") Integer pageSize,
                                 @RequestParam(defaultValue = "id") String sortBy,
                                 @RequestParam(defaultValue = "") Long productId) {
        Integer maxPageSize;
        Integer maxPageNo;
        List<CommentEntity> commentEntityList = new ArrayList<>();

        maxPageSize = commentService.findByProductId(productId).size();
        if (pageSize > maxPageSize)
        {
            pageSize = 12;
        }
        maxPageNo = maxPageSize / pageSize;
        if (pageNo > maxPageNo +1)
        {
            pageNo = maxPageNo +1;
        }
        commentEntityList = commentService.findAllCommentPagingByProductId(productId,pageNo-1, pageSize, sortBy);

        List<CommentMapper> commentMappers = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntityList)
        {
            UserEntity user= userService.findById(commentEntity.getUserId()).get();
            CommentMapper commentMapper = new CommentMapper(commentEntity.getId(), commentEntity.getContent(), commentEntity.getRating(), user.getFullname(), commentEntity.getCreate_at());
            commentMapper.setAvatar(user.getAvatar());
            commentMapper.setComImage(commentEntity.getImage());
            commentMappers.add(commentMapper);
        }

        CommentPagingResponse commentPagingResponse = new CommentPagingResponse(commentMappers, maxPageSize);
        return commentPagingResponse;
    }
//  - Comment filter
    @PostMapping("/user/comment/filter")
    public ResponseEntity<?> filterCommentByRating(@RequestBody FilterForm filterForm) throws ParseException {
        List<CommentEntity> commentEntityList = commentService.filterByRating(filterForm.getRating(), filterForm.getProductId());
        List<CommentMapper> commentMappers = new ArrayList<>();
        for(CommentEntity commentEntity : commentEntityList)
        {
            UserEntity user= userService.findById(commentEntity.getUserId()).get();
            CommentMapper commentMapper = new CommentMapper(commentEntity.getId(), commentEntity.getContent(), commentEntity.getRating(), user.getFullname(), commentEntity.getCreate_at());
            commentMapper.setAvatar(user.getAvatar());
            commentMapper.setComImage(commentEntity.getImage());
            commentMappers.add(commentMapper);
        }
        return ResponseEntity.ok(commentMappers);
    }


}
