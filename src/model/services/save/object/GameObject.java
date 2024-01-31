package model.services.save.object;

import java.io.Serializable;
import java.util.Map;

public class GameObject implements Serializable {

    IntPair position;
    String type;
    String subType;
    Map<String, String> properties;

    public IntPair getPosition() {
        return position;
    }

    public void setPosition(IntPair position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "GameObject{" +
                "position=" + position +
                ", type='" + type + '\'' +
                ", subType='" + subType + '\'' +
                ", properties=" + properties +
                '}';
    }
}
