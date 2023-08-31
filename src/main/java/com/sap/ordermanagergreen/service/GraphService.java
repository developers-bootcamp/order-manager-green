package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.DeliverCancelOrdersDTO;
import com.sap.ordermanagergreen.dto.TopEmployeeDTO;
import com.sap.ordermanagergreen.model.*;
import com.sap.ordermanagergreen.repository.*;
import com.sap.ordermanagergreen.model.OrderStatus;
import com.sap.ordermanagergreen.model.User;
import lombok.Getter;
import lombok.Setter;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;
import org.springframework.data.mongodb.core.aggregation.ConditionalOperators;
import org.springframework.data.mongodb.core.aggregation.ComparisonOperators;
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

    @Getter
    @Setter
    public class MonthlyProductSalesResult {
        private int year;
        private int month;
        private Product product;
        private int totalQuantity;

    }

    public List<TopEmployeeDTO> getTopEmployee(String companyId) {

        Aggregation aggregation = newAggregation(
                match(Criteria.where("auditData.createDate").gte(LocalDate.now().minusMonths(3))),
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

        Aggregation aggregation = newAggregation(
                match(Criteria.where("auditData.createDate").gte(LocalDate.now().minusMonths(3))),
                match(Criteria.where("orderStatus").is(OrderStatus.DONE)),
                unwind("orderItemsList"),
                project()
                        .andExpression("year(auditData.createDate)").as("year")
                        .andExpression("month(auditData.createDate)").as("month")
                        .and("orderItemsList.product").as("product")
                        .and("orderItemsList.quantity").as("quantity"),
                group(fields().and("year").and("month").and("product"))
                        .sum("quantity").as("totalQuantity"),
                project("totalQuantity")
                        .and("year").as("year")
                        .and("month").as("month")
                        .and("product").as("product"),
                sort(Sort.Direction.ASC, "year", "month")
        );

        AggregationResults<MonthlyProductSalesResult> results = mongoTemplate.aggregate(
                aggregation, Order.class, MonthlyProductSalesResult.class
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
                           .and("orderStatus").as("orderStatus"),
                   group("month")
                           .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatus").notEqualToValue(OrderStatus.PAYMENT_CANCELED)).then(1).otherwise(0)).as("delivered")
                           .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatus").equalToValue(OrderStatus.PAYMENT_CANCELED)).then(1).otherwise(0)).as("cancelledPayment")
                           .sum(ConditionalOperators.when(ComparisonOperators.valueOf("orderStatus").equalToValue(OrderStatus.PROCESS_CANCELED)).then(1).otherwise(0)).as("cancelledProcess"),
                   project()
                           .and("_id").as("month")
                           .and("cancelledProcess").plus("cancelledPayment").as("cancelled")
                           .and("delivered").minus("cancelledProcess").as("delivered")
           );
        AggregationResults<org.bson.Document> results = mongoTemplate.aggregate(aggregation, "Orders", org.bson.Document.class);
        List<org.bson.Document> mappedResults = results.getMappedResults();

        List<DeliverCancelOrdersDTO> resultsDTO = new ArrayList<>();
        for (Document mappedResult : mappedResults) {
            Month month = Month.of(mappedResult.getInteger("month"));
            int cancelled = mappedResult.getInteger("cancelled", 0);
            int delivered = mappedResult.getInteger("delivered", 0);

            DeliverCancelOrdersDTO resultDTO = new DeliverCancelOrdersDTO();
            resultDTO.setMonth(month);
            resultDTO.setCancelled(cancelled);
            resultDTO.setDelivered(delivered);

            resultsDTO.add(resultDTO);
        }

        return resultsDTO;
        ////////////////////////////////// Temporary, for data generation only ////////////////////////////////////
    }

    @Autowired
    ICompanyRepository companyRepository;

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

            companies.forEach(c-> companyRepository.save(c));

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
                    OrderStatus.DONE, company1, "143", null, "2", true, d1));

            orders.add(new Order("C", user6, user3, 100,
                    List.of(OrderItem.builder().product(productRepository.findById("2").get()).quantity(3).build()),
                    OrderStatus.DONE, company1, "143", null, "2", true, d1));

            orders.add(new Order("B", user6, user3, 100,
                    List.of(OrderItem.builder().product(productRepository.findById("1").get()).quantity(3).build(),
                            OrderItem.builder().product(productRepository.findById("2").get()).quantity(1).build()),
                    OrderStatus.DONE, company1, "143", null, "2", true,  new AuditData(LocalDateTime.now().minusMonths(1), LocalDateTime.now().minusDays(3))));

            orders.add(new Order("D", user6, user3, 100,
                    List.of(OrderItem.builder().product(productRepository.findById("1").get()).quantity(3).build(),
                            OrderItem.builder().product(productRepository.findById("2").get()).quantity(1).build()),
                    OrderStatus.DONE, company1, "143", null, "2", true,  new AuditData(LocalDateTime.now().minusMonths(1), LocalDateTime.now().minusDays(3))));

            orders.forEach(o -> orderRepository.save(o));
        }
    }