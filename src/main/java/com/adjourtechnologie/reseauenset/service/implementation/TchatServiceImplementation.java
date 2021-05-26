package com.adjourtechnologie.reseauenset.service.implementation;

import com.adjourtechnologie.reseauenset.model.Account;
import com.adjourtechnologie.reseauenset.model.AccountMessage;
import com.adjourtechnologie.reseauenset.model.Group;
import com.adjourtechnologie.reseauenset.model.Message;
import com.adjourtechnologie.reseauenset.repository.AccountMessageRepository;
import com.adjourtechnologie.reseauenset.repository.AccountRepository;
import com.adjourtechnologie.reseauenset.repository.GroupeRepository;
import com.adjourtechnologie.reseauenset.repository.MessageRepository;
import com.adjourtechnologie.reseauenset.service.IAuthenticationFacade;
import com.adjourtechnologie.reseauenset.service.TchatService;
import com.adjourtechnologie.reseauenset.utils.Constantes;
import com.pusher.rest.Pusher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

@Service
@Transactional
public class TchatServiceImplementation implements TchatService {
    private final MessageRepository messageRepository;
    private final GroupeRepository groupeRepository;
    private final AccountRepository accountRepository;
    private final IAuthenticationFacade iAuthenticationFacade;
    private final AccountMessageRepository accountMessageRepository;



    public TchatServiceImplementation(MessageRepository messageRepository,
                                      GroupeRepository groupeRepository,
                                      AccountRepository accountRepository,
                                      AccountMessageRepository accountMessageRepository,
                                      IAuthenticationFacade iAuthenticationFacade) {
        this.messageRepository = messageRepository;
        this.groupeRepository = groupeRepository;
        this.accountRepository = accountRepository;
        this.iAuthenticationFacade = iAuthenticationFacade;
        this.accountMessageRepository = accountMessageRepository;

    }

    @Override
    public ResponseEntity<Message> sendMessage(Message m) {
        if(iAuthenticationFacade.getAuthentication() == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        Message message;
        if(m.getId() != null){
            Optional<Message> messageOptional = messageRepository.findById(m.getId());
            if(messageOptional.isEmpty())
                return  ResponseEntity.unprocessableEntity().build();

            message = messageOptional.get();

            if(!message.getUsername().equals(iAuthenticationFacade.getAuthentication().getName()))
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

            message.setUpdated(true);
            message.setUpdateAt(LocalDateTime.now());

        } else  {
      System.out.println("\n\n"+iAuthenticationFacade.getAuthentication().getName()+"\n\n");
            Account account = accountRepository.findByUserName(iAuthenticationFacade.getAuthentication().getName());
            Optional<Group> groupOptional = groupeRepository.findById(m.getGroupId());

            if(account == null || groupOptional.isEmpty())
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();

            message = new Message();
            message.setSensBy(account);
            message.setGroup(groupOptional.get());
            message.setGroupId(m.getGroupId());
            message.setPseudo(account.getUtilisateur().getPseudo());
            message.setUserId(account.getUtilisateur().getId());
            message.setUsername(account.getUtilisateur().getUsername());
            message.setFirstName(account.getFirst_name());
            message.setLastName(account.getLast_name());
            message.setSexe(account.getSexe());
        }

        message.setContent(m.getContent());

        message = messageRepository.save(message);


        return ResponseEntity.ok(message);
    }

    @Override
    public ResponseEntity<Message> deleteMessage(Message message) {
        if(iAuthenticationFacade.getAuthentication() == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        if(!message.getUsername().equals(iAuthenticationFacade.getAuthentication().getName()))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();


        messageRepository.deleteById(message.getId());

        return ResponseEntity.ok(message);
    }

    @Override
    public ResponseEntity<?> setIsEpeingler(Long accountId, Long messageId) {

        return null;
    }


    @Override
    public Page<Message> findMessagesByGroupeId(Long groupId, Pageable pageable) {
        return messageRepository.getMessageByGroup(groupId, pageable);
    }
}
