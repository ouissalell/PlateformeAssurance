// ========================================
// Cotisation.java
// ========================================
package com.project.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cotisation")
public class Cotisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double montant;

    @Column(name = "date_cotisation", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "beneficiaire_id")
    private Beneficiaire beneficiaire;




    // ✅ Constructeurs
    public Cotisation() {}

    public Cotisation(Double montant, LocalDate date) {
        this.montant = montant;
        this.date = date;
    }

    // ✅ Getters et Setters
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

    public Beneficiaire getBeneficiaire() {
        return beneficiaire;
    }

    // ✅ IMPORTANT : Cette méthode manquait !
    public void setBeneficiaire(Beneficiaire beneficiaire) {
        this.beneficiaire = beneficiaire;
    }

    @Override
    public String toString() {
        return "Cotisation{" +
                "id=" + id +
                ", montant=" + montant +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cotisation that = (Cotisation) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}