package com.sap.ordermanegergreen.repository;

import com.sap.ordermanegergreen.model.Role;
import com.sap.ordermanegergreen.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
//@User(name = "customerProjection", types = User.class)
@Repository
    public interface IUserRepository extends MongoRepository<User, String> {
    @Query(fields = "{'id': 1, 'fullName': 1,'password':1,'address':1}")
    Page<User> findByCompanyIdOrderByRoleIdAscAuditDate_UpdateDateDesc(String companyId, Pageable pageable);
    boolean existsByAddress_Email(String email);
    @Query(fields = "{'id': 1, 'fullName': 1}")
    List<User> findByFullNameStartingWithAndRoleId_IdAndCompanyId_Id(String prefixName, String roleId,String companyId);
}