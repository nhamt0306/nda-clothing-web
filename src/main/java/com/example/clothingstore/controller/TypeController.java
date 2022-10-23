package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.dto.ProductDTO;
import com.example.clothingstore.dto.TypeDTO;
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
        return ResponseEntity.ok(typeService.getAll());
    }

    @GetMapping("/admin/type/{id}")
    public ResponseEntity<?> getTypeById(@PathVariable long id){
        try {
            return ResponseEntity.ok(typeService.findTypeById(id));
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + id, HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/admin/type/create")
    public Object createType(@RequestBody TypeDTO typeDTO) throws ParseException {
        TypeEntity type = new TypeEntity();

        type.setColor(typeDTO.getColor());
        type.setSize(typeDTO.getSize());
        type.setQuantity(typeDTO.getQuantity());
        type.setPrice(typeDTO.getPrice());
        type.setProductEntity(productService.findProductById(typeDTO.getProduct_id()));
        type.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        type.setCreate_at(new Timestamp(System.currentTimeMillis()));
        return typeService.save(type);
    }

    @DeleteMapping("/admin/type/{id}")
    public ResponseEntity<?> deleteTypeById(@PathVariable long id)
    {
        typeService.delete(id);
        return ResponseEntity.ok(LocalVariable.messageDeleteCatSuccess);
    }
}
