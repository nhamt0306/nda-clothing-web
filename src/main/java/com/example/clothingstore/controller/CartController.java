package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.CartProductDTO;
import com.example.clothingstore.mapper.CartProductMapper;
import com.example.clothingstore.model.CartProductEntity;
import com.example.clothingstore.model.UserEntity;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.impl.CartProductServiceImpl;
import com.example.clothingstore.service.impl.ProductServiceImpl;
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
public class CartController {
    @Autowired
    CartProductServiceImpl cartProductService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    ProductServiceImpl productService;

    @GetMapping("/user/cart/getAll")
    public ResponseEntity<?> getAllProductByUser()
    {
        UserEntity user = userDetailService.getCurrentUser();
        List<CartProductEntity> cartProductEntities = cartProductService.getAllProductByCartId(user.getId());
        List<CartProductDTO> cartProductDTOS = new ArrayList<CartProductDTO>();
        for(CartProductEntity cartProductEntity: cartProductEntities)
        {
            CartProductDTO cartProductDTO = new CartProductDTO(cartProductEntity.getQuantity(), cartProductEntity.getPrice(),cartProductEntity.getColor(), cartProductEntity.getSize(), cartProductEntity.getProductEntity().getId());
            cartProductDTOS.add(cartProductDTO);
        };
        return ResponseEntity.ok(cartProductDTOS);
    }


    @PostMapping("/user/cart/add")
    public Object addProductToCart(@RequestBody CartProductDTO cartProductDTO) throws ParseException {
        CartProductEntity cartProductEntity = new CartProductEntity();

        cartProductEntity.setColor(cartProductDTO.getColor());
        cartProductEntity.setPrice(cartProductDTO.getPrice());
        cartProductEntity.setSize(cartProductDTO.getSize());
        cartProductEntity.setQuantity(cartProductDTO.getQuantity());

        cartProductEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        cartProductEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));

        cartProductEntity.setCartEntity(userDetailService.getCurrentUser().getCartEntity());
        cartProductEntity.setProductEntity(productService.findProductById(cartProductDTO.getProduct_id()));
        cartProductService.save(cartProductEntity);
        return "Add to cart success!";
    }

    @PostMapping("/user/cart/increase")
    public Object increaseQuantityProductInCart(@RequestBody CartProductMapper cartProductMapper) throws ParseException {
        cartProductService.increaseQuantity(cartProductMapper.getProductId(), userDetailService.getCurrentUser().getId(), cartProductMapper.getColor(), cartProductMapper.getSize());
        return "Update product success!";
    }

    @PostMapping("/user/cart/decrease")
    public Object decreaseQuantityProductInCart(@RequestBody CartProductMapper cartProductMapper) throws ParseException {
        cartProductService.decreaseQuantity(cartProductMapper.getProductId(), userDetailService.getCurrentUser().getId(), cartProductMapper.getColor(), cartProductMapper.getSize());
        return "Update product success!";
    }

    @DeleteMapping("/user/cart")
    public ResponseEntity<?> deleteProductById(@RequestBody CartProductMapper cartProductMapper)
    {
        cartProductService.deleteProductInCart(cartProductMapper.getProductId(), userDetailService.getCurrentUser().getId(), cartProductMapper.getColor(), cartProductMapper.getSize());
        return ResponseEntity.ok(LocalVariable.messageDeleteCatSuccess);
    }
}
