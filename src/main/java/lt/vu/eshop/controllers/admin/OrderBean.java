package lt.vu.eshop.controllers.admin;

import lt.vu.eshop.entities.CartItem;
import lt.vu.eshop.entities.Order;
import lt.vu.eshop.entities.OrderStatus;
import lt.vu.eshop.entities.UserRole;
import lt.vu.eshop.repositories.impl.OrderRepository;
import lt.vu.eshop.security.Role;
import lt.vu.eshop.security.RoleInterceptor;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import javax.transaction.Transactional;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
@Interceptors(RoleInterceptor.class)
@Role(UserRole.SELLER)
public class OrderBean implements Serializable {
    @Inject
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.getAll();
    }

    public List<CartItem> getOrderItems(Order order) {
        return order.getCartItems();
    }

    @Transactional
    public void cancelOrder(Order order) {
        order = orderRepository.getById(order.getId());
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.persist(order);
        refresh();
    }

    @Transactional
    public void completeOrder(Order order) {
        order = orderRepository.getById(order.getId());
        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.persist(order);
        refresh();
    }

    private void refresh() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        String redirectUrl = facesContext.getExternalContext().getRequestContextPath() + "/admin/orders.xhtml";
        try {
            facesContext.getExternalContext().redirect(redirectUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
