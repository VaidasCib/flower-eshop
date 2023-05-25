package lt.vu.eshop.controllers.admin;

import lt.vu.eshop.entities.Order;

public interface IOrderBean {
    void cancelOrder(Order order);

    void completeOrder(Order order);
}
