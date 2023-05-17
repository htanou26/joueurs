package dao;

import entities.Club;

import java.util.List;

public interface ClubDao {
    void save(Club club);
    void update(Club club, int id);
    void delete(int id);
    Club findById(int id);
    List<Club> findAll();
    List<Club> findByPays(String nom);
    Club findByName(String nom);
}
