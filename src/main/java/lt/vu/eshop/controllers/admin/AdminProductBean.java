package lt.vu.eshop.controllers.admin;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.entities.Product;
import lt.vu.eshop.entities.Seller;
import lt.vu.eshop.entities.UserRole;
import lt.vu.eshop.repositories.impl.ProductRepository;
import lt.vu.eshop.repositories.impl.SellerRepository;
import lt.vu.eshop.security.Role;
import lt.vu.eshop.security.RoleInterceptor;
import org.apache.commons.io.IOUtils;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Named
@ViewScoped
@Interceptors(RoleInterceptor.class)
@Role(UserRole.SELLER)
public class AdminProductBean implements Serializable {
    @Inject
    private ProductRepository productRepository;
    @Inject
    private SellerRepository sellerRepository;

    @Getter
    @Setter
    private Product product;

    @Getter
    @Setter
    private Part imageFile;

    @Getter
    @Setter
    private List<Seller> allSellers;

    @PostConstruct
    public void init() {
        allSellers = sellerRepository.getAll();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Map<String, String> requestParams = facesContext.getExternalContext().getRequestParameterMap();
        String productIdParam = requestParams.get("productId");
        if (productIdParam != null) {
            Long productId = Long.valueOf(productIdParam);
            // Retrieve the product using the product ID from your data source
            product = productRepository.getById(productId);
        }
    }

    @Transactional
    public void saveProduct() {
        refresh();
        productRepository.persist(product);
        goBack();
    }

    @Transactional
    public void removeProduct() {
        productRepository.delete(productRepository.getById(product.getId()));
        goBack();
    }

    private void goBack() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String redirectUrl = externalContext.getRequestContextPath() + "/index.xhtml";
        try {
            externalContext.redirect(redirectUrl);
        } catch (IOException e) {
            // Handle redirect error
        }
    }

    private void refresh() {
        Product managedProduct = productRepository.getById(product.getId());
        managedProduct.setStock(product.getStock());
        managedProduct.setName(product.getName());
        managedProduct.setSeller(product.getSeller());
        managedProduct.setImage(product.getImage());
        managedProduct.setPrice(product.getPrice());
        managedProduct.setDescription(product.getDescription());
        if (imageFile != null) {
            try {
                InputStream imageInputStream = imageFile.getInputStream();
                byte[] imageBytes = IOUtils.toByteArray(imageInputStream);
                if (imageBytes.length != 0)
                    managedProduct.setImage(imageBytes);
            } catch (IOException e) {
                // Handle the error
            }
        }
        product = managedProduct;
    }
}