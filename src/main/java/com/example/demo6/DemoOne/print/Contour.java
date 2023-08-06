package com.example.demo6.DemoOne.print;

import java.util.ArrayList;
import java.util.List;

public class Contour {

    private final List<Line>  listOfLine = new ArrayList<>();

    public Contour addLines(Point p1, Point p2){
        listOfLine.add(new Line(p1,p2));
        return this;
    }

    public List<Line> getListOfLine() {
        return listOfLine;
    }

}
