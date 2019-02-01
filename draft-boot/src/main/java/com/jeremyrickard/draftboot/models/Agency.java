package com.jeremyrickard.draftboot.models;

import javax.persistence.*;

import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@Entity
public class Agency {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "contactId")
    private Person contact;

    @OneToMany
    private List<Opportunity> opportunities;
}