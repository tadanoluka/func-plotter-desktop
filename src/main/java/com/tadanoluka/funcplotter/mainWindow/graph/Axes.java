package com.tadanoluka.funcplotter.mainWindow.graph;


import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Axes {
    private final Canvas graphCanvas;
    private final GraphicsContext graphicsContext;
    private final GraphScaler graphScaler;
    private final GraphOrigin graphOrigin;
    private final GraphUtils graphUtils;

    private final Paint paint = new Color(1,1,1, 0.7);
    private final double lineWidth = 2;

    private final int fontSize = 12;
    private final Font font = new Font("Verdana", fontSize);

    public Axes(Canvas graphCanvas, GraphScaler graphScaler, GraphOrigin graphOrigin, GraphUtils graphUtils) {
        this.graphCanvas = graphCanvas;
        this.graphScaler = graphScaler;
        this.graphOrigin = graphOrigin;
        this.graphUtils = graphUtils;

        graphicsContext = graphCanvas.getGraphicsContext2D();
    }

    private double[][] getAxesLines() {
        double[][] lines = new double[2][4];
        // X Axis
        lines[0] = new double[] {0, graphOrigin.getCanvasCordY(), graphCanvas.getWidth(), graphOrigin.getCanvasCordY()};
        // Y Axis
        lines[1] = new double[] {graphOrigin.getCanvasCordX(), 0, graphOrigin.getCanvasCordX(), graphCanvas.getHeight()};
        return lines;
    }

    private List<double[]> getAxisXNumbers() {
        double numStep = graphScaler.getNumberSegmentStep();
        double segmentLength = graphScaler.getSegmentLengthX();

        double startX = graphOrigin.getCanvasCordX() % segmentLength;
        startX = startX < 0 ? startX + segmentLength : startX;

        double endX = Math.floor((graphCanvas.getWidth() - startX) / segmentLength) * segmentLength + startX;

        startX -= segmentLength;
        endX += segmentLength;

        double startNum = graphUtils.getLocalXFromCanvasX(startX);
        double endNum = graphUtils.getLocalXFromCanvasX(endX);

        List<double[]> xAxisNums = new ArrayList<>();

        double numY = graphOrigin.getCanvasCordY();
        numY = Math.max(numY, 0);
        numY = Math.min(numY, graphCanvas.getHeight());


        if (graphOrigin.getCanvasCordX() < 0) {
            // Case Only Positive Numbers
            double num = startNum;
            for (double x = startX; x < endX; x += segmentLength) {
                xAxisNums.add(new double[] {num, x, numY});
                num += numStep;
            }
        } else if (graphOrigin.getCanvasCordX() > graphCanvas.getWidth()) {
            // Case Only Negative Numbers
            double num = endNum;
            for (double x = endX; x > startX; x -= segmentLength) {
                xAxisNums.add(new double[] {num, x, numY});
                num -= numStep;
            }
        } else {
            // Case Positive and Negative Numbers
            double num = numStep;
            for (double x = graphOrigin.getCanvasCordX() + segmentLength ; x < endX + segmentLength; x += segmentLength) {
                xAxisNums.add(new double[] {num, x, numY});
                num += numStep;
            }
            num = -numStep;
            for (double x = graphOrigin.getCanvasCordX() - segmentLength ; x > startX - segmentLength; x -= segmentLength) {
                xAxisNums.add(new double[] {num, x, numY});
                num -= numStep;
            }
        }
        return xAxisNums;
    }

    private List<double[]> getAxisYNumbers() {
        double numStep = graphScaler.getNumberSegmentStep();
        double segmentLength = graphScaler.getSegmentLengthY();

        double startY = graphOrigin.getCanvasCordY() % segmentLength;
        startY = startY < 0 ? startY + segmentLength : startY;

        double endY = Math.floor((graphCanvas.getHeight() - startY) / segmentLength) * segmentLength + startY;

        startY -= segmentLength;
        endY += segmentLength;

        double startNum = graphUtils.getLocalYFromCanvasY(startY);
        double endNum = graphUtils.getLocalYFromCanvasY(endY);

        List<double[]> yAxisNums = new ArrayList<>();

        double numX = graphOrigin.getCanvasCordX();
        numX = Math.max(numX, 0);
        numX = Math.min(numX, graphCanvas.getWidth());

        if (graphOrigin.getCanvasCordY() < 0) {
            // Case Only Negative Numbers
            double num = startNum;
            for (double y = startY; y < endY; y += segmentLength) {
                yAxisNums.add(new double[] {num, numX, y});
                num -= numStep;
            }
        } else if (graphOrigin.getCanvasCordY() > graphCanvas.getHeight()) {
            // Case Only Positive Numbers
            double num = endNum;
            for (double y = endY; y > startY; y -= segmentLength) {
                yAxisNums.add(new double[] {num, numX, y});
                num += numStep;
            }
        } else {
            // Case Only Positive + Negative Numbers
            double num = -numStep;
            for (double y = graphOrigin.getCanvasCordY() + segmentLength ; y <= endY; y += segmentLength) {
                yAxisNums.add(new double[] {num, numX, y});
                num -= numStep;
            }
            num = numStep;
            for (double y = graphOrigin.getCanvasCordY() - segmentLength ; y >= startY; y-= segmentLength) {
                yAxisNums.add(new double[] {num, numX, y});
                num += numStep;
            }
        }
        return yAxisNums;
    }

    public void draw() {
        drawLines();
        drawNumbers();
    }

    private void drawLines() {
        Paint prevPaint = graphicsContext.getStroke();
        double prevLineWidth = graphicsContext.getLineWidth();

        graphicsContext.setStroke(paint);
        graphicsContext.setLineWidth(lineWidth);
        graphicsContext.beginPath();
        for (double[] lineCords : getAxesLines()) {
            graphicsContext.moveTo(lineCords[0], lineCords[1]);
            graphicsContext.lineTo(lineCords[2], lineCords[3]);
        }
        graphicsContext.stroke();

        graphicsContext.setStroke(prevPaint);
        graphicsContext.setLineWidth(prevLineWidth);
    }

    private void drawNumbers() {
        drawAxisXNumbers();
        drawAxisYNumber();
    }

    private void drawAxisXNumbers() {
        Font prevFont = graphicsContext.getFont();
        Paint prevFill = graphicsContext.getFill();
        TextAlignment prevTextAlign = graphicsContext.getTextAlign();
        VPos prevVPos = graphicsContext.getTextBaseline();

        VPos vPos;
        TextAlignment textAlignment = TextAlignment.CENTER;

        if (graphOrigin.getCanvasCordY() > fontSize) {
            vPos = VPos.BOTTOM;
        } else {
            vPos = VPos.TOP;
        }
        graphicsContext.setFont(font);
        graphicsContext.setFill(paint);
        graphicsContext.setTextAlign(textAlignment);
        graphicsContext.setTextBaseline(vPos);

        List<double[]> xNumbers = getAxisXNumbers();
        DecimalFormat decimalFormat = graphScaler.getDecimalFormat();

        for (double[] number : xNumbers) {
            graphicsContext.fillText(decimalFormat.format(number[0]), number[1], number[2]);
        }

        graphicsContext.setFont(prevFont);
        graphicsContext.setFill(prevFill);
        graphicsContext.setTextAlign(prevTextAlign);
        graphicsContext.setTextBaseline(prevVPos);
    }

    private void drawAxisYNumber() {
        Font prevFont = graphicsContext.getFont();
        Paint prevFill = graphicsContext.getFill();
        TextAlignment prevTextAlign = graphicsContext.getTextAlign();
        VPos prevVPos = graphicsContext.getTextBaseline();

        VPos vPos = VPos.CENTER;
        TextAlignment textAlignment;
        double offset = 2;

        if (graphOrigin.getCanvasCordX() < graphCanvas.getWidth()) {
            textAlignment = TextAlignment.LEFT;
        } else {
            textAlignment = TextAlignment.RIGHT;
            offset = -offset;
        }

        graphicsContext.setFont(font);
        graphicsContext.setFill(paint);
        graphicsContext.setTextAlign(textAlignment);
        graphicsContext.setTextBaseline(vPos);

        List<double[]> yNumbers = getAxisYNumbers();
        DecimalFormat decimalFormat = graphScaler.getDecimalFormat();

        for (double[] number : yNumbers) {
            graphicsContext.fillText(decimalFormat.format(number[0]), number[1] + offset, number[2]);
        }

        graphicsContext.setFont(prevFont);
        graphicsContext.setFill(prevFill);
        graphicsContext.setTextAlign(prevTextAlign);
        graphicsContext.setTextBaseline(prevVPos);
    }
}
