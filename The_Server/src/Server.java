import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Semaphore;

public class Server extends Application {

    Stage window;
    Timer chrono= new Timer();
    String ch="";
    Label activated;

    static Serv sv= new Serv();
    static ConnectDB connectionItem ;


    public static void main(String[] args) throws Exception {
        connectionItem= new ConnectDB(sv);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label heydoc= new Label("HEY DOCTOR..") ;
        Button activate= new Button("ACTIVATE THE SERVER");

        activated= new Label("");


        activate.setFont(Font.font(null, FontWeight.BOLD, 20));
        heydoc.setFont(Font.font(null, FontWeight.BOLD, 32));
        heydoc.setStyle("-fx-text-fill: #D8D8D8");

        window = primaryStage;
        window.setTitle("Clients Manager Server");
        window.setResizable(false);
        window.setMinHeight(400);
        window.setMaxHeight(400);
        window.setMaxWidth(600);
        window.setMinWidth(600);

        VBox vb= new VBox();
        vb.setPadding(new Insets(10,10,10,10));
        vb.setSpacing(10);
        vb.getChildren().addAll(activate,activated);

        HBox hb= new HBox();
        HBox hb1= new HBox();
        hb1.setMinHeight(40);

        hb.getChildren().add(heydoc);
        hb.setAlignment(Pos.TOP_CENTER);
        hb.setStyle("-fx-background-color: #013090");
        hb1.setStyle("-fx-background-color: #013090");
        BorderPane border= new BorderPane();
        border.setTop(hb);
        border.setBottom(hb1);
        border.setCenter(vb);
        vb.setAlignment(Pos.CENTER);

        Scene scene= new Scene(border);
        scene.getStylesheets().addAll("color.css");
        window.setScene(scene);

        window.show();
        window.setOnCloseRequest(e->{
          try {
              System.exit(0);
               sv.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        });



        activate.setOnAction(e->{
            activated.setText("SERVER ACTIVATED!!");
            activated.setStyle("-fx-text-fill: #008000");
            activated.setFont(Font.font(null, FontWeight.BOLD, 20));
            ch = "hello";

        });

        activeEnter(activate);

      chrono.schedule(new TimerTask() {
            @Override
            public void run() {

                if (ch!=""){
                    try {
                        sv.activate();
                        cancel();
                        while(true){
                            String op;
                            op = sv.receive();
                           connectionItem.runOp(op);
                        }
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }

            }


      },1000,1000);

    }


    public void activeEnter(Button b) throws Exception {

        b.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        b.fire();
                    }
                }
        );


    }





}








