package com.adjourtechnologie.reseauenset.web;

import com.adjourtechnologie.reseauenset.model.Account;
import com.adjourtechnologie.reseauenset.model.Group;
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
public class AccountControler {
    private final AccountService accountService;

    public AccountControler(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("account/save")
    public ResponseEntity<Account> accountSave(Account account, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        return accountService.saveAccount(account);
    }

    @GetMapping("account/findAll")
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

    @GetMapping("group/findAll")
    public List<Group> groupFindAll() {
        return accountService.findAllGroup();
    }

}
