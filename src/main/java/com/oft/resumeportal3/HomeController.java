package com.oft.resumeportal3;

import com.oft.resumeportal3.model.Education;
import com.oft.resumeportal3.model.Job;
import com.oft.resumeportal3.model.UserProfile;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

        Optional<UserProfile> profileOptional = userProfileRepository.findByUserName("omer");
        profileOptional.orElseThrow(() -> new RuntimeException("Not found"));
        UserProfile profile1 = profileOptional.get();

        Job job1 = new Job();
        job1.setCompany("Company 1");
        job1.setDesignation("designation");
        job1.setId(1);
        job1.setStartDate(LocalDate.of(2020, 1, 1));
        job1.setEndDate(LocalDate.of(2020, 3, 1));
        job1.getResponsibilities().add("Come up Lagos fish slow cook technique");
        job1.getResponsibilities().add("Advance smoked whole lamb");
        job1.getResponsibilities().add("Give people novel gastronomic experiences ");


        Job job2 = new Job();
        job2.setCompany("Company 2");
        job2.setDesignation("designation 2");
        job2.setId(2);
        job2.setStartDate(LocalDate.of(2019, 1, 1));
        job2.setCurrentJob(true);
        job2.getResponsibilities().add("Come up Lagos fish slow cook technique");
        job2.getResponsibilities().add("Advance smoked whole lamb");
        job2.getResponsibilities().add("Give people novel gastronomic experiences ");

        profile1.getJobs().clear();
        profile1.getJobs().add(job1);
        profile1.getJobs().add(job2);

        Education e1 = new Education();
        e1.setCollege("Awesome College");
        e1.setQualification("Useless Degree");
        e1.setStartDate(LocalDate.of(2020, 1, 1));
        e1.setEndDate(LocalDate.of(2020, 3, 1));

        Education e2 = new Education();
        e2.setCollege("Awesome College");
        e2.setQualification("Useless Degree");
        e2.setStartDate(LocalDate.of(2020, 1, 1));
        e2.setEndDate(LocalDate.of(2020, 3, 1));

        profile1.getEducations().clear();
        profile1.getEducations().add(e1);
        profile1.getEducations().add(e2);

        profile1.getSkills().clear();
        profile1.getSkills().add("Cast Iron Pan");
        profile1.getSkills().add("Carbon Steel Pan");
        profile1.getSkills().add("SousVide cooking");
        profile1.getSkills().add("Artisan Bread");


        userProfileRepository.save(profile1);
        return "profile";
    }

    @GetMapping("/edit")
    public String edit(Principal principal, Model model) {
       String userId= principal.getName();
        model.addAttribute("userId",userId);
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));
        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile",userProfile);
        return "profile-edit";
    }

    @PostMapping("/edit")
    public String postEdit(Principal principal, Model model) {
        String userId = principal.getName();
       // model.addAttribute("userId", principal.getName());
        return "redirect:/view/"+userId;
    }

    @GetMapping("/view/{userId}")
    public String view(@PathVariable("userId") String userId, Model model) {
        Optional<UserProfile> userProfileOptional = userProfileRepository.findByUserName(userId);
        userProfileOptional.orElseThrow(() -> new RuntimeException("Not found: " + userId));

        model.addAttribute("userId", userId);
        UserProfile userProfile = userProfileOptional.get();
        model.addAttribute("userProfile", userProfile);
        //System.out.println(userProfile.getJobs());
        return "profile-templates/" + userProfile.getTheme() + "/index";
    }
}
