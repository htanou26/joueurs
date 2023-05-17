package services;

import dao.DaoImplement.EquipeNationalDaoImplement;
import dao.EquipeNationalDao;
import entities.EquipeNational;

import java.util.List;

public class EquipeNationaleService {
    private EquipeNationalDao equipeNationalDao =new EquipeNationalDaoImplement();
    public List<EquipeNational> findAll() {
        return equipeNationalDao.findAll();
    }
    public EquipeNational findById(int id) {
        return equipeNationalDao.findById(id);
    }
    public EquipeNational findByName(String nom) {
        return equipeNationalDao.findByName(nom);
    }
    public void save(EquipeNational equipeNational) {
        equipeNationalDao.save(equipeNational);
    }
    public void update(EquipeNational equipeNational, int i) {
        equipeNationalDao.update(equipeNational, i);
    }
    public void delete(EquipeNational equipeNational) {
        equipeNationalDao.delete(equipeNational.getId());
    }
}
