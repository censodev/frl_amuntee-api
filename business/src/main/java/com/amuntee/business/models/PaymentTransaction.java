package com.amuntee.business.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "payment_transactions", schema = "amuntee_business", catalog = "")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentTransaction {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;

    private String code;

    private String type;

    @Column(name = "source_type")
    private String sourceType;

    private double amount;

    private double fee;

    private double net;

    @Column(name = "order_code")
    private String orderCode;
}
