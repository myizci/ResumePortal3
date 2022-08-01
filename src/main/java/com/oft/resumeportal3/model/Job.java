package com.oft.resumeportal3.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table
@Getter
@Setter
@ToString
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private  String company;
    private  String designation;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isCurrentJob;



}
