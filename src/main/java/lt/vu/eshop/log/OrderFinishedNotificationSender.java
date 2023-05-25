package lt.vu.eshop.log;

import lt.vu.eshop.controllers.admin.IOrderBean;
import lt.vu.eshop.entities.Order;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;
import java.io.Serializable;

@Decorator
public class OrderFinishedNotificationSender implements IOrderBean, Serializable {
    @Inject
    @Delegate
    private IOrderBean orderBean;

    @Override
    public void cancelOrder(Order order) {
        orderBean.cancelOrder(order);
        System.out.println("Order: " + order.getId() + " was canceled");
        System.out.println("Sending order canceled email to: " + order.getEmail());
        // TODO: Implement email sending
    }

    @Override
    public void completeOrder(Order order) {
        orderBean.completeOrder(order);
        System.out.println("Order: " + order.getId() + " was completed");
        System.out.println("Sending order completed email to: " + order.getEmail());
        // TODO: Implement email sending
    }
}
