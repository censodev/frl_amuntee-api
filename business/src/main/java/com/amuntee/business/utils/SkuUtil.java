package com.amuntee.business.utils;

import lombok.Getter;

@Getter
public class SkuUtil {
    private final String supplierCode;
    private final String productCode;
    private final String sellerCode;
    private final String designCode;

    public SkuUtil(String sku) {
        String[] elements = sku.split("-");
        supplierCode = elements[0];
        productCode = elements[1];
        sellerCode = elements[2].substring(0, 2);
        designCode = elements[2].substring(2);
    }

}
