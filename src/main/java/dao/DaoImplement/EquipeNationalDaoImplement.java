package dao.DaoImplement;

import dao.EquipeNationalDao;
import entities.EquipeNational;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeNationalDaoImplement implements EquipeNationalDao {
    private Connection conn= DB.getConnection();
    @Override
    public void save(EquipeNational equipeNational) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "INSERT INTO EquipeNational (nom, continent, nbCoupe) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, equipeNational.getNom());
            ps.setString(2, equipeNational.getContinent());
            ps.setInt(3, equipeNational.getNbCoupe());


            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    equipeNational.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyé");;
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'une equipe nationale " + e);
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(EquipeNational equipeNational, int id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "UPDATE EquipeNational SET NOM = ?, CONTINENT = ?, NbCoupe = ? WHERE Id = ?");

            ps.setString(1, equipeNational.getNom());
            ps.setString(2, equipeNational.getContinent());
            ps.setInt(3, equipeNational.getNbCoupe());
            ps.setInt(4, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'une equipe nationale " + e);;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM EquipeNational WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'une equipe nationale");
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public EquipeNational findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM EquipeNational WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                EquipeNational equipeNational = new EquipeNational();
                equipeNational.setId(rs.getInt("Id"));
                equipeNational.setNom(rs.getString("Nom"));
                equipeNational.setContinent(rs.getString("Continent"));
                equipeNational.setNbCoupe(rs.getInt("NbCoupe"));
                return equipeNational;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver une equipe nationale");
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<EquipeNational> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM EquipeNational");
            rs = ps.executeQuery();

            List<EquipeNational> listEquipeNational = new ArrayList<>();

            while (rs.next()) {
                EquipeNational equipeNational = new EquipeNational();
                equipeNational.setId(rs.getInt("Id"));
                equipeNational.setNom(rs.getString("Nom"));
                equipeNational.setContinent(rs.getString("Continent"));
                equipeNational.setNbCoupe(rs.getInt("NbCoupe"));

                listEquipeNational.add(equipeNational);
            }

            return listEquipeNational;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner une equipe nationale");
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<EquipeNational> findByContinent(String nom) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM EquipeNational WHERE Continent = ?");

            ps.setString(1, nom);

            rs = ps.executeQuery();

            List<EquipeNational> listEquipeNational = new ArrayList<>();

            if (rs.next()) {
                EquipeNational equipeNational = new EquipeNational();
                equipeNational.setId(rs.getInt("Id"));
                equipeNational.setNom(rs.getString("Nom"));
                equipeNational.setContinent(rs.getString("Continent"));
                equipeNational.setNbCoupe(rs.getInt("NbCoupe"));

                listEquipeNational.add(equipeNational);
            }

            return listEquipeNational;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver une equipe nationale");
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public EquipeNational findByName(String nom) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM EquipeNational WHERE nom = ?");

            ps.setString(1, nom);

            rs = ps.executeQuery();

            if (rs.next()) {
                EquipeNational equipeNational = new EquipeNational();
                equipeNational.setId(rs.getInt("Id"));
                equipeNational.setNom(rs.getString("Nom"));
                equipeNational.setContinent(rs.getString("Continent"));
                equipeNational.setNbCoupe(rs.getInt("NbCoupe"));
                return equipeNational;
            }
            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver une equipe nationale");
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

}
