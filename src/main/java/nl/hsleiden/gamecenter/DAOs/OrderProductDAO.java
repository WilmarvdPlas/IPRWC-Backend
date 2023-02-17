package nl.hsleiden.gamecenter.DAOs;

import nl.hsleiden.gamecenter.repositories.OrderProductRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderProductDAO {

    private final OrderProductRepository repository;

    public OrderProductDAO(OrderProductRepository repository) {
        this.repository = repository;
    }

}
