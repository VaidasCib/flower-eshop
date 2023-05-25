package lt.vu.eshop.controllers.admin;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.entities.Product;
import lt.vu.eshop.entities.Seller;
import lt.vu.eshop.entities.UserRole;
import lt.vu.eshop.log.LoggingInterceptorBinding;
import lt.vu.eshop.repositories.impl.ProductRepository;
import lt.vu.eshop.repositories.impl.SellerRepository;
import lt.vu.eshop.security.Role;
import lt.vu.eshop.security.RoleInterceptor;
import org.apache.commons.io.IOUtils;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.persistence.OptimisticLockException;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Named
@ViewScoped
@Interceptors(RoleInterceptor.class)
@Role(UserRole.SELLER)
@LoggingInterceptorBinding
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
    private Product updatedProduct;

    @Getter
    @Setter
    private Part imageFile;

    @Getter
    @Setter
    private List<Seller> allSellers;

    @Getter
    @Setter
    private boolean errorMsg;

    @PostConstruct
    public void init() {
        allSellers = sellerRepository.getAll();
        Map<String, String> requestParams = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String productIdParam = requestParams.get("productId");
        if (productIdParam != null) {
            Long productId = Long.valueOf(productIdParam);
            // Retrieve the product using the product ID from your data source
            product = productRepository.getById(productId);
        }
    }

    @Transactional
    public void saveProduct() {
        try {
            saveImage();
            productRepository.merge(product);
            goBack();
        } catch (OptimisticLockException e) {
            updatedProduct = productRepository.getById(product.getId());
            errorMsg = true;
        }
    }

    @Transactional
    public void saveAnyway() {
        Product productDb = productRepository.getById(product.getId());
        product.setVersion(productDb.getVersion());
        productRepository.merge(product);
        goBack();
    }

    @Transactional
    public void removeProduct() {
        productRepository.delete(productRepository.getById(product.getId()));
        goBack();
    }

    public void goBack() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        String redirectUrl = externalContext.getRequestContextPath() + "/index.xhtml";
        try {
            externalContext.redirect(redirectUrl);
        } catch (IOException e) {
            // Handle redirect error
        }
    }

    private void saveImage() {
        if (imageFile != null) {
            try {
                InputStream imageInputStream = imageFile.getInputStream();
                byte[] imageBytes = IOUtils.toByteArray(imageInputStream);
                if (imageBytes.length != 0)
                    product.setImage(imageBytes);
            } catch (IOException e) {
                // Handle the error
            }
        }
    }
}