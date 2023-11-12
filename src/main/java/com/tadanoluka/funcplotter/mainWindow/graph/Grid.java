package com.tadanoluka.funcplotter.mainWindow.graph;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.Arrays;

public class Grid {
    private Canvas graphCanvas;
    private GraphicsContext graphicsContext;
    private GraphScaler graphScaler;
    private GraphOrigin graphOrigin;


    private Color color = new Color(1,1,1, 0.2);
    private final String[] lineTypes = {"Solid", "Dashed"};
    private int lineTypeIndex = 1;
    private double lineWidth = 1;
    private double dashLength = 4;
    private double dashInterval = 3;


    public Grid(Canvas graphCanvas, GraphScaler graphScaler, GraphOrigin graphOrigin) {
        this.graphCanvas = graphCanvas;
        this.graphScaler = graphScaler;
        this.graphOrigin = graphOrigin;

        graphicsContext = graphCanvas.getGraphicsContext2D();
    }


    private double[][] getHorizontalLines() {
        double cordY = graphOrigin.getCanvasCordY() % graphScaler.getSegmentLengthY();
        if (cordY < 0) {
            cordY += graphScaler.getSegmentLengthY();
        }
        int amount = (int) ((graphCanvas.getHeight() - cordY) / graphScaler.getSegmentLengthY()) + 1;
        double[][] lines = new double[amount][4];
        for (int i = 0; i < amount; i++) {
            lines[i] = new double[] {0, cordY, graphCanvas.getWidth(), cordY};
            cordY += graphScaler.getSegmentLengthY();
        }
        return lines;
    }

    private double[][] getVerticalLines() {
        double cordX = graphOrigin.getCanvasCordX() % graphScaler.getSegmentLengthX();
        if (cordX < 0) {
            cordX += graphScaler.getSegmentLengthX();
        }
        int amount = (int) ((graphCanvas.getWidth() - cordX) / graphScaler.getSegmentLengthX()) + 1;
        double[][] lines = new double[amount][4];
        for (int i = 0; i < amount; i++) {
            lines[i] = new double[] {cordX, 0, cordX, graphCanvas.getHeight()};
            cordX += graphScaler.getSegmentLengthX();
        }
        return lines;

    }

    private double[][] getLines() {
        double[][] vLines = getVerticalLines();
        double[][] hLines = getHorizontalLines();
        double[][] lines = new double[vLines.length + hLines.length][4];

        System.arraycopy(vLines,0, lines, 0, vLines.length);
        System.arraycopy(hLines,0, lines, vLines.length, hLines.length);

        return lines;
    }

    public void draw() {
        Paint prevPaint = graphicsContext.getStroke();
        double[] prevLineDashes = graphicsContext.getLineDashes();
        double prevLineWidth = graphicsContext.getLineWidth();

        graphicsContext.setStroke(color);
        switch (lineTypeIndex) {
            case 0 -> graphicsContext.setLineDashes();
            default -> graphicsContext.setLineDashes(dashLength, dashInterval);
        }

        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.beginPath();
        for (double[] lineCords : getLines()) {
            graphicsContext.moveTo(lineCords[0], lineCords[1]);
            graphicsContext.lineTo(lineCords[2], lineCords[3]);
        }
        graphicsContext.stroke();

        graphicsContext.setStroke(prevPaint);
        graphicsContext.setLineDashes(prevLineDashes);
        graphicsContext.setLineWidth(prevLineWidth);
    }

    public String[] getLineTypes() {
        return lineTypes;
    }

    public int getLineTypeIndex() {
        return lineTypeIndex;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setLineTypeIndex(int lineTypeIndex) {
        this.lineTypeIndex = lineTypeIndex;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public double getDashLength() {
        return dashLength;
    }

    public void setDashLength(double dashLength) {
        this.dashLength = dashLength;
    }

    public double getDashInterval() {
        return dashInterval;
    }

    public void setDashInterval(double dashInterval) {
        this.dashInterval = dashInterval;
    }
}

