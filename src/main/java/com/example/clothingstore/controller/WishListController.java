package com.example.clothingstore.controller;

import com.example.clothingstore.config.mapper.WishListMapper;
import com.example.clothingstore.model.*;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.impl.ProductServiceImpl;
import com.example.clothingstore.service.impl.TypeServiceImpl;
import com.example.clothingstore.service.impl.WishListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            WishListMapper wishListMapper = new WishListMapper(wishListEntity.getId(), productEntity.getName(), type.getPrice(), productEntity.getImage(), productEntity.getCategoryEntity().getId(), productEntity.getCategoryEntity().getName(), productEntity.getId());
            responseList.add(wishListMapper);
        }
        return ResponseEntity.ok(responseList);
    }

    @PostMapping("/user/wishlist/toggle/{id}")
    public Object toggleProductWishList(@PathVariable long id) throws ParseException {
        if (!productService.existByProductId(id)) {
            return new ResponseEntity<>("Product ID " + id + " not found", HttpStatus.BAD_REQUEST);
        }
        ProductEntity productEntity = productService.findProductById(id);
            List<TypeEntity> typeEntityList = typeService.getAllTypeByProduct(id);

        if (typeEntityList.isEmpty()) {
            return new ResponseEntity<>("Product ID" + id + "has no types", HttpStatus.BAD_REQUEST);
        }

        // get first type in type list
        TypeEntity typeEntity = typeEntityList.get(0);

        // check if existed product in wishlist => delete from wishlist
        List<WishListEntity> listByUser =  wishListService.findAllByUser(userDetailService.getCurrentUser().getId());
        if (!listByUser.isEmpty()) {
            for (WishListEntity w : listByUser) {
                if (id == w.getProductEntity().getId()) {
                    wishListService.delete(w.getId());
                    return getAllWishListByUser();
                }
            }
        }

        WishListEntity wishListEntity = new WishListEntity(typeEntity.getPrice(), typeEntity.getColor(), typeEntity.getSize(), productEntity.getCategoryEntity().getId(), productEntity, userDetailService.getCurrentUser());
        wishListService.save(wishListEntity);

        return getAllWishListByUser();
    }

//    @PostMapping("/user/wishlist/remove/{id}")
//    public Object deleteProductWishListById(@PathVariable long id)
//    {
//        wishListService.delete(id);
//        return getAllWishListByUser();
//    }
}
