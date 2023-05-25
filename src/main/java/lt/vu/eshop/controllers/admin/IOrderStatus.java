package lt.vu.eshop.controllers.admin;

import lt.vu.eshop.entities.Order;

public interface IOrderStatus {
    String getStatus(Order order);
}
