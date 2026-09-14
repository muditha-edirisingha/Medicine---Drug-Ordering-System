package edu.sliit.Controller;

import edu.sliit.dto.Order;
import edu.sliit.dto.OrderItem;
import edu.sliit.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class OrderItemController {
    final OrderItemService service;

    @GetMapping("/get-all-order-items")
    public List<OrderItem> getOrderItems(){
        return service.getOrderItems();
    }

    @GetMapping("/search-by-order-item-id/{orderItemId}")
    public OrderItem searchByOrderItemId(
            @PathVariable Integer orderItemId) {

        return service.searchByOrderItemId(orderItemId);
    }

    @PostMapping("/add-order-item")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOrderItem(@RequestBody OrderItem orderItem){
        service.addOrderItem(orderItem);

    }

    @DeleteMapping("/delete-order-item/{orderItemId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteOrderItem(
            @PathVariable Integer orderItemId) {

        service.deleteByOrderItemId(orderItemId);
    }

    @PutMapping("/update-order-item")
    @ResponseStatus(HttpStatus.OK)
    public void updateOrderItem(
            @RequestBody OrderItem orderItem) {

        service.updateOrderItem(orderItem);
    }

}
