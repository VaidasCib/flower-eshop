package lt.vu.eshop.dto;

import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.Part;
import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Integer stock;
    private Long sellerId;
    private BigDecimal price;
    private Part imageFile;
}
