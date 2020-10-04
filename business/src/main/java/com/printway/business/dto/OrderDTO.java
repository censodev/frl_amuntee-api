package com.printway.business.dto;

import com.printway.business.models.Order;
import com.printway.business.models.OrderProduct;
import com.printway.business.models.PaymentTransaction;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data()
@ToString(callSuper = true)
public class OrderDTO extends Order {
    private List<OrderProduct> products;
    private List<PaymentTransaction> paymentTransactions;
}
