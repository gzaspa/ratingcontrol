package edu.chdtu.report.ratingcontrol.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "CURRENTYEAR")
@IdClass(CurrentYear.class)
public class CurrentYear implements Serializable{
    @Getter @Setter @Column(name = "CURR_YEAR") @Id private short currentYear;
}
