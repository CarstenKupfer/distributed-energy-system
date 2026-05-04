module at.uastw.disysenergygui {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.net.http;


    opens at.uastw.disysenergygui to javafx.fxml;
    exports at.uastw.disysenergygui;
}