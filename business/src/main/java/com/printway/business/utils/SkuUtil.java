package com.printway.business.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class SkuUtil {
    private String supplierCode = null;
    private String productCode = null;
    private String sellerCode = null;
    private String designCode = null;

    public SkuUtil(String sku) {
        try {
            var elements = sku.replace(" ", "").split("-");
            supplierCode = elements[0];
            productCode = elements[1];
            sellerCode = elements[2].substring(0, 2);
            designCode = elements[2].substring(2);
        } catch (Exception ex) {
            log.warn("SKU format is invalid");
        }
    }

}
