package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.repositories.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderDAO {

    private final OrderRepository repository;

    public OrderDAO(OrderRepository repository) {
        this.repository = repository;
    }

}
