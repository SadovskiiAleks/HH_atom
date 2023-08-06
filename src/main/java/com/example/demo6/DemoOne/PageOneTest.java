package com.example.demo6.DemoOne;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.concurrent.atomic.AtomicReference;

public class PageOneTest extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Atom program");
        stage.setWidth(269);
        stage.setHeight(400);

        Button buttonSelectFile = new Button("Select File");
        buttonSelectFile.setMinWidth(100);
        buttonSelectFile.setLayoutX((stage.getWidth() / 2) - (buttonSelectFile.getMinWidth() / 2) - 10);
        buttonSelectFile.setLayoutY(20);

        Label label = new Label("Insert level");
        label.setMinWidth(100);
        label.setLayoutX((stage.getWidth() / 2) - (label.getMinWidth() / 2) + 10);
        label.setLayoutY(80);
        label.visibleProperty().set(false);

        TextField textField = new TextField("Only integer");
        textField.setMinWidth(100);
        textField.setLayoutX((stage.getWidth() / 2) - (textField.getMinWidth() / 2) - 30);
        textField.setLayoutY(100);
        textField.visibleProperty().set(false);

        //Поле масштаба
        Label labelScale = new Label("Insert scale");
        labelScale.setMinWidth(100);
        labelScale.setLayoutX((stage.getWidth() / 2) - (labelScale.getMinWidth() / 2) + 10);
        labelScale.setLayoutY(140);
        labelScale.visibleProperty().set(false);

        Slider slider = new Slider(10, 50, 50);
        slider.setMajorTickUnit(10.0);
        slider.setMinorTickCount(0);
        slider.setSnapToTicks(true);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setMinWidth(100);
        slider.setLayoutX((stage.getWidth() / 2) - (slider.getMinWidth() / 2) - 30);
        slider.setLayoutY(160);
        slider.visibleProperty().set(false);

        //Вывод рисунка
        Button buttonChoseField = new Button("Accept");
        buttonChoseField.setMinWidth(60);
        buttonChoseField.setLayoutX((stage.getWidth() / 2) - (buttonChoseField.getMinWidth() / 2) - 10);
        buttonChoseField.setLayoutY(210);
        buttonChoseField.visibleProperty().set(false);


        FileChooser fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CNC files", "*.CNC"),
                new FileChooser.ExtensionFilter("All", "*"));

        Label labelOfFileVay = new Label();
        AtomicReference<File> fileWay = new AtomicReference<>();

        ReaderCNCInObject readerCNCInObject = new ReaderCNCInObject();

        buttonSelectFile.setOnAction(e -> {
            fileWay.set(fileChooser.showOpenDialog(stage));
            //Проверить выбран ли файл
            if (fileWay.get() != null) {

                //Добавить кнопку изменить файл
                buttonSelectFile.setText("Update file way");
                label.visibleProperty().set(true);
                textField.visibleProperty().set(true);
                buttonChoseField.visibleProperty().set(true);
                labelScale.visibleProperty().set(true);
                slider.visibleProperty().set(true);

                //Чтение файла в буфер приложения
                readerCNCInObject.parsCNC(fileWay.get());
            }
        });


        PrintPicture printer = new PrintPicture();

        buttonChoseField.setOnAction(value -> {
            String s = textField.getText();
            //Проверить правильность введения слоя
            //Можно сделать через Apachi NumberUtils.isNumber(myStringValue);
            try {
                Integer leyOf = Integer.parseInt(textField.getText());
                if (leyOf > 0 & leyOf <= readerCNCInObject.layerMap.size()){
                    printer.print(readerCNCInObject, leyOf, stage, slider.getValue());
                    textField.setText("");
                } else {
                    textField.setText("Level not found");
                }

            } catch (NumberFormatException nfe) {
                textField.setText("Insert Number!");
            }
            System.out.println();

        });

        Pane pane = new Pane(buttonSelectFile,
                labelOfFileVay,
                label,
                textField,
                buttonChoseField,
                slider,
                labelScale);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }
}

