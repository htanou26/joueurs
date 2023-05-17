package dao.DaoImplement;

import dao.JoueurDao;
import entities.Club;
import entities.EquipeNational;
import entities.Joueur;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoueurDaoImplement implements JoueurDao {
    private Connection conn= DB.getConnection();
    @Override
    public void save(Joueur joueur) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "INSERT INTO Joueur (numJoueur, poste, nom, prenom, nbBut, nbExperience, clubId, equipeNationaleId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, joueur.getNumJoueur());
            ps.setString(2, joueur.getPoste());
            ps.setString(3, joueur.getNom());
            ps.setString(4, joueur.getPrenom());
            ps.setInt(5, joueur.getNbBut());
            ps.setInt(6, joueur.getNbExperience());
            ps.setInt(7, joueur.getClub().getId());
            ps.setInt(8, joueur.getEquipeNational().getId());


            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    joueur.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyé");;
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'un joueur " + e);
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Joueur joueur, int id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "UPDATE Joueur SET NUMJOUEUR = ?, POSTE = ?, NOM = ?, PRENOM = ?, NbBUT = ?, NbEXPERIENCE = ?, ClubId = ?, EquipeNationalID = ? WHERE Id = ?");

            ps.setInt(1, joueur.getNumJoueur());
            ps.setString(2, joueur.getPoste());
            ps.setString(3, joueur.getNom());
            ps.setString(4, joueur.getPrenom());
            ps.setInt(5, joueur.getNbBut());
            ps.setInt(6, joueur.getNbExperience());
            ps.setInt(7, joueur.getClub().getId());
            ps.setInt(8, joueur.getEquipeNational().getId());
            ps.setInt(9, id);

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
            ps = conn.prepareStatement("DELETE FROM Joueur WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'un joueur");
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Joueur findById(int id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT h.*, c.*, e.* FROM Joueur h INNER JOIN Club c ON h.ClubId = c.Id INNER JOIN EquipeNational e ON h.EquipeNationalId = e.Id WHERE h.id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Club club = instantiateClub(rs);
                EquipeNational equipeNational = instantiateEquipeNational(rs);
                return instantiateJoueur(rs, equipeNational, club);
            }
            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver le joueur" + e);
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Joueur> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT h.*, c.*, e.* FROM Joueur h INNER JOIN Club c ON h.ClubId = c.Id INNER JOIN EquipeNational e ON h.EquipeNationalId = e.Id");
            rs = ps.executeQuery();
            List<Joueur> list = new ArrayList<>();
            Map<Integer, Club> mapc = new HashMap<>();
            Map<Integer, EquipeNational> mape = new HashMap<>();

            while (rs.next()) {
                Club club = mapc.get(rs.getInt("Id"));
                EquipeNational equipeNational = mape.get(rs.getInt("id"));

                kyles(rs, list, mapc, mape, club, equipeNational);
            }

            return list;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner les histoires" + e);
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Joueur> findByClub(String nom) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT h.*, c.*, e.* FROM Joueur h INNER JOIN Club c ON h.ClubId = c.Id INNER JOIN EquipeNational e ON h.EquipeNationalId = e.Id WHERE c.Nom = ? ORDER BY h.Nom");

            ps.setString(1, nom);

            rs = ps.executeQuery();
            List<Joueur> list = new ArrayList<>();
            Map<Integer, Club> mapc = new HashMap<>();
            Map<Integer, EquipeNational> mape = new HashMap<>();

            while (rs.next()) {
                Club club = mapc.get(rs.getInt("ClubId"));
                EquipeNational equipeNational = mape.get(rs.getInt("EquipeNationalId"));

                kyles(rs, list, mapc, mape, club, equipeNational);
            }

            return list;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner les joueur d'un club donnée" + e);
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    private void kyles(ResultSet rs, List<Joueur> list, Map<Integer, Club> mapc, Map<Integer, EquipeNational> mape, Club club, EquipeNational equipeNational) throws SQLException {
        if (club == null && equipeNational == null) {
            club = instantiateClub(rs);
            mapc.put(rs.getInt("Id"), club);
            equipeNational = instantiateEquipeNational(rs);
            mape.put(rs.getInt("id"), equipeNational);
        }
        Joueur joueur = instantiateJoueur(rs, equipeNational, club);
        list.add(joueur);
    }

    @Override
    public List<Joueur> findByEquipeNational(String nom) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT h.*, c.*, e.* FROM Joueur h INNER JOIN Club c ON h.ClubId = c.Id INNER JOIN EquipeNational e ON h.EquipeNationalId = e.Id WHERE e.Nom = ? ORDER BY h.Nom");

            ps.setString(1, nom);
            rs = ps.executeQuery();
            List<Joueur> list = new ArrayList<>();
            Map<Integer, Club> mapc = new HashMap<>();
            Map<Integer, EquipeNational> mape = new HashMap<>();

            while (rs.next()) {
                Club club = mapc.get(rs.getInt("ClubId"));
                EquipeNational equipeNational = mape.get(rs.getInt("EquipeNationalId"));

                kyles(rs, list, mapc, mape, club, equipeNational);
            }
            return list;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner les joueur d'une equipe donnée" + e);
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    private Joueur instantiateJoueur(ResultSet rs, EquipeNational equipeNational, Club club) throws SQLException {
        Joueur joueur = new Joueur();

        joueur.setId(rs.getInt("Id"));
        joueur.setNumJoueur(rs.getInt("numJoueur"));
        joueur.setPoste(rs.getString("poste"));
        joueur.setNom(rs.getString("Nom"));
        joueur.setPrenom(rs.getString("Prenom"));
        joueur.setNbBut(rs.getInt("nbBut"));
        joueur.setNbExperience(rs.getInt("nbExperience"));
        joueur.setClub(club);
        joueur.setEquipeNational(equipeNational);

        return joueur;
    }

    private EquipeNational instantiateEquipeNational(ResultSet rs) throws SQLException {
        EquipeNational equipeNational = new EquipeNational();
        equipeNational.setId(rs.getInt("id"));
        equipeNational.setContinent(rs.getString("Nom"));
        equipeNational.setNom(rs.getString("Continent"));
        equipeNational.setNbCoupe(rs.getInt("nbCoupe"));
        return equipeNational;
    }

    private Club instantiateClub(ResultSet rs) throws SQLException {
        Club club = new Club();
        club.setId(rs.getInt("id"));
        club.setNom(rs.getString("nom"));
        club.setPays(rs.getString("pays"));
        club.setNbJoueur(rs.getInt("nbJoueur"));
        return club;
    }
}
