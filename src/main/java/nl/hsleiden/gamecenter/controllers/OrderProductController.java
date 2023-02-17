package nl.hsleiden.gamecenter.controllers;

import nl.hsleiden.gamecenter.DAOs.OrderDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/order_product")
public class OrderProductController {

    private final OrderDAO orderDAO;

    @Autowired
    public OrderProductController(OrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

}
