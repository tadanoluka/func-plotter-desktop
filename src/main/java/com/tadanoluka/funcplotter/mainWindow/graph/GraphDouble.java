package com.tadanoluka.funcplotter.mainWindow.graph;

import javafx.geometry.VPos;
import javafx.scene.text.TextAlignment;

import java.text.DecimalFormat;

public class GraphDouble extends GraphText {
    public GraphDouble(double value, DecimalFormat decimalFormat, double x, double y) {
        super(decimalFormat.format(value), x , y);
    }
    public GraphDouble(double value, DecimalFormat decimalFormat, double x, double y, VPos vPos) {
        super(decimalFormat.format(value), x , y, vPos);
    }
    public GraphDouble(double value, DecimalFormat decimalFormat, double x, double y, TextAlignment alignment) {
        super(decimalFormat.format(value), x , y, alignment);
    }
    public GraphDouble(double value, DecimalFormat decimalFormat, double x, double y, TextAlignment alignment, VPos vPos) {
        super(decimalFormat.format(value), x , y, alignment, vPos);
    }
}
