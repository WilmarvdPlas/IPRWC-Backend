package nl.hsleiden.gamecenter.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "account", referencedColumnName = "id", nullable = false)
    private Account account;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "transaction")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<TransactionProduct> transactionProducts;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "date_created")
    private Date dateCreated;

}
