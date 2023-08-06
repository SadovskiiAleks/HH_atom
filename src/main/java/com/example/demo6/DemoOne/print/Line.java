package com.example.demo6.DemoOne.print;

public class Line {
    private Point pointOne;
    private Point pointTwo;

    public Line(Point pointOne, Point pointTwo) {
        this.pointOne = pointOne;
        this.pointTwo = pointTwo;
    }

    public void setPointTwo(Point point) {
        this.pointTwo = point;
    }

    public Point getPointOne() {
        return pointOne;
    }

    public Point getPointTwo() {
        return pointTwo;
    }
}
