package edu.sliit.Controller;

import edu.sliit.dto.Order;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@RestController
@CrossOrigin
public class OrderController {
    @GetMapping
    public Order getOrder(){
        return new Order(
            001,
                LocalDateTime.parse("2007-12-03T10:15:30"),
                "accepted",
                150.0,
                "Ellakkala"


        );
    }
}
