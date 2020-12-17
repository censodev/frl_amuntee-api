package com.printway.business.services;

import com.printway.business.dto.shopify.ShopifyProduct;
import com.printway.business.dto.shopify.ShopifyProductImage;
import com.printway.business.dto.shopify.ShopifyProductVariant;
import com.printway.business.models.*;
import com.printway.business.repositories.ProductRepository;
import com.printway.business.utils.SkuUtil;
import com.printway.common.time.TimeParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopifyService shopifyService;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        var products = productRepository.findAll(pageable);
        products.forEach(prd -> {
            prd.setVariants(null);
            if (prd.getImages() != null)
                prd.setImages(prd.getImages().stream().peek(img -> img.setProduct(null)).collect(Collectors.toList()));
        });
        return products;
    }

    @Override
    public Page<Product> findAllByCreatedBy(Integer createdBy, Pageable pageable) {
        var products = productRepository.findAllByCreatedBy(createdBy, pageable);
        products.forEach(prd -> {
            prd.setVariants(null);
            if (prd.getImages() != null)
                prd.setImages(prd.getImages().stream().peek(img -> img.setProduct(null)).collect(Collectors.toList()));
        });
        return products;
    }

    @Override
    public Product find(int id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null)
            return null;
        if (product.getImages() != null)
            product.setImages(product.getImages().stream().peek(img -> img.setProduct(null)).collect(Collectors.toList()));
        if (product.getVariants() != null)
            product.setVariants(product.getVariants().stream().peek(var -> var.setProduct(null)).collect(Collectors.toList()));
        return product;
    }

    @Override
    public Product saveAndSync(Product product) {
//        create on shopify
        var shopifyProduct = convert(product);
        shopifyProduct = shopifyService.saveProduct(shopifyProduct, product.getStore().getId());

//        create on db
        var productToSaveDB = convert(null, shopifyProduct, product.getStore(), product.getCreatedBy(), product.getProductTemplate(), null, null);
        productToSaveDB = productRepository.save(productToSaveDB);

//        return
        if (productToSaveDB.getImages() != null)
            productToSaveDB.setImages(productToSaveDB.getImages().stream().peek(img -> img.setProduct(null)).collect(Collectors.toList()));
        if (productToSaveDB.getVariants() != null)
            productToSaveDB.setVariants(productToSaveDB.getVariants().stream().peek(var -> var.setProduct(null)).collect(Collectors.toList()));
        return productToSaveDB;
    }

    @Override
    public Product updateAndSync(int id, Product product) {
//        update shopify
        var shopifyProduct = convert(product);
        shopifyProduct = shopifyService.updateProduct(shopifyProduct, product.getStore().getId());

//        update db
        var productToSaveDB = convert(id, shopifyProduct, product.getStore(), product.getCreatedBy(), product.getProductTemplate(), product.getVariants(), product.getImages());
        productToSaveDB = productRepository.save(productToSaveDB);

//        return
        if (productToSaveDB.getImages() != null)
            productToSaveDB.setImages(productToSaveDB.getImages().stream().peek(img -> img.setProduct(null)).collect(Collectors.toList()));
        if (productToSaveDB.getVariants() != null)
            productToSaveDB.setVariants(productToSaveDB.getVariants().stream().peek(var -> var.setProduct(null)).collect(Collectors.toList()));
        return productToSaveDB;
    }

    @Override
    public Product convert(Integer id,
                           ShopifyProduct shopifyProduct,
                           Store store,
                           Integer createdBy,
                           ProductTemplate productTemplate,
                           List<ProductVariant> variants,
                           List<ProductImage> images) {
        var prd = new Product();
        prd.setId(id);
        prd.setShopifyId(shopifyProduct.getId());
        prd.setBodyHtml(shopifyProduct.getBodyHtml());
        prd.setCreatedAt(TimeParser.parseZonedDateTimeToLocalDateTime(shopifyProduct.getCreatedAt()));
        prd.setUpdatedAt(TimeParser.parseZonedDateTimeToLocalDateTime(shopifyProduct.getUpdatedAt()));
        prd.setProductType(shopifyProduct.getProductType());
        prd.setPublishedAt(TimeParser.parseZonedDateTimeToLocalDateTime(shopifyProduct.getPublishedAt()));
        prd.setStore(store);
        prd.setProductTemplate(productTemplate);
        prd.setCreatedBy(createdBy);
        prd.setVendor(shopifyProduct.getVendor());
        prd.setTitle(shopifyProduct.getTitle());
        prd.setTags(shopifyProduct.getTags());
        prd.setPublishedScope(shopifyProduct.getPublishedScope());
        prd.setStatus(shopifyProduct.getStatus());
        if (shopifyProduct.getVariants() != null && shopifyProduct.getVariants().size() > 0)
            prd.setVariants(shopifyProduct.getVariants().stream().map(prdVariant -> {
                var variant = variants != null
                        ? variants.stream()
                                .filter(var -> var.getOption1().equals(prdVariant.getOption1()))
                                .findFirst().orElse(new ProductVariant())
                        : new ProductVariant();
                variant.setBarcode(prdVariant.getBarcode());
                variant.setCompareAtPrice(prdVariant.getCompareAtPrice());
                variant.setCreatedAt(prdVariant.getCreatedAt());
                variant.setImageId(prdVariant.getImageId());
                variant.setPrice(prdVariant.getPrice());
                variant.setTitle(prdVariant.getTitle());
                variant.setSku(prdVariant.getSku());
                variant.setUpdatedAt(prdVariant.getUpdatedAt());
                variant.setShopifyId(prdVariant.getId());
                variant.setOption1(prdVariant.getOption1());
                variant.setOption2(prdVariant.getOption2());
                variant.setOption3(prdVariant.getOption3());
                variant.setProduct(prd);
                return variant;
            }).collect(Collectors.toList()));
        if (shopifyProduct.getImages() != null && shopifyProduct.getImages().size() > 0)
            prd.setImages(shopifyProduct.getImages().stream().map(productImage -> {
                var img = new ProductImage();
                // TODO: Xử lí phần này lúc sửa sản phẩm -> ko có sys id -> thêm mới ảnh
//                img.setId();
                img.setShopifyId(productImage.getId());
                img.setHeight(productImage.getHeight());
                img.setPosition(productImage.getPosition());
                img.setWidth(productImage.getWidth());
                img.setSrc(productImage.getSrc());
                img.setProduct(prd);
                return img;
            }).collect(Collectors.toList()));
        return prd;
    }

    @Override
    public ShopifyProduct convert(Product product) {
        var shopifyPrd = new ShopifyProduct();
        shopifyPrd.setId(product.getShopifyId());
        shopifyPrd.setBodyHtml(product.getBodyHtml());
        shopifyPrd.setCreatedAt(TimeParser.parseLocalDateTimeToISOString(product.getCreatedAt()));
        shopifyPrd.setUpdatedAt(TimeParser.parseLocalDateTimeToISOString(product.getUpdatedAt()));
        shopifyPrd.setProductType(product.getProductType());
        shopifyPrd.setPublishedAt(TimeParser.parseLocalDateTimeToISOString(product.getPublishedAt()));
        shopifyPrd.setVendor(product.getVendor());
        shopifyPrd.setTitle(product.getTitle());
        shopifyPrd.setTags(product.getTags());
        shopifyPrd.setPublishedScope(product.getPublishedScope());
        shopifyPrd.setStatus(product.getStatus());
        if (product.getVariants() != null && product.getVariants().size() > 0)
            shopifyPrd.setVariants(product.getVariants().stream().map(prdVariant -> {
                var variant = new ShopifyProductVariant();
                variant.setBarcode(prdVariant.getBarcode());
                variant.setCompareAtPrice(prdVariant.getCompareAtPrice());
                variant.setCreatedAt(prdVariant.getCreatedAt());
                variant.setImageId(prdVariant.getImageId());
                variant.setPrice(prdVariant.getPrice());
                variant.setTitle(prdVariant.getTitle());
                variant.setSku(prdVariant.getSku());
                variant.setUpdatedAt(prdVariant.getUpdatedAt());
                variant.setId(prdVariant.getShopifyId());
                variant.setOption1(prdVariant.getOption1());
                variant.setOption2(prdVariant.getOption2());
                variant.setOption3(prdVariant.getOption3());
                return variant;
            }).collect(Collectors.toList()));
//        if (product.getImages() != null && product.getImages().size() > 0)
//            shopifyPrd.setImages(product.getImages().stream().map(productImage -> {
//                var img = new ShopifyProductImage();
//                if (productImage.getShopifyId() == 0) {
//                    img.setAttachment(productImage.getAttachment());
//                    return img;
//                }
//                img.setId(productImage.getShopifyId());
//                img.setHeight(productImage.getHeight());
//                img.setPosition(productImage.getPosition());
//                img.setWidth(productImage.getWidth());
//                img.setSrc(productImage.getSrc());
//                return img;
//            }).collect(Collectors.toList()));
        return shopifyPrd;
    }
}
