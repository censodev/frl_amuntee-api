package com.printway.business.repositories;

import com.printway.business.dto.statistic.StatisticQueryParam;
import com.printway.business.dto.statistic.SummaryStatistic;
import com.printway.business.utils.BusinessUtil;
import com.printway.common.time.TimeParser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderCustomRepositoryImpl implements OrderCustomRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<SummaryStatistic> statForSummary(StatisticQueryParam params) {
//        select
//        year(ord.created_at) year,
//                month(ord.created_at) month,
//                day(ord.created_at) day,
//                sum(ord.revenue) revenue,
//                sum(bcf.base_cost_fee) base_cost_fee,
//                count(bcf.order_code) order_count
//        from orders ord
//        join (
//                select
//                ord.code order_code,
//                sum(prd.base_cost * oprd.quantity) base_cost_fee
//                from orders ord
//                join orders_products oprd on ord.code = oprd.order_code
//                left join product_templates prd on oprd.product_code = prd.code
//                where ord.revenue > 0
//                and oprd.seller_code = :sellerCode
//        group by ord.code
//) bcf on bcf.order_code = ord.code
//        where ord.created_at between date(:from) and date(:to)
//        and ord.store_id = :storeId
//        group by year, month, day
//        order by year asc, month asc, day asc
//        ;
        var qrStore = params.getStoreId() != null ? "and ord.store_id = :storeId\n" : "";
        var qrSeller = !params.getSellerCode().equals("") ? "and oprd.seller_code = :sellerCode\n" : "";
        String sql =    "select\n" +
                        "    year(ord.created_at) year,\n" +
                        "    month(ord.created_at) month,\n" +
                        "    day(ord.created_at) day,\n" +
                        "    sum(ord.revenue) revenue,\n" +
                        "    sum(bcf.base_cost_fee) base_cost_fee,\n" +
                        "    count(bcf.order_code) order_count\n" +
                        "from orders ord\n" +
                        "         join (select\n" +
                        "                   ord.code order_code,\n" +
                        "                   sum(prd.base_cost * oprd.quantity) base_cost_fee\n" +
                        "               from orders ord\n" +
                        "                        join orders_products oprd\n" +
                        "                             on ord.code = oprd.order_code\n" +
                        "                        left join product_templates prd\n" +
                        "                                  on oprd.product_code = prd.code\n" +
                        "               where ord.revenue > 0\n" +
                        qrSeller +
                        "               group by ord.code) bcf\n" +
                        "              on bcf.order_code = ord.code\n" +
                        "where ord.created_at between date(:from) and date(:to)\n" +
                        qrStore +
                        "group by year, month, day\n" +
                        "order by year asc, month asc, day asc\n" +
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
        var rs = new ArrayList<SummaryStatistic>();
        for (Object[] obj : rsList) {
            var stat = new SummaryStatistic();
            stat.setYear(obj[0] == null ? null : Integer.parseInt(String.valueOf(obj[0])));
            stat.setMonth(obj[1] == null ? null : Integer.parseInt(String.valueOf(obj[1])));
            stat.setDay(obj[2] == null ? null : Integer.parseInt(String.valueOf(obj[2])));
            stat.setRevenue(Math.round((double) obj[3] * 100) / 100.00);
            stat.setStoreFee(BusinessUtil.calcStoreFee((double) obj[3]));
            stat.setBaseCostFee(obj[4] == null ? null : Math.round((double) obj[4] * 100) / 100.00);
            stat.setOrderCount(obj[5] == null ? null : Integer.parseInt(String.valueOf(obj[5])));
            rs.add(stat);
        }
        return rs;
    }
}
