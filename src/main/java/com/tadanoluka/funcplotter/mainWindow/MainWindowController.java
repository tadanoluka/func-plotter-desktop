package com.tadanoluka.funcplotter.mainWindow;

import com.tadanoluka.funcplotter.mainWindow.graph.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController extends MainWindowView implements Initializable {
    Graph graph;

    @FXML
    public void onRightPaneWidthChange(Number newWidth) {
        double canvasWidth = newWidth.doubleValue();
        graphCanvas.widthProperty().set(canvasWidth);
        graph.update();
    }
    @FXML
    public void onRightPaneHeightChange(Number newHeight) {
        double canvasHeight = newHeight.doubleValue();
        graphCanvas.heightProperty().set(canvasHeight);
        graph.update();
    }
    @FXML
    public void OnGraphCanvasMousePressed(MouseEvent event) {
        graph.onMousePressed(event);
    }
    @FXML
    public void onGraphCanvasMouseDragged(MouseEvent event) {
        graph.onMouseDragged(event);
        graph.update();
    }
    @FXML
    public void onGraphCanvasMouseScroll(ScrollEvent event) {
        graph.onScroll(event);

        DecimalFormat decimalFormat = graph.getGraphScaler().getDecimalFormat();

        String scaleLabelText = decimalFormat.format(graph.getGraphScaler().getScale());
        bottomBarScaleLabel.setText(String.format("Scale: %s", scaleLabelText));

        String scaleRatioLabelText = decimalFormat.format(graph.getGraphScaler().getScaleRatio());
        bottomBarScaleRatioLabel.setText(String.format("Scale Ratio: %s", scaleRatioLabelText));

        graph.update();
    }
    @FXML
    public void onGraphCanvasMouseMoved(MouseEvent event) {
        DecimalFormat decimalFormat = graph.getGraphScaler().getDecimalFormat();

        String xLabelText = decimalFormat.format(graph.getGraphUtils().getLocalXFromCanvasX(event.getX()));
        bottomBarXLabel.setText(String.format("X: %s", xLabelText));

        String yLabelText = decimalFormat.format(graph.getGraphUtils().getLocalYFromCanvasY(event.getY()));
        bottomBarYLabel.setText(String.format("Y: %s", yLabelText));
    }
    @FXML
    public void onCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private double x = 0, y = 0;
    @FXML
    public void onTopBarMousePressed(MouseEvent e) {
        x = e.getSceneX();
        y = e.getSceneY();
    }
    @FXML
    public void onTopBarMouseDragged(MouseEvent e) {
        Stage stage = (Stage) topGridPaneBar.getScene().getWindow();
        stage.setX(e.getScreenX() - x);
        stage.setY(e.getScreenY() - y);
    }

    public void setupColorTab() {
        // Init ColorPicker
        gridColorPicker.setValue(graph.getGrid().getColor());

        // Init ComboBox
        colorTabGridLineTypeComboBox.getItems().addAll(graph.getGrid().getLineTypes());
        colorTabGridLineTypeComboBox.setValue(graph.getGrid().getLineTypes()[graph.getGrid().getLineTypeIndex()]);

        // Init Line width
        gridLineWidthTextField.setText(Double.toString(graph.getGrid().getLineWidth()));
        gridLineWidthSlider.setValue(graph.getGrid().getLineWidth());

        // Init Dash line length
        gridLineDashLengthTextField.setText(Double.toString(graph.getGrid().getDashLength()));
        gridLineDashLengthSlider.setValue(graph.getGrid().getDashLength());

        // Init Dash line interval
        gridLineDashIntervalTextField.setText(Double.toString(graph.getGrid().getDashInterval()));
        gridLineDashIntervalSlider.setValue(graph.getGrid().getDashInterval());
    }
    @FXML
    public void onGridLineColorPickerAction(ActionEvent e) {
        graph.getGrid().setColor(gridColorPicker.getValue());
        graph.update();
    }
    @FXML
    public void onGridLineTypeAction(ActionEvent e) {
        graph.getGrid().setLineTypeIndex(colorTabGridLineTypeComboBox.getSelectionModel().getSelectedIndex());
        if (Objects.equals(colorTabGridLineTypeComboBox.getSelectionModel().getSelectedItem(), "Solid")) {
            gridLineDashLengthSlider.setDisable(true);
            gridLineDashIntervalSlider.setDisable(true);

            gridLineDashLengthTextField.setDisable(true);
            gridLineDashIntervalTextField.setDisable(true);

            gridDashLengthLabel.setDisable(true);
            gridDashIntervalLabel.setDisable(true);

            gridDashLengthHBox.setDisable(true);
            gridDashIntervalHBox.setDisable(true);
        } else {
            gridLineDashLengthSlider.setDisable(false);
            gridLineDashIntervalSlider.setDisable(false);

            gridLineDashLengthTextField.setDisable(false);
            gridLineDashIntervalTextField.setDisable(false);

            gridDashLengthLabel.setDisable(false);
            gridDashIntervalLabel.setDisable(false);

            gridDashLengthHBox.setDisable(false);
            gridDashIntervalHBox.setDisable(false);
        }
        graph.update();
    }

    @FXML
    public void onGridLineWidthSliderMouseDragged(MouseEvent e) {
        graph.getGrid().setLineWidth(gridLineWidthSlider.getValue());
        gridLineWidthTextField.setText(String.format("%.02f", gridLineWidthSlider.getValue()));
        graph.update();
    }

    @FXML
    public void onGridDashLineLengthSliderMouseDragged(MouseEvent e) {
        graph.getGrid().setDashLength(gridLineDashLengthSlider.getValue());
        gridLineDashLengthTextField.setText(String.format("%.02f", gridLineDashLengthSlider.getValue()));
        graph.update();
    }

    @FXML
    public void onGridDashLineIntervalSliderMouseDragged(MouseEvent e) {
        graph.getGrid().setDashInterval(gridLineDashIntervalSlider.getValue());
        gridLineDashIntervalTextField.setText(String.format("%.02f", gridLineDashIntervalSlider.getValue()));
        graph.update();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rightPane.widthProperty().addListener((observable, oldVal, newVal) -> onRightPaneWidthChange(newVal));
        rightPane.heightProperty().addListener((observable, oldVal, newVal) -> onRightPaneHeightChange(newVal));

        graph = GraphFactory.createGraph(graphCanvas);

        setupColorTab();

        graph.update();
    }

}
