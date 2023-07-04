package ra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String title;
    private String description;
    private int  quantity;
    private float price;
    private  int status=1;
    @OneToMany(mappedBy = "proId", targetEntity =Image.class)
    @JsonIgnore
    private List<Image> images;
    @ManyToOne()
    @JoinColumn(name = "cateId")
    private Category cate;
    @OneToMany(mappedBy = "pro",targetEntity = CartItem.class)
    @JsonIgnore
    private List<CartItem> cartItem;
    @OneToMany(mappedBy = "pro",targetEntity = OrderDetail.class)
    @JsonIgnore
    private List<OrderDetail> oDetail;
    @ManyToOne()
    @JoinColumn(name = "userId")
    private Users users;
}
