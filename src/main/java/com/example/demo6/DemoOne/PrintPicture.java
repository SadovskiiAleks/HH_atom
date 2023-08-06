package com.example.demo6.DemoOne;

import com.example.demo6.DemoOne.print.Contour;
import com.example.demo6.DemoOne.print.Line;
import com.example.demo6.DemoOne.print.Point;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PrintPicture {

    public void print(ReaderCNCInObject readerCNCInObject, Integer leyOf, Stage stageFather, double scale) {
        Layer layer = readerCNCInObject.layerMap.get(leyOf);

        Stage stage = new Stage();
        stage.setTitle("Print lay №" + leyOf);

        Canvas canvas = new Canvas(600, 600);
        canvas.setLayoutX(200);
        canvas.setLayoutY(10);

        printFromLayer(canvas, layer, scale);

        //laser power
        Label labelLaserPower = new Label("Laser power");
        labelLaserPower.setMinWidth(100);
        labelLaserPower.setLayoutX(50);
        labelLaserPower.setLayoutY(20);

        TextField textField = new TextField();
        textField.setText(readerCNCInObject.arrayOfM702[1].toString());
        textField.setLayoutX(50);
        textField.setLayoutY(40);

        //scan speed
        Label labelScanSpeed = new Label("Scan speed");
        labelScanSpeed.setMinWidth(100);
        labelScanSpeed.setLayoutX(50);
        labelScanSpeed.setLayoutY(80);

        TextField textField2 = new TextField();
        textField2.setText(readerCNCInObject.arrayOfM704[1].toString());
        textField2.setLayoutX(50);
        textField2.setLayoutY(100);

        Pane pane = new Pane(canvas,
                textField,
                textField2,
                labelLaserPower,
                labelScanSpeed);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    public void printFromLayer(Canvas canvas, Layer layer, double scale) {
        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();

        AtomicInteger counter = new AtomicInteger();
        List<Line> lineOfLayer = new ArrayList<>();
        for (Contour contour:layer.arrayOfContour){
            lineOfLayer.addAll(contour.getListOfLine());
        }
        lineOfLayer.addAll(layer.arrayOfInfill);

        //Поработать с масштабом
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
            Line line = lineOfLayer.get(counter.getAndIncrement());
            drawLine(graphicsContext2D, line, scale,
        canvas.getHeight() / 2,
        canvas.getWidth() / 2);

        });

        Timeline timeleine = new Timeline(keyFrame);

        timeleine.setCycleCount(layer.arrayOfInfill.size()+layer.arrayOfContour.size()*4);
        timeleine.play();

    }



    void drawLine2(GraphicsContext graphicsContext2D, double x1, double y1, double x2, double y2,
                  double scale, double middleHeight, double middleWeight) {
        //Напечатать infill
        graphicsContext2D.setFill(Color.valueOf("#0000ff"));
        graphicsContext2D.beginPath();
        graphicsContext2D.moveTo((x1 * scale) + middleHeight, (y1 * scale) + middleWeight);
        graphicsContext2D.lineTo((x2 * scale) + middleHeight, (y2 * scale) + middleWeight);
        graphicsContext2D.closePath();
        graphicsContext2D.stroke();
    }

    void drawLine(GraphicsContext graphicsContext2D, Line line,
                  double scale, double middleHeight, double middleWeight) {
        //Напечатать infill
        graphicsContext2D.setFill(Color.valueOf("#0000ff"));
        graphicsContext2D.beginPath();
        graphicsContext2D.moveTo((line.getPointOne().getX()* scale) + middleHeight, (line.getPointOne().getY() * scale) + middleWeight);
        graphicsContext2D.lineTo((line.getPointTwo().getX() * scale) + middleHeight, (line.getPointTwo().getY() * scale) + middleWeight);
        graphicsContext2D.closePath();
        graphicsContext2D.stroke();
    }

}