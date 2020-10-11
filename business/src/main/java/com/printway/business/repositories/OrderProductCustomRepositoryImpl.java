package com.printway.business.repositories;

import com.printway.business.dto.statistic.SpecificStatistic;
import com.printway.business.utils.BusinessUtil;
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
    public List<SpecificStatistic> statForSku(LocalDateTime from, LocalDateTime to) {
        /**
         * select
         * 	year(ord.created_at) year,
         * 	month(ord.created_at) month,
         * 	ordprd.sku,
         * 	sum(ordprd.quantity) quantity,
         * 	sum(ordprd.price * ordprd.quantity) revenue
         * from orders_products ordprd
         * join orders ord on ordprd.order_code = ord.code
         * group by ordprd.sku, year, month
         * order by year desc, month desc, revenue desc;
         */
        String query = "select\n" +
                "\tyear(ord.created_at) year,\n" +
                "\tmonth(ord.created_at) month,\n" +
                "\tordprd.sku,\n" +
                "\tsum(ordprd.quantity) quantity,\n" +
                "\tsum(ordprd.price * ordprd.quantity) revenue\n" +
                "from orders_products ordprd\n" +
                "join orders ord on ordprd.order_code = ord.code\n" +
                "group by ordprd.sku, year, month\n" +
                "order by year desc, month desc, revenue desc;";
        return getStatistic(query);
    }

    @Override
    public List<SpecificStatistic> statForProductCode(LocalDateTime from, LocalDateTime to) {
        /**
         * select
         * 	year(ord.created_at) year,
         * 	month(ord.created_at) month,
         * 	ordprd.product_code,
         * 	sum(ordprd.quantity) quantity,
         * 	sum(ordprd.price * ordprd.quantity) revenue
         * from orders_products ordprd
         * join orders ord on ordprd.order_code = ord.code
         * group by ordprd.product_code, year, month
         * order by year desc, month desc, revenue desc;
         */
        String query = "select\n" +
                "\tyear(ord.created_at) year,\n" +
                "\tmonth(ord.created_at) month,\n" +
                "\tordprd.product_code,\n" +
                "\tsum(ordprd.quantity) quantity,\n" +
                "\tsum(ordprd.price * ordprd.quantity) revenue\n" +
                "from orders_products ordprd\n" +
                "join orders ord on ordprd.order_code = ord.code\n" +
                "group by ordprd.product_code, year, month\n" +
                "order by year desc, month desc, revenue desc;";
        return getStatistic(query);
    }

    @Override
    public List<SpecificStatistic> statForDesignCode(LocalDateTime from, LocalDateTime to) {
        /**
         * select
         * 	year(ord.created_at) year,
         * 	month(ord.created_at) month,
         * 	ordprd.design_code,
         * 	sum(ordprd.quantity) quantity,
         * 	sum(ordprd.price * ordprd.quantity) revenue
         * from orders_products ordprd
         * join orders ord on ordprd.order_code = ord.code
         * group by ordprd.design_code, year, month
         * order by year desc, month desc, revenue desc;
         */
        String query = "select\n" +
                "\tyear(ord.created_at) year,\n" +
                "\tmonth(ord.created_at) month,\n" +
                "\tordprd.design_code,\n" +
                "\tsum(ordprd.quantity) quantity,\n" +
                "\tsum(ordprd.price * ordprd.quantity) revenue\n" +
                "from orders_products ordprd\n" +
                "join orders ord on ordprd.order_code = ord.code\n" +
                "group by ordprd.design_code, year, month\n" +
                "order by year desc, month desc, revenue desc;";
        return getStatistic(query);
    }

    @Override
    public List<SpecificStatistic> statForSeller(LocalDateTime from, LocalDateTime to) {
        /**
         * select
         * 	year(ord.created_at) year,
         * 	month(ord.created_at) month,
         * 	ordprd.seller_code,
         * 	sum(ordprd.quantity) quantity,
         * 	sum(ordprd.price * ordprd.quantity) revenue
         * from orders_products ordprd
         * join orders ord on ordprd.order_code = ord.code
         * group by ordprd.seller_code, year, month
         * order by year desc, month desc, revenue desc;
         */
        String query = "select\n" +
                "\tyear(ord.created_at) year,\n" +
                "\tmonth(ord.created_at) month,\n" +
                "\tordprd.seller_code,\n" +
                "\tsum(ordprd.quantity) quantity,\n" +
                "\tsum(ordprd.price * ordprd.quantity) revenue\n" +
                "from orders_products ordprd\n" +
                "join orders ord on ordprd.order_code = ord.code\n" +
                "group by ordprd.seller_code, year, month\n" +
                "order by year desc, month desc, revenue desc;";
        return getStatistic(query);
    }

    @Override
    public List<SpecificStatistic> statForSupplier(LocalDateTime from, LocalDateTime to) {
        /**
         * select
         * 	year(ord.created_at) year,
         * 	month(ord.created_at) month,
         * 	ordprd.supplier_code,
         * 	sum(ordprd.quantity) quantity,
         * 	sum(ordprd.price * ordprd.quantity) revenue
         * from orders_products ordprd
         * join orders ord on ordprd.order_code = ord.code
         * group by ordprd.supplier_code, year, month
         * order by year desc, month desc, revenue desc;
         */
        String query = "select\n" +
                "\tyear(ord.created_at) year,\n" +
                "\tmonth(ord.created_at) month,\n" +
                "\tordprd.supplier_code,\n" +
                "\tsum(ordprd.quantity) quantity,\n" +
                "\tsum(ordprd.price * ordprd.quantity) revenue\n" +
                "from orders_products ordprd\n" +
                "join orders ord on ordprd.order_code = ord.code\n" +
                "group by ordprd.supplier_code, year, month\n" +
                "order by year desc, month desc, revenue desc;";
        return getStatistic(query);
    }

    private List<SpecificStatistic> getStatistic(String query) {
        List<Object[]> rsList = em.createNativeQuery(query).getResultList();
        var rs = new ArrayList<SpecificStatistic>();
        for (Object[] obj : rsList) {
            var stat = new SpecificStatistic();
            stat.setYear(obj[0] == null ? null : Integer.parseInt(String.valueOf(obj[0])));
            stat.setMonth(obj[1] == null ? null : Integer.parseInt(String.valueOf(obj[1])));
            stat.setTitle(obj[2] == null ? null : String.valueOf(obj[2]));
            stat.setQuantity(Integer.parseInt(String.valueOf(obj[3])));
            stat.setRevenue(Math.round((double) obj[4] * 100) / 100.00);
            stat.setStoreFee(BusinessUtil.calcStoreFee((double) obj[4]));
            rs.add(stat);
        }
        return rs;
    }

}
