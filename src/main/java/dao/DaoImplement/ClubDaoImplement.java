package dao.DaoImplement;

import dao.ClubDao;
import entities.Club;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClubDaoImplement implements ClubDao {
    private Connection conn= DB.getConnection();
    @Override
    public void save(Club club) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "INSERT INTO Club (nom, pays, nbJoueur) VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, club.getNom());
            ps.setString(2, club.getPays());
            ps.setInt(3, club.getNbJoueur());


            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    club.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyé");;
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'un club " + e);;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Club club, int id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "UPDATE Club SET NOM = ?, PAYS = ?, NbJOUEUR = ? WHERE Id = ?");

            ps.setString(1, club.getNom());
            ps.setString(2, club.getPays());
            ps.setInt(3, club.getNbJoueur());
            ps.setInt(4, id);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'un club " + e);;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("DELETE FROM Club WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'un Club");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Club findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM Club WHERE id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Club club = new Club();
                club.setId(rs.getInt("Id"));
                club.setNom(rs.getString("Nom"));
                club.setNbJoueur(rs.getInt("NbJoueur"));
                club.setPays(rs.getString("Pays"));
                return club;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un club");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Club> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM Club");
            rs = ps.executeQuery();

            List<Club> listClub = new ArrayList<>();

            while (rs.next()) {
                Club club = new Club();

                club.setId(rs.getInt("Id"));
                club.setNom(rs.getString("Nom"));
                club.setPays(rs.getString("Pays"));
                club.setNbJoueur(rs.getInt("NbJoueur"));

                listClub.add(club);
            }

            return listClub;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner un club");
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public List<Club> findByPays(String nom) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM Club WHERE Pays = ?");

            ps.setString(1, nom);

            rs = ps.executeQuery();

            List<Club> listClub = new ArrayList<>();

            if (rs.next()) {
                Club club = new Club();

                club.setId(rs.getInt("Id"));
                club.setNom(rs.getString("Nom"));
                club.setPays(rs.getString("Pays"));
                club.setNbJoueur(rs.getInt("NbJoueur"));

                listClub.add(club);
            }

            return listClub;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un Club");
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }

    @Override
    public Club findByName(String nom) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM Club WHERE nom = ?");

            ps.setString(1, nom);

            rs = ps.executeQuery();

            if (rs.next()) {
                Club club = new Club();
                club.setId(rs.getInt("Id"));
                club.setNom(rs.getString("Nom"));
                club.setNbJoueur(rs.getInt("NbJoueur"));
                club.setPays(rs.getString("Pays"));
                return club;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver un club");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }
}
