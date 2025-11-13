package com.project.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "remboursement")
public class Remboursement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Montant remboursé ---
    @Column(nullable = false)
    private Double montant;

    // --- Date du remboursement ---

    // ✅ CORRECTION ICI : ajouter le nom de colonne correct
    @Column(name = "date_remboursement", nullable = false)
    private LocalDate date;

    // --- Description facultative (motif, commentaire, etc.) ---
    @Column(length = 255)
    private String description;

    // --- Relation avec le bénéficiaire ---
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "beneficiaire_id", nullable = false)
    private Beneficiaire beneficiaire;

    // --- Constructeurs ---
    public Remboursement() {}

    public Remboursement(Double montant, LocalDate date, String description, Beneficiaire beneficiaire) {
        this.montant = montant;
        this.date = date;
        this.description = description;
        this.beneficiaire = beneficiaire;
    }

    // --- Getters & Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Beneficiaire getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(Beneficiaire beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    // --- toString (utile pour debug) ---
    @Override
    public String toString() {
        return "Remboursement{" +
                "id=" + id +
                ", montant=" + montant +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", beneficiaire=" + (beneficiaire != null ? beneficiaire.getId() : null) +
                '}';
    }

    // --- equals & hashCode basés sur l’ID ---
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Remboursement that = (Remboursement) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
