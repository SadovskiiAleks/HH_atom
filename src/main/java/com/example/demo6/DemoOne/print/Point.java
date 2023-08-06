package com.example.demo6.DemoOne.print;

public class Point {
    private int isG;
    private Float x;
    private Float y;

    public Point(int isG, Float x, Float y) {
        this.isG = isG;
        this.x = x;
        this.y = y;
    }

    public Float getX() {
        return x;
    }

    public Float getY() {
        return y;
    }

    @Override
    public String toString(){
        return "x = " +x.toString() + ", y = " +y.toString();
    }
}
