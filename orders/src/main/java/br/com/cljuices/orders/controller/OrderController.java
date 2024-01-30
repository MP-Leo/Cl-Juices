package br.com.cljuices.orders.controller;

import br.com.cljuices.orders.dto.OrderDTO;
import br.com.cljuices.orders.dto.StatusDto;
import br.com.cljuices.orders.model.Status;
import br.com.cljuices.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

        @Autowired
        private OrderService service;

        @GetMapping()
        public List<OrderDTO> listAll() {
            return service.getAll();
        }

        @GetMapping("/port")
        public String returnPort(@Value("${local.server.port}") String port){
            return String.format("Request received to Order-MS using port %s", port);
        }

        @GetMapping("/{id}")
        public ResponseEntity<OrderDTO> listById(@PathVariable @NotNull Long id) {
            OrderDTO dto = service.getById(id);

            return  ResponseEntity.ok(dto);
        }

        @PostMapping()
        public ResponseEntity<OrderDTO> createOrder(@RequestBody @Valid OrderDTO dto, UriComponentsBuilder uriBuilder) {
            OrderDTO orderPlaced = service.create(dto);

            URI address = uriBuilder.path("/v1/orders/{id}").buildAndExpand(orderPlaced.getId()).toUri();

            return ResponseEntity.created(address).body(orderPlaced);

        }

        @PutMapping("/{id}/status")
        public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @RequestBody StatusDto status){

            OrderDTO dto = service.updateStatus(id, status);

            return ResponseEntity.ok(dto);
        }

        @PutMapping("/{id}/pay")
        public ResponseEntity<OrderDTO> payOrder(@PathVariable Long id){
            StatusDto statusDto = new StatusDto(Status.PAID);
            OrderDTO dto = service.updateStatus(id,statusDto);

            return ResponseEntity.ok(dto);
        }


        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteOrder(@PathVariable @NotNull Long id) {
            service.aproveOrderPayment(id);

            return ResponseEntity.ok().build();

        }
}
