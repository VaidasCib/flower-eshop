package lt.vu.eshop.controllers.admin;

import lt.vu.eshop.entities.Order;
import lt.vu.eshop.entities.OrderStatus;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Alternative
@ApplicationScoped
public class TranslatedOrderStatus implements IOrderStatus {
    private static final Map<OrderStatus, String> orderStatusTranslations;
    static {
        Map<OrderStatus, String> map = new HashMap<>();
        map.put(OrderStatus.PLACED, "Užsakyta");
        map.put(OrderStatus.COMPLETED, "Užbaigta");
        map.put(OrderStatus.CANCELED, "Atšaukta");
        orderStatusTranslations = Collections.unmodifiableMap(map);
    }

    @Override
    public String getStatus(Order order) {
        return orderStatusTranslations.get(order.getStatus());
    }
}
