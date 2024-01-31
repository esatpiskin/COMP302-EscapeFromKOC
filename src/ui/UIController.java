package ui;

import model.game.EscapeGame;
import model.game.data.Direction;
import model.game.data.Phase;
import model.game.data.Position;
import model.game.data.Size;
import model.game.object.environment.FurnitureType;
import model.services.account.LoginValidator;
import model.services.save.SaveManager;
import ui.form.UINavigation;

import javax.swing.*;

/**
 * Class to send messages from UI to domain layer
 * This class should only be constructed by the ViewModel
 */
public class UIController {

    private final UINavigation navigation;

    private final EscapeGame game;

    // selected furniture
    private String furniture;

    /**
     * Construct a new UI Controller instance
     * @implNote DO NOT CALL ViewModel.getInstance() in this method as it will cause a dependency cycle
     * @param navigation UINavigation object passed by ViewModel
     * @param game EscapeGame object passed by ViewModel
     * @param selectedFurniture initial value for selectedFurniture
     */
     UIController(UINavigation navigation, EscapeGame game, String selectedFurniture) {
        this.navigation = navigation;
        this.furniture = selectedFurniture;
        this.game = game;
    }

    /**
     * Side effect: may change displayed form <br>
     * <br>
     * validate the given credentials and navigate to buildscreen if they are valid <br>
     * <br>
     * On error, create an error dialogue explaining the error TODO
     * @param username plaintext username
     * @param password plaintext password
     */
    public void loginValidate(String username, char[] password) {
        // TODO: create error dialogs
        switch (LoginValidator.getInstance().loginValidate(username, password)) {
            case SUCCESS -> {
                game.setPlayerName(username);
                navigation.navigateBuildScreen();
            }
            case INVALID_CREDENTIALS -> showErrorDialog("Invalid credentials. Please try again.");
            case ACCOUNT_EXISTS -> showErrorDialog("Account already exists. Please try again.");
            case UNKNOWN_ERROR -> showErrorDialog("An unknown error occurred. Please try again.");
        }
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Try to create a new account with the given credentials <br>
     * <br>
     * On error, create an error dialogue explaining the error <br>
     * @param username plaintext username
     * @param password plaintext password
     */
    public void registerAccount(String username, char[] password) {
        switch (LoginValidator.getInstance().registerAccount(username, password)) {
            // TODO: create dialogs
            case SUCCESS -> {
            }
            case INVALID_CREDENTIALS -> showErrorDialog("Invalid credentials. Please try again.");
            case ACCOUNT_EXISTS -> showErrorDialog("Account already exists. Please try again.");
            case UNKNOWN_ERROR -> showErrorDialog("An unknown error occurred. Please try again.");
        }
    }

    /*
     * Navigation Functions
     */

    /**
     * Navigate to the help screen
     */
    public void ShowHelpScreen() {
        navigation.navigateHelpScreen();
    }

    /**
     * Navigate to the login screen
     */
    public void ShowLoginScreen() {
        game.newGame();
        game.setGameState(Phase.BUILD);
        navigation.navigateLoginScreen();
    }

    /**
     * Navigates to the build screen
     */
    public void ShowBuildScreen() {
        game.setGameState(Phase.BUILD);
        navigation.navigateBuildScreen();
    }

    /**
     * Navigates to the game screen
     */
    public void ShowGameScreen() {
        var result = game.getBuildLogic().checkFurnitureLimits();
        if (result.isEmpty()) {
            game.setGameState(Phase.PLAY_RUNNING);
            navigation.navigateGameScreen();
        } else {
            showErrorDialog(result.get());
        }
    }

    /**
     * Navigates to the pause screen
     */
    public void ShowPauseScreen() {
        game.setGameState(Phase.PLAY_PAUSED);
        navigation.navigatePauseScreen();
    }

    /**
     * Navigates to the register screen
     */
    public void ShowRegisterScreen() {
        navigation.navigateRegisterScreen();
    }

    /**
     * Navigates to the victory screen
     */
    public void ShowVictoryScreen() {
        game.setGameState(Phase.PLAY_PAUSED);
        navigation.navigateVictoryScreen();
    }

    /**
     * Navigates to the game screen, but ignores normal requirements
     * to transition from build screen
     */
    public void ShowGameScreenForced() {
        // TODO: implement checks and bypass checks for this function
        navigation.navigateGameScreen();
        game.setGameState(Phase.PLAY_RUNNING);
    }

    /**
     * Navigate to the previous screen. Used by screens that have a "back" button.
     */
    public void ShowPreviousScreen() {
        navigation.navigatePreviousScreen();
    }

    /**
     * Call this method when a load event happens to go to the correct screen
     */
    public void RefreshScreen() {
        if (game.getGameState() == Phase.BUILD) {
            ShowBuildScreen();
        } else {
            ShowGameScreenForced();
        }
    }

    /**
     * Start a new game
     */
    public void newGame() {
        game.newGame();
    }

    /**
     * Restart the game without modifying the buildings
     */
    public void restartGame() {
        game.getGameLogic().restartGame();
    }

    /*
     * Game Mode related functions
     */

    /**
     * Pass move player command to the domain layer
     * @param direction requested move direction
     */
    public void movePlayer(Direction direction) {
        game.getGameLogic().movePlayer(direction);
    }

    /**
     * Pass pick up key command to the domain layer
     * @param position to pick the key up from
     */
    public void pickUpKey(Position position) {
        game.getGameLogic().pickUpKey(position);
    }
    public void findSafe(Position position) {
        game.getGameLogic().findSafe(position);
    }

    /**
     * Pass power up pickup command to the domain layer
     * @param position position to pick the power up from
     */
    public void pickUpPowerUp(Position position) {
        // TODO
    }

    /**
     * Pass activate power up command to the domain layer
     */
    public void activatePowerUp() {
        // TODO

    }

    /*
     * Build Mode related functions
     */

    /**
     * Resize the currently shown building to a different size <br>
     * It is the UI layer's responsibility to ensure the size is reasonable as the domain layer
     * will accept any size as valid
     * TODO: it might be a good idea to set a minimum size on the domain layer
     * @param size size to set the building to
     */
    public void changeBuildingSize(Size size) {
        game.getBuildLogic().changeBuildingSize(size);
    }

    /**
     * Add the specified furniture to the specified position of the selected building <br>
     * The furniture type and selected building are stored separately <br>
     * The domain validates the position passed
     * @param position position to add furniture to
     */
    public void addFurniture(Position position) {
        game.getBuildLogic().addFurniture(position, FurnitureType.valueOf(furniture));
    }

    /**
     * Remove furniture from the specified position of the selected building <br>
     * The domain validates the position passed, does nothing if there is no furniture on that position
     * @param position position to remove furniture from
     */
    public void removeFurniture(Position position) {
        game.getBuildLogic().removeFurniture(position);
    }

    /**
     * Move the exit door to the specified position of the selected building <br>
     * Although the UI layer differentiates between this and addFurniture, the domain layer
     * is responsible with validating the position of the Door
     * @param position position to move the Door to
     */
    public void moveExitDoor(Position position) {
        game.getBuildLogic().moveExitDoor(position);
    }

    /**
     * Move the entry exit door to the specified position of the selected building <br>
     * Although the UI layer differentiates between this and addFurniture, the domain layer
     * is responsible with validating the position of the Door
     * @param position position to move the Door to
     */
    public void moveEntryDoor(Position position) {
        game.getBuildLogic().moveEntryDoor(position);
    }


    /**
     * Change the selected building <br>
     * The possible building options are fetched from the domain layer as strings, passing anything else unchecked
     * is likely to cause carnage
     * @param item the building to select
     */
    public void setBuilding(String item) {
        game.setCurrentBuilding(item);
    }


    /**
     * Change the selected furniture <br>
     * The possible furniture options are fetched from the domain layer as strings, passing anything else unchecked
     * is likely to cause carnage
     * @param furnitureName furnitureName to use
     */
    public void setFurniture(String furnitureName) {
        this.furniture = furnitureName;
    }


    /**
     * Save the game
     * TODO: clearer interface and semantics
     */
    public void saveGame() {
        SaveManager.getManager().saveGame(game.getPlayerName(), game.saveGame());
    }

    /**
     * Load the game
     * TODO: clearer interface and semantics
     */
    public void loadGame() {
        game.loadGame(SaveManager.getManager().loadGame(game.getPlayerName()));
    }

}
