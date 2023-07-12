package com.sap.ordermanegergreen.Services;

import com.sap.ordermanegergreen.Models.DiscountTypes;
import com.sap.ordermanegergreen.Models.OrderItems;
import com.sap.ordermanegergreen.Models.Orders;
import com.sap.ordermanegergreen.Models.Product;
import com.sap.ordermanegergreen.Repositories.IOrderRepository;
import com.sap.ordermanegergreen.Repositories.IOrderRepository2;
import com.sap.ordermanegergreen.Repositories.IProductRepository;
import com.sap.ordermanegergreen.exception.ObjectNotExist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private IOrderRepository orderRepository;
    private IOrderRepository2 orderRepository2;
    private IProductRepository productRepository;

    @Autowired
    public OrderService(IOrderRepository orderRepository, IOrderRepository2 orderRepository2, IProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.orderRepository2 = orderRepository2;
        this.productRepository = productRepository;
    }

    public List<Orders> getOrders(Integer pageNo, Integer pageSize, String companyId, int employeeId, String orderStatus) {

        Pageable paging = PageRequest.of(pageNo, pageSize);
        return orderRepository.findByOrderStatusId(paging, companyId, orderStatus);

    }

    public String createOrder(Orders order) {
        Orders newOrdr = this.orderRepository2.insert(order);
        return newOrdr.getId();
    }

    public void updateOrder(String id,Orders order) throws ObjectNotExist {

        if (orderRepository2.findById(id).isEmpty())
            throw new ObjectNotExist();
        orderRepository2.save(order);

    }

    public Map<String, HashMap<Double, Integer>> calculate(Orders order) {
        HashMap<String, HashMap<Double, Integer>> calculatedOrder = new HashMap<String, HashMap<Double, Integer>>();
        double totalAmount = 0;

        for (OrderItems oi: order.getOrderItemsList())
        {
            Product p= oi.getProductId();
            HashMap<Double, Integer> o = new HashMap<Double, Integer>();
            if (p.getDiscountType() == DiscountTypes.FIXED_AMOUNT)
                o.put(p.getPrice() * p.getDiscount() - p.getDiscount(), p.getDiscount());
            else
                o.put((p.getPrice() * p.getDiscount() )/100*(100-p.getDiscount()), p.getDiscount());
            calculatedOrder.put(p.getId(),o);
            totalAmount+=o.get(p.getId());
        }
        HashMap<Double, Integer> o = new HashMap<Double, Integer>();
        o.put(totalAmount, null);
        calculatedOrder.put("-1", o);
        return calculatedOrder;
    }

}
