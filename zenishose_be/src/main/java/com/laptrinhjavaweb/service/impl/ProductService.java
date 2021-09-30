package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.converter.ProductConverter;
import com.laptrinhjavaweb.dto.AttributeDTO;
import com.laptrinhjavaweb.dto.ColorDTO;
import com.laptrinhjavaweb.dto.GroupDTO;
import com.laptrinhjavaweb.dto.request.AttributeRequestDTO;
import com.laptrinhjavaweb.dto.request.Base64DTO;
import com.laptrinhjavaweb.dto.request.ProductRequestDTO;
import com.laptrinhjavaweb.dto.SlideDTO;
import com.laptrinhjavaweb.dto.response.AttributeResponseDTO;
import com.laptrinhjavaweb.dto.response.ProductResponseDTO;
import com.laptrinhjavaweb.dto.response.ResponseDTO;
import com.laptrinhjavaweb.entity.AttributeEntity;
import com.laptrinhjavaweb.entity.ProductEntity;
import com.laptrinhjavaweb.enums.ColorEnum;
import com.laptrinhjavaweb.repository.AttributeRepository;
import com.laptrinhjavaweb.repository.BrandRepository;
import com.laptrinhjavaweb.repository.ProductRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.service.IProductService;
import com.laptrinhjavaweb.util.UploadFileUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Pageable;
import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private UploadFileUtils uploadFileUtils;

    @Override
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
    }

