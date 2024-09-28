/**
 * Ce logiciel est distribué à des fins éducatives.
 *
 * Il est fourni "tel quel", sans garantie d’aucune sorte, explicite
 * ou implicite, notamment sans garantie de qualité marchande, d’adéquation
 * à un usage particulier et d’absence de contrefaçon.
 * En aucun cas, les auteurs ou titulaires du droit d’auteur ne seront
 * responsables de tout dommage, réclamation ou autre responsabilité, que ce
 * soit dans le cadre d’un contrat, d’un délit ou autre, en provenance de,
 * consécutif à ou en relation avec le logiciel ou son utilisation, ou avec
 * d’autres éléments du logiciel.
 *
 * (c) 2022-2024 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */
package fr.univartois.butinfo.ihm;
/**
 * La classe MoveResult permet d'encapsuler le résultat d'un mouvement réalisé sur la
 * grille.
 * Plus particulièrement, une instance de cette classe fournit les informations permettant
 * de savoir si des tuiles ont été déplacées, et le score des fusions qui ont pu être
 * réalisées sur les tuiles lors du mouvement.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class MoveResult {

    /**
     * Le nombre de tuiles qui ont été déplacées.
     */
    private final int numberOfMovedTiles;

    /**
     * Le score des tuiles qui ont été fusionnées.
     */
    private final int mergeScore;

    /**
     * Crée une nouvelle instance de MoveResult.
     *
     * @param numberOfMovedTiles Le nombre de tuiles qui ont été déplacées.
     * @param mergeScore Le score des tuiles qui ont été fusionnées.
     */
    public MoveResult(int numberOfMovedTiles, int mergeScore) {
        this.numberOfMovedTiles = numberOfMovedTiles;
        this.mergeScore = mergeScore;
    }

    /**
     * Donne le nombre de tuiles qui ont été déplacées.
     *
     * @return Le nombre de tuiles qui ont été déplacées.
     */
    public int getNumberOfMovedTiles() {
        return numberOfMovedTiles;
    }

    /**
     * Donne le score des tuiles qui ont été fusionnées.
     *
     * @return Le score des tuiles qui ont été fusionnées.
     */
    public int getMergeScore() {
        return mergeScore;
    }

    /**
     * Vérifie si une tuile (au moins) a été déplacée lors du mouvement.
     *
     * @return Si au moins une tuile a été déplacée.
     */
    public boolean hasMoved() {
        return (numberOfMovedTiles > 0) || (mergeScore > 0);
    }

}
