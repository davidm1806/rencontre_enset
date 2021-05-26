package com.adjourtechnologie.reseauenset.web;

import com.adjourtechnologie.reseauenset.model.Account;
import com.adjourtechnologie.reseauenset.model.Group;
import com.adjourtechnologie.reseauenset.model.Message;
import com.adjourtechnologie.reseauenset.repository.GroupeRepository;
import com.adjourtechnologie.reseauenset.repository.MessageRepository;
import com.adjourtechnologie.reseauenset.service.AccountService;
import com.adjourtechnologie.reseauenset.service.TchatService;
import com.adjourtechnologie.reseauenset.utils.Constantes;
import com.google.gson.Gson;
import com.pusher.rest.Pusher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1.0/account_controller")
@CrossOrigin("*")
public class AccountControler {
    private final AccountService accountService;
    private final GroupeRepository groupeRepository;
    private final TchatService tchatService;
    private final MessageRepository messageRepository;

    public AccountControler(AccountService accountService,
                            TchatService tchatService,
                            MessageRepository messageRepository,
                            GroupeRepository groupeRepository) {
        this.accountService = accountService;
        this.groupeRepository = groupeRepository;
        this.tchatService = tchatService;
        this.messageRepository = messageRepository;

    }

    private Pusher pusher;

    @PostConstruct
    public void configure() {
        pusher = new Pusher(Constantes.API_ID, Constantes.API_KEY, Constantes.SECRET_KEY);
        pusher.setCluster("eu");
        pusher.setEncrypted(true);
    }

    @PostMapping("account/save")
    public ResponseEntity<Account> accountSave(@RequestBody Account account, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        return accountService.saveAccount(account);
    }

    @GetMapping("account/find_all")
    public Page<Account> accountPage(Pageable pageable) {
        return accountService.findAll(pageable);
    }


    @GetMapping("account/add_account_to_group/{accountId}/{groupId}")
    public void addAccountToGroup(@PathVariable("accountId") Long accountId, @PathVariable("groupId") Long groupId) {
        accountService.addAccountToGroup(accountId, groupId);
    }


    @GetMapping("account/remouve_account_to_group/{accountId}/{groupId}")
    public void removeAccountToGroup(@PathVariable("accountId") Long accountId, @PathVariable("groupId") Long groupId) {
        accountService.removeAccountFromGroup(accountId, groupId);
    }

    @PostMapping("group/save")
    public ResponseEntity<Group> groupSave(Group group, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        return accountService.saveGroup(group);
    }

    @GetMapping("group/find_all")
    public List<Group> groupFindAll() {
        return accountService.findAllGroup();
    }

    @GetMapping("group/find_by_type/{typeGroup}")
    public List<Group> groupFindByTypeGroup(@PathVariable String typeGroup) {
        return groupeRepository.findByTypeGroup(typeGroup);
    }

    @DeleteMapping("group/delete/{id}")
    public void groupDelete(@PathVariable Long id) {
        groupeRepository.deleteById(id);
    }


    @GetMapping("account/find_by_id/{id}")
    public ResponseEntity<Account> accountPage(@PathVariable Long id) {
        return accountService.findById(id);
    }


    @GetMapping("group/find_by_account_id/{account_id}")
    public List<Group> findGroupByAccount(@PathVariable Long account_id) {
        return accountService.findGroupByAccount(account_id);
    }


    @PostMapping("success")
    public void success() {

    }


    @GetMapping("message/find_by_group/{groupId}")
    public Page<Message> findByGroup(@PathVariable Long groupId, Pageable pageable){

        return tchatService.findMessagesByGroupeId(groupId, pageable);
    }

    @PostMapping("message/save")
    public ResponseEntity<Message> senMessage(@RequestBody Message message, BindingResult result) {
        if(result.hasErrors())
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();


        ResponseEntity<Message> responseEntity = tchatService.sendMessage(message);
        if(responseEntity.hasBody())
            pusher.trigger(Constantes.CHANEL_TCHAT_1, Constantes.SEND_MESSAGE,  responseEntity.getBody().getId());
        return responseEntity;
    }

    @GetMapping("message/get_message_by_id/{id}")
    public Message getMessage(@PathVariable Long id) {
        Optional<Message> optionalMessage = messageRepository.findById(id);
        if(optionalMessage.isPresent())
            return optionalMessage.get();
        return null;
    }


    @DeleteMapping("message/delete")
    public ResponseEntity<Message> deleteMessage(@RequestBody Message message) {

        ResponseEntity<Message> responseEntity = tchatService.deleteMessage(message);
        if(responseEntity.hasBody())
            pusher.trigger(Constantes.CHANEL_TCHAT_1, Constantes.DELETE_MESSGE, Collections.singletonMap("message", responseEntity.getBody()));
        return responseEntity;
    }


    @GetMapping("group/getOne/{groupId}")
    public ResponseEntity<Group> findGroupById(@PathVariable Long groupId) {
        return accountService.findGroupById(groupId);
    }

}
