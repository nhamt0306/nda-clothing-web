package com.example.clothingstore.controller;

import com.example.clothingstore.dto.CartProductDTO;
import com.example.clothingstore.dto.WishListDTO;
import com.example.clothingstore.mapper.CartProductMapper;
import com.example.clothingstore.mapper.TypeMapper;
import com.example.clothingstore.mapper.WishListMapper;
import com.example.clothingstore.model.*;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.WishListService;
import com.example.clothingstore.service.impl.ProductServiceImpl;
import com.example.clothingstore.service.impl.TypeServiceImpl;
import com.example.clothingstore.service.impl.WishListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")   //Để ghép AuthController với các controller khác
@RequestMapping
@RestController
public class WishListController {
    @Autowired
    WishListServiceImpl wishListService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    TypeServiceImpl typeService;

    @GetMapping("/user/wishlist/getAll")
    public ResponseEntity<?> getAllWishListByUser()
    {
        UserEntity user = userDetailService.getCurrentUser();
        List<WishListEntity> wishListEntities = wishListService.findAllByUser(user.getId());
        List<WishListMapper> responseList = new ArrayList<>();
        for (WishListEntity wishListEntity : wishListEntities)
        {
            ProductEntity productEntity = productService.findProductById(wishListEntity.getProductEntity().getId());
            TypeEntity type = typeService.getTypeByColorAndSizeAndProductId(wishListEntity.getColor(), wishListEntity.getSize(), productEntity.getId());
            WishListMapper wishListMapper = new WishListMapper(wishListEntity.getId(), productEntity.getName(), type.getPrice(), productEntity.getImage(), productEntity.getCategoryEntity().getId(), productEntity.getCategoryEntity().getName());
            responseList.add(wishListMapper);
        }
        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/user/wishlist/add")
    public Object addProductToWishList(@RequestBody WishListDTO wishListDTO) throws ParseException {
        WishListEntity wishListEntity = new WishListEntity(wishListDTO.getColor(), wishListDTO.getSize(), productService.findProductById(wishListDTO.getProductId()), userDetailService.getCurrentUser());
        wishListService.save(wishListEntity);
        return getAllWishListByUser();
    }

    @PostMapping("/user/wishlist/remove/{id}")
    public Object deleteProductWishListById(@PathVariable long id)
    {
        wishListService.delete(id);
        return getAllWishListByUser();
    }
}
