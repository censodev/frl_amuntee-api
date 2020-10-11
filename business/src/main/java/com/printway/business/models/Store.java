package com.printway.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stores")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Store {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "host", unique = true, nullable = false)
    private String host;

    @Column(name = "api_key", nullable = false)
    private String apiKey;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "sync_time")
    private LocalDateTime syncTime;

    @Column(name = "status", columnDefinition = "integer default 1")
    private Integer status;
}
