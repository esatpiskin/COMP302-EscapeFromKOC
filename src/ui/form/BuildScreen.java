package ui.form;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import ui.ViewModel;
import ui.animator.Animator;
import ui.controller.BuildKeyListener;
import ui.controller.BuildMouseAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class BuildScreen {

    private JPanel buildScreenPanel;
    private JComboBox<String> furnitureComboBox;
    private JPanel mapScreenPanel;
    private JComboBox<String> buildingSelectBox;
    private JButton gameModeButton;
    private JButton gameModeForceButton;
    private JButton helpButton;
    private JButton logoutButton;
    private JButton saveAndLogoutButton;
    private JButton loadGameButton;

    public JPanel getBuildPanel() {
        return buildScreenPanel;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("BuildScreen");
        frame.setContentPane(new BuildScreen().buildScreenPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public BuildScreen() {
        // populate the Building selection
        // TODO: wrap the String in a record for type safety
        $$$setupUI$$$();
        var controller = ViewModel.getInstance().getController();

        ViewModel.getInstance().getBuildingNames().forEach(name -> buildingSelectBox.addItem(name));

        ViewModel.getInstance().getFurnitureNames().forEach(name -> furnitureComboBox.addItem(name));

        buildingSelectBox.addItemListener(e -> controller.setBuilding((String) e.getItem()));

        furnitureComboBox.addItemListener(e -> controller.setFurniture((String) e.getItem()));

        // handle input capturing
        buildScreenPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                mapScreenPanel.requestFocusInWindow();
            }
        });

        // initialize buttons

        gameModeButton.addActionListener(e -> controller.ShowGameScreen());

        gameModeForceButton.addActionListener(e -> controller.ShowGameScreenForced());

        helpButton.addActionListener(e -> controller.ShowHelpScreen());

        logoutButton.addActionListener(e -> controller.ShowLoginScreen());
        loadGameButton.addActionListener(e -> {
            controller.loadGame();
            controller.RefreshScreen();
        });
        saveAndLogoutButton.addActionListener(e -> {
            controller.saveGame();
            controller.ShowLoginScreen();
        });
    }

    private void createUIComponents() {
        mapScreenPanel = new Animator(new BuildKeyListener(), new BuildMouseAdapter());
        buildingSelectBox = new JComboBox<>();
        furnitureComboBox = new JComboBox<>();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        buildScreenPanel = new JPanel();
        buildScreenPanel.setLayout(new GridLayoutManager(9, 2, new Insets(0, 0, 0, 0), -1, -1));
        buildScreenPanel.add(mapScreenPanel, new GridConstraints(0, 0, 9, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(500, 500), null, 0, false));
        buildScreenPanel.add(buildingSelectBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buildScreenPanel.add(furnitureComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        buildScreenPanel.add(spacer1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        logoutButton = new JButton();
        logoutButton.setBorderPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.setIcon(new ImageIcon(getClass().getResource("/button-icons/logoutwsavingIcon.png")));
        logoutButton.setRolloverIcon(new ImageIcon(getClass().getResource("/button-icons/logoutwsavingSelected.png")));
        logoutButton.setRolloverSelectedIcon(new ImageIcon(getClass().getResource("/button-icons/logoutwsavingSelected.png")));
        logoutButton.setText("");
        buildScreenPanel.add(logoutButton, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        helpButton = new JButton();
        helpButton.setBorderPainted(false);
        helpButton.setContentAreaFilled(false);
        helpButton.setDefaultCapable(true);
        helpButton.setEnabled(true);
        helpButton.setIcon(new ImageIcon(getClass().getResource("/button-icons/helpIcon.png")));
        helpButton.setInheritsPopupMenu(false);
        helpButton.setOpaque(true);
        helpButton.setRolloverEnabled(true);
        helpButton.setRolloverIcon(new ImageIcon(getClass().getResource("/button-icons/helpIconSelected.png")));
        helpButton.setRolloverSelectedIcon(new ImageIcon(getClass().getResource("/button-icons/helpIconSelected.png")));
        helpButton.setSelected(false);
        helpButton.setSelectedIcon(new ImageIcon(getClass().getResource("/button-icons/helpIconSelected.png")));
        helpButton.setText("");
        helpButton.setVisible(true);
        buildScreenPanel.add(helpButton, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gameModeButton = new JButton();
        gameModeButton.setBorderPainted(false);
        gameModeButton.setContentAreaFilled(false);
        gameModeButton.setIcon(new ImageIcon(getClass().getResource("/button-icons/startgameIcon.png")));
        gameModeButton.setLabel("");
        gameModeButton.setRolloverIcon(new ImageIcon(getClass().getResource("/button-icons/startgameSelected.png")));
        gameModeButton.setRolloverSelectedIcon(new ImageIcon(getClass().getResource("/button-icons/startgameSelected.png")));
        gameModeButton.setText("");
        buildScreenPanel.add(gameModeButton, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        gameModeForceButton = new JButton();
        gameModeForceButton.setBorderPainted(false);
        gameModeForceButton.setContentAreaFilled(false);
        gameModeForceButton.setIcon(new ImageIcon(getClass().getResource("/button-icons/startforcedIcon.png")));
        gameModeForceButton.setLabel("");
        gameModeForceButton.setRolloverIcon(new ImageIcon(getClass().getResource("/button-icons/startforcedSelected.png")));
        gameModeForceButton.setRolloverSelectedIcon(new ImageIcon(getClass().getResource("/button-icons/startforcedSelected.png")));
        gameModeForceButton.setText("");
        buildScreenPanel.add(gameModeForceButton, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        saveAndLogoutButton = new JButton();
        saveAndLogoutButton.setBorderPainted(false);
        saveAndLogoutButton.setContentAreaFilled(false);
        saveAndLogoutButton.setIcon(new ImageIcon(getClass().getResource("/button-icons/savelogoutIcon.png")));
        saveAndLogoutButton.setLabel("");
        saveAndLogoutButton.setRolloverIcon(new ImageIcon(getClass().getResource("/button-icons/savelogoutSelected.png")));
        saveAndLogoutButton.setRolloverSelectedIcon(new ImageIcon(getClass().getResource("/button-icons/savelogoutSelected.png")));
        saveAndLogoutButton.setText("");
        buildScreenPanel.add(saveAndLogoutButton, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        loadGameButton = new JButton();
        loadGameButton.setBorderPainted(false);
        loadGameButton.setContentAreaFilled(false);
        loadGameButton.setDefaultCapable(true);
        loadGameButton.setIcon(new ImageIcon(getClass().getResource("/button-icons/loadGameIcon.png")));
        loadGameButton.setLabel("");
        loadGameButton.setRolloverEnabled(true);
        loadGameButton.setRolloverIcon(new ImageIcon(getClass().getResource("/button-icons/loadGameIconSelected.png")));
        loadGameButton.setRolloverSelectedIcon(new ImageIcon(getClass().getResource("/button-icons/loadGameIconSelected.png")));
        loadGameButton.setText("");
        buildScreenPanel.add(loadGameButton, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return buildScreenPanel;
    }

}

