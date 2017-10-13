package edu.chdtu.report.ratingcontrol.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Getter @Setter
    @ManyToMany
    @JoinTable(name = "SUBJECTS_FOR_GROUPS", joinColumns = {
            @JoinColumn(name = "GROUP_ID", nullable = false, updatable = false) },
            inverseJoinColumns = @JoinColumn(name = "SUBJECT_ID"))
    @Where(clause="semester = 2 AND (kc_id=1 OR kc_id=2)")
    private Set<Subject> subjects;

}
