package com.example.cryptowatcher.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "cryptocurrency")
public class Cryptocurrency implements Serializable {
    @Id
    private Long id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "name")
    private String name;

    @Column(name = "price_usd")
    @JsonProperty("price_usd")
    private Double priceUsd;
}
