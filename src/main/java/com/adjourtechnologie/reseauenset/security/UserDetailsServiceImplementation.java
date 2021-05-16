package com.adjourtechnologie.reseauenset.security;

import com.adjourtechnologie.reseauenset.model.Utilisateur;
import com.adjourtechnologie.reseauenset.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    UserDetailsServiceImplementation(UtilisateurRepository utilisateurRepository) {

        this.utilisateurRepository = utilisateurRepository;
    }


    @Override
    public Utilisateur loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username.toLowerCase());
        if (utilisateur == null) throw new UsernameNotFoundException("invalide user");
        //if (utilisateur.getLoocked()) throw new BadCredentialsException(utilisateur.getUsername() + " : Ce compte est desactivé");

        utilisateur.setOnline(true);

        return utilisateurRepository.save(utilisateur);
    }
}
