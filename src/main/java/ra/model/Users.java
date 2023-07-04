package ra.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    @Size(min =6)
    private String userName;
    private String fullName;
    @Size(min = 6)
    @JsonIgnore
    private String password;
    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;
    @Lob
    private String avatar;
    private String address;
    private boolean status=true;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role ",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Roles> roles=new HashSet<>();
    @OneToMany(targetEntity =Cart.class,mappedBy = "users")
    @JsonIgnore
    private List<Cart> cart;
    @OneToMany(targetEntity =Orders.class,mappedBy = "users")
    @JsonIgnore
    private List<Orders> orders;
    @OneToMany(mappedBy = "users",targetEntity = Product.class)
    @JsonIgnore
    private List<Product> products;
}
