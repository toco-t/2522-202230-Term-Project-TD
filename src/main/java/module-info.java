module ca.bcit.comp2522.termproject.td {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.bcit.comp2522.termproject.td to javafx.fxml;
    exports ca.bcit.comp2522.termproject.td;
}