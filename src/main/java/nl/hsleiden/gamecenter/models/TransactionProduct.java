package nl.hsleiden.gamecenter.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "transaction_product")
public class TransactionProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "transaction", referencedColumnName = "id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "product", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column
    private int count;

    @Column
    private double paymentEuro;

    @Column
    private boolean delivered;

}
