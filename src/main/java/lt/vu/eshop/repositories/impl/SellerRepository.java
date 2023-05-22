package lt.vu.eshop.repositories.impl;

import lt.vu.eshop.entities.Seller;
import lt.vu.eshop.log.LoggingInterceptorBinding;
import lt.vu.eshop.repositories.Repository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@LoggingInterceptorBinding
public class SellerRepository extends Repository<Seller> implements ISellerRepository {
    @Override
    protected Class<Seller> entityClass() {
        return Seller.class;
    }
}
