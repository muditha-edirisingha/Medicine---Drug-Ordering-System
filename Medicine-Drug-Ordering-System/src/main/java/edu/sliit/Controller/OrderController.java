package edu.sliit.Controller;


import edu.sliit.dto.Order;
import edu.sliit.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {

    final OrderService service ;

    @GetMapping("/get-all-order")
    public List<Order> getOrder(){
        return service.getOrder();
    }

    @PostMapping("/add-order")
    public  void addOrder(@RequestBody Order order){
        service.addOrder(order);

    }

    @DeleteMapping("/delete-by-id/{orderId}")
    public void deleteById(@PathVariable Integer orderId){
        service.deleteByOrderId(orderId);
    }
}
