package com.printway.business.repositories;

import com.printway.business.dto.statistic.*;
import com.printway.business.utils.BusinessUtil;
import com.printway.common.time.TimeParser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderProductCustomRepositoryImpl implements OrderProductCustomRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ProductTypeStatistic> statForProductType(StatisticQueryParam params) {
//        select
//        prdt.name,
//                count(ordprd.order_code),
//                sum(ordprd.quantity),
//                sum(ordprd.price * ordprd.quantity)
//        from orders_products ordprd
//        join orders ord
//        on ordprd.order_code = ord.code
//        left join products prd
//        on prd.code = ordprd.product_code
//        left join product_types prdt
//        on prdt.id = prd.type_id
//        where ord.revenue > 0
//        and ord.financial_status = 'paid'
//        and ord.created_at between date(:from) and date(:to)
//        and ord.store_id = :storeId
//        and ordprd.seller_code = :sellerCode
//        group by prdt.name
//        order by count(ordprd.order_code) desc
//        ;
        var qrStore = params.getStoreId() != null ? "and ord.store_id = :storeId\n" : "";
        var qrSeller = !params.getSellerCode().equals("") ? "and ordprd.seller_code = :sellerCode\n" : "";
        var sql =   "        select\n" +
                    "        prdt.name,\n" +
                    "                count(ordprd.order_code),\n" +
                    "                sum(ordprd.quantity),\n" +
                    "                sum(ordprd.price * ordprd.quantity)\n" +
                    "        from orders_products ordprd\n" +
                    "        join orders ord\n" +
                    "        on ordprd.order_code = ord.code\n" +
                    "        left join products prd\n" +
                    "        on prd.code = ordprd.product_code\n" +
                    "        left join product_types prdt\n" +
                    "        on prdt.id = prd.type_id\n" +
                    "        where ord.revenue > 0\n" +
                    "        and ord.financial_status = 'paid'\n" +
                    "        and ord.created_at between date(:from) and date(:to)\n" +
                    qrStore +
                    qrSeller +
                    "        group by prdt.name\n" +
                    "        order by count(ordprd.order_code) desc\n" +
                    "        ;";

        var query = em.createNativeQuery(sql);
        query.setParameter("from", TimeParser.parseLocalDateTimeToISOString(params.getFrom()));
        query.setParameter("to", TimeParser.parseLocalDateTimeToISOString(params.getTo()));

        if (params.getStoreId() != null) {
            query.setParameter("storeId", params.getStoreId());
        }

        if (!params.getSellerCode().equals("")) {
            query.setParameter("sellerCode", params.getSellerCode());
        }

        List<Object[]> rsList = query.getResultList();
        var rs = new ArrayList<ProductTypeStatistic>();
        for (Object[] obj : rsList) {
            var stat = new ProductTypeStatistic();
            stat.setTitle(obj[0] == null ? null : String.valueOf(obj[0]));
            stat.setOrderCount(obj[1] == null ? null : Integer.parseInt(String.valueOf(obj[1])));
            stat.setProductQuantity(obj[2] == null ? null : Integer.parseInt(String.valueOf(obj[2])));
            stat.setRevenue(obj[3] == null ? null : Math.round((double) obj[3] * 100) / 100.00);
            rs.add(stat);
        }
        return rs;
    }

    @Override
    public List<ProductDesignStatistic> statForProductDesign(StatisticQueryParam params) {
//        select
//        prd.name,
//                ordprd.sku,
//                ordprd.seller_code,
//                prd.picture,
//                sum(ordprd.quantity) productQuantity,
//                sum(ordprd.price * ordprd.quantity) revenue
//        from orders_products ordprd
//        join orders ord on ordprd.order_code = ord.code
//        left join products prd on prd.code = ordprd.product_code
//        where ord.revenue > 0
//        and ord.financial_status = 'paid'
//        and ordprd.sku is not null
//        and ordprd.sku != ''
//        and ord.created_at between date(:from) and date(:to)
//        and ord.store_id = :storeId
//        and ordprd.seller_code = :sellerCode
//        group by prd.name, ordprd.sku, ordprd.seller_code, prd.picture
//        order by sum(ordprd.price * ordprd.quantity) desc
//        limit 10
//        ;
        var qrStore = params.getStoreId() != null ? "and ord.store_id = :storeId\n" : "";
        var qrSeller = !params.getSellerCode().equals("") ? "and ordprd.seller_code = :sellerCode\n" : "";
        var sql =   "select\n" +
                    "    prd.name,\n" +
                    "    ordprd.sku,\n" +
                    "    ordprd.seller_code,\n" +
                    "    prd.picture,\n" +
                    "    sum(ordprd.quantity) productQuantity,\n" +
                    "    sum(ordprd.price * ordprd.quantity) revenue\n" +
                    "from orders_products ordprd\n" +
                    "join orders ord on ordprd.order_code = ord.code\n" +
                    "left join products prd on prd.code = ordprd.product_code\n" +
                    "where ord.revenue > 0\n" +
                    "    and ord.financial_status = 'paid'\n" +
                    "    and ordprd.sku is not null\n" +
                    "    and ordprd.sku != ''\n" +
                    "    and ord.created_at between date(:from) and date(:to)\n" +
                    qrStore +
                    qrSeller +
                    "group by prd.name, ordprd.sku, ordprd.seller_code, prd.picture\n" +
                    "order by sum(ordprd.price * ordprd.quantity) desc\n" +
                    "limit 10\n" +
                    ";";

        var query = em.createNativeQuery(sql);
        query.setParameter("from", TimeParser.parseLocalDateTimeToISOString(params.getFrom()));
        query.setParameter("to", TimeParser.parseLocalDateTimeToISOString(params.getTo()));

        if (params.getStoreId() != null) {
            query.setParameter("storeId", params.getStoreId());
        }

        if (!params.getSellerCode().equals("")) {
            query.setParameter("sellerCode", params.getSellerCode());
        }

        List<Object[]> rsList = query.getResultList();
        var rs = new ArrayList<ProductDesignStatistic>();
        for (Object[] obj : rsList) {
            var stat = new ProductDesignStatistic();
            stat.setProductName(obj[0] == null ? null : String.valueOf(obj[0]));
            stat.setSku(obj[1] == null ? null : String.valueOf(obj[1]));
            stat.setSellerName(obj[2] == null ? null : String.valueOf(obj[2]));
            stat.setProductPicture(obj[3] == null ? null : String.valueOf(obj[3]));
            stat.setProductQuantity(obj[4] == null ? null : Integer.parseInt(String.valueOf(obj[4])));
            stat.setRevenue(obj[5] == null ? null : Math.round((double) obj[5] * 100) / 100.00);
            rs.add(stat);
        }
        return rs;
    }

    @Override
    public List<SellerStatistic> statForSeller(StatisticQueryParam params) {
        var qrStore = params.getStoreId() != null ? "and ord.store_id = :storeId\n" : "";
        var qrSeller = !params.getSellerCode().equals("") ? "and ordprd.seller_code = :sellerCode\n" : "";
        var sql =   "select\n" +
                    "    ordprd.seller_code,\n" +
                    "    count(ordprd.order_code) orderCount,\n" +
                    "    sum(ordprd.quantity) productQuantity,\n" +
                    "    sum(ordprd.price * ordprd.quantity) revenue,\n" +
                    "    sum(prd.base_cost * ordprd.quantity) baseCostFee\n" +
                    "from orders_products ordprd\n" +
                    "join orders ord\n" +
                    "    on ordprd.order_code = ord.code\n" +
                    "left join products prd\n" +
                    "    on prd.code = ordprd.product_code\n" +
                    "where ord.revenue > 0\n" +
                    "    and ord.financial_status = 'paid'\n" +
                    "    and ord.created_at between date(:from) and date(:to)\n" +
                    qrStore +
                    qrSeller +
                    "group by ordprd.seller_code\n" +
                    ";";

        var query = em.createNativeQuery(sql);
        query.setParameter("from", TimeParser.parseLocalDateTimeToISOString(params.getFrom()));
        query.setParameter("to", TimeParser.parseLocalDateTimeToISOString(params.getTo()));

        if (params.getStoreId() != null) {
            query.setParameter("storeId", params.getStoreId());
        }

        if (!params.getSellerCode().equals("")) {
            query.setParameter("sellerCode", params.getSellerCode());
        }

        List<Object[]> rsList = query.getResultList();
        var rs = new ArrayList<SellerStatistic>();
        for (Object[] obj : rsList) {
            var stat = new SellerStatistic();
            stat.setName(obj[0] == null ? null : String.valueOf(obj[0]));
            stat.setOrderCount(obj[1] == null ? null : Integer.parseInt(String.valueOf(obj[1])));
            stat.setProductQuantity(obj[2] == null ? null : Integer.parseInt(String.valueOf(obj[2])));
            stat.setRevenue(obj[3] == null ? null : Math.round((double) obj[3] * 100) / 100.00);
            stat.setBaseCostFee(obj[4] == null ? null : Math.round((double) obj[4] * 100) / 100.00);
            stat.setStoreFee(BusinessUtil.calcStoreFee((double) obj[3]));
            rs.add(stat);
        }
        return rs;
    }

    @Override
    public List<SupplierStatistic> statForSupplier(StatisticQueryParam params) {
//        select
//        ord.created_at,
//                ord.code,
//                prd.name prd_name,
//                spl.name spl_name,
//                ordprd.sku,
//                sum(ordprd.quantity) productQuantity,
//                sum(ordprd.price * ordprd.quantity) revenue,
//                sum(prd.base_cost * ordprd.quantity) baseCost
//        from orders_products ordprd
//        join orders ord on ordprd.order_code = ord.code
//        left join products prd on prd.code = ordprd.product_code
//        left join suppliers spl on ordprd.supplier_code = spl.code
//        where ord.revenue > 0
//        and ord.financial_status = 'paid'
//        and ord.created_at between date(:from) and date(:to)
//        and ord.store_id = :storeId
//        and ordprd.seller_code = :sellerCode
//        group by ord.created_at, ord.code, prd.name, spl.name, ordprd.sku
//                ;
        var qrStore = params.getStoreId() != null ? "and ord.store_id = :storeId\n" : "";
        var qrSeller = !params.getSellerCode().equals("") ? "and ordprd.seller_code = :sellerCode\n" : "";
        var sql =   "select\n" +
                    "    ord.created_at,\n" +
                    "    ord.code,\n" +
                    "    prd.name prd_name,\n" +
                    "    spl.name spl_name,\n" +
                    "    ordprd.sku,\n" +
                    "    sum(ordprd.quantity) productQuantity,\n" +
                    "    sum(ordprd.price * ordprd.quantity) revenue,\n" +
                    "    sum(prd.base_cost * ordprd.quantity) baseCost\n" +
                    "from orders_products ordprd\n" +
                    "join orders ord on ordprd.order_code = ord.code\n" +
                    "left join products prd on prd.code = ordprd.product_code\n" +
                    "left join suppliers spl on ordprd.supplier_code = spl.code\n" +
                    "where ord.revenue > 0\n" +
                    "    and ord.financial_status = 'paid'\n" +
                    "    and ord.created_at between date(:from) and date(:to)\n" +
                    qrStore +
                    qrSeller +
                    "group by ord.created_at, ord.code, prd.name, spl.name, ordprd.sku\n" +
                    ";";
        var query = em.createNativeQuery(sql);
        query.setParameter("from", TimeParser.parseLocalDateTimeToISOString(params.getFrom()));
        query.setParameter("to", TimeParser.parseLocalDateTimeToISOString(params.getTo()));

        if (params.getStoreId() != null) {
            query.setParameter("storeId", params.getStoreId());
        }

        if (!params.getSellerCode().equals("")) {
            query.setParameter("sellerCode", params.getSellerCode());
        }

        List<Object[]> rsList = query.getResultList();
        var rs = new ArrayList<SupplierStatistic>();
        for (Object[] obj : rsList) {
            var stat = new SupplierStatistic();
            stat.setDate(obj[0] == null ? null : String.valueOf(obj[0]));
            stat.setOrderId(obj[1] == null ? null : String.valueOf(obj[1]));
            stat.setProductName(obj[2] == null ? null : String.valueOf(obj[2]));
            stat.setSupplierName(obj[3] == null ? null : String.valueOf(obj[3]));
            stat.setSku(obj[4] == null ? null : String.valueOf(obj[4]));
            stat.setQuantity(obj[5] == null ? null : Integer.parseInt(String.valueOf(obj[5])));
            stat.setPrice(obj[6] == null ? null : Math.round((double) obj[6] * 100) / 100.00);
            stat.setBaseCost(obj[7] == null ? null : Math.round((double) obj[7] * 100) / 100.00);
            rs.add(stat);
        }
        return rs;
    }

}
