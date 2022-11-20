package com.example.clothingstore.service;

import com.example.clothingstore.model.AddressEntity;

import java.util.List;

public interface AddressService {
    AddressEntity findAddressById(Long id);
    AddressEntity save(AddressEntity address);
    void delete(Long id);
    List<AddressEntity> getAllByUserId(Long id);
    AddressEntity getAddressDefaultOfUser(Long id);
    Boolean existAddressByUserId(Long id);
    boolean existById(Long id);
}
