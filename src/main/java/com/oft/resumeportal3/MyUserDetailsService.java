package com.oft.resumeportal3;

import com.oft.resumeportal3.model.MyUserDetails;
import com.oft.resumeportal3.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

//    public MyUserDetailsService(UserRepository uSerRepository) {
//        this.uSerRepository = uSerRepository;
//    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);


        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

        return user.map(p-> new MyUserDetails(p)).get();

    }
}
