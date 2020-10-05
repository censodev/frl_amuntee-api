package com.printway.business.repositories;

import com.printway.business.dto.statistic.RevenueSpecificStatistic;
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
    public List<RevenueSpecificStatistic> statForSku(LocalDateTime from, LocalDateTime to) {
        /**
         * select
         * 	year(ord.created_at),
         *     month(ord.created_at),
         *     ordprd.sku,
         *     sum(ordprd.quantity),
         *     sum(ordprd.price * ordprd.quantity)
         * from orders_products ordprd
         * join orders ord on ordprd.order_code = ord.code
         * group by ordprd.sku, year(ord.created_at), month(ord.created_at);
         */
        String query = "select \n" +
                "\tyear(ord.created_at),\n" +
                "    month(ord.created_at),\n" +
                "    ordprd.sku,\n" +
                "    sum(ordprd.quantity),\n" +
                "    sum(ordprd.price * ordprd.quantity)\n" +
                "from orders_products ordprd\n" +
                "join orders ord on ordprd.order_code = ord.code\n" +
                "group by ordprd.sku, year(ord.created_at), month(ord.created_at);";
        return getStatistic(query);
    }

    @Override
    public List<RevenueSpecificStatistic> statForProductCode(LocalDateTime from, LocalDateTime to) {
        /**
         * select
         * 	year(ord.created_at),
         *     month(ord.created_at),
         *     ordprd.product_code,
         *     sum(ordprd.quantity),
         *     sum(ordprd.price * ordprd.quantity)
         * from orders_products ordprd
         * join orders ord on ordprd.order_code = ord.code
         * group by ordprd.product_code, year(ord.created_at), month(ord.created_at);
         */
        String query = "select \n" +
                "\tyear(ord.created_at),\n" +
                "    month(ord.created_at),\n" +
                "    ordprd.product_code,\n" +
                "    sum(ordprd.quantity),\n" +
                "    sum(ordprd.price * ordprd.quantity)\n" +
                "from orders_products ordprd\n" +
                "join orders ord on ordprd.order_code = ord.code\n" +
                "group by ordprd.product_code, year(ord.created_at), month(ord.created_at);";
        return getStatistic(query);
    }

    @Override
    public List<RevenueSpecificStatistic> statForDesignCode(LocalDateTime from, LocalDateTime to) {
        /**
         * select
         * 	year(ord.created_at),
         *     month(ord.created_at),
         *     ordprd.design_code,
         *     sum(ordprd.quantity),
         *     sum(ordprd.price * ordprd.quantity)
         * from orders_products ordprd
         * join orders ord on ordprd.order_code = ord.code
         * group by ordprd.design_code, year(ord.created_at), month(ord.created_at);
         */
        String query = "select \n" +
                "\tyear(ord.created_at),\n" +
                "    month(ord.created_at),\n" +
                "    ordprd.design_code,\n" +
                "    sum(ordprd.quantity),\n" +
                "    sum(ordprd.price * ordprd.quantity)\n" +
                "from orders_products ordprd\n" +
                "join orders ord on ordprd.order_code = ord.code\n" +
                "group by ordprd.design_code, year(ord.created_at), month(ord.created_at);";
        return getStatistic(query);
    }

    @Override
    public List<RevenueSpecificStatistic> statForSeller(LocalDateTime from, LocalDateTime to) {
        /**
         * select
         * 	year(ord.created_at),
         *     month(ord.created_at),
         *     ordprd.seller_code,
         *     sum(ordprd.quantity),
         *     sum(ordprd.price * ordprd.quantity)
         * from orders_products ordprd
         * join orders ord on ordprd.order_code = ord.code
         * group by ordprd.seller_code, year(ord.created_at), month(ord.created_at);
         */
        String query = "select \n" +
                "\tyear(ord.created_at),\n" +
                "    month(ord.created_at),\n" +
                "    ordprd.seller_code,\n" +
                "    sum(ordprd.quantity),\n" +
                "    sum(ordprd.price * ordprd.quantity)\n" +
                "from orders_products ordprd\n" +
                "join orders ord on ordprd.order_code = ord.code\n" +
                "group by ordprd.seller_code, year(ord.created_at), month(ord.created_at);";
        return getStatistic(query);
    }

    @Override
    public List<RevenueSpecificStatistic> statForSupplier(LocalDateTime from, LocalDateTime to) {
        /**
         * select
         * 	year(ord.created_at),
         *     month(ord.created_at),
         *     ordprd.supplier_code,
         *     sum(ordprd.quantity),
         *     sum(ordprd.price * ordprd.quantity)
         * from orders_products ordprd
         * join orders ord on ordprd.order_code = ord.code
         * group by ordprd.supplier_code, year(ord.created_at), month(ord.created_at);
         */
        String query = "select \n" +
                "\tyear(ord.created_at),\n" +
                "    month(ord.created_at),\n" +
                "    ordprd.supplier_code,\n" +
                "    sum(ordprd.quantity),\n" +
                "    sum(ordprd.price * ordprd.quantity)\n" +
                "from orders_products ordprd\n" +
                "join orders ord on ordprd.order_code = ord.code\n" +
                "group by ordprd.supplier_code, year(ord.created_at), month(ord.created_at);";
        return getStatistic(query);
    }

    private List<RevenueSpecificStatistic> getStatistic(String query) {
        List<Object[]> rsList = em.createNativeQuery(query).getResultList();
        var rs = new ArrayList<RevenueSpecificStatistic>();
        for (Object[] obj : rsList) {
            var stat = new RevenueSpecificStatistic();
            stat.setYear(obj[0] == null ? null : Integer.parseInt(String.valueOf(obj[0])));
            stat.setMonth(obj[1] == null ? null : Integer.parseInt(String.valueOf(obj[1])));
            stat.setTitle(obj[2] == null ? null : String.valueOf(obj[2]));
            stat.setQuantity(Integer.parseInt(String.valueOf(obj[3])));
            stat.setRevenue((double) obj[4]);
            rs.add(stat);
        }
        return rs;
    }

}
