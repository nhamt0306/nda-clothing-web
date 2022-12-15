package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.mapper.TypeMapper;
import com.example.clothingstore.model.OrderEntity;
import com.example.clothingstore.model.TransactionEntity;
import com.example.clothingstore.model.TypeEntity;
import com.example.clothingstore.service.impl.OrderServiceImpl;
import com.example.clothingstore.service.impl.ProductServiceImpl;
import com.example.clothingstore.service.impl.TransactionServiceImpl;
import com.example.clothingstore.service.impl.TypeServiceImpl;
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

@CrossOrigin(origins = "*")   //Để ghép AuthController với các controller khác
@RequestMapping
@RestController
public class TypeController {
    @Autowired
    TypeServiceImpl typeService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    TransactionServiceImpl transactionService;

    @GetMapping("/admin/type/getAll")
    public ResponseEntity<?> getAllType()
    {
        List<TypeEntity> typeEntityList = typeService.getAll();
        List<TypeMapper> responseList = new ArrayList<>();
        for (TypeEntity type : typeEntityList)
        {
            TypeMapper mapper = new TypeMapper(type.getQuantity(), type.getPrice(), type.getSize(), type.getColor(), type.getSale(), type.getSold(), type.getProductEntity().getId());
            responseList.add(mapper);
        }
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/admin/type/{id}")
    public ResponseEntity<?> getTypeById(@PathVariable long id){
        try {
            TypeEntity type = typeService.findTypeById(id);
            TypeMapper mapper = new TypeMapper(type.getQuantity(), type.getPrice(), type.getSize(), type.getColor(), type.getSale(), type.getSold(), type.getProductEntity().getId());
            return ResponseEntity.ok(mapper);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Cannot find type with id = " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/type/product/{id}")
    public ResponseEntity<?> getTypeByProductId(@PathVariable long id){
        try {
            List<TypeEntity> typeEntityList = typeService.getAllTypeByProduct(id);
            List<TypeMapper> responseList = new ArrayList<>();
            for (TypeEntity type : typeEntityList)
            {
                TypeMapper mapper = new TypeMapper(type.getQuantity(), type.getPrice(), type.getSize(), type.getColor(), type.getSale(), type.getSold(), type.getProductEntity().getId());
                responseList.add(mapper);
            }
            return ResponseEntity.ok(responseList);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Cannot find any type in product with id = " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/type/cas/{id}")
    public ResponseEntity<?> getTypeAndColorByProductId(@PathVariable long id)
    {
        return new ResponseEntity<>(typeService.getListColorAndSize(id), HttpStatus.OK);
    }

//    @PostMapping("/admin/type/create")
//    public Object createType(@RequestBody TypeDTO typeDTO) throws ParseException {
//        if (!productService.existByProductId(typeDTO.getProduct_id()))
//        {
//            return new ResponseEntity<>("Cannot find product with id = "+ typeDTO.getProduct_id(), HttpStatus.NOT_FOUND);
//        }
//        TypeEntity type = new TypeEntity();
//        type.setColor(typeDTO.getColor());
//        type.setSize(typeDTO.getSize());
//        type.setQuantity(typeDTO.getQuantity());
//        type.setPrice(typeDTO.getPrice());
//        type.setProductEntity(productService.findProductById(typeDTO.getProduct_id()));
//        type.setSale(0L);
//        type.setSold(0L);
//        type.setUpdate_at(new Timestamp(System.currentTimeMillis()));
//        type.setCreate_at(new Timestamp(System.currentTimeMillis()));
//        typeService.save(type);
//        TypeMapper mapper = new TypeMapper(type.getQuantity(), type.getPrice(), type.getSize(), type.getColor(), type.getSale(), type.getSold(), type.getProductEntity().getId());
//        return mapper;
//    }

    @PostMapping("/admin/type/create/{id}")
    public Object createType(@RequestBody Object req, @PathVariable long id) throws ParseException {
        List<Map<String, String>> typeListReq =(List<Map<String, String>>) req;
        List<TypeMapper> typeMappers = new ArrayList<>();
        for (Map<String, String> typeDTO:typeListReq)
        {
            if (!productService.existByProductId(id))
            {
                return new ResponseEntity<>("Cannot find product with id = "+ id, HttpStatus.NOT_FOUND);
            }
            TypeEntity type = new TypeEntity();
            type.setColor(typeDTO.get("color"));
            type.setSize(Long.parseLong(typeDTO.get("size")));
            type.setQuantity(Long.parseLong(typeDTO.get("quantity")));
            type.setPrice(Long.parseLong(typeDTO.get("price")));
            type.setProductEntity(productService.findProductById(id));
            type.setSale(0L);
            type.setSold(0L);
            type.setUpdate_at(new Timestamp(System.currentTimeMillis()));
            type.setCreate_at(new Timestamp(System.currentTimeMillis()));
            typeService.save(type);
            TypeMapper mapper = new TypeMapper(type.getQuantity(), type.getPrice(), type.getSize(), type.getColor(), type.getSale(), type.getSold(), type.getProductEntity().getId());
            typeMappers.add(mapper);
        }
        return typeMappers;
    }

    @DeleteMapping("/admin/type/{id}")
    public Object deleteTypeById(@PathVariable long id)
    {
        TypeEntity type = typeService.findTypeById(id);
        List<TransactionEntity> transactionEntity = transactionService.getTransactionByColorAndSizeAndProductId(type.getColor(), type.getSize(), type.getProductEntity().getId());
        for (TransactionEntity transactionEntity1: transactionEntity)
        {
            OrderEntity orderEntity = orderService.findOrderById(transactionEntity1.getOrderEntity().getId());
            if (orderEntity.getStatus().equals(LocalVariable.pendingMessage)) // Nếu tình trạng là đang đợi thì mới được hủy
            {
                orderEntity.setStatus(LocalVariable.cancelMessage);
                orderEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
                orderService.addNewOrder(orderEntity);
            }
        }

        typeService.delete(id);
        return ResponseEntity.ok("Delete type success!");
    }

    @PutMapping("/admin/type/{productId}")
    public Object updateType(@PathVariable long productId, @RequestBody Object req) throws ParseException {
        // check if product not exists
        if (!productService.existByProductId(productId)){
            return new ResponseEntity<>("Cannot find product with id = " + productId, HttpStatus.NOT_FOUND);
        }

        List<Map<String, String>> typeListReq = (List<Map<String, String>>) req;
        List<TypeMapper> typeMappers = new ArrayList<>();

        // get all type of products
        List<TypeEntity> typeEntityList = typeService.getAllTypeByProduct(productId);
        for (TypeEntity type : typeEntityList) {
            String color = type.getColor();
            String size = type.getSize().toString();
            String quantity = type.getQuantity().toString();
            String price = type.getPrice().toString();

            Map<String, String> map = new HashMap<String, String>();
            map.put("color", color);
            map.put("size", size);
            map.put("quantity", quantity);
            map.put("price", price);

            if (!typeListReq.contains(map)) {
                TypeEntity typeEntity = typeService.getTypeByColorAndSizeAndProductId(color, Long.parseLong(size), productId);
                typeEntity.setStatus(LocalVariable.disableStatus);
                typeService.save(typeEntity);
            }
        }

        for (Map<String, String> typeDTO : typeListReq)
        {
            TypeEntity typeEntity = typeService.getTypeByColorAndSizeAndProductId(typeDTO.get("color"), Long.parseLong(typeDTO.get("size")), productId);
            // if type exists
            if (typeEntity != null) {
                if (!typeEntity.getStatus().equals(LocalVariable.activeStatus)) {
                    typeEntity.setStatus(LocalVariable.activeStatus);
                }
                typeEntity.setQuantity(Long.parseLong(typeDTO.get("quantity")));
                typeEntity.setPrice(Long.parseLong(typeDTO.get("price")));
            }

            if (typeEntity == null) {
                typeEntity = new TypeEntity();
                typeEntity.setColor(typeDTO.get("color"));
                typeEntity.setSize(Long.parseLong(typeDTO.get("size")));
                typeEntity.setQuantity(Long.parseLong(typeDTO.get("quantity")));
                typeEntity.setPrice(Long.parseLong(typeDTO.get("price")));
                typeEntity.setProductEntity(productService.findProductById(productId));
                typeEntity.setSale(0L);
                typeEntity.setSold(0L);
                typeEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
                typeEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
            }

            typeService.save(typeEntity);

            TypeMapper mapper = new TypeMapper(typeEntity.getQuantity(),
                    typeEntity.getPrice(),
                    typeEntity.getSize(),
                    typeEntity.getColor(),
                    typeEntity.getSale(),
                    typeEntity.getSold(),
                    typeEntity.getProductEntity().getId());
            typeMappers.add(mapper);
        }
        return typeMappers;
    }
}
