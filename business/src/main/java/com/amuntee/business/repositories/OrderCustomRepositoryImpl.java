package com.amuntee.business.repositories;

import com.amuntee.business.dto.OrderStat;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrderCustomRepositoryImpl implements OrderCustomRepository {
    @PersistenceContext
    EntityManager em;

    @Override
    public List<OrderStat> statOrders(LocalDateTime from, LocalDateTime to) {
//        select
//        year(created_at) year,
//                month(created_at) as month,
//        count(*) as ordersCount,
//        sum(sub_total_price) as subTotalPrice,
//        sum(total_price) as totalPrice
//        from orders
//        group by year(created_at),  month(created_at)
//        ;
        String query = "select\n" +
                "\tyear(created_at) as year,\n" +
                "\tmonth(created_at) as month,\n" +
                "\tcount(*) as ordersCount,\n" +
                "\tsum(sub_total_price) as subTotalPrice,\n" +
                "\tsum(total_price) as totalPrice\n" +
                "from orders\n" +
                "group by year(created_at),  month(created_at)";
        List<Object[]> rsList = em.createNativeQuery(query).getResultList();
        var rs = new ArrayList<OrderStat>();
        for (Object[] obj : rsList) {
            var stat = new OrderStat();
            stat.setYear(obj[0] == null ? null : Integer.parseInt(String.valueOf(obj[0])));
            stat.setMonth(obj[1] == null ? null : Integer.parseInt(String.valueOf(obj[1])));
            stat.setOrdersCount(Integer.parseInt(String.valueOf(obj[2])));
            stat.setSubTotalPrice((double) obj[3]);
            stat.setTotalPrice((double) obj[4]);
            rs.add(stat);
        }
        return rs;
    }
}
