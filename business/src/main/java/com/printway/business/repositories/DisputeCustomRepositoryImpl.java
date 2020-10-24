package com.printway.business.repositories;

import com.printway.business.dto.statistic.DisputeStatistic;
import com.printway.business.dto.statistic.StatisticQueryParam;
import com.printway.common.time.TimeParser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class DisputeCustomRepositoryImpl implements DisputeCustomRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<DisputeStatistic> statForDispute(StatisticQueryParam params) {
//        select
//        year(time) year,
//                month(time) month,
//                day(time) day,
//                sum(dispute_fee) fee
//        from disputes
//        where time between date(:from) and date(:to)
//        and ord.store_id = :storeId
//        group by year, month, day
//        ;
        var qrStore = params.getStoreId() != null ? "and ord.store_id = :storeId\n" : "";
        var sql =   "        select\n" +
                    "        year(time) year,\n" +
                    "                month(time) month,\n" +
                    "                day(time) day,\n" +
                    "                sum(dispute_fee) fee\n" +
                    "        from disputes\n" +
                    "        where time between date(:from) and date(:to)\n" +
                    qrStore +
                    "        group by year, month, day\n" +
                    "        ;";
        var query = em.createNativeQuery(sql);
        query.setParameter("from", TimeParser.parseLocalDateTimeToISOString(params.getFrom()));
        query.setParameter("to", TimeParser.parseLocalDateTimeToISOString(params.getTo()));

        if (params.getStoreId() != null) {
            query.setParameter("storeId", params.getStoreId());
        }

        List<Object[]> rsList = query.getResultList();
        var rs = new ArrayList<DisputeStatistic>();
        for (Object[] obj : rsList) {
            var stat = new DisputeStatistic();
            stat.setYear(obj[0] == null ? null : Integer.parseInt(String.valueOf(obj[0])));
            stat.setMonth(obj[1] == null ? null : Integer.parseInt(String.valueOf(obj[1])));
            stat.setDay(obj[2] == null ? null : Integer.parseInt(String.valueOf(obj[2])));
            stat.setDispute(obj[3] == null ? null : Double.parseDouble(String.valueOf(obj[3])));
            rs.add(stat);
        }
        return rs;
    }
}
