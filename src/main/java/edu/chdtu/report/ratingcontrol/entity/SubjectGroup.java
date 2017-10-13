package edu.chdtu.report.ratingcontrol.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@IdClass(SubjectGroupPrimaryKey.class)
@Table (name = "SUBJECTS_FOR_GROUPS")
public class SubjectGroup implements Serializable {
    @Getter @Setter
    @Id
    @Column(name = "Subject_ID",insertable = false, updatable = false)
    private int subjectId;

    @Getter @Setter
    @Id
    @Column(name = "GROUP_ID",insertable = false, updatable = false)
    private int groupId;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;

    @Getter @Setter
    @ManyToOne
    @JoinColumn (name = "GROUP_ID")
    private Group group;

}
