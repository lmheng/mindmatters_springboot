package sg.edu.iss.mindmatters.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import sg.edu.iss.mindmatters.model.MyUserPrincipal;
import sg.edu.iss.mindmatters.model.User;
import sg.edu.iss.mindmatters.repo.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
	 
    @Autowired
    private UserRepository userRepo;
     
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(email);
        if (user == null || !user.isEnabled() ) {
            throw new UsernameNotFoundException("User not valid");
        }
        
        return new MyUserPrincipal(user);
    }
 
}
