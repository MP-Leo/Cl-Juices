package br.com.cljuices.orders.service;

import br.com.cljuices.orders.dto.OrderDTO;
import br.com.cljuices.orders.dto.StatusDto;
import br.com.cljuices.orders.model.Order;
import br.com.cljuices.orders.model.Status;
import br.com.cljuices.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private final ModelMapper modelMapper;


    public List<OrderDTO> getAll() {
        return repository.findAll().stream()
                .map(p -> modelMapper.map(p, OrderDTO.class))
                .collect(Collectors.toList());
    }

    public OrderDTO getById(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        return modelMapper.map(order, OrderDTO.class);
    }

    public OrderDTO create(OrderDTO dto) {
        Order order = modelMapper.map(dto, Order.class);

        order.setDateTime(LocalDateTime.now());
        order.setStatus(Status.PLACED);
        order.getItems().forEach(item -> item.setOrder(order));
        Order saved = repository.save(order);

        return modelMapper.map(saved, OrderDTO.class);
    }

    public OrderDTO updateStatus(Long id, StatusDto dto) {

        Order order = repository.byIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(dto.getStatus());
        repository.updateStatus(dto.getStatus(), order);
        return modelMapper.map(order, OrderDTO.class);
    }

    public void aproveOrderPayment(Long id) {

        Order order = repository.byIdWithItems(id);

        if (order == null) {
            throw new EntityNotFoundException();
        }

        order.setStatus(Status.PAID);
        repository.updateStatus(Status.PAID, order);
    }
}
