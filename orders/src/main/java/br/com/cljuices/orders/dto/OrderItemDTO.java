package br.com.cljuices.orders.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {

    private Long id;
    private Integer amount;
    private String description;
}
