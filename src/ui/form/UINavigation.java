package ui.form;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * The UI Navigation class handles swapping the displayed card as well as a navigation stack. It should not be called
 * directly but called through UIController to handle the side effects of navigation events. <br>
 * To register a new UI form and enable navigation: <br>
 * 1. Create the UI form <br>
 * 2. Instantiate the UI form inside the MainForm class and add it to the cards there <br>
 * 3. Add a navigation method in this class <br>
 * 4. Add a method in the UIController class that calls the method here and handles any side effects <br>
 */
public class UINavigation {

    JPanel cards;

    Stack<String> navigationStack = new Stack<>();

    public UINavigation(JPanel cards) {
        if (!(cards.getLayout() instanceof CardLayout)) {
            throw new RuntimeException("UI Navigation received an invalid JPanel");
        }
        this.cards = cards;
        navigationStack.push("LOGIN");
    }

    /**
     * Navigate to the help screen
     */
    public void navigateHelpScreen() {
        navigationStack.push("HELP");
        ((CardLayout) cards.getLayout()).show(cards, "HELP");
    }

    /**
     * Navigate to the login screen
     */
    public void navigateLoginScreen() {
        navigationStack.push("LOGIN");
        ((CardLayout) cards.getLayout()).show(cards, "LOGIN");
    }

    /**
     * Navigate to the register screen
     */
    public void navigateRegisterScreen() {
        navigationStack.push("REGISTER");
        ((CardLayout) cards.getLayout()).show(cards, "REGISTER");
    }

    /**
     * Navigate to the build screen
     */
    public void navigateBuildScreen() {
        navigationStack.push("BUILD");
        ((CardLayout) cards.getLayout()).show(cards, "BUILD");
    }

    /**
     * Navigate to the game screen
     */
    public void navigateGameScreen() {
        navigationStack.push("GAME");
        ((CardLayout) cards.getLayout()).show(cards, "GAME");
    }

    /**
     * navigate to the pause screen
     */
    public void navigatePauseScreen() {
        navigationStack.push("PAUSE");
        ((CardLayout) cards.getLayout()).show(cards, "PAUSE");
    }

    /**
     * Navigate to the victory screen
     */
    public void navigateVictoryScreen() {
        navigationStack.push("VICTORY");
        ((CardLayout) cards.getLayout()).show(cards, "VICTORY");
    }

    /**
     * Navigate to the previous screen by popping the navigation stack
     */
    public void navigatePreviousScreen() {
        if (navigationStack.size() < 1) {
            ((CardLayout) cards.getLayout()).show(cards, "LOGIN");
        } else {
            navigationStack.pop(); // current frame
            ((CardLayout) cards.getLayout()).show(cards, navigationStack.peek()); // previous frame
        }
    }

}


