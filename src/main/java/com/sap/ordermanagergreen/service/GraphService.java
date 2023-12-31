package com.sap.ordermanagergreen.service;

import com.mongodb.BasicDBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.dto.TopEmployeeDTO;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.model.Currency;
import com.sap.ordermanagergreen.repository.*;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.util.JwtToken;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
import java.util.*;
import java.util.stream.Collectors;
import java.util.ArrayList;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import org.springframework.data.mongodb.core.MongoTemplate;


@Service
public class GraphService {

    @Autowired
    public MongoTemplate mongoTemplate;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IProductCategoryRepository productCategoryRepository;

    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    ICompanyRepository companyRepository;

@Getter
@Setter
public class MonthlyProductSalesResult {
    private int month;
    private int year;
   private List<ProductData> products;

    @Getter
    @Setter
    public static class ProductData {
        private String product;
        private int quantity;
    }
}
    @Getter
    @Setter
    public class DynamicGraphResult{
        private Object field;
        private int count;
    }


    public List<TopEmployeeDTO> getTopEmployee(String companyId) {

        Aggregation aggregation = newAggregation(
                match(Criteria.where("auditData.updateDate").gte(LocalDate.now().minusMonths(3))),
                match(Criteria.where("orderStatus").is(OrderStatus.DONE)),
                match(Criteria.where("company.$id").is(companyId)),
                group("employee").count().as("countOfDeliveredOrders"),
                project("countOfDeliveredOrders").and("_id").as("user"),
                sort(Sort.Direction.DESC, "countOfDeliveredOrders"),
                limit(5)
        );

        AggregationResults<TopEmployeeDTO> result = mongoTemplate.aggregate(
                aggregation, Order.class, TopEmployeeDTO.class
        );

        return result.getMappedResults();
    }

    public List<MonthlyProductSalesResult> getMonthlyProductSales() {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeMonthsAgo = currentDate.minusMonths(3);

        Aggregation aggregationTemp = newAggregation(
                match(Criteria.where("auditData.updateDate").gte(threeMonthsAgo).and("orderStatus").is("DONE")),
                unwind("orderItemsList"),
                project()
                        .andExpression("month(auditData.updateDate)").as("month")
                        .and("orderItemsList.product").as("productId")
                        .and("orderItemsList.quantity").as("quantity"),
                lookup("Product", "productId.$id", "_id", "productData"),
                unwind("productData"),
                project()
                        .and("productData.name").as("product")
                        .and("quantity").as("quantity"),
                group(Fields.fields("product"))
                        .sum("quantity").as("totalQuantity"),
                sort(Sort.Direction.DESC, "totalQuantity"),
                limit(5),
                project()
                        .and("_id.product").as("product")
        );
        AggregationResults<String> resultsTemp = mongoTemplate.aggregate(
                aggregationTemp, "Orders", String.class
        );
        List<String> productNames = resultsTemp.getMappedResults()
                .stream()
                .map(result -> result.substring(9, result.lastIndexOf("}") - 1))
                .collect(Collectors.toList());

        Aggregation aggregation = newAggregation(
                match(Criteria.where("auditData.updateDate").gte(threeMonthsAgo).and("orderStatus").is("DONE")),
                unwind("orderItemsList"),
                project()
                        .andExpression("month(auditData.updateDate)").as("month")
                        .andExpression("year(auditData.updateDate)").as("year")
                        .and("orderItemsList.product").as("productId")
                        .and("orderItemsList.quantity").as("quantity"),
                lookup("Product", "productId.$id", "_id", "productData"),
                unwind("productData"),
                project()
                        .and("year").as("year")
                        .and("month").as("month")
                        .and("productData.name").as("product")
                        .and("quantity").as("quantity"),
                group(Fields.fields("product", "month","year"))
                        .sum("quantity").as("totalQuantity"),
                project()
                        .and("_id.product").as("product")
                        .and("_id.month").as("month")
                        .and("year").as("year")
                        .and("totalQuantity").as("totalQuantity"),
                match(Criteria.where("product").in(productNames)),
                project()
                        .and("product").as("product")
                        .and("month").as("month")
                        .and("year").as("year")
                        .and("totalQuantity").as("totalQuantity"),
                group("month","year")
                        .push(new BasicDBObject("product", "$product").append("quantity", "$totalQuantity"))
                        .as("products"),
                project()
                        .and("_id.month").as("month")
                        .and("_id.year").as("year")
                        .and("products").as("products")
        );

        AggregationResults<MonthlyProductSalesResult> results = mongoTemplate.aggregate(
                aggregation, "Orders", MonthlyProductSalesResult.class
        );

        return results.getMappedResults();
       }

   public List<DeliverCancelOrdersDTO> getDeliverCancelOrders() {
        LocalDate currentDate = LocalDate.now();
        LocalDate threeMonthsAgo = currentDate.minusMonths(3);

           Aggregation aggregation = newAggregation(
                   match(Criteria.where("auditData.updateDate").gte(threeMonthsAgo)),
                   project()
                           .andExpression("month(auditData.updateDate)").as("month")
                           .andExpression("year(auditData.updateDate)").as("year")
                           .and("orderStatus").as("orderStatus"),
                   group("month","year")
                           .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatus").notEqualToValue(OrderStatus.PAYMENT_CANCELED)).then(1).otherwise(0)).as("delivered")
                           .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatus").equalToValue(OrderStatus.PAYMENT_CANCELED)).then(1).otherwise(0)).as("cancelledPayment")
                           .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatus").equalToValue(OrderStatus.process_CANCELED)).then(1).otherwise(0)).as("cancelledProcess"),
                   project()
                           .and("_id.month").as("month")
                           .and("_id.year").as("year")
                           .and("cancelledProcess").plus("cancelledPayment").as("cancelled")
                           .and("delivered").minus("cancelledProcess").as("delivered")
           );
        AggregationResults<DeliverCancelOrdersDTO> results = mongoTemplate.aggregate(aggregation, "Orders", DeliverCancelOrdersDTO.class);
        List<DeliverCancelOrdersDTO> mappedResults = results.getMappedResults();

        return results.getMappedResults();
        }
        

