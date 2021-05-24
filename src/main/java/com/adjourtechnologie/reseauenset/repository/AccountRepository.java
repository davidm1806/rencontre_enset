package com.adjourtechnologie.reseauenset.repository;

import com.adjourtechnologie.reseauenset.model.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select a from Account a order by a.first_name")
    Page<Account> findAllAccount(Pageable pageable);
    @Query("select a from Account a where a.utilisateur.id= :utilisateurId")
    Account findAccount(Long utilisateurId);
}
