module java.gent13 {
    requires MaterialFX;
    requires javafx.media;
    requires json.simple;
    requires com.fasterxml.jackson.databind;
    requires okhttp3;
    requires org.json;
    requires java.net.http;
    requires javafx.base;
    requires org.apache.commons.configuration2;
    requires commons.validator;
    requires com.google.gson;
    requires junit;

    exports domein;
        opens domein to javafx.base;


     exports resources;
    exports main;
    exports model;
    exports gui.components;
    exports gui.screens;
    opens model to javafx.base;
}