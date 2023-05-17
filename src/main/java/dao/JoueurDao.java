package dao;

import entities.Joueur;

import java.util.List;

public interface JoueurDao {
    void save(Joueur joueur);
    void update(Joueur joueur, int id);
    void delete(int id);
    Joueur findById(int id);
    List<Joueur> findAll();
    List<Joueur> findByClub(String nom);
    List<Joueur> findByEquipeNational(String nom);
}
