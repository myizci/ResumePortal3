package com.oft.resumeportal3.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@ToString
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer theme;
    private String summary;
    private String userName;
    @NotBlank
    @Size(min=2, max=10)
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String designation;
    @ElementCollection(targetClass = String.class)
    private List<String> skills = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "job_id")
    List<Job> jobs = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "education_id")
    List<Education> educations = new ArrayList<>();

}
