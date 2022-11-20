package com.example.clothingstore.controller;

import com.example.clothingstore.model.AddressEntity;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.impl.AddressServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
public class AddressController {
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    UserDetailService userDetailService;


    @GetMapping("/user/address/getAll")
    public Object getAllAdressByUser()
    {
        return addressService.getAllByUserId(userDetailService.getCurrentUser().getId());
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, value = "/user/address/create")
    @ResponseBody
    public Object createAddress(@RequestBody AddressEntity address) throws ParseException {
        List<AddressEntity> addressEntities = addressService.getAllByUserId(userDetailService.getCurrentUser().getId());
        for (AddressEntity addressEntity: addressEntities)
        {
            addressEntity.setAdd_default(false);
            addressService.save(addressEntity);
        }
        address.setUserEntity(userDetailService.getCurrentUser());
        addressService.save(address);
        return getAllAdressByUser();
    }

    @PostMapping("/user/address/change")
    public Object changAddress(@RequestBody AddressEntity address) throws ParseException {
        AddressEntity addressEntity = addressService.findAddressById(address.getId());
        if (address.getAddress() != null)
        {
            addressEntity.setAddress(address.getAddress());
        }
        if (address.getPhoneNumber() != null)
        {
            addressEntity.setPhoneNumber(address.getPhoneNumber());
        }
        if (address.getName() != null)
        {
            addressEntity.setName(address.getName());
        }
        if (address.getNote() != null)
        {
            addressEntity.setNote(address.getNote());
        }
        address.setUserEntity(userDetailService.getCurrentUser());
        addressService.save(addressEntity);
        return getAllAdressByUser();
    }

    @DeleteMapping("/user/address/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable long id)
    {
        try
        {
            addressService.delete(id);
            return ResponseEntity.ok("Delete address success!");
        }
        catch (Exception e)
        {
            return ResponseEntity.ok("Cannot find address with id = "+ id);
        }

    }

    @PostMapping("/user/address/setDefault/{id}")
    public ResponseEntity<?> setAddressDefault(@PathVariable long id)
    {
        List<AddressEntity> addressEntities = addressService.getAllByUserId(userDetailService.getCurrentUser().getId());
        for (AddressEntity address: addressEntities)
        {
            address.setAdd_default(false);
            addressService.save(address);
        }
        AddressEntity addressDefault = addressService.findAddressById(id);
        addressDefault.setAdd_default(true);
        addressService.save(addressDefault);
        return ResponseEntity.ok("Set address default success!");
    }
}
