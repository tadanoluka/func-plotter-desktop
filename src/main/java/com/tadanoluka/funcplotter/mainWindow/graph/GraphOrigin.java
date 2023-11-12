package com.tadanoluka.funcplotter.mainWindow.graph;


import javafx.scene.canvas.Canvas;

public class GraphOrigin {
    private final Canvas graphCanvas;

    private double canvasCordX = 0.0;
    private double canvasCordY = 0.0;
    private double offsetX = 0;
    private double offsetY = 0;

    public GraphOrigin(Canvas graphCanvas) {
        this.graphCanvas = graphCanvas;
    }

    private void updateCanvasCordX() {
        canvasCordX = graphCanvas.getWidth() / 2  + offsetX;
    }
    private void updateCanvasCordY() {
        canvasCordY = graphCanvas.getHeight() / 2 + offsetY;
    }

    public double getCanvasCordX() {
        updateCanvasCordX();
        return canvasCordX;
    }

    public double getCanvasCordY() {
        updateCanvasCordY();
        return canvasCordY;
    }

    public void addOffsetX(double value) {
        offsetX += value;
    }

    public void addOffsetY(double value) {
        offsetY += value;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }
}
