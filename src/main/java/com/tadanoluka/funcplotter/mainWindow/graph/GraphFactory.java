package com.tadanoluka.funcplotter.mainWindow.graph;

import javafx.scene.canvas.Canvas;

public class GraphFactory {

    public static Graph createGraph(Canvas graphCanvas) {
        GraphOrigin graphOrigin = new GraphOrigin(graphCanvas);
        GraphScaler graphScaler = new GraphScaler(graphCanvas, graphOrigin);
        GraphUtils graphUtils = new GraphUtils(graphOrigin, graphScaler);
        Grid grid = new Grid(graphCanvas, graphScaler, graphOrigin);
        Subgrid subgrid = new Subgrid(graphCanvas, graphScaler, graphOrigin);
        Axes axes = new Axes(graphCanvas, graphScaler, graphOrigin, graphUtils);
        return new Graph(graphCanvas, graphScaler, graphOrigin, graphUtils, grid, subgrid, axes, new PlottedFunction());
    }
}
