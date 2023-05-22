package lt.vu.eshop.log;


import lt.vu.eshop.entities.Seller;
import lt.vu.eshop.repositories.impl.ISellerRepository;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public class SellerRepositoryLogger implements ISellerRepository {
    @Inject
    @Delegate
    private ISellerRepository sellerRepository;

    @Override
    public Seller getById(Long id) {
        Seller seller = sellerRepository.getById(id);
        System.out.println("Accessing seller: " + seller.getName());
        return seller;
    }
}
