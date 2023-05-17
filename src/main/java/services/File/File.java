package services.File;

import entities.Club;
import entities.EquipeNational;
import entities.Joueur;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import services.ClubService;
import services.EquipeNationaleService;
import services.JoueurService;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.*;
public class File implements FileInterface{
    private ClubService clubService = new ClubService();
    private EquipeNationaleService equipeNationaleService = new EquipeNationaleService();
    private JoueurService joueurService = new JoueurService();
    private List<Joueur> joueurList = new ArrayList<>();
    @Override
    public void lireFichierTexte(String nomFichier) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(nomFichier));
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                try {
                    if (clubService.findByName(parts[6])!=null && equipeNationaleService.findByName(parts[7]) != null && !joueurExist(new Joueur(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), clubService.findByName(parts[6]), equipeNationaleService.findByName(parts[7])))) {
                        addJoueur(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), clubService.findByName(parts[6]), equipeNationaleService.findByName(parts[7]));
                    }
                } catch (Exception e){
                    System.out.println("Erreur de Saisie ");
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void addJoueur(int i, String part, String part1, String part2, int i1, int i2, Club byName, EquipeNational byName1) {
        try {
            Joueur joueur = new Joueur(i, part, part1, part2, i1, i2, byName, byName1);
            if(!joueurExist(joueur)) {
                joueurService.save(joueur);
            } else {
                joueurService.update(joueur, getId(joueur));
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public int getId(Joueur joueur){
        for (Joueur joueur1: joueurService.findAll()){
            if (joueur1.equals(joueur)) return joueur1.getId();
        }
        return -1;
    }
    @Override
    public void ecrireFichierTexte(String nomFichier) {
        try {
            joueurList = joueurService.findAll();
            BufferedWriter writer = new BufferedWriter(new FileWriter(nomFichier));
            for (Joueur joueur : joueurList) {
                String line = joueur.getNumJoueur() + "," + joueur.getPoste() + "," + joueur.getNom() + "," + joueur.getPrenom() + "," + joueur.getNbBut() + "," + joueur.getNbExperience() + "," + joueur.getClub().getNom() + "," + joueur.getEquipeNational().getNom();
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void lireFichierExcel(String nomFichier) {
        try {
            FileInputStream fichier = new FileInputStream(new java.io.File(nomFichier));
            // Création du classeur Excel à partir du fichier
            XSSFWorkbook classeur = new XSSFWorkbook(fichier);
            // Sélection de la première feuille de calcul
            XSSFSheet feuille = classeur.getSheetAt(0);
            // Itération sur chaque ligne de la feuille
            Iterator<Row> itLignes = feuille.iterator();
            while (itLignes.hasNext()) {
                // Ignore la première ligne (les en-têtes)
                Row ligne = itLignes.next();
                if (ligne.getRowNum() == 0) {
                    continue;
                }

                Joueur joueur = new Joueur();
                joueur.setNumJoueur((int) ligne.getCell(0).getNumericCellValue());
                joueur.setPoste(ligne.getCell(1).getStringCellValue());
                joueur.setNom(ligne.getCell(2).getStringCellValue());
                joueur.setPrenom(ligne.getCell(3).getStringCellValue());
                joueur.setNbBut((int) ligne.getCell(4).getNumericCellValue());
                joueur.setNbExperience((int) ligne.getCell(5).getNumericCellValue());

                Club club = new Club();
                club.setNom(ligne.getCell(6).getStringCellValue());
                club.setPays(ligne.getCell(7).getStringCellValue());
                club.setNbJoueur((int) ligne.getCell(8).getNumericCellValue());
                joueur.setClub(club);

                EquipeNational equipeNational = new EquipeNational();
                equipeNational.setNom(ligne.getCell(9).getStringCellValue());
                equipeNational.setContinent(ligne.getCell(10).getStringCellValue());
                equipeNational.setNbCoupe((int) ligne.getCell(11).getNumericCellValue());
                joueur.setEquipeNational(equipeNational);

                if (!joueurExist(joueur))
                    joueurService.save(joueur);
            }

            fichier.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void ecrireFichierExcel(String nomFichier) {
        joueurList = joueurService.findAll();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Joueurs");

        // Créer un TreeMap pour trier les joueurs par ordre alphabétique du nom
        Map<String, Joueur> sortedJoueurs = new TreeMap<>();
        for (Joueur joueur : joueurList) {
            sortedJoueurs.put(joueur.getNom(), joueur);
        }

        Set<String> keySet = sortedJoueurs.keySet();
        int rownum = 0;
        for (String key : keySet) {
            XSSFRow row = sheet.createRow(rownum++);
            Joueur joueur = sortedJoueurs.get(key);

            Cell numJoueurCell = row.createCell(0);
            numJoueurCell.setCellValue(joueur.getNumJoueur());

            Cell posteCell = row.createCell(1);
            posteCell.setCellValue(joueur.getPoste());

            Cell nomCell = row.createCell(2);
            nomCell.setCellValue(joueur.getNom());

            Cell prenomCell = row.createCell(3);
            prenomCell.setCellValue(joueur.getPrenom());

            Cell nbButCell = row.createCell(4);
            nbButCell.setCellValue(joueur.getNbBut());

            Cell nbExperienceCell = row.createCell(5);
            nbExperienceCell.setCellValue(joueur.getNbExperience());

            Cell clubCell = row.createCell(6);
            clubCell.setCellValue(joueur.getClub().getNom());

            Cell equipeNationalCell = row.createCell(7);
            equipeNationalCell.setCellValue(joueur.getEquipeNational().getNom());
        }

        try {
            FileOutputStream out = new FileOutputStream(new java.io.File(nomFichier));
            workbook.write(out);
            out.close();
            System.out.println("Fichier Excel écrit avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void lireFichierJson(String nomFichier) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(nomFichier));
            JSONArray jsonArray = (JSONArray) obj;
            Iterator<?> iterator = jsonArray.iterator();
            while (iterator.hasNext()) {
                JSONObject jsonObject = (JSONObject) iterator.next();
                int numJoueur = Integer.parseInt((String) jsonObject.get("numJoueur"));
                String poste = (String) jsonObject.get("poste");
                String nom = (String) jsonObject.get("nom");
                String prenom = (String) jsonObject.get("prenom");
                int nbBut = Integer.parseInt((String) jsonObject.get("nbBut"));
                int nbExperience = Integer.parseInt((String) jsonObject.get("nbExperience"));
                String nomClub = (String) jsonObject.get("nomClub");
                String paysClub = (String) jsonObject.get("paysClub");
                int nbJoueursClub = Integer.parseInt((String) jsonObject.get("nbJoueursClub"));
                String nomEquipeNational = (String) jsonObject.get("nomEquipeNational");
                String continentEquipeNational = (String) jsonObject.get("continentEquipeNational");
                int nbCoupe = Integer.parseInt((String) jsonObject.get("nbCoupe"));
                Club club = new Club(nomClub, paysClub, nbJoueursClub);
                EquipeNational equipeNational = new EquipeNational(nomEquipeNational, continentEquipeNational, nbCoupe);
                try {
                    if (nbBut > 0 && nbExperience > 0 && !joueurExist(new Joueur(numJoueur, poste, nom, prenom, nbBut, nbExperience, club, equipeNational))) {
                        addJoueur(numJoueur, poste, nom, prenom, nbBut, nbExperience, club, equipeNational);
                    }
                } catch (Exception e) {
                    System.out.println("Erreur de Saisie ");
                }
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void ecrireFichierJson(String nomFichier) {
        try {
            joueurList = joueurService.findAll();
            // Création d'un objet Gson pour la sérialisation en JSON
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            // Ouverture du fichier en écriture
            FileWriter writer = new FileWriter(nomFichier);
            // Sérialisation de la liste d'histoires en JSON et écriture dans le fichier
            gson.toJson(joueurList, writer);
            // Fermeture du fichier
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean joueurExist(Joueur joueur) {
        for (Joueur joueur1 : joueurService.findAll()){
            if (joueur1.equals(joueur)) return true;
        }
        return false;
    }
}
