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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.random;

/**
 * La classe Grid représente la grille du jeu du 2048.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public final class Grid {

    /**
     * La taille de la grille (en nombre de tuiles par côté).
     * Dans cette version du jeu, la taille reste fixe.
     */
    public static final int SIZE = 4;

    /**
     * Les tuiles qui composent cette grille.
     */
    private Tile[][] allTiles = new Tile[SIZE][SIZE];
    Random rand = new Random();

    /**
     * Crée une nouvelle instance de Grid.
     */
    public Grid() {
        initialize();
        startGame();
    }

    /**
     * Initialise cette grille en créant les tuiles qui la composent.
     */
    private void initialize() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                allTiles[i][j] = new Tile();
            }
        }
    }

    /**
     * Réinitialise cette grille en effaçant le contenu des tuiles.
     */
    public void clear() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                allTiles[i][j].clear();
            }
        }
    }

    /**
     * Donne la tuile située à la position donnée dans cette grille.
     *
     * @param row L'indice de la ligne de la tuile à récupérer.
     * @param column L'indice de la colonne de la tuile à récupérer.
     *
     * @return La tuile à la position donnée.
     */
    public Tile get(int row, int column) {
        return allTiles[row][column];
    }

    /**
     * Donnent les tuiles de cette grille qui ne contiennent aucun nombre.
     *
     * @return La liste des tuiles vides.
     */
    public List<Tile> availableTiles() {
        List<Tile> tiles = new ArrayList<>();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Tile tile = allTiles[i][j];
                if (tile.isEmpty()) {
                    tiles.add(tile);
                }
            }
        }

        return tiles;
    }

    /**
     * Ajoute une valeur à une tuile vide.
     *
     * @return Le boolean indiquant s'il reste une tuile vide.
     */
    public boolean addValueTile() {
        List<Tile> availableTiles = this.availableTiles();
        int size = availableTiles.size();
        if(size == 0) return false;
        Random rand = new Random();
        availableTiles.get(rand.nextInt(size)).setValue(rand.nextDouble() < 0.9 ? 2 : 4);
        return true;
    }

    public void startGame(){
        for(int i = 0; i < 2; i++) addValueTile();
    }

    /**
     * Déplace le contenu des tuiles de cette grille vers le haut.
     *
     * @return Le résultat du déplacement, en termes de nombre de tuiles déplacées et/ou
     *         fusionnées.
     */
    public MoveResult moveUp() {
        return move(MoveDirection.UP);
    }

    /**
     * Déplace le contenu des tuiles de cette grille vers la droite.
     *
     * @return Le résultat du déplacement, en termes de nombre de tuiles déplacées et/ou
     *         fusionnées.
     */
    public MoveResult moveRight() {
        return move(MoveDirection.RIGHT);
    }

    /**
     * Déplace le contenu des tuiles de cette grille vers le bas.
     *
     * @return Le résultat du déplacement, en termes de nombre de tuiles déplacées et/ou
     *         fusionnées.
     */
    public MoveResult moveDown() {
        return move(MoveDirection.DOWN);
    }

    /**
     * Déplace le contenu des tuiles de cette grille vers la gauche.
     *
     * @return Le résultat du déplacement, en termes de nombre de tuiles déplacées et/ou
     *         fusionnées.
     */
    public MoveResult moveLeft() {
        return move(MoveDirection.LEFT);
    }

    /**
     * Déplace le contenu des tuiles de cette grille dans la direction donnée.
     *
     * @param direction La direction dans laquelle les tuiles doivent être déplacées.
     *
     * @return Le résultat du déplacement, en termes de nombre de tuiles déplacées et/ou
     *         fusionnées.
     */
    private MoveResult move(MoveDirection direction) {
        int nbMoved = 0;
        int mergeScore = 0;

        for (int i = 0; i < SIZE; i++) {
            nbMoved += move(direction, i);
            mergeScore += merge(direction, i);
            nbMoved += move(direction, i);
        }

        return new MoveResult(nbMoved, mergeScore);
    }

    /**
     * Déplace le contenu des tuiles de cette grille dans la direction donnée.
     *
     * @param direction La direction dans laquelle les tuiles doivent être déplacées.
     * @param fixed L'indice de la ligne/colonne sur laquelle le déplacement est appliqué.
     *
     * @return Le résultat du déplacement, en termes de nombre de tuiles déplacées et/ou
     *         fusionnées.
     */
    private int move(MoveDirection direction, int fixed) {
        int nbMoved = 0;

        // On parcourt toutes les tuiles de la ligne/colonne considérée.
        int inc = direction.getIncrement();
        for (int i = direction.getStart(); isInBound(i); i += inc) {
            for (int k = i; isInBound(k - inc)
                    && direction.getFrom(allTiles, fixed, k - inc).isEmpty(); k -= inc) {
                // On récupère la tuile courante et la précédente, dans la direction demandée.
                Tile currentTile = direction.getFrom(allTiles, fixed, k);
                Tile previousTile = direction.getFrom(allTiles, fixed, k - inc);

                // On déplace le contenu de la tuile courante dans la précédente.
                if (!currentTile.isEmpty()) {
                    previousTile.setValue(currentTile.getValue());
                    currentTile.clear();
                    nbMoved++;
                }
            }
        }

        return nbMoved;
    }

    public Tile[] getNeighbors(int row, int column) {
        Tile[] neighbors = new Tile[4];
        if(isInBound(row - 1) && isInBound(column) && allTiles[row-1][column] != null) neighbors[0] =allTiles[row - 1][column];
        if(isInBound(row + 1) && isInBound(column) && allTiles[row+1][column] != null) neighbors[1] =allTiles[row + 1][column];
        if(isInBound(row) && isInBound(column - 1) && allTiles[row][column-1] != null) neighbors[2] =allTiles[row][column - 1];
        if(isInBound(row) && isInBound(column + 1) && allTiles[row][column+1] != null) neighbors[3] =allTiles[row][column + 1];
        return neighbors;
    }

    public int evaluateV1() {
        double score = 0;
        int nbCase = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Tile tile = allTiles[i][j];
                if (!tile.isEmpty()) {
                    //on compte le nombre de cases pour un certain score
                    score -= tile.getValue();
                    nbCase++;
                }
            }
        }
        return (int) (score / nbCase);
    }

    public int evaluateV2() {
        double score = 0;
        int nbCase = 0;
        //Le nombre de voisin ayant une valeur différente
        int sameValNeighbors = 0;
        //valeur maximal de la grille


        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Tile tile = allTiles[i][j];
                if (!tile.isEmpty()) {
                    //on compte le nombre de cases pour un certain score
                    score -= tile.getValue();

                    nbCase++;
                    // on regarde si des cases voisines ont la même valeur
                    sameValNeighbors = 0;
                    for (Tile neighbor : getNeighbors(i, j)) {
                        if (neighbor != null) {
                            if (neighbor.getValue() == tile.getValue()) score += sameValNeighbors;
                            else score += 1/4 * tile.getValue();;
                        }
                    }
                    score -= sameValNeighbors * tile.getValue() / 2;
                }
            }
        }

        return (int) (score / (nbCase));
    }

    public Grid copyGrid() {
        Grid grid = new Grid();
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                grid.allTiles[i][j].setValue(allTiles[i][j].getValue());
            }
        }
        return grid;
    }

    public boolean equals(Grid grid) {
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                if(this.allTiles[i][j].getValue()!= grid.allTiles[i][j].getValue()) return false;
            }
        }
        return true;
    }

    public int maxGrid(){
        int max = 0;
        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                max = max(max, allTiles[i][j].getValue());
            }
        }
        return max;
    }

    public int[] directionIA(int depth) {

        if(this.isBlocked()) return new int[]{0, 0};
        if (depth == 0) return new int[]{0, this.evaluateV2()};

        int[] resultDirection;
        //si tu joue
        int[] minResult = {rand.nextInt(4), 0};
        Grid[] grids = {copyGrid(), copyGrid(), copyGrid(), copyGrid()};
        grids[0].moveUp();
        if (!this.equals(grids[0])) grids[0].addValueTile();
        grids[1].moveRight();
        if (!this.equals(grids[1])) grids[1].addValueTile();
        grids[2].moveDown();
        if (!this.equals(grids[2])) grids[2].addValueTile();
        grids[3].moveLeft();
        if (!this.equals(grids[3])) grids[3].addValueTile();

        for (int i = 0; i < 4; i++) {
            resultDirection = new int[]{i, grids[i].directionIA(depth - 1)[1]};
            if (resultDirection[1] < minResult[1] && !this.equals(grids[i])) {
                minResult = resultDirection;
            }
        }
        return minResult;

    }

    /**
     * Fusionne le contenu des tuiles de cette grille dans la direction donnée.
     *
     * @param direction La direction dans laquelle les tuiles doivent être fusionnées.
     * @param fixed L'indice de la ligne/colonne sur laquelle la fusion est appliquée.
     *
     * @return Le score cumulé des fusions réalisées.
     */
    private int merge(MoveDirection direction, int fixed) {
        int score = 0;

        // On parcourt toutes les tuiles de la ligne/colonne considérée.
        int inc = direction.getIncrement();
        for (int i = direction.getStart(); isInBound(i); i += inc) {
            // On récupère la tuile courante et la précédente, dans la direction demandée.
            Tile currentTile = direction.getFrom(allTiles, fixed, i);
            Tile previousTile = direction.getFrom(allTiles, fixed, i - inc);

            // Lorsque les tuiles sont égales, on les fusionne.
            if (previousTile.equals(currentTile)) {
                score += previousTile.merge(currentTile);
            }
        }

        return score;
    }

    /**
     * Vérifie si la grille est bloquée, c'est-à-dire s'il n'est plus possible de déplacer
     * aucune tuile.
     *
     * @return Si la grille est bloquée.
     */
    public boolean isBlocked() {
        // On parcourt la grille à la recherche de mouvements possibles.
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (get(i, j).isEmpty()) {
                    // La tuile est vide, donc une autre peut prendre sa place.
                    return false;
                }

                if (tileCanBeMerged(i, j)) {
                    // La tuile peut être fusionnée, donc elle peut être déplacée.
                    return false;
                }
            }
        }

        // Aucun mouvement possible n'a été trouvé.
        return true;
    }

    /**
     * Vérifie si une tuile peut être fusionnée avec l'une des tuiles voisines.
     *
     * @param row La ligne de la tuile à vérifier.
     * @param column La colonne de la tuile à vérifier.
     *
     * @return Si la tuile peut être fusionnée.
     */
    private boolean tileCanBeMerged(int row, int column) {
        Tile tile = get(row, column);
        return tileCanBeMerged(tile, row - 1, column)
                || tileCanBeMerged(tile, row, column - 1)
                || tileCanBeMerged(tile, row, column + 1)
                || tileCanBeMerged(tile, row + 1, column);
    }

    /**
     * Vérifie si deux tuiles peuvent être fusionnées.
     *
     * @param tile La tuile de référence.
     * @param otherRow La ligne de l'autre tuile à vérifier.
     * @param otherColumn La colonne de l'autre tuile à vérifier.
     *
     * @return Si les tuiles peuvent être fusionnées.
     */
    private boolean tileCanBeMerged(Tile tile, int otherRow, int otherColumn) {
        if (!isInBound(otherRow)) {
            // La ligne de l'autre tuile est en dehors des bornes.
            return false;
        }

        if (!isInBound(otherColumn)) {
            // La colonne de l'autre tuile est en dehors des bornes.
            return false;
        }

        // On récupère l'autre tuile, et on compare sa valeur.
        Tile other = get(otherRow, otherColumn);
        return tile.equals(other);
    }

    /**
     * Vérifie si un indice (pour une ligne ou une colonne) se trouve bien sur la grille.
     *
     * @param index L'indice à vérifier.
     *
     * @return Si l'indice donné est sur la grille.
     */
    private static boolean isInBound(int index) {
        return (0 <= index) && (index < SIZE);
    }

}
