package model.services.save.object;

import java.io.Serializable;
import java.util.List;

public class BuildingSave implements Serializable {

    String name;
    IntPair size;

    List<GameObject> objects;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IntPair getSize() {
        return size;
    }

    public void setSize(IntPair size) {
        this.size = size;
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public void setObjects(List<GameObject> objects) {
        this.objects = objects;
    }

    @Override
    public String toString() {
        return "BuildingSave{" +
                "name='" + name + '\'' +
                ", size=" + size +
                ", objects=" + objects +
                '}';
    }
}