//    @Override
//    public List<ProductResponseDTO> findAll(String brandCode) {
//        if (brandCode != null) {
//            return productRepository.findProductByBrandCode(brandCode).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
//        } else {
//            return productRepository.findAll().stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
//        }
//    }
    
    @Override
    public List<ProductResponseDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDTO> findAllCheckBandCode(Integer minValue, Integer maxValue, String brandCode, Pageable pageable) {
        if (brandCode != null) {
            if(maxValue==-1){
                return productRepository.findAllProductByBrandCodeAndActive(brandCode, pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
            }
            if(maxValue==0){
               maxValue = 200000000;
            }
            return productRepository.findAllProductByBrandCodeAndActive(minValue,maxValue,brandCode, pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
        } else {
            if(maxValue==-1){
                return productRepository.findAllProductActive(pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
            }
            if(maxValue==0){
                maxValue = 200000000;
            }
            return productRepository.findAllProductActive(minValue,maxValue,pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
        }
    }

    @Override
    public List<ProductResponseDTO> findAllProductActiveByName(Integer minValue, Integer maxValue,String name, Pageable pageable) {
        if(name.equals("none")){
            if(maxValue == -1){
                return productRepository.findAllProductActive(pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
            }
            if(maxValue==0){
                maxValue = 200000000;
            }
            return productRepository.findAllProductActive(minValue,maxValue,pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
        }
        else{
            if(maxValue==-1){
                return productRepository.findAllProductActiveByName(name,pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
            }
            if(maxValue==0){
                maxValue = 200000000;
            }
            return productRepository.findAllProductActiveByName(minValue,maxValue,name,pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
        }
    }

    @Override
    public List<ProductResponseDTO> findTop3ProductRelated(Integer number, Pageable pageable) {
        return productRepository.findTop3ProductRelated(number,pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
    }
    
    @Override
    public ProductResponseDTO findById(Long id) {
        ProductEntity productEntity = productRepository.findById(id).get();
        ProductResponseDTO result = productConverter.convertToResponseDTO(productEntity);
        return result;
    }

    @Override
    public ProductResponseDTO findById(Long id, String color, String size) {
        ProductEntity productEntity = productRepository.findById(id).get();
        ProductResponseDTO result = productConverter.convertToResponseDTO(productEntity);
        Set<String> sizes = new HashSet<>();
        Set<String> colors = new HashSet<>();
        for (AttributeEntity item: productEntity.getAttributes()) {
            sizes.add(item.getSize());
            colors.add(item.getColor());
        }
        List<ColorDTO> colorDTOS = new ArrayList<>();
        String[] sizeStrings = new String[sizes.size()];
        if (colors.size() > 0) {
            for (String item : colors) {
                ColorDTO colorDTO = new ColorDTO();
                colorDTO.setCode(item);
                for (ColorEnum colorEnum : ColorEnum.values()) {
                    if (colorEnum.name().equals(item)) {
                        colorDTO.setName(colorEnum.getValue());
                    }
                }
                colorDTOS.add(colorDTO);
            }
        }
        if (sizes.size() > 0) {
            int i = 0;
            for(String item :sizes){
                sizeStrings[i] = item;
                i++;
            }
        }
        result.setColor(colorDTOS);
        result.setSize(sizeStrings);
        if (StringUtils.isBlank(size) || StringUtils.isBlank(color)) {
            size = sizeStrings[0];
            color = colorDTOS.get(0).getCode();
        }
        AttributeEntity attributeEntity = attributeRepository.findBySizeAndColorAndProduct_Id(size, color, id);
        if (attributeEntity != null) {
            result.setImageNames(attributeEntity.getImages().split(","));
        } else {
            result.setImageNames("no-product.png".split(","));
        }
        return result;
    }

    @Override
    @Transactional
    public ProductResponseDTO save(ProductRequestDTO dto) {
        try {
            ProductEntity entity = productConverter.convertToEntity(dto);
            entity.setBrand(brandRepository.findByCode(dto.getBrandCode()));
            saveThumbnail(dto, entity);
            saveImage(dto, entity);
            return productConverter.convertToResponseDTO(productRepository.save(entity));
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public ProductResponseDTO update(long id, ProductRequestDTO dto) {
        try {
            ProductEntity existsCategory = productRepository.findById(id).get();
            ProductEntity updateCategory = productConverter.convertToEntity(dto);
            updateCategory.setImages(existsCategory.getImages());
            updateCategory.setThumbnail(existsCategory.getThumbnail());
            updateCategory.setBrand(brandRepository.findByCode(dto.getBrandCode()));
            updateCategory.setCreatedDate(existsCategory.getCreatedDate());
            updateCategory.setStatusProduct(true);
            saveThumbnail(dto, updateCategory);
            saveImage(dto, updateCategory);


            updateCategory = productRepository.save(updateCategory);
            return productConverter.convertToResponseDTO(updateCategory);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public void delete(long[] ids) {
        for (long id: ids) {
            ProductEntity existsCategory = productRepository.findById(id).get();
            existsCategory.setStatusProduct(false);
            productRepository.save(existsCategory);
        }
    }

    @Override
    public List<AttributeDTO> findAttributeList(Long productId) {
        List<AttributeDTO> result = new ArrayList<>();
        List<AttributeEntity> attributeEntities = attributeRepository.findByProduct_Id(productId);
        for (AttributeEntity item: attributeEntities) {
            AttributeDTO attributeDTO = new AttributeDTO();
            attributeDTO.setSize(item.getSize());
            attributeDTO.setColor(item.getColor());
            attributeDTO.setId(item.getId());
            result.add(attributeDTO);
        }
        return result;
    }

    @Override
    public AttributeResponseDTO findAttribute(Long id) {
        AttributeEntity attributeEntity = attributeRepository.findById(id).get();
        AttributeResponseDTO result = new AttributeResponseDTO();
        result.setId(attributeEntity.getId());
        result.setColor(attributeEntity.getColor());
        result.setSize(attributeEntity.getSize());
        result.setImages(attributeEntity.getImages().split(","));
        return result;
    }

    @Override
    @Transactional
    public AttributeResponseDTO addAttribute(Long productId, AttributeRequestDTO attributeRequestDTO) {
        try {
            AttributeResponseDTO result = new AttributeResponseDTO();
            AttributeEntity attributeEntity = new AttributeEntity();
            attributeEntity.setColor(attributeRequestDTO.getColor().toUpperCase());
            attributeEntity.setSize(attributeRequestDTO.getSize());
            attributeEntity.setImages(String.join(",", attributeRequestDTO.getImages()));
            attributeEntity.setProduct(productRepository.findById(productId).get());
            attributeEntity = attributeRepository.save(attributeEntity);
            result.setColor(attributeEntity.getColor());
            result.setSize(attributeEntity.getSize());
            result.setImages(attributeEntity.getImages().split(","));
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public AttributeResponseDTO updateAttribute(Long id, AttributeRequestDTO attributeRequestDTO) {
        try {
            AttributeResponseDTO result = new AttributeResponseDTO();
            AttributeEntity existAttribute = attributeRepository.findById(id).get();
            existAttribute.setColor(attributeRequestDTO.getColor());
            existAttribute.setSize(attributeRequestDTO.getSize());
            existAttribute.setImages(String.join(",", attributeRequestDTO.getImages()));
            existAttribute = attributeRepository.save(existAttribute);
            result.setColor(existAttribute.getColor());
            result.setSize(existAttribute.getSize());
            result.setImages(existAttribute.getImages().split(","));
            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public void deleteAttribute(long[] ids) {
        for (long id: ids) {
            attributeRepository.deleteById(id);
        }
    }

    @Override
    public ResponseDTO findProduct(String filter, Pageable pageable) {
        ResponseDTO result = new ResponseDTO();
        if("H".equals(filter)){
            Page<ProductEntity> page = productRepository.findProductHot(pageable);
            result.setTotalElements((int)page.getTotalElements());
            result.setData(page.stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList()));
        }else if("N".equals(filter)){
            Page<ProductEntity> page = productRepository.findProductNew(pageable);
            result.setTotalElements((int)page.getTotalElements());
            result.setData(page.stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList()));
        } else {
            result.setData(productRepository.findAll(pageable).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public List<ProductResponseDTO> findProductByAdmin(String filter, Pageable pageable) {
        if("H".equals(filter)){
            return productRepository.findProductHot().stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
        }else if("N".equals(filter)){
            return productRepository.findProductNew().stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
        }
        return productRepository.findAll().stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
    }

    @Override
    public List<SlideDTO> getSlides() {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<SlideDTO> results = new ArrayList<>();
        for (ProductEntity item: productEntities) {
            SlideDTO slideDTO = new SlideDTO();
            slideDTO.setImageName(item.getThumbnail());
            results.add(slideDTO);
        }
        return results;
    }

    @Override
    public List<GroupDTO> getGroups() {
        List<GroupDTO> results = new ArrayList<>();
        GroupDTO newProduct = new GroupDTO();
        newProduct.setCode("new");
        List<ProductResponseDTO> productNews = productRepository.findByIsNew(1).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
        newProduct.setProducts(productNews);
        results.add(newProduct);
        GroupDTO bestSellProduct = new GroupDTO();
        bestSellProduct.setCode("sell");
        List<ProductResponseDTO> productHots = productRepository.findByIsHot(1).stream().map(item -> productConverter.convertToResponseDTO(item)).collect(Collectors.toList());
        bestSellProduct.setProducts(productHots);
        results.add(bestSellProduct);
        return results;
    }

    private void saveImage(ProductRequestDTO dto, ProductEntity entity) {
        if (dto.getImageUploads() != null && dto.getImageUploads().size() > 0) {
            List<String> images = new ArrayList<>();
            for (Base64DTO item: dto.getImageUploads()) {
                if (item.getImageBase64() != null) {
                    String path = item.getImageName();
                    byte[] bytes = Base64.decodeBase64(item.getImageBase64().getBytes());
                    uploadFileUtils.writeOrUpdate(path, bytes);
                    images.add(item.getImageName());
                }
            }
            if (images.size() > 0) {
                entity.setImages(String.join(",", images));
            }
        }
    }

    private void saveThumbnail(ProductRequestDTO dto, ProductEntity entity) {
        if (dto.getThumbnail() != null && StringUtils.isNotBlank(dto.getThumbnail().getImageBase64())) {
            if (dto.getThumbnail().getImageBase64() != null) {
                String path = dto.getThumbnail().getImageName();
                byte[] bytes = Base64.decodeBase64(dto.getThumbnail().getImageBase64().getBytes());
                uploadFileUtils.writeOrUpdate(path, bytes);
                entity.setThumbnail(dto.getThumbnail().getImageName());
            }
        }
    }
}
