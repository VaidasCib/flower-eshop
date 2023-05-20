package lt.vu.eshop.controllers;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.entities.Product;
import lt.vu.eshop.repositories.impl.ProductRepository;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Serializable;
import java.util.Base64;
import java.util.List;

@Named
@ViewScoped
public class ViewProductsBean implements Serializable {
    @Inject
    private ProductRepository productRepository;

    @Getter
    @Setter
    private List<Product> allProducts;

    @PostConstruct
    public void init() {
        allProducts = productRepository.getAll();
    }

    public String getImageBase64(Product product) {
        if (product != null && product.getImage() != null) {
            return Base64.getEncoder().encodeToString(product.getImage());
        }
        return null;
    }

    public void viewProduct(Product product) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String productIdParam = "productId=" + product.getId();
        String redirectUrl = externalContext.getRequestContextPath() + "/product.xhtml?" + productIdParam;
        try {
            externalContext.redirect(redirectUrl);
        } catch (IOException e) {
            // Handle redirect error
        }
    }

    public void viewProductAdmin(Product product) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String productIdParam = "productId=" + product.getId();
        String redirectUrl = externalContext.getRequestContextPath() + "/admin/productAdmin.xhtml?" + productIdParam;
        try {
            externalContext.redirect(redirectUrl);
        } catch (IOException e) {
            // Handle redirect error
        }
    }

}
