package edu.chdtu.report.ratingcontrol.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public class BaseEntity {
    @Getter @Setter @Id private int id;
}
