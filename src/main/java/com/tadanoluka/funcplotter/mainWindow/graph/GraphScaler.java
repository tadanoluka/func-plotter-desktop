package com.tadanoluka.funcplotter.mainWindow.graph;

import javafx.scene.canvas.Canvas;

import java.text.DecimalFormat;

public class GraphScaler {
    private final Canvas graphCanvas;
    private final GraphOrigin graphOrigin;
    private double scale = 1;
    private double scaleRatio = 1.1;
    private double inverseScaleRatio = 1.0 / scaleRatio;
    private double baseSegmentLength = 60;
    private double segmentLength = baseSegmentLength;
    private double segmentLengthX = baseSegmentLength;
    private double segmentLengthY = baseSegmentLength;
    private double numberSegmentStep = 1;

    private DecimalFormat decimalFormat = new DecimalFormat("#.##");
    private double[] numberSegmentStepMultipliers = new double[] {1, 2, 5};
    private int numberSegmentStepMultiplierIndex = 0;
    private int numberSegmentStepMultiplierPow = 0;

    public GraphScaler(Canvas graphCanvas, GraphOrigin graphOrigin) {
        this.graphCanvas = graphCanvas;
        this.graphOrigin = graphOrigin;
    }

    public double getSegmentLengthX() {
        segmentLengthX = getSegmentLength();
        return segmentLengthX;
    }

    public double getSegmentLengthY() {
        segmentLengthY = getSegmentLength();
        return segmentLengthY;
    }

    public void zoomInToCanvasPoint(double x, double y) {
        zoomRelativeToPointOnCanvas(x, y, scaleRatio);
        scale *= scaleRatio;
    }

    public void zoomOutFromCanvasPoint(double x, double y) {
        zoomRelativeToPointOnCanvas(x, y, inverseScaleRatio);
        scale /= scaleRatio;
    }

    private void zoomRelativeToPointOnCanvas(double x, double y, double scaleRatio) {
        double zoomPointOffsetX = x - graphCanvas.getWidth() / 2;
        double zoomPointOffsetY = y - graphCanvas.getHeight() / 2;

        double newLocalOffsetX = graphOrigin.getOffsetX() * scaleRatio - zoomPointOffsetX * (scaleRatio - 1);
        double newLocalOffsetY = graphOrigin.getOffsetY() * scaleRatio - zoomPointOffsetY * (scaleRatio - 1);

        graphOrigin.setOffsetX(newLocalOffsetX);
        graphOrigin.setOffsetY(newLocalOffsetY);
    }

    public double getNumberSegmentStep() {
        return numberSegmentStep;
    }

    private void recalculateNumberSegmentStep() {
        int index = numberSegmentStepMultiplierIndex;
        double[] multipliers = numberSegmentStepMultipliers;
        double multiplier = multipliers[index];
        int pow = numberSegmentStepMultiplierPow;
        numberSegmentStep = multiplier * Math.pow(10, pow);
    }

    private void increaseNumberSegmentStep() {
        // [1, 2 ,5]
        double[] multipliers = numberSegmentStepMultipliers;
        numberSegmentStepMultiplierIndex += 1;
        if (numberSegmentStepMultiplierIndex > multipliers.length - 1) {
            numberSegmentStepMultiplierIndex = 0;
            numberSegmentStepMultiplierPow += 1;

        }
        recalculateNumberSegmentStep();
    }

    private void reduceNumberSegmentStep() {
        //[1, 2 ,5]
        double[] multipliers = numberSegmentStepMultipliers;
        numberSegmentStepMultiplierIndex -= 1;
        if (numberSegmentStepMultiplierIndex < 0) {
            numberSegmentStepMultiplierIndex = multipliers.length - 1;
            numberSegmentStepMultiplierPow -= 1;

        }
        recalculateNumberSegmentStep();
    }


    private void recalculateNumberSegmentLength() {
        segmentLength = scale * baseSegmentLength * numberSegmentStep;
        if (segmentLength > 100) {
            reduceNumberSegmentStep();
            recalculateNumberSegmentLength();
        } else if (segmentLength < 40) {
            increaseNumberSegmentStep();
            recalculateNumberSegmentLength();
        }
    }

    public double getSegmentLength() {
        recalculateNumberSegmentLength();
        return segmentLength;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }

    public double getScale() {
        return scale;
    }

    public double getScaleRatio() {
        return scaleRatio;
    }
}
