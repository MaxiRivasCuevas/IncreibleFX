module org.example.gestionclinica {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.auth.oauth2;
    requires google.cloud.firestore;
    requires com.google.api.apicommon;
    requires firebase.admin;
    requires google.cloud.core;
    requires com.google.auth;

    opens org.example.gestionclinica to javafx.fxml;
    exports org.example.gestionclinica;
}