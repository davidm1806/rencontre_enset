package com.adjourtechnologie.reseauenset.web;

import com.adjourtechnologie.reseauenset.model.Account;
import com.adjourtechnologie.reseauenset.model.Group;
import com.adjourtechnologie.reseauenset.repository.GroupeRepository;
import com.adjourtechnologie.reseauenset.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1.0/account_controller")
@CrossOrigin("*")
public class AccountControler {
    private final AccountService accountService;
    private final GroupeRepository groupeRepository;

    public AccountControler(AccountService accountService, GroupeRepository groupeRepository) {
        this.accountService = accountService;
        this.groupeRepository = groupeRepository;
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



}
