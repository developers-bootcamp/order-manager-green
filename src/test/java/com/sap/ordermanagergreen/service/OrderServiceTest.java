//package com.sap.ordermanagergreen.service;
//
//import com.sap.ordermanagergreen.dto.TokenDTO;
////import com.sap.ordermanagergreen.exception.CompanyNotExistException;
//import com.sap.ordermanagergreen.model.*;
//import com.sap.ordermanagergreen.repository.ICompanyRepository;
//import com.sap.ordermanagergreen.repository.IOrderRepository;
//import com.sap.ordermanagergreen.repository.IProductRepository;
//import com.sap.ordermanagergreen.resolver.ProductResolver;
//import com.sap.ordermanagergreen.resolver.TokenResolver;
//import com.sap.ordermanagergreen.util.JwtToken;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import com.sap.ordermanagergreen.resolver.OrderResolver;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
//import static org.hamcrest.Matchers.any;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@ExtendWith({MockitoExtension.class, OrderResolver.class, ProductResolver.class, TokenResolver.class})
//
//public class OrderServiceTest {
//    @InjectMocks
//    private OrderService orderService;
//    @Mock
//    private IOrderRepository iOrderRepository;
//    @Mock
//    private IProductRepository iProductRepository;
//    @Mock
//    private ICompanyRepository companyRepository;
//    @Mock
//    private JwtToken jwtToken;
//
//
//    @BeforeEach
//    private void setUp() {
//        System.out.print("~~~~~~~~~~~~~~ TEST: ");
//    }
//    @AfterAll
//    private static void check(){
//        System.out.println("finished");
//    }
//
//    @Test
//    public void testCalculateOrderAmount_whenSuccessfully_thenReturnCalculatedMap(Order order, Product product) {
//
//        OrderItem orderItem = OrderItem.builder().quantity(1).product(product).build();
//        order.setOrderItemsList(List.of(orderItem));
//
//        when(iProductRepository.findById(orderItem.getProduct().getId())).thenReturn(Optional.of(product));
//        Map<String, HashMap<Double, Integer>> result=new HashMap<>();
//try {
//     result = orderService.calculate(order);
//}
//      catch (Exception e){
//
//      }
//
//        System.out.println(result);
//        if (product.getDiscountType() == DiscountType.FIXED_AMOUNT)
//            assertEquals(result.get(product.getId()).keySet().iterator().next(), (product.getPrice() - product.getDiscount()) * orderItem.getQuantity());
//        else
//            assertEquals(result.get(product.getId()).keySet().iterator().next(), (product.getPrice() / 100) * (100 - orderItem.getQuantity()));
//        assertEquals(result.get(product.getId()).values().iterator().next(), product.getDiscount());
//
//    }
////    @Test
////    public void testCreateOrder_whenOk(Order order,TokenDTO token) throws Exception {
////        Mockito.when(iOrderRepository.save(order)).thenReturn(order);
////        String result = orderService.add(order,token);
////        Mockito.verify(iOrderRepository, Mockito.times(1));
////        Assertions.assertEquals(order, result);
////    }
////    @Test
////    public void testCreateOrder_whenCompanyNotExist_throwException(Order order,TokenDTO token)throws CompanyNotExistException,Exception {
////        Mockito.when(this.companyRepository.findById(token.getCompanyId())==null).then(throw new CompanyNotExistException);
////
////        assertThatThrownBy(() -> orderService.add(order,token)).isInstanceOf(CompanyNotExistException.class);
////    }
//}
