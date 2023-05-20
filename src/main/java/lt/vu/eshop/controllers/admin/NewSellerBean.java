package lt.vu.eshop.controllers.admin;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.entities.Seller;
import lt.vu.eshop.entities.UserRole;
import lt.vu.eshop.repositories.impl.SellerRepository;
import lt.vu.eshop.security.Role;
import lt.vu.eshop.security.RoleInterceptor;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.transaction.Transactional;
import java.io.Serializable;

@Named
@ViewScoped
@Interceptors(RoleInterceptor.class)
@Role(UserRole.SELLER)
public class NewSellerBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private SellerRepository sellerRepository;

    @Getter
    @Setter
    private Seller seller = new Seller();

    @Transactional
    public String createSeller() {
        sellerRepository.persist(seller);
        return "/index.xhtml?faces-redirect=true";
    }
}
