package com.oft.resumeportal3;

import com.oft.resumeportal3.model.Education;
import com.oft.resumeportal3.model.Job;
import com.oft.resumeportal3.model.UserProfile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    private final UserProfileRepository userProfileRepository;

    public HomeController(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    @GetMapping("/")
    public String home() {

//        Optional<UserProfile> profileOptional = userProfileRepository.findByUserName("omer");
//        profileOptional.orElseThrow(() -> new RuntimeException("Not found"));
//        UserProfile profile1 = profileOptional.get();
//
//        Job job1 = new Job();
//        job1.setCompany("Company 1");
//        job1.setDesignation("designation");
//        job1.setId(1);
//        job1.setStartDate(LocalDate.of(2020, 1, 1));
//        job1.setEndDate(LocalDate.of(2020, 3, 1));
//        job1.getResponsibilities().add("Come up Lagos fish slow cook technique");
//        job1.getResponsibilities().add("Advance smoked whole lamb");
//        job1.getResponsibilities().add("Give people novel gastronomic experiences ");
//
//
//        Job job2 = new Job();
//        job2.setCompany("Company 2");
//        job2.setDesignation("designation 2");
//        job2.setId(2);
//        job2.setStartDate(LocalDate.of(2019, 1, 1));
//        job2.setCurrentJob(true);
//        job2.getResponsibilities().add("Come up Lagos fish slow cook technique");
//        job2.getResponsibilities().add("Advance smoked whole lamb");
//        job2.getResponsibilities().add("Give people novel gastronomic experiences ");
//
//        profile1.getJobs().clear();
//        profile1.getJobs().add(job1);
//        profile1.getJobs().add(job2);
//
//        Education e1 = new Education();
//        e1.setCollege("Awesome College");
//        e1.setQualification("Useless Degree");
//        e1.setStartDate(LocalDate.of(2020, 1, 1));
//        e1.setEndDate(LocalDate.of(2020, 3, 1));
//
//        Education e2 = new Education();
//        e2.setCollege("Awesome College");
//        e2.setQualification("Useless Degree");
//        e2.setStartDate(LocalDate.of(2020, 1, 1));
//        e2.setEndDate(LocalDate.of(2020, 3, 1));
//
//        profile1.getEducations().clear();
//        profile1.getEducations().add(e1);
//        profile1.getEducations().add(e2);
//
//        profile1.getSkills().clear();
//        profile1.getSkills().add("Cast Iron Pan");
//        profile1.getSkills().add("Carbon Steel Pan");
//        profile1.getSkills().add("SousVide cooking");
//        profile1.getSkills().add("Artisan Bread");
//
//
//        userProfileRepository.save(profile1);
        return "index";
    }

    @GetMapping("/edit")
    public String edit(Principal principal, Model model, @RequestParam(required = false) String add) {

        String userName= principal.getName();

        model.addAttribute("userId",userName);

        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userName);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userName));
        UserProfile userProfile = userProfileOptional.get();
        System.out.println("Theme value before change: "+userProfile.getTheme());
        if("job".equals(add)){
            userProfile.getJobs().add(new Job());
        }else if("education".equals(add)){
            userProfile.getEducations().add(new Education());
        }else if("skill".equals(add)){
            userProfile.getSkills().add("");
        }
        model.addAttribute("userProfile",userProfile);

//        if(bindingResult.hasErrors()){
//            System.out.println(bindingResult.getAllErrors());
//            return "profile-edit";
//        }
        return "profile-edit";
    }

    @GetMapping("/delete")
    public String delete(Model model, Principal principal, @RequestParam String type, @RequestParam int index) {
        String userId = principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));
        UserProfile userProfile = userProfileOptional.get();
        if ("job".equals(type)) {
            userProfile.getJobs().remove(index);
        } else if ("education".equals(type)) {
            userProfile.getEducations().remove(index);
        } else if ("skill".equals(type)) {
            userProfile.getSkills().remove(index);
        }
        userProfileRepository.save(userProfile);
        return "redirect:/edit";
    }

    @PostMapping("/edit")
    public String postEdit(Principal principal, @ModelAttribute UserProfile userProfile) {
        String userName = principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userName);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userName));
        UserProfile savedUserProfile = userProfileOptional.get();
       // we pull savedUserProfile to get the right id and username

        userProfile.setId(savedUserProfile.getId());
        userProfile.setUserName(userName);
        userProfileRepository.save(userProfile);
        return "redirect:/view/"+userName;
    }

    @GetMapping("/view/{userId}")
    public String view(Principal principal,@PathVariable("userId") String userId, Model model) {
       if(principal!=null && principal.getName()!=null){
           boolean currentUsersProfile = principal.getName().equals(userId);
           model.addAttribute("currentUsersProfile",currentUsersProfile);
       }
        String userName = principal.getName();
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));

        model.addAttribute("userId", userId);
        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile", userProfile);

        return "profile-templates/" + userProfile.getTheme() + "/index";
    }
}
