package com.example.newminiii2;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HelloApplication extends Application {
    Stage stage=new Stage();
    @Override
    public void start(Stage dummy) throws IOException {
        GridPane root = new GridPane();
        root.setMinSize(100, 100);
        root.setPadding(new Insets(30, 30, 30, 30));
        root.setHgap(5);
        root.setVgap(5);
        Scene scene = new Scene(root, 600, 600);
        //Label res=new Label();
        Label tit = new Label("WELCOME TO AIRLINE RESERVATION!!!");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
        tit.setFont(font);
        Label id = new Label("MAIL ID:");
        TextField mailid = new TextField();
        tit.setTextFill(Color.MIDNIGHTBLUE);
        tit.setScaleX(1);
        tit.setScaleY(2);
        tit.setEffect(new Reflection());
        mailid.setPrefWidth(100);
        Label password = new Label("PASSWORD:");
        TextField pw = new TextField();
        pw.setPrefWidth(100);
        Text warningTxt = new Text();
        warningTxt.setX(445);
        warningTxt.setY(140);
        warningTxt.setWrappingWidth(200);
        warningTxt.setTextAlignment(TextAlignment.CENTER);
        warningTxt.setFill(Color.rgb(255,0,0));
        root.addRow(9,warningTxt);
        Button loginbutton = new Button("LOGIN");
        loginbutton.setOnAction(event -> {
            String getMailId = mailid.getText();
            String getPw = pw.getText();

            if(getMailId.isEmpty() || getPw.isEmpty())
                warningTxt.setText("Field's can't be empty");
            else{
                DatabaseConnection connectNow = new DatabaseConnection();
                Connection connectDB = connectNow.getConnection();
                String connectQuery = "SELECT count(1) FROM userlogin WHERE userEmail = '"+ getMailId +"' AND userPassword = '"+ getPw +"'";

                try {
                    Statement statement = connectDB.createStatement();
                    ResultSet queryOutput = statement.executeQuery(connectQuery);

                    while (queryOutput.next()) {
                        if (queryOutput.getInt(1) == 1){
                            warningTxt.setText("Successful login");
                            stage.close();

                            //calling instance of loginbuttonmethod
                            loginbuttonmethod();
                        }
                        else warningTxt.setText("Invalid logincredentials");
                    }
                }
                catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        Button register=new Button("REGISTER");
        register.setOnAction(event ->{
            registermethod();
        });
        root.addRow(0, tit);
        root.addRow(7, id, mailid);
        root.addRow(8, password, pw);
        root.setAlignment(Pos.CENTER);
        root.add(loginbutton, 0,13);
        root.add(register,1,13);
        stage.setTitle("AIRLINE RESERVATION");
        FileInputStream input = new FileInputStream("src/main/resources/pexels-anni-roenkae-4175070.jpg");
        Image image = new Image(input);
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.RIGHT, 0, true, Side.TOP, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
        // create Background
        Background background = new Background(backgroundimage);

        // set background
        root.setBackground(background);
        root.setStyle("-fx-padding: 20;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 10;"
                + "-fx-border-radius: 10;"
                + "-fx-border-color: black;");
        stage.setScene(scene);
        stage.show();

    }

    private void registermethod() {
        Stage st = new Stage();
        GridPane root = new GridPane();
        root.setMinSize(400, 200);
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setHgap(5);
        root.setVgap(5);
        Scene scene = new Scene(root, 600, 600);
        DatePicker checkInDatePicker = new DatePicker();
        Label tit = new Label("REGISTRATION!!!");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
        tit.setFont(font);
        tit.setTextFill(Color.CHOCOLATE);
        tit.setScaleX(1);
        tit.setScaleY(2);
        Label username = new Label("USERNAME:");
        Font font2 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        username.setFont(font2);
        TextField t1 = new TextField();
        t1.setPrefWidth(80);
        Label email = new Label("EMAIL ID:");
        Font font3 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        email.setFont(font3);
        TextField t2 = new TextField();
        t2.setPrefWidth(80);
        Label date = new Label("DATE OF BIRTH:");
        Font font4 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        date.setFont(font4);
        Label age = new Label("AGE:");
        TextField t4 = new TextField();
        Font font5 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        age.setFont(font5);
        t4.setPrefWidth(80);
        Label password = new Label("PASSWORD:");
        Font font6 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        password.setFont(font6);
        TextField d4 = new TextField();
        d4.setPrefWidth(80);
        Text warning = new Text();
        warning.setX(445);
        warning.setY(140);
        warning.setWrappingWidth(200);
        warning.setFill(Color.rgb(255,0,0));
        Button registersubmit = new Button("SUBMIT AND LOGIN");
        Font font10 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        registersubmit.setFont(font10);
        registersubmit.setOnAction(e ->

      {
          String getUsername = t1.getText();
          String getEmail = t2.getText();
          Integer getAge = Integer.parseInt(t4.getText());
          String getPassword = d4.getText();
          LocalDate localDate = checkInDatePicker.getValue();
          String getDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));

          boolean validEmail = isValidEmail(getEmail);
          boolean validPassword = isValidPassword(getPassword);


          System.out.println(getUsername + getPassword + getEmail + getAge + getDate + validPassword + validEmail);

          try {
              if (validEmail && validPassword) {
                  DatabaseConnection connectNow = new DatabaseConnection();
                  Connection connectDB = connectNow.getConnection();
                  String connectQuery = " INSERT INTO userlogin(userName, userEmail, userDOB, userAge, userPassword)"
                          + " VALUES (?, ?, ?, ?, ?)";

                  PreparedStatement preparedStatement = connectDB.prepareStatement(connectQuery);
                  preparedStatement.setString(1, getUsername);
                  preparedStatement.setString(2, getEmail);
                  preparedStatement.setString(3, getDate);
                  preparedStatement.setInt(4, getAge);
                  preparedStatement.setString(5, getPassword);

                  preparedStatement.execute();

                  connectDB.close();

                  warning.setText("Successfully registered");
                  st.close();
                  stage.show();

              } else if (!validEmail && !validPassword){
                  warning.setText("Fill valid email and password");
              } else if (!validEmail) {
                  warning.setText("Fill valid email");
              } else if (!validPassword) {
                  warning.setText("Password should meet the required condition");
              }
          }
          catch (SQLException sqlException)
          {
              sqlException.printStackTrace();
              warning.setText("Input Credentials already found");
          }
      });
        root.addRow(0, tit);
        root.addRow(2, username, t1);
        root.addRow(3, email, t2);
        root.addRow(4, date, checkInDatePicker);
        root.addRow(8, age, t4);
        root.addRow(10, password, d4);
        root.addRow(11,warning);
        root.setAlignment(Pos.CENTER);
        root.add(registersubmit, 0, 15);
        st.setTitle("TICKET BOOKING");
        BackgroundFill bgfill = new BackgroundFill(Color.PALEGOLDENROD, CornerRadii.EMPTY, Insets.EMPTY);
        Background bgcolor = new Background(bgfill);
        root.setBackground(bgcolor);
            /*FileInputStream input = new FileInputStream("src/main/resources/pexels-anni-roenkae-4175070.jpg");
            Image image = new Image(input);
            BackgroundImage backgroundimage = new BackgroundImage(image,
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                    new BackgroundPosition(Side.RIGHT, 0, true, Side.TOP, 0, true),
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
            // create Background
            Background background = new Background(backgroundimage);

            // set background
            root.setBackground(background);*/
        root.setStyle("-fx-padding: 20;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 10;"
                + "-fx-border-radius: 10;"
                + "-fx-border-color: black;");
        st.setScene(scene);
        st.show();
    }

    private void loginbuttonmethod() {
        Stage st = new Stage();
        GridPane root = new GridPane();
        root.setMinSize(400, 200);
        root.setPadding(new

                Insets(10, 10, 10, 10));
        root.setHgap(5);
        root.setVgap(5);
        Scene scene = new Scene(root, 600, 600);
        DatePicker checkInDatePicker = new DatePicker();
        Label tit = new Label("WELCOME TO AIRLINE RESERVATION!!!");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
        tit.setFont(font);
        tit.setTextFill(Color.CHOCOLATE);
        tit.setScaleX(1);
        tit.setScaleY(2);
        Label from = new Label("FROM");
        Font font2 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        from.setFont(font2);
        TextField t1 = new TextField();
        t1.setPrefWidth(80);
        Label des = new Label("DESTINATION");
        Font font3 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        des.setFont(font3);
        TextField t2 = new TextField();
        t2.setPrefWidth(80);
        Label date = new Label("DATE");
        Font font4 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        date.setFont(font4);
        Label res = new Label("RESERVATION DETAILS");
        Font font1 = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
        res.setFont(font1);
        res.setTextFill(Color.CHOCOLATE);
        res.setScaleX(1);
        res.setScaleY(2);
        Label name = new Label("PASSENGER 1 NAME");
        TextField t4 = new TextField();
        Font font5 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        name.setFont(font5);
        t4.setPrefWidth(80);
        Label name2 = new Label("PASSENGER 2 NAME");
        Font font6 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        name2.setFont(font6);
        TextField d4 = new TextField();
        d4.setPrefWidth(80);
        Label name3 = new Label("PASSENGER 3 NAME");
        Font font7 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        name3.setFont(font7);
        TextField c4 = new TextField();
        c4.setPrefWidth(80);
        Label pd1 = new Label("PASSPORT NUMBER");
        Font font11 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        pd1.setFont(font11);
        TextField p = new TextField();
        p.setPrefWidth(80);
        Label pd2 = new Label("PASSPORT NUMBER");
        Font font12 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        pd2.setFont(font12);
        TextField s = new TextField();
        s.setPrefWidth(80);
        Label pd3 = new Label("PASSPORT NUMBER");
        Font font13 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        pd3.setFont(font13);
        TextField q = new TextField();
        q.setPrefWidth(80);
        Label ticket = new Label("NO.OF.TICKETS");
        Font font8 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        ticket.setFont(font8);
        TextField t5 = new TextField();
        t5.setPrefWidth(80);
        Label type = new Label("TICKET TYPE");
        Font font9 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        type.setFont(font9);
        ComboBox<String> combo = new ComboBox<String>();
        ObservableList<String> list = combo.getItems();
        list.add("Business");
        list.add("Economic");
        list.add("Premium");
        HBox hbox = new HBox(15);
        Scene scene2 = new Scene(hbox, 240, 120, Color.BEIGE);
        hbox.setPadding(new

                Insets(10, 10, 10, 10));
        hbox.getChildren().

                addAll(type, combo);

        Button submit = new Button("SUBMIT AND GET TICKET");
        Font font10 = Font.font("verdana", FontWeight.SEMI_BOLD, FontPosture.REGULAR, 12);
        submit.setFont(font10);
        submit.setOnAction(e ->
        {
            String getFrom = t1.getText();
            String getDestination = t2.getText();
            LocalDate localDate = checkInDatePicker.getValue();
            String getDate = localDate.format(DateTimeFormatter.ofPattern("dd-MM-YY"));
            String getDay = localDate.getDayOfWeek().toString();
            Integer getNoOfTickets = Integer.parseInt(t5.getText());
            String getPassengerName1 = t4.getText();
            String getPassportNo1 = p.getText();
            String getPassengerName2 = d4.getText();
            String getPassportNo2 = s.getText();
            String getPassengerName3 = c4.getText();
            String getPassportNo3 = q.getText();
            String getCombo = combo.getSelectionModel().getSelectedItem();

            System.out.println(getFrom + getDestination + getDate + getDay + getNoOfTickets + getPassengerName1 + getPassportNo1 + getPassengerName2 + getPassportNo2 + getPassengerName3 + getPassportNo3 + getCombo);

            if(!getFrom.isEmpty()&&!getDestination.isEmpty()&&(getNoOfTickets != null)&&!getPassengerName1.isEmpty()&&!getPassportNo1.isEmpty()&&!getCombo.isEmpty())
            {
                try{
                    DatabaseConnection connectNow = new DatabaseConnection();
                    Connection connectDB = connectNow.getConnection();
                    String connectQuery = "INSERT INTO reservationdetails (fromPlace,destination,noOfTickets,passenger1,passportNum1,passenger2,passportNum2,passenger3,passportNum3,ticketType,date,day)" + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";

                    PreparedStatement preparedStatement = connectDB.prepareStatement(connectQuery);
                    preparedStatement.setString(1, getFrom);
                    preparedStatement.setString(2, getDestination);
                    preparedStatement.setInt(3, getNoOfTickets);
                    preparedStatement.setString(4, getPassengerName1);
                    preparedStatement.setString(5,getPassportNo1);

                    preparedStatement.setString(10,getCombo);
                    preparedStatement.setString(11,getDate);
                    preparedStatement.setString(12,getDay);
                    if(getNoOfTickets == 3) {
                        preparedStatement.setString(6, getPassengerName2);
                        preparedStatement.setString(7, getPassportNo2);
                        preparedStatement.setString(8, getPassengerName3);
                        preparedStatement.setString(9, getPassportNo3);
                    }
                    else if(getNoOfTickets == 2)
                    {
                        preparedStatement.setString(6, getPassengerName2);
                        preparedStatement.setString(7, getPassportNo2);
                        preparedStatement.setString(8, null);
                        preparedStatement.setString(9, null);
                    }
                    else if(getNoOfTickets == 1)
                    {
                        preparedStatement.setString(6, null);
                        preparedStatement.setString(7,null);
                        preparedStatement.setString(8, null);
                        preparedStatement.setString(9,null);
                    }

                    preparedStatement.execute();

                    connectDB.close();
                    //st.close();
                    submitmethod();
                    st.close();
                }catch (SQLException sqlException)
                {
                    sqlException.printStackTrace();
                }
            }
            else System.out.println("Field's can't be empty");

            //submitmethod();

        });
        /*MenuBar bar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem open = new MenuItem("open");
        MenuItem save = new MenuItem("save");
        MenuItem copy = new MenuItem("copy");
        Menu Edit = new Menu("Edit");
        MenuItem cut = new MenuItem("cut");
        fileMenu.getItems().addAll(open, save);
        Edit.getItems().addAll(cut, copy);
        bar.getMenus().addAll(fileMenu, Edit);
        VBox v = new VBox(bar);
        Scene scene3 = new Scene(v, 320, 240);*/
        root.addRow(0, tit);
        root.addRow(2, from, t1);
        root.addRow(3, des, t2);
        root.addRow(4, date, checkInDatePicker);
        root.addRow(6, ticket, t5);
        root.addRow(7, res);
        root.addRow(8, name, t4);
        root.addRow(10, name2, d4);
        root.addRow(12, name3, c4);
        root.addRow(9, pd1, p);
        root.addRow(11, pd2, s);
        root.addRow(13, pd3, q);
        root.addRow(14, type, hbox);
        root.setAlignment(Pos.CENTER);
        root.add(submit, 0, 15);
        st.setTitle("TICKET BOOKING");
        BackgroundFill bgfill = new BackgroundFill(Color.PALEGOLDENROD, CornerRadii.EMPTY, Insets.EMPTY);
        Background bgcolor = new Background(bgfill);
        root.setBackground(bgcolor);
        hbox.setBackground(bgcolor);
        /*FileInputStream input = new FileInputStream("src/main/resources/pexels-anni-roenkae-4175070.jpg");
        Image image = new Image(input);
        BackgroundImage backgroundimage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.RIGHT, 0, true, Side.TOP, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
        // create Background
        Background background = new Background(backgroundimage);*/
        root.setStyle("-fx-padding: 20;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 10;"
                + "-fx-border-radius: 10;"
                + "-fx-border-color: black;");
        st.setScene(scene);
        st.show();
    }

    private void submitmethod() {
        Stage local=new Stage();
        AnchorPane root = new AnchorPane();
        Scene scene = new Scene(root, 600, 600);
        local.setTitle("RESERVATION COMPLETION");
        BackgroundFill bgfill = new BackgroundFill(Color.PALEGOLDENROD, CornerRadii.EMPTY, Insets.EMPTY);
        Background bgcolor = new Background(bgfill);
        root.setBackground(bgcolor);
        root.setStyle("-fx-padding: 20;"
                + "-fx-border-style: solid inside;"
                + "-fx-border-width: 2;"
                + "-fx-border-insets: 10;"
                + "-fx-border-radius: 10;"
                + "-fx-border-color: black;");

        Rectangle rectangle = new Rectangle(300,425);
        rectangle.setX(150);
        rectangle.setY(100);
        rectangle.setArcWidth(25);
        rectangle.setArcHeight(25);
        rectangle.setFill(Color.rgb(255,255,255));
        rectangle.setEffect(new DropShadow());
        Label title = new Label("Deccan Airlines");
        title.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        title.setLayoutX(175);
        title.setLayoutY(145);
        Label fromLabel = new Label("From");
        fromLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
        fromLabel.setLayoutX(175);
        fromLabel.setLayoutY(200);
        Label destinationLabel = new Label("To");
        destinationLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
        destinationLabel.setLayoutX(400);
        destinationLabel.setLayoutY(200);
        Text fromValueTxt = new Text("Coimbatore");
        fromValueTxt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        fromValueTxt.setLayoutX(175);
        fromValueTxt.setLayoutY(240);
        fromValueTxt.setWrappingWidth(100);
        fromValueTxt.setTextAlignment(TextAlignment.LEFT);
        Text destinationValueTxt = new Text("Chennai");
        destinationValueTxt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        destinationValueTxt.setLayoutX(316);
        destinationValueTxt.setLayoutY(240);
        destinationValueTxt.setWrappingWidth(100);
        destinationValueTxt.setTextAlignment(TextAlignment.RIGHT);
        Line designline1 = new Line(175,260,420,260);
        Label passengerCountLabel = new Label("Passenger count:");
        passengerCountLabel.setLayoutX(175);
        passengerCountLabel.setLayoutY(275);
        passengerCountLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
        Text passengerCountValueTxt = new Text("3");
        passengerCountValueTxt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
        passengerCountValueTxt.setX(295);
        passengerCountValueTxt.setY(287);
        passengerCountValueTxt.setWrappingWidth(10);
        passengerCountValueTxt.setTextAlignment(TextAlignment.LEFT);
        Text passenger1Txt = new Text("Kavya");
        passenger1Txt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        passenger1Txt.setX(175);
        passenger1Txt.setY(330);
        passenger1Txt.setWrappingWidth(80);
        passenger1Txt.setTextAlignment(TextAlignment.LEFT);
        Text passenger2Txt = new Text("Kaviya");
        passenger2Txt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        passenger2Txt.setX(255);
        passenger2Txt.setY(330);
        passenger2Txt.setWrappingWidth(80);
        passenger2Txt.setTextAlignment(TextAlignment.CENTER);
        Text passenger3Txt = new Text("Jeeva");
        passenger3Txt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        passenger3Txt.setX(335);
        passenger3Txt.setY(330);
        passenger3Txt.setWrappingWidth(80);
        passenger3Txt.setTextAlignment(TextAlignment.RIGHT);
        Text passport1 = new Text("12345678");
        passport1.setX(175);
        passport1.setY(345);
        passport1.setTextAlignment(TextAlignment.LEFT);
        passport1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 8));
        passport1.setWrappingWidth(60);
        Text passport2 = new Text("12345678");
        passport2.setX(265);
        passport2.setY(345);
        passport2.setTextAlignment(TextAlignment.CENTER);
        passport2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 8));
        passport2.setWrappingWidth(60);
        Text passport3 = new Text("12345678");
        passport3.setX(354);
        passport3.setY(345);
        passport3.setTextAlignment(TextAlignment.RIGHT);
        passport3.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 8));
        passport3.setWrappingWidth(60);
        Line designline2 = new Line(175,370,420,370);
        Label classLabel = new Label("Class");
        classLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
        classLabel.setLayoutX(175);
        classLabel.setLayoutY(385);
        Text classValueTxt = new Text("Economic");
        classValueTxt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        classValueTxt.setLayoutX(175);
        classValueTxt.setLayoutY(420);
        classValueTxt.setWrappingWidth(100);
        classValueTxt.setTextAlignment(TextAlignment.LEFT);
        Label bookingIdLabel = new Label("Booking\nId");
        bookingIdLabel.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
        bookingIdLabel.setLayoutX(175);
        bookingIdLabel.setLayoutY(435);
        Text bookingIdValueTxt = new Text("999");
        bookingIdValueTxt.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 14));
        bookingIdValueTxt.setLayoutX(175);
        bookingIdValueTxt.setLayoutY(480);
        bookingIdValueTxt.setWrappingWidth(50);
        bookingIdValueTxt.setTextAlignment(TextAlignment.LEFT);
        Rectangle rectangle2 = new Rectangle(90,50);
        rectangle2.setFill(Color.rgb(0,0,0,0.9));
        rectangle2.setX(332);
        rectangle2.setY(390);
        rectangle2.setArcHeight(15);
        rectangle2.setArcWidth(15);
        Text dateDay = new Text("12-01-23\nWEDNESDAY");
        dateDay.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 11));
        dateDay.setTextAlignment(TextAlignment.CENTER);
        dateDay.setX(345);
        dateDay.setY(413);
        dateDay.setFill(Color.rgb(255,255,255));
        root.getChildren().addAll(rectangle,title,fromLabel,destinationLabel,fromValueTxt,destinationValueTxt,designline1,passengerCountLabel,passengerCountValueTxt,passenger1Txt,passenger2Txt,passenger3Txt,passport1,passport2,passport3,designline2,classLabel,classValueTxt,bookingIdLabel,bookingIdValueTxt,rectangle2,dateDay);

        //Database connection for retrieval
        try{
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = connectNow.getConnection();

            String connectQuery = "SELECT * FROM reservationdetails ORDER BY reservationId DESC;";
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);
            int count =0;
            while (queryOutput.next())
            {
                if(count == 1) break;
                count++;
                Integer getbookingId = queryOutput.getInt(1);
                String getfrom = queryOutput.getString(2);
                String getDestination = queryOutput.getString(3);
                Integer getPassengercount = queryOutput.getInt(4);
                String getPassenger1 = queryOutput.getString(5);
                String getPassport1 = queryOutput.getString(6);
                String getPassenger2 = queryOutput.getString(7);
                String getPassport2 = queryOutput.getString(8);
                String getPassenger3 = queryOutput.getString(9);
                String getPassport3 = queryOutput.getString(10);
                String getClass = queryOutput.getString(11);
                String getDateDay = queryOutput.getString(12) + "\n" + queryOutput.getString(13);
                //System.out.println(getfrom+getDestination+getPassengercount+getPassenger1+getPassport1+getPassport1+getPassenger2+getPassport2+getPassenger3+getPassport3+getDateDay);

                fromValueTxt.setText(getfrom);
                destinationValueTxt.setText(getDestination);
                passengerCountValueTxt.setText(String.valueOf(getPassengercount));
                passenger1Txt.setText(getPassenger1);
                passenger2Txt.setText(getPassenger2);
                passenger3Txt.setText(getPassenger3);
                passport1.setText(getPassport1);
                passport2.setText(getPassport2);
                passport3.setText(getPassport3);
                classValueTxt.setText(getClass);
                bookingIdValueTxt.setText(String.valueOf(getbookingId));
                dateDay.setText(getDateDay);

            }

            connectDB.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        local.setScene(scene);
        local.show();
        }

    /*private void newmethod() {
        Stage l=new Stage();
        GridPane root = new GridPane();
        root.setMinSize(100, 100);
        root.setPadding(new Insets(30, 30, 30, 30));
        root.setHgap(5);
        root.setVgap(5);
        Scene scene = new Scene(root, 600, 600);
        //Label res=new Label();
        Label tit = new Label("THANK YOU!!!");
        Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
        tit.setFont(font);
        tit.setTextFill(Color.MIDNIGHTBLUE);
        tit.setScaleX(1);
        tit.setScaleY(2);
        tit.setEffect(new Reflection());
        tit.setAlignment(Pos.CENTER);
        root.addRow(0, tit);
        l.setScene(scene);
        l.show();
    }*/

    public boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);

        String actualDomain = "kongu.edu";//TODO string index out of bound Exception arises during email validation at sometimes
        String actualDomain2= "gmail.com";
        boolean domainState = false;
        if(!email.isEmpty()) {
            String domain = email.substring(email.length() - actualDomain.length());
            if(domain.equals(actualDomain) && matcher.matches())
                domainState = true;
            else if(domain.equals(actualDomain2)&& matcher.matches())
                domainState = true;
        }

        return domainState;
    }

    public boolean isValidPassword(String password)
    {
        //password should be morethan 12 characters

        boolean testlength = false;

        if(password.length() >= 12) testlength = true;

        if(testlength) return true;
        else return false;

    }
    public static void main(String[] args) {
        launch(args);
    }
