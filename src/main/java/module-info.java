module ch.kbw.totoluto.betrandparadox {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens ch.kbw.totoluto.bertrandparadox to javafx.fxml;
    exports ch.kbw.totoluto.bertrandparadox;
}