package lt.vu.eshop.controllers;

import lombok.Getter;
import lombok.Setter;
import lt.vu.eshop.dto.CartItemDTO;
import lt.vu.eshop.entities.CartItem;
import lt.vu.eshop.entities.Order;
import lt.vu.eshop.entities.OrderStatus;
import lt.vu.eshop.entities.Product;
import lt.vu.eshop.repositories.impl.CartItemRepository;
import lt.vu.eshop.repositories.impl.OrderRepository;
import lt.vu.eshop.repositories.impl.ProductRepository;
import lt.vu.eshop.security.UserStorage;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Named
@SessionScoped
public class CartBean implements Serializable {
    @Inject
    private ProductRepository productRepository;

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private CartItemRepository cartItemRepository;

    @Inject
    private UserStorage userStorage;

    @Getter
    @Setter
    private List<CartItemDTO> cartItems = new ArrayList<>();

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String address;

    @Getter
    @Setter
    private String deliveryType;

    public void addToCart(Product product, int quantity) {
        // Check if the product is already in the cart
        for (CartItemDTO item : cartItems) {
            if (item.getProductId().equals(product.getId())) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        // Product not in the cart, add it as a new item
        cartItems.add(new CartItemDTO(product.getId(), quantity));
    }

    public Product getProduct(CartItemDTO item) {
        return productRepository.getById(item.getProductId());
    }

    public void removeFromCart(CartItemDTO cartItem) {
        Iterator<CartItemDTO> iterator = cartItems.iterator();
        while (iterator.hasNext()) {
            CartItemDTO item = iterator.next();
            if (item.getProductId().equals(cartItem.getProductId())) {
                iterator.remove();
                return;
            }
        }
    }

    @Transactional
    public String checkout() {
        Order order = new Order();
        order.setUser(userStorage.getUser());
        order.setName(name);
        order.setEmail(email);
        order.setAddress(address);
        order.setDeliveryType(deliveryType);
        order.setStatus(OrderStatus.PLACED);
        orderRepository.persist(order);
        cartItems.stream().map(cartItemDTO -> {
            CartItem cartItem = new CartItem();
            cartItem.setOrder(order);
            cartItem.setQuantity(cartItemDTO.getQuantity());
            cartItem.setProduct(productRepository.getById(cartItemDTO.getProductId()));
            return cartItem;
        }).forEach(cartItemRepository::persist);
        cartItems.clear();
        return back();
    }

    public String back() {
        return "index.xhtml";
    }
}