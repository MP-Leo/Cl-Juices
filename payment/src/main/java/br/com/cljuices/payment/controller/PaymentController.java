package br.com.cljuices.payment.controller;

import br.com.cljuices.payment.dto.PaymentDTO;
import br.com.cljuices.payment.service.PaymentService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping("/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping
    public Page<PaymentDTO> listPayment(@PageableDefault(size = 10) Pageable pageable) {
        return paymentService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> detail(@PathVariable @NotNull Long id) {
        PaymentDTO dto = paymentService.getById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PaymentDTO> register(@RequestBody @Valid PaymentDTO dto, UriComponentsBuilder uriBuilder) {
        PaymentDTO payment = paymentService.createPayment(dto);
        URI uri = uriBuilder.path("/pagamentos/{id}").buildAndExpand(payment.getId()).toUri();

        return ResponseEntity.created(uri).body(payment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> update(@PathVariable @NotNull Long id, @RequestBody @Valid PaymentDTO dto) {
        PaymentDTO updated = paymentService.updatePayment(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDTO> remove(@PathVariable @NotNull Long id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirm")
    @CircuitBreaker(name = "payOrder", fallbackMethod = "paymentConfirmedWithPendingAuthorization")
    public void confirmPayment(@PathVariable @NotNull Long id){
        paymentService.confirmPayment(id);
    }


    public void paymentConfirmedWithPendingAuthorization(@PathVariable @NotNull Long id, Exception ex){
        paymentService.updatePayment(id);
    }

}
