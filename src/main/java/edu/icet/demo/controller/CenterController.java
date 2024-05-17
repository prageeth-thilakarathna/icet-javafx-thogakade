package edu.icet.demo.controller;

import javafx.scene.control.Alert;
import lombok.Getter;

import java.text.DecimalFormat;

public class CenterController {
    @Getter
    private static final CenterController instance = new CenterController();

    private CenterController(){}

    public static final Alert alert = new Alert(Alert.AlertType.NONE);

    public static final DecimalFormat df = new DecimalFormat("0.00");
}
