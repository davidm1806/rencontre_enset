package com.adjourtechnologie.reseauenset.service.implementation;

import com.adjourtechnologie.reseauenset.model.Account;
import com.adjourtechnologie.reseauenset.model.Filiere;
import com.adjourtechnologie.reseauenset.model.Group;
import com.adjourtechnologie.reseauenset.model.Utilisateur;
import com.adjourtechnologie.reseauenset.repository.AccountRepository;
import com.adjourtechnologie.reseauenset.repository.FiliereRepository;
import com.adjourtechnologie.reseauenset.repository.GroupeRepository;
import com.adjourtechnologie.reseauenset.service.AccountService;
import com.adjourtechnologie.reseauenset.service.UtilisateurService;
import com.adjourtechnologie.reseauenset.utils.Constantes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("AccountService")
@Transactional
public class AccountServiceImplementation implements AccountService {
    private final AccountRepository accountRepository;
    private final UtilisateurService utilisateurService;
    private final GroupeRepository groupeRepository;
    private final FiliereRepository filiereRepository;

    public AccountServiceImplementation(AccountRepository accountRepository,
                                        FiliereRepository filiereRepository,
                                        UtilisateurService utilisateurService, GroupeRepository groupeRepository) {
        this.accountRepository = accountRepository;
        this.utilisateurService = utilisateurService;
        this.groupeRepository = groupeRepository;
        this.filiereRepository = filiereRepository;
    }

    @Override
    public ResponseEntity<Account> saveAccount(Account account) {
        Account ac;
        if(account.getId() != null){
            Optional<Account> accountOptional = accountRepository.findById(account.getId());
            if(accountOptional.isEmpty())
                return ResponseEntity.unprocessableEntity().build();
            ac = accountOptional.get();
            ac.setUpdateAt(LocalDateTime.now());
            ac.setFirst_name(account.getFirst_name());
            ac.setLast_name(account.getLast_name());
            ac.setMatricule(account.getMatricule());
        } else {
            if(account.getUtilisateur() == null)
                return ResponseEntity.notFound().build();

            ResponseEntity<Utilisateur> utilisateurResponseEntity = utilisateurService.saveUser(account.getUtilisateur());
            if(!utilisateurResponseEntity.hasBody())
                return new ResponseEntity<>(utilisateurResponseEntity.getStatusCode());

            ac = new Account();
            ac.setUtilisateur(utilisateurResponseEntity.getBody());

            ac.setFirst_name(account.getFirst_name());
            ac.setLast_name(account.getLast_name());
            ac.setMatricule(account.getMatricule());

            ac = accountRepository.save(ac);

            Group primaryGroup = groupeRepository.findByName(Constantes.PRIMARY_GROUP);
            if(primaryGroup != null)
                ac.getGroups().add(primaryGroup);

            if(account.getPromotion() != null) {
                Group promotionGroup = groupeRepository.findByName(account.getPromotion().toUpperCase() + " " + Constantes.SURFIXE_GROUP_PROMOTION);
                if(promotionGroup != null)
                    ac.getGroups().add(promotionGroup);
            }

            if(account.getFiliereId() != null) {
                Filiere filiere = filiereRepository.findById(account.getFiliereId()).orElse(null);
                ac.setFiliere(filiere);
                if(filiere != null && filiere.getDepartement() != null){
                    Group departmentGroup = groupeRepository.findByName(filiere.getDepartement().getName().toUpperCase());
                    if(departmentGroup != null)
                        ac.getGroups().add(departmentGroup);
                }
            }
        }

        ac.setAddress(account.getAddress());
        ac.setAddress(account.getAddress());
        ac.setDateEntree(account.getDateEntree());
        ac.setDateDiplomation(account.getDateDiplomation());
        ac.setEmail(account.getEmail());
        ac.setFiliere(account.getFiliere());
        ac.setPhone(account.getPhone());
        if (account.getImagePart() != null) {
            try{
                ac.setPhotoProfile(new String(account.getImagePart().getBytes()));
                ac.setHasPhotoProfile(true);
            } catch (IOException e){
                e.getStackTrace();
            }
        }
        ac.setPromotion(account.getPromotion());

        return ResponseEntity.ok(accountRepository.save(ac));
    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return accountRepository.findAllAccount(pageable);
    }

    @Override
    public void addAccountToGroup(@NonNull Long accountId, @NonNull Long groupId) {
        Optional<Group> groupOptional = groupeRepository.findById(groupId);
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (groupOptional.isPresent() && accountOptional.isPresent())
            accountOptional.get().getGroups().add(groupOptional.get());

    }

    @Override
    public void removeAccountFromGroup(Long accountId, Long groupId) {
        Optional<Group> groupOptional = groupeRepository.findById(groupId);
        Optional<Account> accountOptional = accountRepository.findById(accountId);

        if (groupOptional.isPresent() && accountOptional.isPresent())
            accountOptional.get().getGroups().remove(groupOptional.get());
    }


    @Override
    public ResponseEntity<Group> saveGroup(Group group) {
        Group gp;
        if(group.getId() != null) {
            Optional<Group> optionalGroup = groupeRepository.findById(group.getId());
            if(optionalGroup.isEmpty())
                return ResponseEntity.unprocessableEntity().build();

            gp = optionalGroup.get();
            gp.setCreated_at(LocalDateTime.now());

        } else {
            gp = new Group();
            gp.setName(group.getName());
            gp.setDescription(group.getDescription());
        }
        return ResponseEntity.ok(groupeRepository.save(gp));
    }

    @Override
    public List<Group> findAllGroup() {
        return groupeRepository.findAll();
    }
}
