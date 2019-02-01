package com.jeremyrickard.draftboot.models;

import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@Entity
public class Opportunity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String solicitationNumber;
    private String noticeType;
    private Date posted;
    private Date responsesDue;
    private String classificationCode;
    private String naicsCode;

    @ManyToOne
    @JoinColumn(name = "agencyId")
    private Agency agency;
}