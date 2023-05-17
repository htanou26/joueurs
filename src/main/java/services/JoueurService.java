package services;

import dao.DaoImplement.JoueurDaoImplement;
import dao.JoueurDao;
import entities.Club;
import entities.Joueur;

import java.util.List;

public class JoueurService {
    private JoueurDao joueurDao = new JoueurDaoImplement();
    public List<Joueur> findAll() {
        return joueurDao.findAll();
    }
    public List<Joueur> findByClub(String c) {
        return joueurDao.findByClub(c);
    }
    public Joueur findById(int id) {
        return joueurDao.findById(id);
    }
    public void save(Joueur joueur) {
        joueurDao.save(joueur);
    }
    public void update(Joueur joueur, int id) {
        joueurDao.update(joueur, id);
    }
    public void remove(int i) {
        joueurDao.delete(i);
    }
}
