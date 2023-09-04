//package com.sap.ordermanagergreen.repository;
//
//import com.sap.ordermanagergreen.model.Order;
//import com.sap.ordermanagergreen.model.OrderStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.repository.query.FluentQuery;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.function.Function;
//
//public class OrderRepository implements  IOrderRepository{
//    @Autowired
//    private MongoTemplate mongoTemplate;
//    @Override
//    public List<Order> findByOrderStatusInAndCompanyId(Pageable pageable, List<OrderStatus> orderStatus, String companyId) {
//        return null;
//    }
//
//    @Override
//    public List<Order> findByFilter(Map<String, String> filters) {
//        Query query = new Query();
//
//        // Build the query dynamically based on the filter
//        for (String key : filters.keySet()) {
//            Criteria criteria = Criteria.where(key).is(filters.get(key));
//            query.addCriteria(criteria);
//        }
//
//        return mongoTemplate.find(query, Order.class);
//    }
//
//    @Override
//    public <S extends Order> S insert(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Order> List<S> insert(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public <S extends Order> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Order> List<S> findAll(Example<S> example) {
//        return null;
//    }
//
//    @Override
//    public <S extends Order> List<S> findAll(Example<S> example, Sort sort) {
//        return null;
//    }
//
//    @Override
//    public <S extends Order> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Order> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends Order> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends Order, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }
//
//    @Override
//    public <S extends Order> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Order> List<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public Optional<Order> findById(String s) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(String s) {
//        return false;
//    }
//
//    @Override
//    public List<Order> findAll() {
//        return null;
//    }
//
//    @Override
//    public List<Order> findAllById(Iterable<String> strings) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(String s) {
//
//    }
//
//    @Override
//    public void delete(Order entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends String> strings) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Order> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public List<Order> findAll(Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<Order> findAll(Pageable pageable) {
//        return null;
//    }
//}
