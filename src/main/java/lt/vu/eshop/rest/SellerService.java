package lt.vu.eshop.rest;

import lt.vu.eshop.entities.Seller;
import lt.vu.eshop.repositories.impl.SellerRepository;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@RequestScoped
public class SellerService {
    @Inject
    private SellerRepository sellerRepository;

    public SellerDTO getById(Long id) {
        Seller seller = sellerRepository.getById(id);
        return mapToDTO(seller);
    }

    @Transactional
    public SellerDTO save(SellerDTO dto) {
        Seller seller = sellerRepository.getById(dto.getId());
        if (seller == null) {
            seller = new Seller();
        }
        seller.setId(dto.getId());
        seller.setAddress(dto.getAddress());
        seller.setName(dto.getName());
        seller.setDescription(dto.getDescription());
        seller.setEmail(dto.getEmail());
        seller.setPhoneNumber(dto.getPhoneNumber());
        sellerRepository.persist(seller);
        return mapToDTO(seller);
    }

    private SellerDTO mapToDTO(Seller seller) {
        SellerDTO dto = new SellerDTO();
        dto.setId(seller.getId());
        dto.setName(seller.getName());
        dto.setDescription(seller.getDescription());
        dto.setEmail(seller.getEmail());
        dto.setAddress(seller.getAddress());
        dto.setPhoneNumber(seller.getPhoneNumber());
        return dto;
    }
}
