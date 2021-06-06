package com.adjourtechnologie.reseauenset.service;

import com.adjourtechnologie.reseauenset.model.Account;
import com.adjourtechnologie.reseauenset.model.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;

import java.util.List;

public interface AccountService {
    ResponseEntity<Account> saveAccount(Account account);
    Page<Account> findAll(Pageable pageable);
    ResponseEntity<Account> findById(Long id);

    ResponseEntity<?> addAccountToGroup(@NonNull String matricule, @NonNull  Long groupId);
    ResponseEntity<?> removeAccountFromGroup(@NonNull String matricule, @NonNull  Long groupId);

    ResponseEntity<Group> saveGroup(Group group);
    List<Group> findAllGroup();

    List<Group> findGroupByAccount(Long accountId);

    ResponseEntity<Group> findGroupById(Long groupId);


}
