package services.File;

public interface FileInterface {
    void lireFichierTexte(String nomFichier);
    void ecrireFichierTexte(String nomFichier);
    void lireFichierExcel(String nomFichier);
    void ecrireFichierExcel(String nomFichier);
    void lireFichierJson(String nomFichier);
    void ecrireFichierJson(String nomFichier);
}
