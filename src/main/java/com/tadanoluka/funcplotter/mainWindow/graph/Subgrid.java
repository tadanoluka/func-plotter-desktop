package com.tadanoluka.funcplotter.mainWindow.graph;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Subgrid {
    private Canvas graphCanvas;
    private GraphicsContext graphicsContext;
    private GraphScaler graphScaler;
    private GraphOrigin graphOrigin;

    private Color color = new Color(1,1,1, 0.1);
    private final String[] lineTypes = {"Solid", "Dashed"};
    private int lineTypeIndex = 1;
    private double lineWidth = 1;
    private double dashLength = 2;
    private double dashInterval = 2;

    public Subgrid(Canvas graphCanvas, GraphScaler graphScaler, GraphOrigin graphOrigin) {
        this.graphCanvas = graphCanvas;
        this.graphScaler = graphScaler;
        this.graphOrigin = graphOrigin;

        this.graphicsContext = graphCanvas.getGraphicsContext2D();
    }


    private double[][] getHorizontalLines() {
        double subgridSegmentLengthY = graphScaler.getSegmentLengthY() / 5;
        double cordY = graphOrigin.getCanvasCordY() % subgridSegmentLengthY;
        if (cordY < 0) {
            cordY += subgridSegmentLengthY;
        }
        int amount = (int) ((graphCanvas.getHeight() - cordY) / subgridSegmentLengthY) + 1;
        double[][] lines = new double[amount][4];
        for (int i = 0; i < amount; i++) {
            lines[i] = new double[] {0, cordY, graphCanvas.getWidth(), cordY};
            cordY += subgridSegmentLengthY;
        }
        return lines;
    }

    private double[][] getVerticalLines() {
        double subgridSegmentLengthX = graphScaler.getSegmentLengthX() / 5;
        double cordX = graphOrigin.getCanvasCordX() % subgridSegmentLengthX;
        if (cordX < 0) {
            cordX += subgridSegmentLengthX;
        }
        int amount = (int) ((graphCanvas.getWidth() - cordX) / subgridSegmentLengthX) + 1;
        double[][] lines = new double[amount][4];
        for (int i = 0; i < amount; i++) {
            lines[i] = new double[] {cordX, 0, cordX, graphCanvas.getHeight()};
            cordX += subgridSegmentLengthX;
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
