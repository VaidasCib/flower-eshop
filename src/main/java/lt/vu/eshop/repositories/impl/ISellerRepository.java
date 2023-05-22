package lt.vu.eshop.repositories.impl;

import lt.vu.eshop.entities.Seller;

public interface ISellerRepository {
    Seller getById(Long id);
}
