package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends MongoRepository<User, String> {

    User findByAddress_Email(String email);

    boolean existsByAddress_Email(String email);

    boolean existsByFullName(String fullName);

    //@Query(fields = "{'id': 1, 'fullName': 1,'password':1,'address':1}")
    Page<User> findByCompanyIdOrderByRoleIdAscAuditData_UpdateDateDesc(String companyId, Pageable pageable);

    @Query(fields = "{'id': 1, 'fullName': 1}")
    List<User> findByFullNameStartingWithAndRoleId_IdAndCompanyId_Id(String prefixName, String roleId, String companyId);


}
