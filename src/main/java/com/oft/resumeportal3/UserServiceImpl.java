package com.oft.resumeportal3;

import com.oft.resumeportal3.model.User;
import com.oft.resumeportal3.model.UserProfile;

import java.util.Optional;

public class UserServiceImpl {
    private final UserProfileRepository userProfileRepository;

    public UserServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    public UserProfile findProfileByUserName(String username){
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(username);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + username));
        return userProfileOptional.get();
    }
}
