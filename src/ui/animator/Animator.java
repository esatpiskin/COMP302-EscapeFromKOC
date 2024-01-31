package ui.animator;
import ui.ViewModel;
import ui.controller.GameInputAdapter;
import ui.drawable.Drawable;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The Animator is a JPanel that handles drawing Drawables to the screen during game mode and build mode <br>
 * It also captures input and mouse clicks with the provided keyListener and mouseAdapters to propagate to the
 * domain layer
 */
public class Animator extends JPanel implements ActionListener {

    private final ViewModel viewModel = ViewModel.getInstance();

    // global offset TODO: dynamically or externally update these

    private int tileSize = viewModel.getTileSize();
    /**
     * @implNote offsets need to be at least tile size to ensure
     * building is not drawn out of bounds
     */
    private final int offsetX = tileSize;
    private final int offsetY = tileSize;

    /**
     * Create a new Animator with the given keyListener and mouseAdapter
     * @param keyListener the keyListener
     * @param mouseAdapter the mouseAdapter
     */
    public Animator(KeyListener keyListener, GameInputAdapter mouseAdapter) {
        // the UITimer schedules redrawing
        UITimer t = UITimer.getInstance();
        t.addActionListener(this);
        addKeyListener(keyListener);
        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        // When cards are flipped, this component may not have
        // focus, this ensures that it will always grab focus
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mouseAdapter.setOffsetX(offsetX - tileSize);
                mouseAdapter.setOffsetY(offsetY - tileSize);
            }
        });

    }


    /**
     * The paint component fetches the drawables from the ViewModel to draw to the screen
     * The Animator also passes the requested offset values so the visuals can be moved
     * @param g graphics context
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // apply global offset
        g.translate(offsetX, offsetY);
        // fetch drawables
        Iterable<Drawable> drawables = viewModel.getDrawables();
        // draw
        for (Drawable d: drawables) {
            d.draw(g);
        }
    }

    /**
     * We give the actionListener to the UITimer and schedule a repaint every time this is called
     * @param e unused
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }


}
