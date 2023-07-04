package ra.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "order_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private float price;
    private  int quantity;
    @ManyToOne()
    @JoinColumn(name = "proId")
    private Product pro;
    @ManyToOne()
    @JoinColumn(name = "orderId")
    private Orders order;
}
