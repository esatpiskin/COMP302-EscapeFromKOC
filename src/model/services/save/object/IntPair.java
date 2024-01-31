package model.services.save.object;

import java.io.Serializable;

public class IntPair implements Serializable {

    int first;
    int second;

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    @Override
    public String toString() {
        return "IntPair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }
}
