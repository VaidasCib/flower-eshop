package lt.vu.eshop.controllers.admin;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.dto.ProductDTO;
import lt.vu.eshop.entities.Product;
import lt.vu.eshop.entities.Seller;
import lt.vu.eshop.entities.UserRole;
import lt.vu.eshop.repositories.impl.ProductRepository;
import lt.vu.eshop.repositories.impl.SellerRepository;
import lt.vu.eshop.security.Role;
import lt.vu.eshop.security.RoleInterceptor;
import org.apache.commons.io.IOUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
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
import java.util.concurrent.CompletableFuture;

@Named
@ViewScoped
@Interceptors(RoleInterceptor.class)
@Role(UserRole.SELLER)
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

    @Resource
    private ManagedExecutorService managedExecutorService;

    @PostConstruct
    public void init() {
        allSellers = sellerRepository.getAll();
    }

    @Transactional
    public void createProduct() {
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
            e.printStackTrace();
        }
        productRepository.persist(product);

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                System.out.println("Doing some internal async stuff");
                Thread.sleep(5000);
                System.out.println("Done");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, managedExecutorService);
        future.exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });

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
}
