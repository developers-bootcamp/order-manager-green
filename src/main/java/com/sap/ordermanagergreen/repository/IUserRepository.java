package com.sap.ordermanagergreen.repository;

import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.model.Role;
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

    boolean existsByFullNameAndCompany_Id(String fullName,String companyId);
    boolean existsByFullName(String fullName);

    boolean existsByPassword(String password);


    //@Query(fields = "{'id': 1, 'fullName': 1,'password':1,'address':1}")
    List<User> findAllByCompany_IdOrderByRoleIdAscAuditData_UpdateDateDesc( String id);

    @Query(fields = "{'id': 1, 'fullName': 1}")
    List<User> findByFullNameStartingWithAndRole_IdAndCompany_Id(String prefixName, String role, String company);

}
