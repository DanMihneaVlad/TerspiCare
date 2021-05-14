package org.fis2021.terpsicare;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.*;
import javafx.scene.control.*;

public class AlertBox {

    public static void display(String title, String message){
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Text label = new Text("text");
        label.setId("text");
        label.setText(message);
        Button closeButton = new Button ("OK");
        closeButton.setId("OK");
        closeButton.setOnAction(e -> window.close());

        VBox layout =  new VBox(10);
        layout.getChildren().addAll(label,closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
