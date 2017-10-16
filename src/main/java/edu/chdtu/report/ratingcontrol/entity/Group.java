package edu.chdtu.report.ratingcontrol.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "GROUPS")
public class Group extends BaseEntity{
    @Getter @Setter @Column(name = "NAME") private String name;
    @Getter @Setter
    @OneToMany(mappedBy = "group")
    @Where(clause="students0_.active1 = 'T'")
    private List<Student> students;
    @Getter @Setter @Column(name = "CREATION_YEAR") private short creationYear;
    @Getter @Setter @Column(name = "KURS") private short startYear;
    @Getter @Setter @Column(name = "TUTION_FORM") private char tuitionForm;
}
