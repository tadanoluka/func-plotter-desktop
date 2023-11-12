package com.tadanoluka.funcplotter.mainWindow.graph;

public class GraphUtils {
    private final GraphOrigin graphOrigin;
    private final GraphScaler graphScaler;

    public GraphUtils(GraphOrigin graphOrigin, GraphScaler graphScaler) {
        this.graphOrigin = graphOrigin;
        this.graphScaler = graphScaler;
    }

    public double getLocalXFromCanvasX(double canvasCordX) {
        return (-graphOrigin.getCanvasCordX() + canvasCordX) / graphScaler.getSegmentLengthX() * graphScaler.getNumberSegmentStep();
    }

    public double getLocalYFromCanvasY(double canvasCordY) {
        return (graphOrigin.getCanvasCordY() - canvasCordY) / graphScaler.getSegmentLengthY() * graphScaler.getNumberSegmentStep();
    }
}
