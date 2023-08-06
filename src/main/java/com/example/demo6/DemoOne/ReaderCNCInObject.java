package com.example.demo6.DemoOne;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReaderCNCInObject {

    Map<Integer, Layer> layerMap = new HashMap<>();
    Float[] arrayOfM702 = new Float[4];
    Float[] arrayOfM704 = new Float[4];

    public ReaderCNCInObject parsCNC(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while ((char) reader.read() != ';') {
                //Подойдем к IndexTab
            }
            while ((char) reader.read() != '[') {
                //Подойдем к [...
            }
            int[] arrayOfIndexTab = parsIndexTab(reader.readLine());
            reader.close();
            //Проверка закрытия потока
            parsLayer(file, arrayOfIndexTab, layerMap);

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    private void parsLayer(File file, int[] arrayOfIndexTab, Map<Integer, Layer> layerMap) {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            int switchOfLayer = -1;
            String readString;

            //Счтавем первую часть и запомним мощьность лазера
            for (int i = 0; i < arrayOfIndexTab[0] - 1; i++) {
                readString = reader.readLine();

                switch (readString) {
                    case "contour":
                        switchOfLayer = 1;
                        continue;
                    case "infill":
                        switchOfLayer = 2;
                        continue;
                    case "support":
                        switchOfLayer = 3;
                        continue;
                }

                if (readString.contains("M702")) {
                    arrayOfM702[switchOfLayer] = Float.parseFloat(readString.substring(5, readString.length()));
                }
                if (readString.contains("M704")) {
                    arrayOfM704[switchOfLayer] = Float.parseFloat(readString.substring(5, readString.length()));
                }
            }

            //Прочитаем первый уровень
            readString = reader.readLine();

            for (int i = 0; i < arrayOfIndexTab.length - 1; i++) {

                switchOfLayer = -1;

                Layer layer = new Layer();
                while ((readString = reader.readLine()) != null && !readString.contains(";")) {

                    switch (readString) {
                        case "contour":
                            switchOfLayer = 1;
                            continue;
                        case "infill":
                            switchOfLayer = 2;
                            continue;
                        case "support":
                            switchOfLayer = 3;
                            continue;
                    }
                    layer.addInfo(readString, switchOfLayer);
                }

                layerMap.put(i + 1, layer);
            }

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int[] parsIndexTab(String indexTab) {

        String[] items = indexTab.replaceAll("\\[", "").replaceAll("\\]", "").replaceAll("\\s", "").split(",");

        int[] results = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            try {
                results[i] = Integer.parseInt(items[i]);
            } catch (NumberFormatException nfe) {
                //Вывести ошибку
            }
            ;
        }
        return results;
    }

    public Layer getLayer(int lay) {
        return layerMap.get(lay);
    }
}
