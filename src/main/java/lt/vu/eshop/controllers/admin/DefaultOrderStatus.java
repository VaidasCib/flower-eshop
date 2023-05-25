package lt.vu.eshop.controllers.admin;

import lt.vu.eshop.entities.Order;

public class DefaultOrderStatus implements IOrderStatus {
    @Override
    public String getStatus(Order order) {
        return order.getStatus().name();
    }
}
