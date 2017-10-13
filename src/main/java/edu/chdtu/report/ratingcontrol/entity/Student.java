package edu.chdtu.report.ratingcontrol.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "STUDENTS")
public class Student extends BaseEntity{
    @Getter @Setter @Column(name = "NAME") String name;
    @Getter @Setter @Column(name = "SURNAME") String surname;
    @ManyToOne @JsonIgnore @JoinColumn(name = "GROUP_ID") @Getter @Setter private Group group;
    @Getter @Setter @Column(name = "ACTIVE1") char active;


}
