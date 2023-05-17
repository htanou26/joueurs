package dao;

import entities.EquipeNational;

import java.util.List;

public interface EquipeNationalDao {
    void save(EquipeNational equipeNational);
    void update(EquipeNational equipeNational, int id);
    void delete(int id);
    EquipeNational findById(int id);
    List<EquipeNational> findAll();
    List<EquipeNational> findByContinent(String nom);
    EquipeNational findByName(String nom);
}
