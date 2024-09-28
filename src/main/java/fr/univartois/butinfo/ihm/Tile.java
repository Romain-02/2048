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
import java.util.Objects;

/**
 * La classe Tile représente une tuile de la grille du jeu du 2048.
 * Il est important de noter qu'une tuile ne se déplace pas sur la grille : c'est son
 * contenu qui peut être échangé avec celui des tuiles voisines.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class Tile {

    /**
     * La valeur de la tuile.
     */
    private int value;

    /**
     * Vérifie si cette tuile est vide.
     *
     * @return La valeur {@code true} si la tuile est vide, ou
     *         la valeur {@code false} si elle contient une valeur non nulle.
     */
    public boolean isEmpty() {
        return value == 0;
    }

    /**
     * Donne la valeur de cette tuile.
     *
     * @return La valeur de cette tuile.
     */
    public int getValue() {
        return value;
    }

    /**
     * Modifie la valeur de cette tuile.
     *
     * @param value La nouvelle valeur pour cette tuile.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Retire l'éventuelle valeur de cette tuile.
     */
    public void clear() {
        this.value = 0;
    }

    /**
     * Fusionne cette tuile avec une autre, en ajoutant la valeur de la tuile donnée
     * à la valeur de celle-ci.
     * L'autre tuile perd sa valeur courante, et devient donc vide.
     *
     * @param tile La tuile avec laquelle cette tuile doit être fusionnée.
     *
     * @return La nouvelle valeur de cette tuile.
     */
    public int merge(Tile tile) {
        setValue(value + tile.getValue());
        tile.clear();
        return value;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            // Cet objet est exactement le même que l'autre.
            return true;
        }

        if (object == null) {
            // L'autre objet est null, donc il ne peut être égal à celui-ci.
            return false;
        }

        if (getClass() != object.getClass()) {
            // L'autre objet n'est pas une tuile, donc il ne peut être égal à celui-ci.
            return false;
        }

        // Il ne reste plus qu'à comparer la valeur de l'autre tuile.
        Tile other = (Tile) object;
        return value == other.value;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        if (isEmpty()) {
            // Une tuile vide est représentée par une chaîne vide.
            return "";
        }

        // Une tuile non vide est représentée par sa valeur.
        return Integer.toString(value);
    }

}
