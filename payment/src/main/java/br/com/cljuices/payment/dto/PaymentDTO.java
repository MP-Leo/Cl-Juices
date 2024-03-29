package br.com.cljuices.payment.dto;

import br.com.cljuices.payment.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentDTO {

    private Long id;
    private BigDecimal value;
    private String name;
    private String number;
    private String expiration;
    private String code;
    private Status status;
    private Long paymentMethodId;
    private Long orderId;

}
