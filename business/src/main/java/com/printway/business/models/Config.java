package com.printway.business.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "configs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Config {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue
    private int id;

    @Column(name = "facebook_token_title")
    private String facebookTokenTitle;

    @Column(name = "facebook_token")
    private String facebookToken;

    @Column(name = "telegram_bot_token")
    private String telegramBotToken;

    private Integer status;
}
