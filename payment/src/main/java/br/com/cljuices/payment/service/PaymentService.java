package br.com.cljuices.payment.service;

import br.com.cljuices.payment.dto.PaymentDTO;
import br.com.cljuices.payment.http.OrderClient;
import br.com.cljuices.payment.model.Payment;
import br.com.cljuices.payment.model.Status;
import br.com.cljuices.payment.repository.PaymentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final ModelMapper modelMapper;

    private final OrderClient orderClient;


    public PaymentService(PaymentRepository paymentRepository, ModelMapper modelMapper, OrderClient orderClient) {
        this.paymentRepository = paymentRepository;
        this.modelMapper = modelMapper;
        this.orderClient = orderClient;
    }

    public Page<PaymentDTO> getAll(Pageable pageable) {
        return paymentRepository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, PaymentDTO.class));
    }

    public PaymentDTO getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO createPayment(PaymentDTO dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setStatus(Status.CREATED);
        paymentRepository.save(payment);

        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO updatePayment(Long id, PaymentDTO dto) {
        Payment payment = modelMapper.map(dto, Payment.class);
        payment.setId(id);
        payment = paymentRepository.save(payment);

        return modelMapper.map(payment, PaymentDTO.class);
    }

    public PaymentDTO deletePayment(Long id) {
        Optional<Payment> byId = paymentRepository.findById(id);

        if (byId.isEmpty()){
            return null;
        }

        Payment payment = byId.get();
        paymentRepository.deleteById(payment.getId());

        return modelMapper.map(payment, PaymentDTO.class);
    }

    public void confirmPayment(Long id){
        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isEmpty()) {
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMED);
        paymentRepository.save(payment.get());
        orderClient.payOrder(payment.get().getOrderId());
    }

    public void updatePayment(Long id) {

        Optional<Payment> payment = paymentRepository.findById(id);

        if (payment.isEmpty()) {
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMED_WITHOUT_INTEGRATION);
        paymentRepository.save(payment.get());
    }
}
