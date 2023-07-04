package ra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float price;
    private  int quantity;
    @ManyToOne()
    @JoinColumn(name = "proId")
    private Product pro;
    @ManyToOne()
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
