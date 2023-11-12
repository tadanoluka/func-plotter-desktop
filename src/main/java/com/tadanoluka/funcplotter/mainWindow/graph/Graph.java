package com.tadanoluka.funcplotter.mainWindow.graph;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public class Graph {
    private final Canvas graphCanvas;
    private final GraphicsContext graphicsContext;
    private final GraphScaler graphScaler;
    private final GraphOrigin graphOrigin;
    private final GraphUtils graphUtils;
    private final Grid grid;
    private final Subgrid subgrid;
    private final Axes axes;
    private final PlottedFunction plottedFunction;

    private double lastMouseX;
    private double lastMouseY;


    public Graph(Canvas graphCanvas, GraphScaler graphScaler, GraphOrigin graphOrigin, GraphUtils graphUtils, Grid grid, Subgrid subgrid, Axes axes, PlottedFunction plottedFunction) {
        this.graphCanvas = graphCanvas;
        this.graphScaler = graphScaler;
        this.graphOrigin = graphOrigin;
        this.graphUtils = graphUtils;
        this.grid = grid;
        this.subgrid = subgrid;
        this.axes = axes;
        this.plottedFunction = plottedFunction;

        graphicsContext = graphCanvas.getGraphicsContext2D();
    }

    public void onMousePressed(MouseEvent event) {
        lastMouseX = event.getX();
        lastMouseY = event.getY();
    }

    public void onMouseDragged(MouseEvent event) {
        double dragDistanceX = event.getX() - lastMouseX;
        double dragDistanceY = event.getY() - lastMouseY;

        graphOrigin.addOffsetX(dragDistanceX);
        graphOrigin.addOffsetY(dragDistanceY);

        lastMouseX = event.getX();
        lastMouseY = event.getY();
    }

    public void onScroll(ScrollEvent event) {
        if (event.getTextDeltaY() > 0) {
            graphScaler.zoomInToCanvasPoint(event.getX(), event.getY());
        } else {
            graphScaler.zoomOutFromCanvasPoint(event.getX(), event.getY());
        }
    }

    public void update() {
        clear();
        subgrid.draw();
        grid.draw();
        axes.draw();
    }

    public void clear() {
        graphicsContext.clearRect(0, 0, graphCanvas.getWidth(), graphCanvas.getHeight());
    }

    public GraphScaler getGraphScaler() {
        return graphScaler;
    }

    public GraphUtils getGraphUtils() {
        return graphUtils;
    }

    public Grid getGrid() {
        return grid;
    }

}




