package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.TypeDTO;
import com.example.clothingstore.dto.WishListDTO;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.model.TypeEntity;
import com.example.clothingstore.model.UserEntity;
import com.example.clothingstore.model.WishListEntity;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.impl.ProductServiceImpl;
import com.example.clothingstore.service.impl.WishListServiceImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class WishListController {
    @Autowired
    WishListServiceImpl wishListService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    ProductServiceImpl productService;
    @GetMapping("/user/wishlist/getAll")
    public ResponseEntity<?> getAllWishlist()
    {
        List<WishListEntity> listEntityList = wishListService.findAllByUser(userDetailService.getCurrentUser().getId());
        if (listEntityList.isEmpty())
        {
            return ResponseEntity.ok("Không có sản phẩm trong danh sách yêu thích!");
        }

        Map<Long, String > productList = new HashMap<Long, String>();
        for (WishListEntity wishListEntity : listEntityList)
        {
            productList.put(wishListEntity.getId(), productService.findProductById(wishListEntity.getId()).getName());
        }
        return ResponseEntity.ok(productList);
    }


    @PostMapping("/user/wishlist/add")
    public Object addToWishlist(@RequestBody WishListDTO wishListDTO) throws ParseException {
        ProductEntity productEntity = productService.findProductById(wishListDTO.getProductId());
        UserEntity user = userDetailService.getCurrentUser();
        if (wishListService.existByProduct(productEntity.getId(), user.getId()))
        {
            return "Sản phẩm đã thêm vào danh sách yêu thích!";
        }
        WishListEntity wishListEntity = new WishListEntity();
        wishListEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        wishListEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        wishListEntity.setUserEntity(user);
        wishListEntity.setProductEntity(productEntity);
        wishListService.save(wishListEntity);
        return "Thêm sản phẩm vào danh sách yêu thích thành công!";
    }

    @DeleteMapping("/user/wishlist/delete")
    public Object deleteFromWishlist(@RequestBody WishListDTO wishListDTO) throws Exception {
        ProductEntity productEntity = productService.findProductById(wishListDTO.getProductId());
        UserEntity user = userDetailService.getCurrentUser();
        if (wishListService.existByProduct(productEntity.getId(), user.getId()))
        {
            wishListService.deleteByProductIdAndUserId(productEntity.getId(), user.getId());
            return "Xóa sản phẩm khỏi danh sách yêu thích thành công!";
        }
        return "Sản phẩm không tồn tại trong danh sách yêu thích!";
    }
}
