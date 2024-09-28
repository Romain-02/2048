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
 * L'énumération MoveDirection énumère les différentes directions dans lesquelles il est
 * possible de déplacer les tuiles de la grille du jeu du 2048.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public enum MoveDirection {

    /**
     * La direction UP, pour déplacer les tuiles vers le haut.
     */
    UP(1, 1) {

        /*
         * (non-Javadoc)
         *
         * @see
         * fr.univartois.butinfo.ihm.game2048.model.MoveDirection#getFrom(fr.univartois.
         * butinfo.ihm.game2048.model.Tile[][], int, int)
         */
        @Override
        public Tile getFrom(Tile[][] tiles, int fixed, int moving) {
            // Ici, ce sont les colonnes qui sont fixes.
            return tiles[moving][fixed];
        }

    },

    /**
     * La direction RIGHT, pour déplacer les tuiles vers la droite.
     */
    RIGHT(Grid.SIZE - 2, -1) {

        /*
         * (non-Javadoc)
         *
         * @see
         * fr.univartois.butinfo.ihm.game2048.model.MoveDirection#getFrom(fr.univartois.
         * butinfo.ihm.game2048.model.Tile[][], int, int)
         */
        @Override
        public Tile getFrom(Tile[][] tiles, int fixed, int moving) {
            // Ici, ce sont les lignes qui sont fixes.
            return tiles[fixed][moving];
        }

    },

    /**
     * La direction DOWN, pour déplacer les tuiles vers le bas.
     */
    DOWN(Grid.SIZE - 2, -1) {

        /*
         * (non-Javadoc)
         *
         * @see
         * fr.univartois.butinfo.ihm.game2048.model.MoveDirection#getFrom(fr.univartois.
         * butinfo.ihm.game2048.model.Tile[][], int, int)
         */
        @Override
        public Tile getFrom(Tile[][] tiles, int fixed, int moving) {
            // Ici, ce sont les colonnes qui sont fixes.
            return tiles[moving][fixed];
        }

    },

    /**
     * La direction LEFT, pour déplacer les tuiles vers la gauche.
     */
    LEFT(1, 1) {

        /*
         * (non-Javadoc)
         *
         * @see
         * fr.univartois.butinfo.ihm.game2048.model.MoveDirection#getFrom(fr.univartois.
         * butinfo.ihm.game2048.model.Tile[][], int, int)
         */
        @Override
        public Tile getFrom(Tile[][] tiles, int fixed, int moving) {
            // Ici, ce sont les lignes qui sont fixes.
            return tiles[fixed][moving];
        }

    };

    /**
     * L'indice de la deuxième tuile dans le sens de cette direction.
     */
    private final int start;

    /**
     * La valeur de l'incrémentation à appliquer pour passer à la tuile suivante, dans le
     * sens de cette direction.
     */
    private final int increment;

    /**
     * Crée une nouvelle instance de MoveDirection.
     *
     * @param start L'indice de la deuxième tuile dans le sens de cette direction.
     * @param increment La valeur de l'incrémentation à appliquer pour passer à la tuile
     *        suivante.
     */
    private MoveDirection(int start, int increment) {
        this.start = start;
        this.increment = increment;
    }

    /**
     * Donne l'indice de la deuxième tuile dans le sens de cette direction.
     * Toute itération démarre à partir de la deuxième tuile pour permettre de regarder la
     * tuile précédente.
     *
     * @return L'indice de la deuxième tuile dans le sens de cette direction.
     */
    public int getStart() {
        return start;
    }

    /**
     * Donne la valeur de l'incrémentation à appliquer pour passer à la tuile suivante,
     * dans le sens de cette direction.
     *
     * @return La valeur de l'incrémentation à appliquer pour passer à la tuile suivante.
     */
    public int getIncrement() {
        return increment;
    }

    /**
     * Donne la tuile à la position demandée, en tenant compte d'un mouvement appliqué
     * dans cette direction.
     *
     * @param tiles La matrice des tuiles considérées.
     * @param fixed L'indice de la partie fixe dans la grille par rapport à cette
     *        direction.
     * @param moving L'indice de la partie en mouvement dans la grille par rapport à cette
     *        direction.
     *
     * @return La tuile à la position demandée.
     */
    public abstract Tile getFrom(Tile[][] tiles, int fixed, int moving);

}
