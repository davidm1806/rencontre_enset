package com.adjourtechnologie.reseauenset.repository;

import com.adjourtechnologie.reseauenset.model.AccountMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountMessageRepository extends JpaRepository<AccountMessage, Long> {

}
