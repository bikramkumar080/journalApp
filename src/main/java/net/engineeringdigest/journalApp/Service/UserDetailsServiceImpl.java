package net.engineeringdigest.journalApp.Service;

import lombok.extern.apachecommons.CommonsLog;
import net.engineeringdigest.journalApp.Repository.UserEntryRepository;
import net.engineeringdigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntryRepository userEntryRepository;;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userEntryRepository.findByUsername(username);
        if (user != null) {
            UserDetails userDetails =  org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
            return userDetails;
        }
        throw new UsernameNotFoundException("User not found " + username);
    }
}
