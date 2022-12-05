module ca.bcit.comp2522.termproject.td {
    requires javafx.controls;
    requires javafx.fxml;


    opens ca.bcit.comp2522.termproject.td to javafx.fxml;
    exports ca.bcit.comp2522.termproject.td;
    exports ca.bcit.comp2522.termproject.td.driver;
    opens ca.bcit.comp2522.termproject.td.driver to javafx.fxml;
    exports ca.bcit.comp2522.termproject.td.weapon;
    opens ca.bcit.comp2522.termproject.td.weapon to javafx.fxml;
    exports ca.bcit.comp2522.termproject.td.unit;
    opens ca.bcit.comp2522.termproject.td.unit to javafx.fxml;
    exports ca.bcit.comp2522.termproject.td.enums;
    opens ca.bcit.comp2522.termproject.td.enums to javafx.fxml;
    exports ca.bcit.comp2522.termproject.td.interfaces;
    opens ca.bcit.comp2522.termproject.td.interfaces to javafx.fxml;
    exports ca.bcit.comp2522.termproject.td.map;
    opens ca.bcit.comp2522.termproject.td.map to javafx.fxml;
    exports ca.bcit.comp2522.termproject.td.items;
    opens ca.bcit.comp2522.termproject.td.items to javafx.fxml;
}