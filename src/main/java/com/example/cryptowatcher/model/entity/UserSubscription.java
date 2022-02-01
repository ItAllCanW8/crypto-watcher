package com.example.cryptowatcher.model.entity;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "user_subscription")
public class UserSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "crypto_symbol")
    private String cryptoSymbol;

    @Column(name = "active")
    private boolean active;

    @Column(name = "reg_time_price")
    private Double regTimePrice;
}
