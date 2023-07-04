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
@Table(name = "reviews_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewsProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(columnDefinition = "date")
    private String time;
    private boolean status;
    @OneToMany(mappedBy = "reProId",targetEntity = Image.class)
    @JsonIgnore
    private List<Image> images;
}
