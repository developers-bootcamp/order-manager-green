//package com.sap.ordermanegergreen.Repositorys;
//
//import com.sap.ordermanegergreen.Models.Product_Category;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.repository.query.FluentQuery;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.function.Function;
//
//public class CategoryRepository implements  ICategoryRepository{
//    @Override
//    public boolean existsByName(String name) {
//        return false;
//    }
//
//    @Override
//    public <S extends Product_Category> S insert(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product_Category> List<S> insert(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product_Category> Optional<S> findOne(Example<S> example) {
//        return Optional.empty();
//    }
//
//    @Override
//    public <S extends Product_Category> List<S> findAll(Example<S> example) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product_Category> List<S> findAll(Example<S> example, Sort sort) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product_Category> Page<S> findAll(Example<S> example, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product_Category> long count(Example<S> example) {
//        return 0;
//    }
//
//    @Override
//    public <S extends Product_Category> boolean exists(Example<S> example) {
//        return false;
//    }
//
//    @Override
//    public <S extends Product_Category, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product_Category> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product_Category> List<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public Optional<Product_Category> findById(String s) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(String s) {
//        return false;
//    }
//
//    @Override
//    public List<Product_Category> findAll() {
//        return null;
//    }
//
//    @Override
//    public List<Product_Category> findAllById(Iterable<String> strings) {
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
//    public void delete(Product_Category entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends String> strings) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Product_Category> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public List<Product_Category> findAll(Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<Product_Category> findAll(Pageable pageable) {
//        return null;
//    }
//}
