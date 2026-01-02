package com.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import jakarta.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int positionId;
    private String positionName;
    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Employee> employees;

    public Position() {
    }

}
