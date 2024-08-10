package ru.gb.timesheet.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(name = "name")
    String name;

//    @ManyToMany(fetch = FetchType.EAGER,
//        cascade = {
//                CascadeType.PERSIST,
//                CascadeType.MERGE
//        },
//        mappedBy = "roles") // roles
//    @JsonIgnoreProperties("roles")
//    private Set<User> users = new HashSet<>();

    @ToString.Exclude
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

}