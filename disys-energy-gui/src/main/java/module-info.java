module at.uastw.disysenergygui {
    requires javafx.controls;
    requires javafx.fxml;


    opens at.uastw.disysenergygui to javafx.fxml;
    exports at.uastw.disysenergygui;
}