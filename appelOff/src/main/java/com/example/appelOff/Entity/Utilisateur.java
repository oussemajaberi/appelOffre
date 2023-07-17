package com.example.appelOff.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_entity")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "varchar(36)")

    private String id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "username")

    private String username;

    @Column(name = "email")

    private String email;


    @JsonIgnore
    @OneToMany(mappedBy = "createur")
    private List<Offre> offresCreees;
}