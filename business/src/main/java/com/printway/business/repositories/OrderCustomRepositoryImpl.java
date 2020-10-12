package com.printway.business.repositories;

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
    public List<SummaryStatistic> statForSummary(LocalDateTime from, LocalDateTime to, Integer storeId) {
//        select
//            year(ord.created_at) year,
//            month(ord.created_at) month,
//            sum(ord.revenue) revenue,
//            sum(bcf.base_cost_fee) base_cost_fee
//        from orders ord
//        join (select
//                ord.code,
//                sum(prd.base_cost * oprd.quantity) base_cost_fee
//            from orders ord
//            join orders_products oprd
//                on ord.code = oprd.order_code
//            left join products prd
//                on oprd.product_code = prd.code
//            where ord.revenue > 0
//            group by ord.code) bcf
//            on bcf.code = ord.code
//        where ord.created_at between date(:from) and date(:to)
//            and ord.store_id = :storeId
//        group by year, month
//        ;
        String sql = storeId != null
                ?   "        select\n" +
                    "            year(ord.created_at) year,\n" +
                    "            month(ord.created_at) month,\n" +
                    "            sum(ord.revenue) revenue,\n" +
                    "            sum(bcf.base_cost_fee) base_cost_fee\n" +
                    "        from orders ord\n" +
                    "        join (select\n" +
                    "                ord.code,\n" +
                    "                sum(prd.base_cost * oprd.quantity) base_cost_fee\n" +
                    "            from orders ord\n" +
                    "            join orders_products oprd\n" +
                    "                on ord.code = oprd.order_code\n" +
                    "            left join products prd\n" +
                    "                on oprd.product_code = prd.code\n" +
                    "            where ord.revenue > 0\n" +
                    "            group by ord.code) bcf\n" +
                    "            on bcf.code = ord.code\n" +
                    "        where ord.created_at between date(:from) and date(:to)\n" +
                    "            and ord.financial_status = 'paid'\n" +
                    "            and ord.store_id = :storeId\n" +
                    "        group by year, month\n" +
                    "        ;"

                :   "        select\n" +
                    "            year(ord.created_at) year,\n" +
                    "            month(ord.created_at) month,\n" +
                    "            sum(ord.revenue) revenue,\n" +
                    "            sum(bcf.base_cost_fee) base_cost_fee\n" +
                    "        from orders ord\n" +
                    "        join (select\n" +
                    "                ord.code,\n" +
                    "                sum(prd.base_cost * oprd.quantity) base_cost_fee\n" +
                    "            from orders ord\n" +
                    "            join orders_products oprd\n" +
                    "                on ord.code = oprd.order_code\n" +
                    "            left join products prd\n" +
                    "                on oprd.product_code = prd.code\n" +
                    "            where ord.revenue > 0\n" +
                    "            group by ord.code) bcf\n" +
                    "            on bcf.code = ord.code\n" +
                    "        where ord.created_at between date(:from) and date(:to)\n" +
                    "            and ord.financial_status = 'paid'\n" +
                    "        group by year, month\n" +
                    "        ;";
        var query = em.createNativeQuery(sql);
        query.setParameter("from", TimeParser.parseLocalDateTimeToISOString(from));
        query.setParameter("to", TimeParser.parseLocalDateTimeToISOString(to));

        if (storeId != null) {
            query.setParameter("storeId", storeId);
        }

        List<Object[]> rsList = query.getResultList();
        var rs = new ArrayList<SummaryStatistic>();
        for (Object[] obj : rsList) {
            var stat = new SummaryStatistic();
            stat.setYear(obj[0] == null ? null : Integer.parseInt(String.valueOf(obj[0])));
            stat.setMonth(obj[1] == null ? null : Integer.parseInt(String.valueOf(obj[1])));
            stat.setRevenue(Math.round((double) obj[2] * 100) / 100.00);
            stat.setStoreFee(BusinessUtil.calcStoreFee((double) obj[2]));
            stat.setBaseCostFee(obj[3] == null ? null : Math.round((double) obj[3] * 100) / 100.00);
            rs.add(stat);
        }
        return rs;
    }
}
