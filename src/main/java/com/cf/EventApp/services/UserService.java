package com.cf.EventApp.services;

import com.cf.EventApp.repo.UserRepository;
import com.cf.EventApp.models.Provider;
import com.cf.EventApp.models.Role;
import com.cf.EventApp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    public void processOAuthPostLogin(String username, String provider) {
        Boolean existUser = repo.existsByUsername(username);
        if (!existUser) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setProvider(Provider.valueOf(provider.toUpperCase()));
            newUser.setEnabled(true);

            repo.save(newUser);
            System.out.println("Created new user: " + username);
        }

    }

    public void updateAuthenticationType(String username, String oauth2ClientName) {
        Provider provider = Provider.valueOf(oauth2ClientName.toUpperCase());
        repo.updateProvider(username, provider);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User loaded = repo.getUserByUsername(username);

        if( loaded != null && loaded.isEnabled()) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

            for(Role role : loaded.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
            }
            org.springframework.security.core.userdetails.User user =
                    new org.springframework.security.core.userdetails.User(
                            loaded.getUsername()
                            , loaded.getPassword()
                            , grantedAuthorities);
            return user;
        } else {
            throw new UsernameNotFoundException("could not find username: " + username);
        }

    }

    public List<User> getAllUsers() {
        return (List<User>) repo.findAll();
    }

    public User createUser(User toAdd) {
        return repo.save(toAdd);
    }

    public boolean existsByUsername(String username){
        return repo.existsByUsername(username);
    }

    public User getUserById(long id){
        return repo.findById(id).orElse(null);
    }

    public void updateUser(User newData) {
        repo.save(newData);
    }

    public void deleteUser(long id) {
        //Soft Delete
        User user = repo.findById(id).orElse(null);
        user.setEnabled(false);
        repo.save(user);
        //        template.update("UPDATE user SET enabled = 0 WHERE id = ?", id);
    }

}
