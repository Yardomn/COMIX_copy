module unitXX {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires org.json;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.dataformat.xml;
    requires com.fasterxml.jackson.databind;

    opens unitXX to javafx.fxml;
    exports core.comix to javafx.graphics;
    exports unitXX;

    exports core.comix.persistence.db to com.fasterxml.jackson.databind;
    opens core.comix.model.comic to com.fasterxml.jackson.databind;
}
