module ca.bcit.comp2522.termproject.td {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.bcit.comp2522.termproject.td to javafx.fxml;
    exports ca.bcit.comp2522.termproject.td;
    exports ca.bcit.comp2522.termproject.td.hello;
    opens ca.bcit.comp2522.termproject.td.hello to javafx.fxml;
    exports ca.bcit.comp2522.termproject.td.weapon;
    opens ca.bcit.comp2522.termproject.td.weapon to javafx.fxml;
}