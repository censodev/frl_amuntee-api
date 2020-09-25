package com.amuntee.auth.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "partners")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Partner {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

}
