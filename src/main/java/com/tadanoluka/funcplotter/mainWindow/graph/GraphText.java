package com.tadanoluka.funcplotter.mainWindow.graph;

import javafx.geometry.VPos;
import javafx.scene.text.TextAlignment;

public class GraphText {
    public String text;
    public double x;
    public double y;
    public TextAlignment alignment = TextAlignment.LEFT;
    public VPos vPos = VPos.BASELINE;

    public GraphText(String text, double x, double y) {
        this.text = text;
        this.x = x;
        this.y = y;
    }
    public GraphText(String text, double x, double y, VPos vPos) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.vPos = vPos;
    }
    public GraphText(String text, double x, double y, TextAlignment alignment) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.alignment = alignment;
    }
    public GraphText(String text, double x, double y, TextAlignment alignment, VPos vPos) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.alignment = alignment;
        this.vPos = vPos;
    }
}
