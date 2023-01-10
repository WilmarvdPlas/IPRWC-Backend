package nl.hsleiden.gamecenter.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "\"cart_product\"")
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName = "id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
    private Account account;

}

