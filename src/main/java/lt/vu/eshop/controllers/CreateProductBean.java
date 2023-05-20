package lt.vu.eshop.controllers;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.dto.ProductDTO;
import lt.vu.eshop.entities.Product;
import lt.vu.eshop.entities.Seller;
import lt.vu.eshop.repositories.impl.ProductRepository;
import lt.vu.eshop.repositories.impl.SellerRepository;
import org.apache.commons.io.IOUtils;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class CreateProductBean implements Serializable {
    @Inject
    private ProductRepository productRepository;
    @Inject
    private SellerRepository sellerRepository;

    @Getter @Setter
    private List<Seller> allSellers;

    @Getter
    @Setter
    private ProductDTO productDto = new ProductDTO();

    @PostConstruct
    public void init() {
        allSellers = sellerRepository.getAll();
    }

    @Transactional
    public String createProduct() {
        Product product = new Product();
        product.setSeller(sellerRepository.getById(productDto.getSellerId()));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setStock(productDto.getStock());
        product.setPrice(productDto.getPrice());

        try {
            Part imagePart = productDto.getImageFile();
            InputStream imageInputStream = imagePart.getInputStream();
            byte[] imageBytes = IOUtils.toByteArray(imageInputStream);
            product.setImage(imageBytes);
        } catch (IOException e) {
            // Handle the error
        }

        productRepository.persist(product);
        return "index.xhtml?faces-redirect=true";
    }
}
