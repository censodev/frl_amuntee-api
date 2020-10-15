package com.printway.business.repositories;

import com.printway.business.dto.statistic.ProductTypeStatistic;
import com.printway.business.dto.statistic.SellerStatistic;
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
    public List<ProductTypeStatistic> statForProductType(LocalDateTime from, LocalDateTime to, Integer storeId) {
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
//        group by prdt.name
//        order by count(ordprd.order_code) desc
//        ;
        var sql = storeId != null
                ?   "select\n" +
                    "    prdt.name,\n" +
                    "    count(ordprd.order_code),\n" +
                    "    sum(ordprd.quantity),\n" +
                    "    sum(ordprd.price * ordprd.quantity)\n" +
                    "from orders_products ordprd\n" +
                    "join orders ord\n" +
                    "    on ordprd.order_code = ord.code\n" +
                    "left join products prd\n" +
                    "    on prd.code = ordprd.product_code\n" +
                    "left join product_types prdt\n" +
                    "    on prdt.id = prd.type_id\n" +
                    "where ord.revenue > 0\n" +
                    "    and ord.financial_status = 'paid'\n" +
                    "    and ord.created_at between date(:from) and date(:to)\n" +
                    "    and ord.store_id = :storeId\n" +
                    "group by prdt.name\n" +
                    "order by count(ordprd.order_code) desc\n" +
                    ";"

                :   "select\n" +
                    "    prdt.name,\n" +
                    "    count(ordprd.order_code),\n" +
                    "    sum(ordprd.quantity),\n" +
                    "    sum(ordprd.price * ordprd.quantity)\n" +
                    "from orders_products ordprd\n" +
                    "join orders ord\n" +
                    "    on ordprd.order_code = ord.code\n" +
                    "left join products prd\n" +
                    "    on prd.code = ordprd.product_code\n" +
                    "left join product_types prdt\n" +
                    "    on prdt.id = prd.type_id\n" +
                    "where ord.revenue > 0\n" +
                    "    and ord.financial_status = 'paid'\n" +
                    "    and ord.created_at between date(:from) and date(:to)\n" +
                    "group by prdt.name\n" +
                    "order by count(ordprd.order_code) desc\n" +
                    ";";
        var query = em.createNativeQuery(sql);
        query.setParameter("from", TimeParser.parseLocalDateTimeToISOString(from));
        query.setParameter("to", TimeParser.parseLocalDateTimeToISOString(to));
        if (storeId != null) {
            query.setParameter("storeId", storeId);
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
    public List<ProductTypeStatistic> statForProductDesign(LocalDateTime from, LocalDateTime to, Integer storeId) {
        return null;
    }

    @Override
    public List<SellerStatistic> statForSeller(LocalDateTime from, LocalDateTime to, Integer storeId) {
        var sql = storeId != null
                ?   "select\n" +
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
                    "  and ord.financial_status = 'paid'\n" +
                    "  and ord.created_at between date(:from) and date(:to)\n" +
                    "  and ord.store_id = :storeId\n" +
                    "group by ordprd.seller_code\n" +
                    ";"

                :   "select\n" +
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
                    "  and ord.financial_status = 'paid'\n" +
                    "  and ord.created_at between date(:from) and date(:to)\n" +
                    "group by ordprd.seller_code\n" +
                    ";";
        var query = em.createNativeQuery(sql);
        query.setParameter("from", TimeParser.parseLocalDateTimeToISOString(from));
        query.setParameter("to", TimeParser.parseLocalDateTimeToISOString(to));
        if (storeId != null) {
            query.setParameter("storeId", storeId);
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
    public List<ProductTypeStatistic> statForSupplier(LocalDateTime from, LocalDateTime to, Integer storeId) {
        return null;
    }

}
