package com.code.ecommercebackend.services.variant;

import com.code.ecommercebackend.dtos.response.variant.VariantResponse;
import com.code.ecommercebackend.exceptions.DataNotFoundException;
import com.code.ecommercebackend.mappers.attribute.VariantMapper;
import com.code.ecommercebackend.models.AttributeValue;
import com.code.ecommercebackend.models.Inventory;
import com.code.ecommercebackend.models.ProductAttribute;
import com.code.ecommercebackend.models.Variant;
import com.code.ecommercebackend.repositories.InventoryRepository;
import com.code.ecommercebackend.repositories.ProductAttributeRepository;
import com.code.ecommercebackend.repositories.VariantRepository;
import com.code.ecommercebackend.services.BaseServiceImpl;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class VariantServiceImpl extends BaseServiceImpl<Variant, String> implements VariantService {

    private final VariantRepository variantRepository;
    private final VariantMapper variantMapper;
    private final InventoryRepository inventoryRepository;
    private final ProductAttributeRepository productAttributeRepository;

    public VariantServiceImpl(MongoRepository<Variant, String> repository,
                              VariantRepository variantRepository,
                              VariantMapper variantMapper, InventoryRepository inventoryRepository, ProductAttributeRepository productAttributeRepository) {
        super(repository);
        this.variantRepository = variantRepository;
        this.variantMapper = variantMapper;
        this.inventoryRepository = inventoryRepository;
        this.productAttributeRepository = productAttributeRepository;
    }

    @Override
    public List<VariantResponse> findAllByVariantId(List<String> variantsId) {
        List<Variant> variants = variantRepository.findAllById(variantsId);
        return variants.stream().map(variantMapper::toVariantResponse).toList();
    }



    @Override
    public VariantResponse findByProductIdAndAttribute(String productId, String attr1, String attr2) throws DataNotFoundException {

        Variant variant;
        if(attr2 != null) {
            variant = variantRepository
                    .findByProductIdAndAttributeValue1AndAttributeValue2(productId, attr1, attr2)
                    .orElseThrow(() -> new DataNotFoundException("variant not found"));
        } else {
            variant = variantRepository
                    .findByProductIdAndAttributeValue1(productId, attr1)
                    .orElseThrow(() -> new DataNotFoundException("variant not found"));
        }
        return mapToVariantResponse(variant);
    }

    public VariantResponse mapToVariantResponse(Variant variant) {
        VariantResponse variantResponse = variantMapper.toVariantResponse(variant);
        List<Inventory> inventories = inventoryRepository.findByVariantId(variant.getId());
        int quantity = Math.max(0,
                inventories.stream().mapToInt(i -> i.getImportQuantity() - i.getSaleQuantity()).sum());
        variantResponse.setQuantity(quantity);
        List<ProductAttribute> attributes = productAttributeRepository.findByProductId(variant.getProduct().getId());
        ProductAttribute attribute = attributes.get(0);
        List<AttributeValue> attributeValues = attribute.getAttributeValues();
        for (AttributeValue attributeValue : attributeValues) {
            if(attributeValue.getValue().equals(variant.getAttributeValue1())) {
                variantResponse.setImage(attributeValue.getImage());
            }
        }
        return variantResponse;
    }


}