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
 * (c) 2022-2023 Romain Wallon - Université d'Artois.
 * Tous droits réservés.
 */

package fr.univartois.butinfo.ihm;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * La classe HelloController illustre le fonctionnement du contrôleur associé à une vue.
 *
 * @author Romain Wallon
 *
 * @version 0.1.0
 */
public class HelloController implements EventHandler<KeyEvent> {

    private Label[][] tileLabels = new Label[4][4];

    Grid grid = new Grid();
    int score = 0;

    static boolean playIA = false;


    @FXML
    private Label scoreLabel;

    @FXML
    private Button buttonIA;

    @FXML
    private Button down;

    @FXML
    private Button left;

    @FXML
    private Button right;

    @FXML
    private GridPane tileGrid;

    @FXML
    private Button up;

    @FXML
    void down(ActionEvent event) {
        playDirection("DOWN");
    }

    @FXML
    void left(ActionEvent event) {
        playDirection("LEFT");
    }

    @FXML
    void right(ActionEvent event) {
        playDirection("RIGHT");
    }

    @FXML
    void up(ActionEvent event) {
        playDirection("UP");
    }

    @Override
    public void handle(KeyEvent event) {
        // joue sur la grille en fonction de la flèche appuyée
        if(event.getCode() == KeyCode.UP) playDirection("UP");
        if(event.getCode()== KeyCode.RIGHT) playDirection("RIGHT");
        if(event.getCode()== KeyCode.DOWN) playDirection("DOWN");
        if(event.getCode()== KeyCode.LEFT) playDirection("LEFT");
    }

    @FXML
    void restart(ActionEvent event) {
        restartGame();
    }

    public void restartGame() {
        grid.clear();
        changeButtons(false);
        scoreLabel.setText("Score : 0");
        score = 0;
        grid = new Grid();
        updateTiles();
    }

    @FXML
    void playIA(ActionEvent event) {
        playIA = !playIA;
        // Crée un nouveau thread pour exécuter la boucle de l'IA
        Thread aiThread = new Thread(() -> {
            while (!grid.isBlocked() && playIA) {
                try {
                    // Met à jour l'interface utilisateur dans le thread de l'interface utilisateur
                    Platform.runLater(() -> {
                        // on met false pour minimiser le score
                        switch (grid.directionIA(6)[0]) {
                            case 0:
                                playDirection("UP");
                                break;
                            case 1:
                                playDirection("RIGHT");
                                break;
                            case 2:
                                playDirection("DOWN");
                                break;
                            case 3:
                                playDirection("LEFT");
                                break;
                        }
                    });

                    Thread.sleep(200); // Pause de 200 millisecondes entre chaque itération
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        // Démarre le thread de l'IA
        aiThread.start();
    }

    public void continueParty(MoveResult mr){
        score += mr.getMergeScore();
        scoreLabel.setText("Score : " + score);

        grid.addValueTile();
        updateTiles();

        if(grid.isBlocked()){
            changeButtons(true);
        }
    }

    public void changeButtons(boolean b) {
        up.setDisable(b);
        right.setDisable(b);
        down.setDisable(b);
        left.setDisable(b);
    }

    public void playDirection(String direction){
        MoveResult mr;
        switch (direction){
            case "UP":
                mr = grid.moveUp();
                break;
            case "RIGHT":
                mr = grid.moveRight();
                break;
            case "DOWN":
                mr = grid.moveDown();
                break;
            case "LEFT":
                mr = grid.moveLeft();
                break;
            default:
                mr = grid.moveUp();
                break;
        }
        if(mr.hasMoved()) {
            continueParty(mr);
        }
    }


    /**
     * Le label de l'application, où l'on va pouvoir afficher des messages.
     * Ce label sera initialisé automatiquement par JavaFX grâce à l'annotation
     * {@link FXML}.
     */
    @FXML
    private Label welcomeText;

    /**
     * Cette méthode exécute une action lorsque l'utilisateur clique sur le bouton de la
     * fenêtre.
     * Le lien avec le bouton de l'application sera réalisé automatiquement par JavaFX
     * grâce à l'annotation {@link FXML}.
     */
    @FXML
    protected void onHelloButtonClick() {
        // En fait, on va simplement afficher un message dans le label de l'application.
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    void initialize() {
        tileGrid.setBackground(new Background(new BackgroundFill(Color.valueOf("#" + Integer.toHexString(0xbbada0)), null, null)));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Label label = new Label(grid.get(i, j) + "");
                tileGrid.setMargin(label, new Insets(i == 0 ? 20 : 10, j == 3 ? 20 : 10, i == 3 ? 20 : 10, j == 0 ? 20 : 10));

                label.setTextFill(Color.GREY);
                label.setFont(Font.font("Arial", FontWeight.BOLD, 35));

                label.setAlignment(Pos.CENTER);
                tileGrid.add(label, j, i);
                tileLabels[i][j] = label;
                if(grid.get(i, j).getValue() != 0){
                    label.setBackground(computeBackground(grid.get(i, j).getValue()));
                } else{
                    label.setBackground(computeBackground(1));
                }
                label.setMaxWidth(200);
                label.setMaxHeight(200);
            }
        }
    }

    void updateTiles(){
        int val;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                val = grid.get(i, j).getValue();
                tileLabels[i][j].setTextFill(Color.GREY);
                if(val != 0) {
                    tileLabels[i][j].setText(val + "");
                    tileLabels[i][j].setBackground(computeBackground(val));
                    if(grid.get(i, j).getValue() > 4)
                        tileLabels[i][j].setTextFill(Color.WHITE);
                } else{
                    tileLabels[i][j].setText("");
                    tileLabels[i][j].setBackground(null);
                    tileLabels[i][j].setBackground(computeBackground(1));
                }
            }
        }
    }

    private static Background computeBackground(int value) {
        // On calcule d'abord à quelle puissance de 2 la valeur correspond.

        int powerOf2 = 0;
        while ((value >> powerOf2) > 0) {
            powerOf2++;
        }

        // Tableau contenant les différentes couleurs pour chaque puissance de 2
        int[] tabColors = {0xccbeb1, 0xeee4da, 0xede0c8, 0xf2b179, 0xf59563, 0xf67c5f, 0xf65e3b, 0xedcf72, 0xedcc61, 0xedc850, 0xedc53f, 0xedc22e, 0x000000, 0x000000, 0x000000};

        // On crée enfin l'arrière-plan à partir de ce pourcentage.
        return new Background(
                new BackgroundFill(
                        Color.valueOf("#" + Integer.toHexString(tabColors[powerOf2-1])),
                        CornerRadii.EMPTY,
                        Insets.EMPTY));
    }

}
