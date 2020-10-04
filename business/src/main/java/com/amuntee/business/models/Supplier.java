package com.amuntee.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "suppliers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supplier {
    @Id
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "code", nullable = true, length = 20)
    private String code;

    @Basic
    @Column(name = "name", nullable = true, length = 100)
    private String name;

    @Basic
    @Column(name = "address", nullable = true, length = 250)
    private String address;

    @Basic
    @Column(name = "phone", nullable = true, length = 15)
    private String phone;

    @Basic
    @Column(name = "email", nullable = true, length = 150)
    private String email;
}
