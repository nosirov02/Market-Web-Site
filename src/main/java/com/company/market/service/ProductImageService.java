package com.company.market.service;

import com.company.market.dto.ImageDto;
import com.company.market.entity.ImageEntity;
import com.company.market.entity.ProductImageEntity;
import com.company.market.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductImageService {
    @Autowired
    private ProductImageRepository repository;
    @Autowired
    private ImageService attachService;

    public void create(Integer imageId, Integer productId) {
        ProductImageEntity entity = new ProductImageEntity();
        entity.setProductId(productId);
        entity.setImageId(imageId);
        repository.save(entity);
    }

    public void create(List<Integer> imageList, Integer productId) {
        imageList.forEach(id -> create(id, productId));
    }

    public List<ImageDto> getProductImageDtoList(Integer id) {
        List<ProductImageEntity> productImageList = repository.findAllByProductId(id);
        return productImageList.stream().map(e -> attachService.getImageDto(e.getImageId())).collect(Collectors.toList());
    }

    public List<ImageEntity> getProductImageList(Integer productId) {
        return repository.findAllByProductId(productId).stream().map(ProductImageEntity::getImage).collect(Collectors.toList());
    }
}
