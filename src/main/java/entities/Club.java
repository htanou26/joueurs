package entities;

import java.util.Objects;

public class Club {
    private int id;
    private String nom;
    private String pays;
    private int nbJoueur;

    public Club() {
    }

    public Club(String nom, String pays, int nbJoueur) {
        this.nom = nom;
        this.pays = pays;
        this.nbJoueur = nbJoueur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public int getNbJoueur() {
        return nbJoueur;
    }

    public void setNbJoueur(int nbJoueur) {
        this.nbJoueur = nbJoueur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Club)) return false;
        Club club = (Club) o;
        return getNbJoueur() == club.getNbJoueur() && Objects.equals(getNom(), club.getNom()) && Objects.equals(getPays(), club.getPays());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNom(), getPays(), getNbJoueur());
    }

    @Override
    public String toString() {
        return "Club{" +
                "nom='" + nom + '\'' +
                ", pays='" + pays + '\'' +
                ", nbJoueur=" + nbJoueur +
                '}';
    }
}
