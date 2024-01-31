package ui.drawable;

import model.game.data.Direction;
import model.game.data.Position;
import ui.ViewModel;
import ui.drawable.data.ZOrder;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public abstract class Drawable implements Comparable<Drawable> {

    /**
     * z_order is required to sort drawable objects in ascending order
     * so objects with lower z order will be drawn first (in the background)
     */
    protected ZOrder zOrder;
    protected Position position;
    protected Direction motionVector;
    protected Direction facing = Direction.STILL;
    protected double interpolationStep = 0.0;
    protected Image image;

    /**
     * Tile sized should be used to scale objects to the screen
     */
    public int TILE_SIZE = ViewModel.getInstance().getTileSize();

    // Constructors

    public Drawable() {
        this(new Position(0, 0), Direction.STILL, ZOrder.Z_ORDER_FURNITURE);
    }

    public Drawable(Position position) {
        this(position, Direction.STILL, ZOrder.Z_ORDER_FURNITURE);
    }

    public Drawable(Position position, Direction motion_vector) {
        this(position, motion_vector, ZOrder.Z_ORDER_FURNITURE);
    }

    public Drawable(Position position, Direction motion_vector, ZOrder zOrder) {
        this.position = position;
        this.motionVector = motion_vector;
        this.zOrder = zOrder;
    }

    /**
     * Draw the drawable into the graphics context
     * @param g graphics context
     */
    public abstract void draw(Graphics g);

    /**
     * Get the X position of the tile
     * @implNote this is awful, it is probably a better idea to not have this and instead shift the building specifically
     * relative to others and then use a global offset in the animator
     * @return the x position
     */
    protected int getX() {
        return this.position.x();
    }

    /**
     * Get the Y position of the tile
     * @implNote this is awful, it is probably a better idea to not have this and instead shift the building specifically
     * relative to others and then use a global offset in the animator
     * @return the y position
     */
    protected int getY() {
        return this.position.y();
    }

    /**
     * Get the ZOrder of the Drawable
     * @return the ZOrder
     */
    public ZOrder getzOrder() {
        return zOrder;
    }

    /**
     * Set the ZOrder of the Drawable
     * @param zOrder the ZOrder
     */
    public void setzOrder(ZOrder zOrder) {
        this.zOrder = zOrder;
    }

    /**
     * Get the position of the Drawable
     * @return the position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Set the position of the Drawable
     * @param position the position
     */
    public void setPosition(Position position) {
        this.position = position;
    }

    /**
     * Get the facing of the Drawable
     * @return the facing
     */
    public Direction getFacing() {
        return facing;
    }

    /**
     * Set the facing of the Drawable
     * @param facing the facing
     */
    public void setFacing(Direction facing) {
        this.facing = facing;
    }

    /**
     * Get X offset to be used for interpolation by passing the interpolation step
     * @param interpolationStep interpolation step
     * @return x offset position
     */
    protected double getOffsetX(double interpolationStep) {
        switch (this.motionVector) {
            case UP, DOWN, STILL -> {
                return 0.0;
            }
            case LEFT -> {
                return -interpolationStep + 1;
            }
            case RIGHT -> {
                return interpolationStep - 1;
            }
        }
        return 0.0;
    }

    /**
     * Get Y offset to be used for interpolation by passing the interpolation step
     * @param interpolationStep interpolation step
     * @return y offset position
     */
    protected double getOffsetY(double interpolationStep) {
        switch (this.motionVector) {
            case LEFT, RIGHT, STILL -> {
                return 0.0;
            }
            case UP -> {
                return -interpolationStep + 1;
            }
            case DOWN -> {
                return interpolationStep - 1;
            }
        }
        return 0.0;
    }

    /**
     * CompareTo is used to Z sort Drawables
     * @param o the Drawable to be compared.
     * @return -1 if other is below, 1 if the other is above, 0 if the other is in the same layer
     */
    @Override
    public final int compareTo(Drawable o) {
        return Integer.compare(this.zOrder.ordinal(), o.zOrder.ordinal());
    }

    /**
     * Get an invisible (empty) Drawable
     * @return invisible Drawable
     */
    public static Drawable invisible() {
        return new Drawable() {
            @Override
            public void draw(Graphics g) {

            }
        };
    }

    /**
     * Called by the DrawableFactory to update the interpolation step
     * @param motionVector direction
     * @param numberOfSteps number of steps to interpolate in total
     */
    public void interpolate(Direction motionVector, int numberOfSteps) {
        if (motionVector != Direction.STILL) {
            this.motionVector = motionVector;
            this.interpolationStep = 0.0;
        }
        this.interpolationStep += 1.0 / numberOfSteps;
        if (this.interpolationStep >= 0.999) {
            this.interpolationStep = 1.0;
            this.motionVector = motionVector;
        }
    }

    /**
     * Called by DrawableFactory to update the facing of the Drawable if it has changed
     * @param newVector new facing
     */
    public void updateFacing(Direction newVector) {
        if (newVector != Direction.STILL) {
            this.facing = newVector;
        }
    }

    /**
     * Return a rotated version of the image <br>
     * Images are assumed to face down by default
     * @param image image to rotate
     * @param facing facing to rotate to
     * @return rotated image
     */
    public Image rotateImage(Image image, Direction facing) {
        double rotation = 0;
        if (image instanceof BufferedImage) {
            switch (facing) {
                case DOWN, STILL -> {
                    return image;
                }
                case UP -> rotation = Math.toRadians(180);
                case LEFT -> rotation = Math.toRadians(90);
                case RIGHT -> rotation = Math.toRadians(-90);
            }
        } else {
            return image;
        }
        AffineTransform tx = AffineTransform.getRotateInstance(rotation, image.getWidth(null)/2.0, image.getHeight(null)/2.0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

        return op.filter((BufferedImage) image, null);
    }
}
