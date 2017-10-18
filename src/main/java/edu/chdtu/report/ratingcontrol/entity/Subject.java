package edu.chdtu.report.ratingcontrol.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "SUBJECTS")
public class Subject extends BaseEntity{
    @Getter @Setter @Column(name = "NAME") private String name;
    @Getter @Setter @Column(name = "SEMESTER") private short semester;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name="kc_id")
    private KnowledgeControl knowledgeControl;
    @Getter @Setter
    @ManyToMany
    @JoinTable(name = "SUBJECTS_FOR_GROUPS", joinColumns = {
            @JoinColumn(name = "SUBJECT_ID", nullable = false, updatable = false) },
            inverseJoinColumns = @JoinColumn(name = "GROUP_ID"))
    private Set<Group> groups;
    @Getter @Setter
    @OneToMany(mappedBy = "subject")
    List<SubjectGroup> subjectGroup;
}
