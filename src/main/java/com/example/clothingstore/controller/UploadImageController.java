package com.example.clothingstore.controller;

import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadImageController {
    @Autowired
    ProductServiceImpl productService;
    @PostMapping("/admin/product/{id}/image")
    public ResponseEntity<?> uploadCommentImg(@PathVariable long id,
                                              @RequestParam(value = "image", required = false) MultipartFile image) {
        if(!image.getContentType().equals("image/png") && !image.getContentType().equals("image/jpeg")) {
            return new ResponseEntity<>("File khong hop le!", HttpStatus.BAD_REQUEST);
        }
        ProductEntity productEntity = productService.uploadImage(id, image);

        return ResponseEntity.ok("Upload image success:" + productEntity.getImage());
    }


}
