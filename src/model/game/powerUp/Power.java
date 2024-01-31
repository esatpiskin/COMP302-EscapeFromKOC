package model.game.powerUp;


import model.game.data.Position;
import model.game.data.Size;

public class Power {

    protected Size size;
    protected Position position;
    protected String imgName;
    protected String powerType;

    public void doAction() {

    }


    public String getImgName() {
        return imgName;
    }

    public String getPowerType() {
        return powerType;
    }

    public void setPowerType(String powerType) {
        this.powerType = powerType;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Power(Size size , Position pos , String img , String type) {
        this.size = size;
        this.position = pos;
        this.powerType = type;
        this.imgName = img;
    }


    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }


    public void powerPicked() {
        System.out.println(getPowerType() + "selected powerup.");}

    public void powerPower(){
        System.out.println("bu cikar mi");
    }

    public int getType() {
        return 0;
    }


}
