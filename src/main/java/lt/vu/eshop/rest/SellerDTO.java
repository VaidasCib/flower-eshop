package lt.vu.eshop.rest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDTO {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String email;
    private String phoneNumber;
}
