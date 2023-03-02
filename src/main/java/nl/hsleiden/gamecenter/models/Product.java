package nl.hsleiden.gamecenter.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String name;

    @Column
    private double price;

    @Column
    private String image;

    @Column
    private int stock;

    @Column
    private int discountPercentage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<TransactionProduct> transactionProducts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<TransactionProduct> cartProducts;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "date_created")
    private Date dateCreated;

}
