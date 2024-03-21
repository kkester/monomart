package mart.mono.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchased_items")
public class PurchasedItem {
    @Id
    @GeneratedValue(generator = "uuid-hibernate-generator")
    @GenericGenerator(name = "uuid-hibernate-generator", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "purchase_id")
    @JsonIgnore
    private Purchase purchase;

    @OneToOne
    private Product product;

    private Integer quantity;
}
