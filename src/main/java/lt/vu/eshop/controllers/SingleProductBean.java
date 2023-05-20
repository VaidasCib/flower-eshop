package lt.vu.eshop.controllers;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.entities.Product;
import lt.vu.eshop.repositories.impl.ProductRepository;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Base64;
import java.util.Map;

@Named
@ViewScoped
public class SingleProductBean implements Serializable {
    @Inject
    private CartBean cartBean;

    @Inject
    private ProductRepository productRepository;

    @Getter
    @Setter
    private Product product;

    @Getter
    @Setter
    private int quantity;

    @Getter
    private BigDecimal totalPrice;

    public void updateTotalPrice() {
        totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        String productIdParam = requestParams.get("productId");
        if (productIdParam != null) {
            Long productId = Long.valueOf(productIdParam);
            // Retrieve the product using the product ID from your data source
            product = productRepository.getById(productId);
        }
    }

    public String getImageBase64() {
        if (product != null && product.getImage() != null) {
            return Base64.getEncoder().encodeToString(product.getImage());
        }
        return null;
    }

    public void addToCart(Product product) {
        cartBean.addToCart(product, quantity);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String redirectUrl = facesContext.getExternalContext().getRequestContextPath() + "/cart.xhtml";
        try {
            facesContext.getExternalContext().redirect(redirectUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}