    public List<DynamicGraphResult> dynamicGraph(String object,String field){

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("auditData.updateDate").gte(LocalDate.now().minusMonths(3))),
                Aggregation.project()
                        .and(field).as(field)
                        .andExpression("auditData.updateDate").substring(0,7).as("monthYear"),
                Aggregation.group(field).count().as("count"),
                Aggregation.project("count")
                        .and("_id").as("field")
                        .and("count").as("count")
        );

        AggregationResults<DynamicGraphResult> results=mongoTemplate.aggregate(
                aggregation,object,DynamicGraphResult.class
        );
        return results.getMappedResults();
    }



    public void fill() {
            List<Company> companies = new ArrayList<Company>();
            List<Role> roles = new ArrayList<Role>();
            List<User> users = new ArrayList<User>();
            List<Order> orders = new ArrayList<Order>();

            AuditData d = AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build();
            AuditData d1 = new AuditData(LocalDateTime.now(), LocalDateTime.now());
            AuditData d2 = new AuditData(LocalDateTime.now(), LocalDateTime.now());
            AuditData d3 = new AuditData(LocalDateTime.now(), LocalDateTime.now());

            Company company1 = new Company("11", "Poto", null, d3);
            Company company2 = new Company("12", "PotoGeula", null, d2);
            Company company3 = new Company("13", "Grafgik", null, d2);
            companies.add(company1);
            companies.add(company2);
            companies.add(company3);
            Role role1 = new Role("101", AvailableRole.ADMIN, "bos", d3);
            Role role2 = new Role("102", AvailableRole.EMPLOYEE, "GOOD EMPLOYEE", d2);
            Role role3 = new Role("103", AvailableRole.CUSTOMER, "CUSTOMER", d1);
            roles.add(role1);
            roles.add(role2);
            roles.add(role3);
            User user1 = new User("1001", "Shlomo Cohen", "1001", new Address(), role1, company1, d3);
            User user2 = new User("1002", "Yoram", "1002", new Address(), role2, company1, d2);
            User user6 = new User("1006", "Mendi", "1006", new Address(), role2, company1, d2);
            User user7 = new User("1007", "Morya", "1007", new Address(), role2, company1, d2);

            User user3 = new User("1003", "family Simoni", "1003", new Address(), role3, company1, d1);
            User user4 = new User("1004", "family Markoviz", "1004", new Address(), role3, company1, d1);
            User user5 = new User("1005", "family Chayimoviz", "1005", new Address(), role3, company1, d1);
            users.add(user2);
            users.add(user3);
            users.add(user4);
            users.add(user5);
            users.add(user6);
            users.add(user7);

            for (int i = 1; i < 10; i++) {
                AuditData ds = AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build();
                ProductCategory pc = new ProductCategory(String.valueOf(i), "name" + i, "desc" + i, company1, AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build());
                productCategoryRepository.save(pc);
                Product p = new Product(String.valueOf(i), "aaa", "aaa", 40, 50, DiscountType.PERCENTAGE, pc, 4, company1, AuditData.builder().updateDate(LocalDateTime.now()).createDate(LocalDateTime.now()).build());
                productRepository.save(p);
            }

            users.forEach(o -> userRepository.save(o));

            orders.add(new Order("A", user2, user3, 100,
                    List.of(OrderItem.builder().product(productRepository.findById("1").get()).quantity(200).build()),
                    OrderStatus.DONE, company1, Currency.DOLLAR, "143", null, "2", true, d1));
            orders.add(new Order("C", user6, user3, 100,
                    List.of(OrderItem.builder().product(productRepository.findById("2").get()).quantity(3).build()),
                    OrderStatus.DONE, company1,Currency.DOLLAR, "143", null, "2", true, d1));

            orders.add(new Order("B", user6, user3, 100,
                    List.of(OrderItem.builder().product(productRepository.findById("1").get()).quantity(3).build(),
                            OrderItem.builder().product(productRepository.findById("2").get()).quantity(1).build()),
                    OrderStatus.DONE, company1,Currency.DOLLAR, "143", null, "2", true,  new AuditData(LocalDateTime.now().minusMonths(1), LocalDateTime.now().minusDays(3))));

            orders.add(new Order("D", user6, user3, 100,
                    List.of(OrderItem.builder().product(productRepository.findById("1").get()).quantity(3).build(),
                            OrderItem.builder().product(productRepository.findById("2").get()).quantity(1).build()),
                    OrderStatus.DONE, company1,Currency.DOLLAR, "143", null, "2", true,  new AuditData(LocalDateTime.now().minusMonths(1), LocalDateTime.now().minusDays(3))));

            orders.forEach(o -> orderRepository.save(o));
        }
    }