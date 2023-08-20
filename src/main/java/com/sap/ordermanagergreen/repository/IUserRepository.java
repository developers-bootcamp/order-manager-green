package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

    User findByAddress_Email(String email);

    boolean existsByAddress_Email(String email);

    boolean existsByFullName(String fullName);

    //@Query(fields = "{'id': 1, 'fullName': 1,'password':1,'address':1}")
    Page<User> findByCompany_IdOrderByRoleIdAscAuditData_UpdateDateDesc(String id, Pageable pageable);

    @Query(fields = "{'id': 1, 'fullName': 1}")
    List<User> findByFullNameStartingWithAndRole_IdAndCompany_Id(String prefixName, String role, String company);

}
