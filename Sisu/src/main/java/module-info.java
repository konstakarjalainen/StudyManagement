module Sisu {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires com.google.gson;
    requires org.apache.httpcomponents.httpclient;
    requires org.apache.httpcomponents.httpcore;

    opens fi.tuni.prog3.sisu.controller to javafx.fxml;
    opens fi.tuni.prog3.sisu.entity to com.google.gson;
    opens fi.tuni.prog3.sisu.entity.sisu to com.google.gson;
    opens fi.tuni.prog3.sisu.client to com.google.gson;

    exports fi.tuni.prog3.sisu;
    exports fi.tuni.prog3.sisu.controller;
    exports fi.tuni.prog3.sisu.entity;
    exports fi.tuni.prog3.sisu.entity.sisu;
    exports fi.tuni.prog3.sisu.client;

}