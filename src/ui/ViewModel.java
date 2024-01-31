package  ui;

import model.game.EscapeGame;
import model.game.data.Phase;
import model.game.data.Position;
import model.game.data.Size;
import model.game.object.environment.FurnitureType;
import ui.drawable.Drawable;
import ui.form.UINavigation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The View Model provides a view into the domain for UI classes
 */
public class ViewModel {


    private static final int TILE_SIZE = 50;

    private final EscapeGame escapeGame; // = new EscapeGame();
    private static ViewModel viewModel = null;
    private final JPanel cards = new JPanel(new CardLayout());

    private final UIController controller;

    /**
     * Get instance of UI Controller.
     * @return instance of the UI Controller class
     */
    public UIController getController() {
        return controller;
    }


    /**
     * Get the tile size used by the animator
     * @return the animator tile size
     */
    public int getTileSize() {
        return TILE_SIZE;
    }

    /**
     * Get the JPanel containing all UI forms in the frame as cards in a CardLayout
     * @return the panels
     */
    public JPanel getPanels() {
        return cards;
    }

    /**
     * Get the list of building names in the order at which they appear
     * @return the list of building names
     */
    public List<String> getBuildingNames() {return escapeGame.getBuildingNames();
    }

    /**
     * Get the furniture names as a list
     * @return the list of furniture names
     */
    public List<String> getFurnitureNames(){return FurnitureType.getFurnitureTypeNames(); }

    /**
     * Get the grid position of the bottom right corner of the building
     * @return building bound
     */
    public Position getBuildingBounds() {
        Size size = escapeGame.getBuildLogic().getCurrentBuildingSize();
        return new Position(size.x() + 1, size.y() + 1);
    }


    private ViewModel() {
        escapeGame = new EscapeGame(() -> getController().ShowVictoryScreen());
        controller = new UIController(new UINavigation(getPanels()), escapeGame, getFurnitureNames().get(0));
        // escapeGame.listener = () -> getController().ShowVictoryScreen();
        //escapeGame.getGameLogic().setListener(() -> getController().ShowVictoryScreen());
    }

    /**
     * Get the ViewModel instance
     * @return the instance
     */
    public static synchronized ViewModel getInstance(){
        if(viewModel == null){
            viewModel = new ViewModel();
        }
        return viewModel;
    }

    /**
     * Get the list of drawables for the current animator to draw <br>
     * The return values of this method changes based on whether the game is in build mode
     * or game mode
     * @return the Drawables
     */
    public Iterable<Drawable> getDrawables() {
        if (escapeGame.getGameState() == Phase.BUILD) {
            return escapeGame.getBuildLogic().getGraphics();
        } else {
            return escapeGame.getGameLogic().getGraphics();
        }
    }

    /**
     * Fetch the game result to display in VictoryScreen
     * @return the string to display
     */
    public String getGameResult() {
        if (escapeGame.getGameLogic().getPlayer().getHealth() <= 0) {
            return "You are dead.";
        } else if (escapeGame.getGameLogic().getRemainingTime() <= 0) {
            return "You ran out of time.";
        } else {
            return "You win!";
        }
    }
}