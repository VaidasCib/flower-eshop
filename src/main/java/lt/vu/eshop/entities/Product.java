package lt.vu.eshop.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    private String name;

    @Basic
    @Column(length = 10000000)
    private byte[] image;

    @Basic
    private String description;

    @Basic
    private Integer stock;

    @Basic
    private BigDecimal price;

    @ManyToOne
    private Seller seller;

    @Version
    private int version;
}
