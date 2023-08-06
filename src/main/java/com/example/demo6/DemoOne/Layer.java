package com.example.demo6.DemoOne;

import com.example.demo6.DemoOne.print.Contour;
import com.example.demo6.DemoOne.print.Line;
import com.example.demo6.DemoOne.print.Point;

import java.util.ArrayList;
import java.util.List;

public class Layer {

    char[] chars;
    List<Contour> arrayOfContour = new ArrayList<>();
    List<Line> arrayOfInfill = new ArrayList<>();
    List<String> arrayOfSupport = new ArrayList<>();

    public Layer parsByte(char[] chars) {

        this.chars = chars;
        return this;
    }


    public void addInfo(String inString, int switchOfLayer) {
        switch (switchOfLayer) {
            case (1):
                parseContour(inString);
                break;
            case (2):
                parseInfill(inString);
                break;
            case (3):
                parseSupport(inString);
                break;
        }
    }

    private void parseContour(String inString) {
        String[] xY;
        //Считать первую точку и создать объект из первой точки и массива точек
        if (inString.substring(0, 1).equals("G")) {
            xY = inString.substring(3, inString.length()).split(" ");
            Float x = Float.parseFloat(xY[0].substring(1,xY[0].length())) ;
            Float y  = Float.parseFloat(xY[1].substring(1,xY[1].length()));
            int isG = Integer.parseInt(inString.substring(1,2));

            Point point = new Point(isG,x,y);
            if (isG == 0){

                Contour contour = new Contour().addLines(point,null);
                arrayOfContour.add(contour);

            } else {
                if(arrayOfContour.get(arrayOfContour.size()-1).getListOfLine().get(
                        arrayOfContour.get(arrayOfContour.size()-1).getListOfLine().size()-1
                ).getPointTwo() == null ){
                    // Если последняя точка ноль то я должен изменить эту точку
                    arrayOfContour.get(arrayOfContour.size()-1).getListOfLine().get(
                        arrayOfContour.get(arrayOfContour.size()-1).getListOfLine().size()-1)
                            .setPointTwo(point);
                } else {
                    // Если последняя точка не ноль то я должен создать новую линию
                    Point pointFrom = arrayOfContour.get(arrayOfContour.size()-1).getListOfLine().get(
                            arrayOfContour.get(arrayOfContour.size()-1).getListOfLine().size()-1).getPointTwo();

                    arrayOfContour.get(arrayOfContour.size()-1).addLines(pointFrom,point);

                }

            }

        }

    }

    private void parseInfill(String inString) {
        String[] xY;
        if (inString.substring(0, 1).equals("G")) {

            xY = inString.substring(3, inString.length()).split(" ");
            Float x = Float.parseFloat(xY[0].substring(1,xY[0].length())) ;
            Float y  = Float.parseFloat(xY[1].substring(1,xY[1].length()));
            int isG = Integer.parseInt(inString.substring(1,2));
            Point point = new Point(isG,x,y);
            if (isG == 0){
                arrayOfInfill.add(new Line(point,null));
            } else {
                arrayOfInfill.get(arrayOfInfill.size()-1).setPointTwo(point);
            }
        }

    }

    private void parseSupport(String inString) {
        // Примера поддержек нет в файле
        arrayOfSupport.add(inString);
    }

}
