package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.AddressEntity;
import com.example.clothingstore.repository.AddressRepository;
import com.example.clothingstore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Override
    public AddressEntity findAddressById(Long id) {
        return addressRepository.findById(id).get();
    }

    @Override
    public AddressEntity save(AddressEntity address) {
        return addressRepository.save(address);
    }

    @Override
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    @Override
    public List<AddressEntity> getAllByUserId(Long id) {
        return addressRepository.getAllByUserEntityId(id);
    }

    @Override
    public AddressEntity getAddressDefaultOfUser(Long id) {
        return addressRepository.getDefaultAddressForUser(id);
    }
}
