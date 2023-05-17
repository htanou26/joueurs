package services;

import dao.ClubDao;
import dao.DaoImplement.ClubDaoImplement;
import entities.Club;

import java.util.List;

public class ClubService {
    private ClubDao clubDao =new ClubDaoImplement();
    public List<Club> findAll() {
        return clubDao.findAll();
    }
    public Club findById(int id) {
        return clubDao.findById(id);
    }
    public Club findByName(String nom) {
        return clubDao.findByName(nom);
    }
    public void save(Club club) {
        clubDao.save(club);
    }
    public void update(Club club, int i) {
        clubDao.update(club, i);
    }
    public void delete(Club club) {
        clubDao.delete(club.getId());
    }
}
