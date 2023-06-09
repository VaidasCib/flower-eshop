package lt.vu.eshop.controllers.admin;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.entities.Seller;
import lt.vu.eshop.entities.UserRole;
import lt.vu.eshop.log.LoggingInterceptorBinding;
import lt.vu.eshop.repositories.impl.SellerRepository;
import lt.vu.eshop.security.Role;
import lt.vu.eshop.security.RoleInterceptor;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import java.io.Serializable;

@Named
@ViewScoped
@Interceptors(RoleInterceptor.class)
@Role(UserRole.SELLER)
@LoggingInterceptorBinding
public class SellerBean implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private SellerRepository sellerDAO;

    @Getter @Setter
    private Seller selectedSeller;

    @PostConstruct
    public void init() {
        String sellerId = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
        if (sellerId != null) {
            selectedSeller = sellerDAO.getById(Long.parseLong(sellerId));
        }
    }
}