package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.ProductDTO;
import com.example.clothingstore.dto.TypeDTO;
import com.example.clothingstore.mapper.TypeMapper;
import com.example.clothingstore.model.CategoryEntity;
import com.example.clothingstore.model.ProductEntity;
import com.example.clothingstore.model.TypeEntity;
import com.example.clothingstore.service.impl.ProductServiceImpl;
import com.example.clothingstore.service.impl.TypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")   //Để ghép AuthController với các controller khác
@RequestMapping
@RestController
public class TypeController {
    @Autowired
    TypeServiceImpl typeService;
    @Autowired
    ProductServiceImpl productService;

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
            return ResponseEntity.ok(typeService.findTypeById(id));
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

    @PostMapping("/admin/type/create")
    public Object createType(@RequestBody TypeDTO typeDTO) throws ParseException {
        if (!productService.existByProductId(typeDTO.getProduct_id()))
        {
            return new ResponseEntity<>("Cannot find product with id = "+ typeDTO.getProduct_id(), HttpStatus.NOT_FOUND);
        }
        TypeEntity type = new TypeEntity();
        type.setColor(typeDTO.getColor());
        type.setSize(typeDTO.getSize());
        type.setQuantity(typeDTO.getQuantity());
        type.setPrice(typeDTO.getPrice());
        type.setProductEntity(productService.findProductById(typeDTO.getProduct_id()));
        type.setSale(0L);
        type.setSold(0L);
        type.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        type.setCreate_at(new Timestamp(System.currentTimeMillis()));
        typeService.save(type);
        TypeMapper mapper = new TypeMapper(type.getQuantity(), type.getPrice(), type.getSize(), type.getColor(), type.getSale(), type.getSold(), type.getProductEntity().getId());
        return mapper;
    }

    @DeleteMapping("/admin/type/{id}")
    public ResponseEntity<?> deleteTypeById(@PathVariable long id)
    {
        typeService.delete(id);
        return ResponseEntity.ok("Delete type success!");
    }
}
