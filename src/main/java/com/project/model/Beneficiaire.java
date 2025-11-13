package com.project.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "beneficiaire")
public class Beneficiaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(name = "date_naissance") // si ta colonne s’appelle date_naissance
    private String dateNaissance;

    // 🔥 Correction importante ici :
    @Column(name = "numero_ss", nullable = false)
    private String numeroSS;

    @OneToMany(mappedBy = "beneficiaire", cascade = CascadeType.ALL)
    private List<Cotisation> cotisations;

    @OneToMany(mappedBy = "beneficiaire", cascade = CascadeType.ALL)
    private List<Remboursement> remboursements;

    // ✅ Constructeur par défaut
    public Beneficiaire() {}

    // ✅ Getters et Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNumeroSS() {
        return numeroSS;
    }
    public void setNumeroSS(String numeroSS) {
        this.numeroSS = numeroSS;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }
    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
}
