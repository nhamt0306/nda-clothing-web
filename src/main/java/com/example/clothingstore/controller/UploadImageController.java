package com.example.clothingstore.controller;

import com.example.clothingstore.dto.ImageProductDTO;
import com.example.clothingstore.model.ImageProductEntity;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.service.impl.ImageProductServiceImpl;
import com.example.clothingstore.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UploadImageController {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ImageProductServiceImpl imageProductService;

    @PostMapping("/admin/product/{id}/image")
    public ResponseEntity<?> uploadCommentImg(@PathVariable long id,
                                              @RequestParam(value = "image", required = false) MultipartFile image) {
        if(!image.getContentType().equals("image/png") && !image.getContentType().equals("image/jpeg")) {
            return new ResponseEntity<>("File khong hop le!", HttpStatus.BAD_REQUEST);
        }
        ProductEntity productEntity = productService.uploadImage(id, image);

        return ResponseEntity.ok("Upload image success:" + productEntity.getImage());
    }

    @PostMapping("/admin/product/{id}/imageDetail")
    public ResponseEntity<?> uploadImgDetail(@PathVariable long id,
                                              @RequestParam(value = "image", required = false) List<MultipartFile> imageList) {
        for (MultipartFile multipartFile : imageList)
        {
            if(!multipartFile.getContentType().equals("image/png") && !multipartFile.getContentType().equals("image/jpeg")) {
                return new ResponseEntity<>("File khong hop le!", HttpStatus.BAD_REQUEST);
            }
            imageProductService.uploadImage(id, multipartFile);
        }
        return ResponseEntity.ok("Upload image success!");
    }

    @GetMapping("/product/{id}/imageDetail")
    public ResponseEntity<?> getAllImageDetailByProduct(@PathVariable long id) {
        List<ImageProductEntity> imageProductEntities = imageProductService.getAllImageByProduct(id);
        List<ImageProductDTO> imageProductDTOS = new ArrayList<>();
        for (ImageProductEntity imageProductEntity : imageProductEntities)
        {
            ImageProductDTO imageProductDTO = new ImageProductDTO(imageProductEntity.getId(), imageProductEntity.getImage(), imageProductEntity.getProductEntity().getId());
            imageProductDTOS.add(imageProductDTO);
        }
        return ResponseEntity.ok(imageProductDTOS);
    }

    @DeleteMapping("/admin/product/imageDetail/{id}")
    public ResponseEntity<?> deleteImageProductById(@PathVariable long id)
    {
        try{
            imageProductService.delete(id);
            return ResponseEntity.ok("Delete image product success!");
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Cannot find image with id = "+ id, HttpStatus.NOT_FOUND);
        }
    }
}
