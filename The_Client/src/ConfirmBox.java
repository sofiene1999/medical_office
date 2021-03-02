import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.awt.*;

public class ConfirmBox {

    static boolean answer;

    public static boolean display(String title, String msg){

        Stage window= new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);

        window.setMinWidth(350);
        window.setMaxWidth(350);
        window.setMinHeight(150);
        window.setMaxHeight(150);

        Label label = new Label(msg);
        Button yesButton = new Button();
        yesButton.setText("Yes");
        activeEnter(yesButton);
        yesButton.setOnAction(e -> {
            answer=true;
            window.close();
        });

        Button noButton= new Button();
        noButton.setText("no");
        activeEnter(noButton);
        noButton.setOnAction(e -> {
            answer=false;
            window.close();
        });

        Label l= new Label(msg);
        HBox button= new HBox(10);
        button.getChildren().addAll(yesButton,noButton);
        button.setAlignment(Pos.CENTER);
        VBox v= new VBox(10);
        v.getChildren().addAll(l,button);
        v.setAlignment(Pos.CENTER);

        Scene scene = new Scene(v,350,150);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

    public static void activeEnter(Button b){

        b.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        b.fire();
                    }
                }
        );
    }
}
