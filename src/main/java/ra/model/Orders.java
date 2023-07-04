package ra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  float total;
    private String receiverName;
    private String address;
    private String phone;

    private String email;
    private boolean status;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(columnDefinition = "date")
    private String timeReceiver;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(columnDefinition = "date")
    private String createAt;

    @OneToMany(mappedBy = "order",targetEntity = OrderDetail.class)
    @JsonIgnore
    private List<OrderDetail> listPro;
    @ManyToOne
    @JoinColumn(name = "userId")
    private Users users;

}
