package edu.chdtu.report.ratingcontrol.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="KNOWLEDGE_CONTROL")
public class KnowledgeControl extends BaseEntity {
    @Getter @Setter
    @Column(name = "NAME" )
    private String name;
}
