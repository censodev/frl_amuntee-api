package com.printway.auth.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

//    @JsonIgnore
    private String password;

    @Column(nullable = false, unique = true, length = 2)
    private String code;

    private String fullname;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    @Column(name = "partner_id")
    private int partnerId;

    @Column(name = "role_id")
    private int roleId;

    @Column(name = "position_id")
    private int positionId;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dob;

    private int status;

    private Double profit1;
    private Double profit2;
    private Double profit3;
    private Double profit4;
    private Double profit5;

    private Double bonus1;
    private Double bonus2;
    private Double bonus3;
    private Double bonus4;
    private Double bonus5;

    @Column(name = "reset_code")
    private String resetCode;
}
