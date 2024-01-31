package model.game.powerUp;

import model.game.data.Position;
import model.game.data.Size;

public class extraTime extends Power{


    @Override
    public void powerPicked() {

        super.powerPicked();
    }

    public extraTime(Size size, Position pos, String img, String type) {
        super(size, pos, img, type);
    }
}
