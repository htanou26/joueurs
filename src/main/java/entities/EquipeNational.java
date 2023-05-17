package entities;

import java.util.Objects;

public class EquipeNational {
    private int id;
    private String Nom;
    private String Continent;
    private int nbCoupe;

    public EquipeNational() {
    }

    public EquipeNational(String nom, String continent, int nbCoupe) {
        Nom = nom;
        Continent = continent;
        this.nbCoupe = nbCoupe;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getContinent() {
        return Continent;
    }

    public void setContinent(String continent) {
        Continent = continent;
    }

    public int getNbCoupe() {
        return nbCoupe;
    }

    public void setNbCoupe(int nbCoupe) {
        this.nbCoupe = nbCoupe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EquipeNational)) return false;
        EquipeNational that = (EquipeNational) o;
        return getNbCoupe() == that.getNbCoupe() && Objects.equals(getNom(), that.getNom()) && Objects.equals(getContinent(), that.getContinent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNom(), getContinent(), getNbCoupe());
    }

    @Override
    public String toString() {
        return "EquipeNational{" +
                "Nom='" + Nom + '\'' +
                ", Continent='" + Continent + '\'' +
                ", nbCoupe=" + nbCoupe +
                '}';
    }
}
