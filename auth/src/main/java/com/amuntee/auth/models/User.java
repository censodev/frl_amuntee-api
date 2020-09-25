package com.amuntee.auth.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @Column(nullable = false, unique = true)
    private String code;

    private String fullname;

    private String email;

    private String phone;

    @Column(name = "partner_id")
    private int partnerId;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "position_id")
    private int positionId;

    private LocalDate dob;

    private int status;
}
