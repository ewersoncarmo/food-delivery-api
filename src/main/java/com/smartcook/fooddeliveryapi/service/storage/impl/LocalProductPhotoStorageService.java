package com.smartcook.fooddeliveryapi.service.storage.impl;

import com.smartcook.fooddeliveryapi.domain.event.ProductPhotoStorageEvent;
import com.smartcook.fooddeliveryapi.service.storage.AbstractProductPhotoStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "api.storage.type", havingValue = "local")
public class LocalProductPhotoStorageService extends AbstractProductPhotoStorage {

    @Override
    protected void store(ProductPhotoStorageEvent event) {
        System.out.println("Storing product photo " + event.getFileName());
    }

    @Override
    public String retrieve(String fileName) {
        System.out.println("Retrieving product photo " + fileName);
        return fileName;
    }

    @Override
    public void remove(String fileName2) {
        System.out.println("Removing product photo " + fileName2);
    }
}
