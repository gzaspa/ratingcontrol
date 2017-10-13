package edu.chdtu.report.ratingcontrol.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by os199 on 13.10.2017.
 */
@Entity
@Table(name = "VIKLADACHI")
public class Teacher extends BaseEntity{
    @Getter @Setter @Column(name = "NAME") private String name;
    @Getter @Setter @Column(name = "SURNAME") private String surname;
}
