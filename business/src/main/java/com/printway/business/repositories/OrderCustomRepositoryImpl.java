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
    public List<SummaryStatistic> statForSummary(LocalDateTime from, LocalDateTime to) {
//        select
//        year(created_at) year,
//                month(created_at) as month,
//        sum(sub_total_price) as subTotalPrice
//        from orders
//        where created_at between date(:from) and date(:to)
//        group by year(created_at),  month(created_at)
//        ;
        String sql = "select\n" +
                "\tyear(created_at) year,\n" +
                "\tmonth(created_at) as month,\n" +
                "\tsum(sub_total_price) as subTotalPrice\n" +
                "from orders\n" +
                "where created_at between date(:from) and date(:to)\n" +
                "group by year(created_at),  month(created_at)\n" +
                ";";
        var query = em.createNativeQuery(sql);
        query.setParameter("from", TimeParser.parseLocalDateTimeToISOString(from));
        query.setParameter("to", TimeParser.parseLocalDateTimeToISOString(to));
        List<Object[]> rsList = query.getResultList();
        var rs = new ArrayList<SummaryStatistic>();
        for (Object[] obj : rsList) {
            var stat = new SummaryStatistic();
            stat.setYear(obj[0] == null ? null : Integer.parseInt(String.valueOf(obj[0])));
            stat.setMonth(obj[1] == null ? null : Integer.parseInt(String.valueOf(obj[1])));
            stat.setRevenue(Math.round((double) obj[2] * 100) / 100.00);
            stat.setStoreFee(BusinessUtil.calcStoreFee((double) obj[2]));
            rs.add(stat);
        }
        return rs;
    }
}
