package com.amuntee.business.dto;

import com.amuntee.business.models.Order;
import com.amuntee.business.models.OrderProduct;
import com.amuntee.business.models.PaymentTransaction;
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
