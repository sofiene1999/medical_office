import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class Login extends Application {
    Scene sceneLogin;
    Label loginmsg = new Label("Please write your code to login\n (Make sure that the surver is running)");
    Stage window;
    Button login= new Button("Login");
    PasswordField code= new PasswordField();
    Label lcode= new Label("                                     CODE");
    String thecode="12345";
    Label heydoc= new Label("HEY DOCTOR..") ;
    Label error = new Label("");
    Scene sceneWelcome;
    Scene sceneaddClient;
    Scene scene_search_client;
    Scene scene_infoclient;
    Scene scene_modify;
    Scene scene_schedule;
    String fullname="";

    String ID= "ID:    ";
    String NAME="NAME:    ";
    String FAMILY_NAME= "FAMILY NAME:    ";
    String PHONE_NUMBER= "PHONE NUMBER:    ";
    String E_MAIL="E-MAIL:    ";
    String AMOUNT="TOTAL FEES:    ";
    String PAYED="PAYED AMOUNT:    ";
    String START="TREATMENT START:    ";
    String END="TREATMENT END:    ";
    String DATE="FIX_AN_APPOINTMENT: ";
    String HISTORY="APPOINTMENTS:";
    String ER ="";
    String chId="";
    String firstword= "";

    int selectedIndex= -1;
    Client cl= new Client();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        window= primaryStage;
        window.setTitle("Clients Manager");
        window.setResizable(false);
        window.setMinHeight(400);
        window.setMaxHeight(400);
        window.setMaxWidth(600);
        window.setMinWidth(600);

        TextField ip = new TextField("");
        ip.setPromptText("HOST IP ADDRESS");

        GridPane grid= new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(10);
        grid.setHgap(10);

        HBox hb= new HBox();
        HBox hb1= new HBox();
        hb1.setMinHeight(40);

        hb.getChildren().add(heydoc);
        hb.setAlignment(Pos.TOP_CENTER);
        hb.setStyle("-fx-background-color: #013090");
        hb1.setStyle("-fx-background-color: #013090");

        heydoc.setFont(Font.font(null, FontWeight.BOLD, 32));
        heydoc.setStyle("-fx-text-fill: #D8D8D8");

        code.setPromptText("Write the code here");
        loginmsg.setFont(new Font("Arial",17));
        lcode.setFont(new Font("Arial",17));
        login.setFont(new Font("Arial",15));
        activeEnter(login);
        // login.setStyle("-fx-font-size: 24");
        error.setFont(new Font("Arial",15));
        login.setOnAction(e->{
            if(code.getText().equals(thecode)){
                try {
                    cl.connecti(ip.getText());
                    window.setScene(sceneWelcome);
                } catch (IOException ex) {
                    error.setText("Connection failed !");
                    error.setTextFill(Color.rgb(210, 39, 30));
                    ex.printStackTrace();
                }
            }else{
                error.setText("Password is incorrect!");
                error.setTextFill(Color.rgb(210, 39, 30));
            }
        });

        GridPane.setConstraints(loginmsg,0,0);
        GridPane.setConstraints(ip,1,0);
        GridPane.setConstraints(lcode, 0,1);
        GridPane.setConstraints(code,1,1);
        GridPane.setConstraints(error,0,2);
        GridPane.setConstraints(login,1,3);

        grid.getChildren().addAll(loginmsg,code,lcode,error,login,ip);
        grid.setAlignment(Pos.CENTER);
        BorderPane border= new BorderPane();
        border.setTop(hb);

        border.setCenter(grid);
        border.setBottom(hb1);
//----------------------------------------------------------------------------------------------------
        sceneLogin= new Scene(border);
        sceneLogin.getStylesheets().addAll("color.css");
        window.setScene(sceneLogin);
        window.show();
//------------------------------------------------------------------------------------------------------
        //welcome page

        Label choice= new Label("YOU ALWAYS HAVE A CHOICE");
        Button add_client= new Button("ADD PATIENT");
        Button search_client = new Button("SEARCH FOR A PATIENT");
        Button b_schedule= new Button("THE SCHEDULE");
        choice.setFont(new Font("Arial",20));

        activeEnter(add_client);
        activeEnter(search_client);
        activeEnter(b_schedule);


        GridPane grid1 = new GridPane();
        grid1.setPadding(new Insets(10,10,10,10));
        grid1.setVgap(30);
        grid1.setHgap(10);

        GridPane.setConstraints(choice,0,0);
        GridPane.setConstraints(add_client,0,1);
        GridPane.setConstraints(search_client,0,2);
        GridPane.setConstraints(b_schedule,0,3);

        grid1.getChildren().addAll(choice,add_client,search_client,b_schedule);
        grid1.setAlignment(Pos.CENTER);
        GridPane.setHalignment(add_client, HPos.CENTER);
        GridPane.setHalignment(search_client, HPos.CENTER);
        GridPane.setHalignment(b_schedule, HPos.CENTER);

        BorderPane border1= new BorderPane();
        HBox hb2= new HBox();

        hb2.setStyle("-fx-background-color: #013090");


        Label welcome= new Label("WELCOME..");
        welcome.setFont(Font.font(null, FontWeight.BOLD, 32));
        welcome.setStyle("-fx-text-fill: #FFFFFF");
        hb2.getChildren().add(welcome);
        hb2.setAlignment(Pos.CENTER);


        border1.setTop(hb2);
        border1.setCenter(grid1);

        sceneWelcome= new Scene(border1,sceneLogin.getWidth(),sceneLogin.getHeight());
        sceneWelcome.getStylesheets().addAll("color.css");

        add_client.setOnAction(e ->{
            window.setScene(sceneaddClient);
        });
        search_client.setOnAction(e->{
            window.setScene(scene_search_client);
        });



//-------------------------------------------------------------------------------------------
        //add a client


        Label name= new Label("NAME");
        Label fam_name= new Label("FAMILY NAME");
        Label email= new Label("E-MAIL");
        Label phone_num= new Label("PHONE NUMBER");
        Button add= new Button("ADD");
        Button back= new Button("BACK");
        Label err= new Label("");
        Label mop= new Label("");//label informing that email is optional

        mop.setText("(optional)");
        mop.setStyle("-fx-font-size: 15");
        mop.setStyle("-fx-text-fill: #FF0000");



        name.setFont(new Font("Arial",15));
        fam_name.setFont(new Font("Arial",15));
        email.setFont(new Font("Arial",15));
        phone_num.setFont(new Font("Arial",15));
        add.setFont(new Font("Arial",15));

        activeEnter(back);
        activeEnter(add);

        TextField tname= new TextField("");
        TextField tfam_name= new TextField("");
        TextField temail= new TextField("");
        TextField tphone_num= new TextField("");
        Label nothing =new Label("                                 ");


        tname.setPromptText("Write the name here");
        tfam_name.setPromptText("Write the family name here");
        temail.setPromptText("*******@***.**");
        tphone_num.setPromptText("Write the phone number here");

        GridPane grid2 = new GridPane();
        grid2.setPadding(new Insets(10,10,10,10));
        grid2.setVgap(30);
        grid2.setHgap(10);


        GridPane.setConstraints(name,0,0);
        GridPane.setConstraints(fam_name,0,1);
        GridPane.setConstraints(email,0,2);
        GridPane.setConstraints(phone_num,0,3);
        GridPane.setConstraints(tname,1,0);
        GridPane.setConstraints(tfam_name,1,1);
        GridPane.setConstraints(temail,1,2);
        GridPane.setConstraints(mop,3,2);
        GridPane.setConstraints(tphone_num,1,3);
        GridPane.setConstraints(add,1,4);
        GridPane.setConstraints(err,0,4);


        grid2.getChildren().addAll(name,fam_name,email,phone_num,tname,tfam_name,temail,mop,tphone_num,add,err);
        grid2.setAlignment(Pos.CENTER);

        GridPane.setHalignment(add,HPos.RIGHT);
        BorderPane border2 =new BorderPane();
        HBox hb3= new HBox();
        hb3.setSpacing(20);

        hb3.setStyle("-fx-background-color: #013090");
        Label addclient= new Label("ADD CLIENT..");
        addclient.setFont(Font.font(null, FontWeight.BOLD, 32));
        addclient.setStyle("-fx-text-fill: #FFFFFF");

        GridPane grid3= new GridPane();
        grid3.setPadding(new Insets(5,10,5,10));
        grid3.setVgap(30);
        grid3.setHgap(10);

        GridPane.setConstraints(back,0,0);
        GridPane.setConstraints(nothing,1,0);
        GridPane.setConstraints(addclient,2,0);

        grid3.getChildren().addAll(back,addclient,nothing);
        hb3.getChildren().addAll(grid3);

        border2.setTop(hb3);
        border2.setCenter(grid2);

        sceneaddClient= new Scene(border2,sceneLogin.getWidth(),sceneLogin.getHeight());
        sceneaddClient.getStylesheets().add("color.css");

        back.setOnAction(e-> window.setScene(sceneWelcome));
        add.setOnAction(e->{

            if (!(tname.getText().equals("")) && (!(tfam_name.getText().equals(""))) && (!(tphone_num.getText().equals("")))  ) {
                if ((check(tname.getText())) && ((check(tfam_name.getText()))) && (isPhoneNumber(tphone_num.getText()))) {
                   System.out.println("admitted");
                    try {
                        cl.Send("add");
                        cl.Send(tname.getText());
                        cl.Send(tfam_name.getText());
                        cl.Send(tphone_num.getText());
                        if (isValidmail(temail.getText()))
                             cl.Send(temail.getText());
                        else
                            cl.Send("");


                        err.setText(cl.receive());
                        err.setStyle("-fx-font-size: 15");
                        err.setStyle("-fx-text-fill: #008000");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                else {
                    err.setText("invalid information ");
                    err.setStyle("-fx-font-size: 15");
                    err.setStyle("-fx-text-fill: #FF0000");

                }
            }
            else {
                err.setText("Required field");
                err.setStyle("-fx-font-size: 15");
                err.setStyle("-fx-text-fill: #FF0000");
            }



        });
//---------------------------------------------------------------------------------------------
        // search for a client

        TextField tname1= new TextField("");
        TextField tfam_name1= new TextField("");
        TextField tphone_number1= new TextField("");
        Label name1= new Label("NAME");
        Label fam_name1= new Label("FAMILY NAME");
        Label phone_number1= new Label("PHONE NUMBER: ");
        Button infos = new Button("INFOS");
        Button delete= new Button("DELETE");
        Button modify =new Button("MODIFY");
        Button back1= new Button("BACK");
        Label err1= new Label("");



        phone_number1.setFont(new Font("Arial",15));
        name1.setFont(new Font("Arial",15));
        fam_name1.setFont(new Font("Arial",15));
        infos.setFont(new Font("Arial",15));
        delete.setFont(new Font("Arial",15));
        modify.setFont(new Font("Arial",15));

        Label nothing1 =new Label("                 ");

        tname1.setPromptText("Write the name here");
        tfam_name1.setPromptText("Write the family name here");
        tphone_number1.setPromptText("write the phone number here");

        activeEnter(back1);
        activeEnter(infos);


        GridPane grid4 = new GridPane();
        grid4.setPadding(new Insets(10,10,10,10));
        grid4.setVgap(30);
        grid4.setHgap(10);


        GridPane.setConstraints(name1,0,0);
        GridPane.setConstraints(fam_name1,0,1);
        GridPane.setConstraints(tname1,1,0);
        GridPane.setConstraints(tfam_name1,1,1);
        GridPane.setConstraints(phone_number1,0,2);
        GridPane.setConstraints(tphone_number1,1,2);
        GridPane.setConstraints(infos,1,3);
        GridPane.setConstraints(delete,2,3);
        GridPane.setConstraints(modify,0,3);
        GridPane.setConstraints(err1,0,4);

        grid4.getChildren().addAll(name1,fam_name1,tname1,tfam_name1,tphone_number1,phone_number1,infos,err1,delete,modify);
        grid4.setAlignment(Pos.CENTER);

        GridPane.setHalignment(infos,HPos.CENTER);
        BorderPane border3 =new BorderPane();
        HBox hb4= new HBox();
        hb4.setSpacing(20);

        hb4.setStyle("-fx-background-color: #013090");
        Label lsearch =new Label("SEARCH FOR A PATIENT ?");
        lsearch.setFont(Font.font(null, FontWeight.BOLD, 32));
        lsearch.setStyle("-fx-text-fill: #FFFFFF");

        GridPane grid5= new GridPane();
        grid5.setPadding(new Insets(5,10,5,10));
        grid5.setVgap(30);
        grid5.setHgap(10);

        GridPane.setConstraints(back1,0,0);
        GridPane.setConstraints(nothing1,1,0);
        GridPane.setConstraints(lsearch,2,0);

        grid5.getChildren().addAll(back1,lsearch,nothing1);
        hb4.getChildren().addAll(grid5);

        border3.setTop(hb4);
        border3.setCenter(grid4);

        scene_search_client= new Scene(border3,sceneLogin.getWidth(),sceneLogin.getHeight());
        scene_search_client.getStylesheets().add("color.css");
        back1.setOnAction(e-> window.setScene(sceneWelcome));


        infos.setOnAction( e->{

            fullname= tname1.getText()+" "+tfam_name1.getText();

            // --------------------------------------------------------------------------------------------------------------
            //  Client infos normalement jawha behy

            Button back2= new Button("BACK");
            Label nothing2= new Label("                ");
            ListView<String> listView;


            BorderPane border4 =new BorderPane();
            HBox hb5= new HBox();
            hb5.setSpacing(20);

            hb5.setStyle("-fx-background-color: #013090");
            Label infoClient =new Label("CLIENT: ");

            Label clientname= new Label(fullname) ;
            infoClient.setFont(Font.font(null, FontWeight.BOLD, 32));
            infoClient.setStyle("-fx-text-fill: #FFFFFF");
            clientname.setFont(Font.font(null, FontWeight.BOLD, 32));
            clientname.setStyle("-fx-text-fill: #B8B8B8");

            GridPane grid6= new GridPane();
            grid6.setPadding(new Insets(5,10,5,10));
            grid6.setVgap(30);
            grid6.setHgap(10);

            GridPane.setConstraints(back2,0,0);
            GridPane.setConstraints(nothing2,1,0);
            GridPane.setConstraints(infoClient,2,0);
            GridPane.setConstraints(clientname,3,0);

            grid6.getChildren().addAll(back2,nothing2,infoClient,clientname);
            hb5.getChildren().addAll(grid6);
            listView = new ListView<>();

            try {

                cl.Send("info");
                cl.Send(tname1.getText());
                cl.Send(tfam_name1.getText());
                cl.Send(tphone_number1.getText());
                String ch=receiveData();
                if(ch.equals("patient not found."))
                    ER ="\t\t\t\t"+ch;
                HISTORY="APPOINTMENTS: " + cl.receive();

            } catch(IOException ex){
                ex.printStackTrace();
            }

            listView.getItems().addAll(ID,NAME,FAMILY_NAME,PHONE_NUMBER,E_MAIL,AMOUNT,PAYED,START,END , HISTORY,ER);

            border4.setTop(hb5);
            border4.setCenter(listView);

            scene_infoclient= new Scene(border4,sceneLogin.getWidth(),sceneLogin.getHeight());
            scene_infoclient.getStylesheets().add("color.css");
            back2.setOnAction(e2-> {window.setScene(scene_search_client);
                ID= "ID: ";
                NAME="NAME: ";
                FAMILY_NAME= "FAMILY NAME: ";
                PHONE_NUMBER= "PHONE NUMBER: ";
                E_MAIL="E-MAIL: ";
                AMOUNT="TOTAL FEES: ";
                PAYED="PAYED AMOUNT: ";
                START="TREATMENT START: ";
                END="TREATMENT END: ";
                ER="";
                HISTORY="APPOINTMENTS: ";
                //setting the info scene to the initial state after use
            });
            activeEnter(back2);

            window.setScene(scene_infoclient);

        });


//---------------------------------------------------------------------------------------------------------
        //modify client normalement jawha behy

        activeEnter(modify);

        modify.setOnAction(e->{
            fullname= tname1.getText()+" "+tfam_name1.getText();
            Button back2= new Button("BACK");
            Button submit= new Button("SUBMIT");
            submit.setFont(new Font("Arial",15));
            TextField respond =new TextField("");
            Label info= new Label("SELECT THE ITEM AND WRITE THE NEW VALUE: ");
            Label errM= new Label("");

            info.setFont(new Font("Arial",15));

            GridPane grid7= new GridPane();
            grid7.setPadding(new Insets(10,10,10,10));
            grid7.setVgap(10);
            grid7.setHgap(10);

            GridPane.setConstraints(info,0,0);
            GridPane.setConstraints(respond,1,0);
            GridPane.setConstraints(submit,1,1);
            GridPane.setConstraints(errM,0,1);


            grid7.getChildren().addAll(info,respond,submit ,  errM);

            Label nothing2= new Label("                ");
            ListView<Item> listView;


            BorderPane border4 =new BorderPane();
            HBox hb5= new HBox();
            hb5.setSpacing(20);
            HBox hb6= new HBox();
            hb6.setSpacing(10);
            hb6.setPadding(new Insets(10,10,10,10));
            hb6.setStyle("-fx-background-color: #E8E8E8");
            hb6.getChildren().addAll(grid7);

            hb5.setStyle("-fx-background-color: #013090");
            Label infoClient =new Label("PATIENT: ");
            System.out.println("the fullname is: "+fullname);
            Label clientname= new Label(fullname) ;
            infoClient.setFont(Font.font(null, FontWeight.BOLD, 32));
            infoClient.setStyle("-fx-text-fill: #FFFFFF");
            clientname.setFont(Font.font(null, FontWeight.BOLD, 32));
            clientname.setStyle("-fx-text-fill: #B8B8B8");

            GridPane grid6= new GridPane();
            grid6.setPadding(new Insets(5,10,5,10));
            grid6.setVgap(30);
            grid6.setHgap(10);

            GridPane.setConstraints(back2,0,0);
            GridPane.setConstraints(nothing2,1,0);
            GridPane.setConstraints(infoClient,2,0);
            GridPane.setConstraints(clientname,3,0);

            grid6.getChildren().addAll(back2,nothing2,infoClient,clientname);
            hb5.getChildren().addAll(grid6);

            listView = new ListView<>();
            err.setText("");
            //show info before selection
            if ((!(tname1.getText().equals("")))  && (!(tfam_name1.getText().equals(""))) &&(!(tphone_number1.getText().equals("")))  ){
                try {
                    cl.Send("info");
                    cl.Send(tname1.getText());
                    cl.Send(tfam_name1.getText());
                    cl.Send(tphone_number1.getText());


                    chId =receiveData();//receives data from server and displays
                    String empty=cl.receive();
                } catch (IOException ex) {ex.printStackTrace();}}
            else {
                errM.setText("Required field");
                errM.setStyle("-fx-font-size: 15");
                errM.setStyle("-fx-text-fill: #FF0000");
            }

            ObservableList<Item> items= FXCollections.observableArrayList(
                    new Item(ID),
                    new Item(NAME),
                    new Item(FAMILY_NAME),
                    new Item(PHONE_NUMBER),
                    new Item(E_MAIL),
                    new Item(AMOUNT),
                    new Item(PAYED),
                    new Item(START),
                    new Item(END),
                    new Item(DATE)
            );
            listView.setItems(items);

            listView.setOnMouseClicked(event -> {
                firstword= getword1(listView.getSelectionModel().getSelectedItem().toString());
                String selectedItem =getword2( listView.getSelectionModel().getSelectedItem().toString());
                selectedIndex= listView.getSelectionModel().getSelectedIndex();
                respond.setText(selectedItem);
            });
            Dialog d= new Alert(Alert.AlertType.INFORMATION,String.valueOf(selectedIndex));

            submit.setOnAction(e3->{
                int s =selectedIndex ;
                if (selectedIndex==0){
                    errM.setText("Can't modify the ID !!");
                    errM.setStyle("-fx-font-size: 15");
                    errM.setStyle("-fx-text-fill: #FF0000");

                }else{

                if (!(chId.equals("patient not found."))) {
                    if (test(respond.getText(), s)) {
                        try {
                            cl.Send("modify");
                            cl.Send(Integer.toString(s));
                            cl.Send(chId);
                            cl.Send(respond.getText());

                            d.setContentText( cl.receive());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    else {
                        d.setContentText(d.getContentText()+" insert valid information");
                    }
                }
                else{
                    d.setContentText(" patient not found . modification is impossible");

                }
                d.show();
                items.remove(selectedIndex);
                items.add(selectedIndex,new Item(firstword+" "+respond.getText()));
                respond.clear(); }
            });

            // listView.getItems().addAll(ID,NAME,FAMILY_NAME,PHONE_NUMBER,E_MAIL,AMOUNT,START,END);


            border4.setTop(hb5);
            border4.setCenter(listView);
            border4.setBottom(hb6);

            scene_modify= new Scene(border4,sceneLogin.getWidth(),sceneLogin.getHeight());
            scene_modify.getStylesheets().add("color.css");
            back2.setOnAction(e2-> window.setScene(scene_search_client));
            activeEnter(back2);
            activeEnter(submit);

            window.setScene(scene_modify);


        });
//-----------------------------------------------------------------------------------------------------
// delete normalement jawha behy
            activeEnter(delete);

        delete.setOnAction(e->{
            if (!(tname1.getText().equals("")) && (!(tfam_name1.getText().equals(""))) && (!(tphone_number1.getText().equals("")))  )
            {
                if (deleteConfirm()){

                try {
                    cl.Send("delete");
                    cl.Send(tname1.getText());
                    cl.Send(tfam_name1.getText());
                    cl.Send(tphone_number1.getText());
                    System.out.println("this is messed up but i am beyond out");
                    err1.setStyle("-fx-font-size: 15");
                    err1.setStyle("-fx-text-fill: #008000");
                    err1.setText(cl.receive());

                } catch (IOException ex) {
                    ex.printStackTrace();
                } }
            }
            else{
                err1.setStyle("-fx-font-size: 15");
                err1.setStyle("-fx-text-fill: #008000");
                err1.setText("field required");
            }
        });
//----------------------------------------------------------------------------------------------------
        //today schedule normalement jawha behy

        b_schedule.setOnAction(e->{


            Button back2= new Button("BACK");
            Button sumbit= new Button("SUMBIT");
            activeEnter(sumbit);

            Label nothing2= new Label("       ");
            ListView<String> listView;


            BorderPane border4 =new BorderPane();
            HBox hb5= new HBox();
            hb5.setSpacing(20);

            hb5.setStyle("-fx-background-color: #013090");
            Label schedule =new Label("WHAT DO WE HAVE FOR.. ");
            TextField date_chosen =new TextField();
            date_chosen.setPromptText("write a date.");


            schedule.setFont(Font.font(null, FontWeight.BOLD, 25));
            schedule.setStyle("-fx-text-fill: #FFFFFF");

            GridPane grid6= new GridPane();
            grid6.setPadding(new Insets(5,10,5,10));
            grid6.setVgap(30);
            grid6.setHgap(10);

            GridPane.setConstraints(back2,0,0);
            GridPane.setConstraints(nothing2,1,0);
            GridPane.setConstraints(date_chosen,2,1);
            GridPane.setConstraints(sumbit,3,1);
            GridPane.setConstraints(schedule,2,0);


            grid6.getChildren().addAll(back2,nothing2,date_chosen, schedule , sumbit);
            hb5.getChildren().addAll(grid6);


            listView = new ListView<>();

            sumbit.setOnAction(e1 ->{
                List<String> items1=new ArrayList<String>();
                ObservableList<String> observableItems1= FXCollections.observableList(items1);
                if (isValidDate(date_chosen.getText())) {
                    try {
                        cl.Send("schedule");
                        cl.Send(date_chosen.getText());

                        String row = cl.receive();
                        do {
                            items1.add(row);
                            row=cl.receive();
                        }while(!(row.equals("fin")) );

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    listView.setItems(observableItems1);
                }
                else {
                    items1.add("the date should fellow the format: AAAA-MM-JJ");
                }
                listView.setItems(observableItems1);

            });


            listView.setOnMouseClicked(event -> {
                listView.getSelectionModel().getSelectedItem();
            });

            border4.setTop(hb5);
            border4.setCenter(listView);

            scene_schedule= new Scene(border4,sceneLogin.getWidth(),sceneLogin.getHeight());
            scene_schedule.getStylesheets().add("color.css");
            back2.setOnAction(e2-> window.setScene(sceneWelcome));
            activeEnter(back2);



            window.setScene(scene_schedule);
        });
    }

    public void activeEnter(Button b){

        b.setOnKeyPressed(event -> {
                    if (event.getCode().equals(KeyCode.ENTER)) {
                        b.fire();
                    }
                }
        );
    }

    public String receiveData() throws IOException{
        String msg = cl.receive();
        if (!(msg.equals("patient not found."))) {
            ID = "ID: " + msg;
            NAME = "NAME: " + cl.receive();
            FAMILY_NAME = "FAMILY_NAME: " + cl.receive();
            PHONE_NUMBER = "PHONE_NUMBER: " + cl.receive();
            E_MAIL = "E-MAIL: " + cl.receive();
            AMOUNT = "TOTAL_FEES(DT): " + cl.receive();
            PAYED = "PAYED_AMOUNT(DT): " + cl.receive();
            START = "TREATMENT_START: " + cl.receive();
            END = "TREATMENT_END: " + cl.receive();
        }
        return msg;
    }

    //valid information
    public boolean test(String ch , int s) {
        switch (s) {
            case 0://id
                return false;

            case 1:
            case 2: // names
                return check(ch);
            case 3:// phone number
                return isPhoneNumber(ch);
            case 5:
            case 6://payments
                if (ch.equals(""))
                    return true;
                else {
                    try {
                        int n = Integer.parseInt(ch);
                        return true;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
            case 4://mail
                return isValidmail(ch);
            case 9:
            case 7:
            case 8:
                return isValidDate(ch);
            default:
                return false ;
        }
    }



    //check if a string has only characters
    public boolean check(String ch) {
        if (ch.equals(""))
            return false;

        for (int i = 0; i <ch.length(); i++)
        {
            if ((!Character.isLetter(ch.charAt(i))))
                return false;
        }
        return true;
    }

    //check phone number
    public boolean isPhoneNumber(String ch) {
        if (ch.equals(""))
            return false;

        for (int i = 0; i < ch.length(); i++) {
            if ((!Character.isDigit(ch.charAt(i))))
                return false;
        }
        return ch.length() == 8;
    }

    public  boolean isValidmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public   static boolean isValidDate(String ch) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            df.setLenient(false);
            date = df.parse(ch);

        } catch (ParseException e) {
            return false;
        }

        return date != null;

    }

    public boolean deleteConfirm(){

        return (ConfirmBox.display("Delete", "Sure you want to delete ?"));

    }
    public String getword2(String ch){

        String r="";
        for(int i=0;i<ch.length();i++){

            if(ch.charAt(i)==' '){
                for (int j=i+1;j<ch.length();j++){

                    r+=ch.charAt(j);

                }
                return r;
            }
        }
        return "";
    }

    public String getword1(String ch){
        int i=0;
        String r="";
        while (ch.charAt(i)!=' '){
            r+=ch.charAt(i);
            i++;
        }
        return r;

    }

}



