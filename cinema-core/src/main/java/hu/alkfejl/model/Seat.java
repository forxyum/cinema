package hu.alkfejl.model;

public class Seat {
    private int resId;
    private int number;

    public Seat(int resId, int number) {
        this.resId = resId;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
