package com.adjourtechnologie.reseauenset.repository;

import com.adjourtechnologie.reseauenset.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("select m from Message  m where m.group.id = :groupId")
    Page<Message> getMessageByGroup(Long groupId, Pageable pageable);

    @Query("update Message s set s.epingle= :isEpingle")
    @Modifying
    void setIsEpingle(boolean isEpingle);

    @Query(nativeQuery = true, value = "select m.* from message m " +
            "join account_message am on am.message_id= m.id " +
            "join account a on a.id= am.account_id " +
            "join group_account ga on ga.id= am.groupe_id " +
            "where ga.id = :groupId")
    List<Message> findMarkMessages(Long groupId);

}
