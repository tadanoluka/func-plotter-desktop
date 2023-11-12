module com.tadanoluka.funcplotter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;

    opens com.tadanoluka.funcplotter to javafx.fxml;
    opens com.tadanoluka.funcplotter.mainWindow to javafx.fxml;
    opens com.tadanoluka.funcplotter.mainWindow.titledPropertyPanel to javafx.fxml;

    exports com.tadanoluka.funcplotter;
}