package com.project.model;

import jakarta.persistence.*;

@Entity
@Table(name="utilisateur")
public class Utilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String login;

    @Column(nullable=false)
    private String motDePasse;

    @Column(nullable=false)
    private String role; // "ADMIN" ou "USER"

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "beneficiaire_id", unique = true)
    private Beneficiaire beneficiaire;


    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Beneficiaire getBeneficiaire() { return beneficiaire; }
    public void setBeneficiaire(Beneficiaire beneficiaire) { this.beneficiaire = beneficiaire; }
}
