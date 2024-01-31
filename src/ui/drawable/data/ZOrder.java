package ui.drawable.data;

/**
 * The ZOrder enum represents the draw order of Drawable objects
 * @implNote When adding enum variants, make sure the back-most ZOrder is on the top of the list and the front-most is
 * at the bottom
 */
public enum ZOrder {
    Z_ORDER_BACKGROUND,
    Z_ORDER_FURNITURE,
    /*
    More values can be added here
     */
    Z_ORDER_FOREGROUND,
}
