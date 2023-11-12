package com.tadanoluka.funcplotter.mainWindow;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.skins.JFXCheckBoxOldSkin;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainWindowView {
    @FXML
    public Canvas graphCanvas;
    @FXML
    public Pane rightPane;
    @FXML
    public Label bottomBarXLabel;
    @FXML
    public Label bottomBarYLabel;
    @FXML
    public Label bottomBarScaleLabel;
    @FXML
    public Label bottomBarScaleRatioLabel;
    @FXML
    public Button closeButton;
    @FXML
    public GridPane topGridPaneBar;
    @FXML
    public JFXComboBox<String> colorTabGridLineTypeComboBox;
    @FXML
    public ColorPicker gridColorPicker;
    @FXML
    public JFXSlider gridLineWidthSlider;
    @FXML
    public JFXTextField gridLineWidthTextField;
    @FXML
    public Label gridDashLengthLabel;
    @FXML
    public HBox gridDashLengthHBox;
    @FXML
    public JFXSlider gridLineDashLengthSlider;
    @FXML
    public JFXTextField gridLineDashLengthTextField;
    @FXML
    public Label gridDashIntervalLabel;
    @FXML
    public HBox gridDashIntervalHBox;
    @FXML
    public JFXSlider gridLineDashIntervalSlider;
    @FXML
    public JFXTextField gridLineDashIntervalTextField;
    @FXML
    public JFXScrollPane jfxScrollPane1;
    @FXML
    public VBox categoryVBox1;
    @FXML
    public VBox categoryVBox2;

}
