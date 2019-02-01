package com.jeremyrickard.draftboot.models;

import java.util.List;

import javax.persistence.Entity;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
@Entity
public class Person {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    private String lastName;
    private String firstName;
    private String emailAddress;

    @OneToMany( mappedBy = "contact")
    List<Agency> agencies;
}