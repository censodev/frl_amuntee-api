package com.printway.business.services;

import com.printway.business.dto.ImageUpload;
import com.printway.business.dto.shopify.ShopifyProduct;
import com.printway.business.dto.shopify.ShopifyProductVariant;
import com.printway.business.models.*;
import com.printway.business.repositories.ProductImageRepository;
import com.printway.business.repositories.ProductRepository;
import com.printway.business.repositories.ProductVariantRepository;
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

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

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
        var productToSaveDB = convert(null, shopifyProduct, product.getStore(), product.getCreatedBy(), product.getProductTemplate(), null);
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
        var productToSaveDB = convert(id, shopifyProduct, product.getStore(), product.getCreatedBy(), product.getProductTemplate(), product.getVariants());
        productToSaveDB = productRepository.save(productToSaveDB);

//        return
        if (productToSaveDB.getImages() != null)
            productToSaveDB.setImages(productToSaveDB.getImages().stream().peek(img -> img.setProduct(null)).collect(Collectors.toList()));
        if (productToSaveDB.getVariants() != null)
            productToSaveDB.setVariants(productToSaveDB.getVariants().stream().peek(var -> var.setProduct(null)).collect(Collectors.toList()));
        return productToSaveDB;
    }

    @Override
    public ProductImage saveAndSyncImage(ImageUpload imageUpload) {
        var shopifyImage = shopifyService.saveImage(imageUpload.getSrc(), imageUpload.getShopifyProductId(), imageUpload.getStoreId());
        var product = productRepository.findByShopifyId(shopifyImage.getProductId());
        var image = ProductImage.builder()
                .position(shopifyImage.getPosition())
                .width(shopifyImage.getWidth())
                .height(shopifyImage.getHeight())
                .shopifyId(shopifyImage.getId())
                .src(shopifyImage.getSrc())
                .product(product)
                .build();
        image = productImageRepository.save(image);
        product.setImages(null);
        product.setVariants(null);
        image.setProduct(product);
        return image;
    }

    @Override
    public ProductImage saveAndSyncImageBase64(ImageUpload imageUpload) {
        var shopifyImage = shopifyService.saveImageAsBase64(imageUpload.getSrc(), imageUpload.getShopifyProductId(), imageUpload.getStoreId());
        var product = productRepository.findByShopifyId(shopifyImage.getProductId());
        var image = ProductImage.builder()
                .position(shopifyImage.getPosition())
                .width(shopifyImage.getWidth())
                .height(shopifyImage.getHeight())
                .shopifyId(shopifyImage.getId())
                .src(shopifyImage.getSrc())
                .product(product)
                .build();
        image = productImageRepository.save(image);
        product.setImages(null);
        product.setVariants(null);
        image.setProduct(product);
        return image;
    }

    @Override
    public void deleteAndSyncImage(Long id, Long shopifyProductId, int storeId) {
        shopifyService.deleteImage(id, shopifyProductId, storeId);
        productImageRepository.delete(productImageRepository.findByShopifyId(id));
    }

    @Override
    public Product convert(Integer id,
                           ShopifyProduct shopifyProduct,
                           Store store,
                           Integer createdBy,
                           ProductTemplate productTemplate,
                           List<ProductVariant> variants) {
        var prd = productRepository.findById(id).orElse(new Product());
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
                                .filter(var -> var.getShopifyId() == prdVariant.getId())
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
//                if (productImage.getShopifyId() == null) {
//                    img.setAttachment(productImage.getSrc());
//                    return img;
//                }
//                img.setId(productImage.getShopifyId());
//                return img;
//            }).collect(Collectors.toList()));
        return shopifyPrd;
    }

    @Override
    public ProductVariant findVariant(Integer id) {
        var variant = this.productVariantRepository.findById(id).orElse(null);
        var product = variant.getProduct();
        product.setVariants(null);
        product.setImages(product.getImages().stream().peek(img -> img.setProduct(null)).collect(Collectors.toList()));
        variant.setProduct(product);
        return variant;
    }

    @Override
    public ProductVariant updateVariant(Integer id, ProductVariant variant, int storeId) {
        var product = variant.getProduct();
        product.setVariants(null);
        product.setImages(product.getImages().stream().peek(img -> img.setProduct(null)).collect(Collectors.toList()));
        var shopifyVariant = ShopifyProductVariant.builder()
                .id(variant.getShopifyId())
                .barcode(variant.getBarcode())
                .option1(variant.getOption1())
                .option2(variant.getOption2())
                .option3(variant.getOption3())
                .price(variant.getPrice())
                .compareAtPrice(variant.getCompareAtPrice())
                .sku(variant.getSku())
                .imageId(variant.getImageId())
                .build();
        shopifyVariant = shopifyService.updateProductVariant(shopifyVariant, storeId);
        variant = ProductVariant.builder()
                .id(id)
                .shopifyId(shopifyVariant.getId())
                .barcode(shopifyVariant.getBarcode())
                .option1(shopifyVariant.getOption1())
                .option2(shopifyVariant.getOption2())
                .option3(shopifyVariant.getOption3())
                .price(shopifyVariant.getPrice())
                .compareAtPrice(shopifyVariant.getCompareAtPrice())
                .sku(shopifyVariant.getSku())
                .imageId(shopifyVariant.getImageId())
                .createdAt(shopifyVariant.getCreatedAt())
                .updatedAt(shopifyVariant.getUpdatedAt())
                .product(product)
                .build();
        variant = productVariantRepository.save(variant);
        variant.setProduct(product);
        return variant;
    }

    @Override
    public ProductVariant createVariant(ProductVariant variant, int storeId) {
        var product = variant.getProduct();
        product.setVariants(null);
        product.setImages(product.getImages().stream().peek(img -> img.setProduct(null)).collect(Collectors.toList()));
        var shopifyVariant = ShopifyProductVariant.builder()
                .id(variant.getShopifyId())
                .barcode(variant.getBarcode())
                .option1(variant.getOption1())
                .option2(variant.getOption2())
                .option3(variant.getOption3())
                .price(variant.getPrice())
                .compareAtPrice(variant.getCompareAtPrice())
                .sku(variant.getSku())
                .imageId(variant.getImageId())
                .build();
        shopifyVariant = shopifyService.saveProductVariant(variant.getProduct().getShopifyId(), shopifyVariant, storeId);
        variant = ProductVariant.builder()
                .shopifyId(shopifyVariant.getId())
                .barcode(shopifyVariant.getBarcode())
                .option1(shopifyVariant.getOption1())
                .option2(shopifyVariant.getOption2())
                .option3(shopifyVariant.getOption3())
                .price(shopifyVariant.getPrice())
                .compareAtPrice(shopifyVariant.getCompareAtPrice())
                .sku(shopifyVariant.getSku())
                .imageId(shopifyVariant.getImageId())
                .createdAt(shopifyVariant.getCreatedAt())
                .updatedAt(shopifyVariant.getUpdatedAt())
                .product(product)
                .build();
        variant = productVariantRepository.save(variant);
        variant.setProduct(product);
        return variant;
    }
}
