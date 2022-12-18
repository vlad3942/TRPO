package ru.ssau.delivery.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "courier")
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String passportDetails;
    private Boolean isFree;
    private String forNotes;

    @OneToOne(optional = false)
    @JoinColumn(name="user_id", unique = true, nullable = false, updatable = false)
    private User user;
}
