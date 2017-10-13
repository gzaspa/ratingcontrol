package edu.chdtu.report.ratingcontrol.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "SUBJECTS")
public class Subject extends BaseEntity{
    @Getter @Setter @Column(name = "NAME") private String name;
    @Getter @Setter @Column(name = "SEMESTER") private short semester;

}
