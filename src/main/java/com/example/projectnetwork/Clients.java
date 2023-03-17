package com.example.projectnetwork;

import javafx.animation.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Clients extends Application {
    private Stage windows;
    String clientname;
    private Socket socket;
    private DataOutputStream toServer; private DataInputStream fromServer;

    private void connectToServer() throws IOException {
        System.out.println("Сервак");
        socket = new Socket("localhost", 8008);
        //Socket
        toServer = new DataOutputStream(socket.getOutputStream());fromServer = new DataInputStream(socket.getInputStream());
    }

    public Button kahootButton() {
        Button btn = new Button();
        System.out.println("OO");
        btn.setMinWidth(600 / 2. - 5);btn.setMinHeight(600 / 2. - 5);

        btn.setTextFill(Color.WHITE);
        btn.setWrapText(true);btn.setPadding(new Insets(10));
//asljaso
        Font font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15);
        btn.setFont(font);return btn;
    }

    public Scene lastrez() {
        StackPane stackPane = new StackPane();
        int sum = 0;
        for (int i = 0; i < ball.length; i++) {
            sum+=ball[i];
        }
        try {
            toServer.writeUTF(clientname + ": " + sum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Text text = new Text("Your result: " + sum);
        stackPane.getChildren().addAll(text);
        Scene scene = new Scene(stackPane, 600, 600);
        return scene;

    }

    int[] ball;
    Timeline t;
    public Scene ballscen(){
        try {
            toServer.writeUTF( clientname + ":" +ball[ioe]);
            System.out.println(clientname + ":" +ball[ioe]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final int[] seco = {4};
        Label kik = new Label("00:30");
        KeyFrame ktr = new KeyFrame(Duration.millis(1000), event -> {
            kik.setText(String.format("00:%02d", --seco[0]));
            if (kik.getText().equals("00:05")) {
                kik.setTextFill(Color.RED);
            }
            if (seco[0] == 0) {
                try {
                    if (ioe<qtovoprsize-1){
                        System.out.println(fromServer.readUTF());

                        ioe++;
                        windows.setScene(igra());
                    } else {
                        windows.setScene(lastrez());

                    }
                }catch (Exception e){

                }
            }});
        Timeline t = new Timeline(ktr);
        t.setCycleCount(4);
        t.play();

        StackPane pane = new StackPane();
        Text text = new Text("+" + Integer.toString(ball[ioe]));
        if (ball[ioe] == 0) {
            text.setFill(Color.RED);
        }else {
            text.setFill(Color.GREEN);
        }
        text.setStyle("-fx-font-size: 32");

        pane.getChildren().addAll(text);
        Scene scene = new Scene(pane, 600, 600);
        return scene;
    }

    public Scene igra() throws IOException {
        System.out.println("Ehala");
        Scene scene;
        String arg = fromServer.readUTF();
        System.out.println(arg);

        int[] seco = {30};
        Label kik = new Label("00:30");
        KeyFrame ktr = new KeyFrame(Duration.millis(1000), event -> {
            kik.setText(String.format("00:%02d", --seco[0]));
            if (kik.getText().equals("00:05")) {
                kik.setTextFill(Color.RED);
            }
            if (seco[0] == 0) {
                windows.setScene(ballscen());
                seco[0] = 30;
                t.stop();

            }});

        t = new Timeline(ktr);
        t.setCycleCount(30);
        t.play();
        if(arg.equals("f")){
            System.out.println("Вопрос Fillin");
            String otv = fromServer.readUTF();
            System.out.println("Принят правльный ответ");

            StackPane stac = new StackPane();
            stac.setStyle("-fx-background-color: #3e147f");
            TextField th = new TextField();
            th.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if(th.getText().equals(otv)) {
                        ball[ioe] = 1000 * seco[0] / 30;
                    }
                    try {
//                                toServer.writeInt(ball[ioe]);
//                                System.out.println("Отправлено");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        windows.setScene(ballscen());
                        seco[0] = 30;
                    }catch (Exception e){

                    }

                }
            });


            stac.getChildren().addAll(th);
            scene = new Scene(stac, 600, 600);

        }else{
            System.out.println("Вопрос Test");
            int siz = fromServer.readInt();
            System.out.println("Размер " + siz);
            String otv = fromServer.readUTF();


            StackPane stac = new StackPane();
            Button[] buttons = new Button[4];
            for (int j = 0; j < buttons.length; j++) {
                buttons[j] = new Button();
                buttons[j].setMinWidth(600 / 2);
                buttons[j].setMinHeight(600 / 2);
                buttons[j].setTextFill(Color.WHITE);
                buttons[j].setWrapText(true);
                buttons[j].setPadding(new Insets(10));
                Font font = Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 15);
                buttons[j].setFont(font);
            }
            buttons[0].setStyle("-fx-background-color: red");
            buttons[1].setStyle("-fx-background-color: blue");
            buttons[2].setStyle("-fx-background-color: orange");
            buttons[3].setStyle("-fx-background-color: green");

            String lah = fromServer.readUTF();
            String[] indot =lah.split(";");
            final int[] zb = {0};
            for (int i = 0; i < buttons.length; i++) {
                int finalI = i;
                buttons[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {

                        if(zb[0] == 0){
                            if(indot[finalI].equals(otv)) {
                                ball[ioe] = 1000 * seco[0] / 30;
                            }
                            //toServer.writeInt(ball[ioe]);
                            //   System.out.println("Отправлено");
                            try {
                                zb[0]++;
                                windows.setScene(ballscen());
                                seco[0] = 30;

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

            int c = 0;
            GridPane pg = new GridPane();
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    if(c!=indot.length){
                        pg.add(buttons[c], k, j);
                        c++;
                    }}
            }

            stac.getChildren().addAll(pg);
            scene = new Scene(stac, 600, 600);
        }
        return scene;
    }

    int countClient;
    int qtovoprsize;
    int ioe = 0;
    public StackPane nicknamePane() {
        StackPane stack = new StackPane();
        TextField t = new TextField();
        t.setPromptText("Enter username");
        t.setMaxWidth(600 / 3);
        t.setMinHeight(40);
        t.setAlignment(Pos.CENTER);
        Button bt = new Button("Enter");
        bt.setMaxWidth(600 / 3);
        bt.setMinHeight(40);
        bt.setStyle("-fx-background-color:#333333");
        bt.setTextFill(Color.WHITE);
        bt.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 16));
        VBox vBo = new VBox(10);
        vBo.setMaxWidth(600 / 2);
        vBo.setMaxHeight(600 / 2);
        vBo.setAlignment(Pos.CENTER);
        vBo.getChildren().addAll(t, bt);

        stack.getChildren().addAll(vBo);
        stack.setStyle("-fx-background-color: #46178f");

        bt.setOnAction(e -> {
            try {
                toServer.writeUTF(t.getText());
                clientname = t.getText();
                Pane pane = new Pane();
                Text text = new Text("WAITING FOR PLAYERS...");
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1), text);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0);
                fadeTransition.setCycleCount(Animation.INDEFINITE);
                fadeTransition.setAutoReverse(true);
                fadeTransition.play();
                text.setLayoutX(600/2 -10);
                text.setLayoutY(600/2);
                pane.getChildren().addAll(text);
                windows.setScene(new Scene(pane,  600, 600));

                new Thread(()->{

                    try {
                        String a  = fromServer.readUTF();
                        System.out.println(a);

                        if (a.equals("Helloo")){
                            //Platform.runLater(()->{
                            System.out.println("Start gaming");
                            Platform.runLater(() -> {
                                try {
                                    qtovoprsize = fromServer.readInt();
                                    ball = new int[qtovoprsize];
                                    for (int i = 0; i < ball.length; i++) {
                                        ball[i] = 0;
                                    }
                                    System.out.println("Ketti");
                                    windows.setScene(igra());
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                }
                            });

                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }).start();


            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane stackPane = new StackPane();
        TextField tf = new TextField();
        tf.setPromptText("Game PIN");
        tf.setMaxWidth(600 / 3);
        tf.setMinHeight(40);
        tf.setAlignment(Pos.CENTER);
        Button btn = new Button("Enter");
        btn.setMaxWidth(600 / 3);
        btn.setMinHeight(40);
        btn.setStyle("-fx-background-color:#333333");
        btn.setTextFill(Color.WHITE);
        btn.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 16));
        VBox vBox = new VBox(10);
        vBox.setMaxWidth(600 / 2);
        vBox.setMaxHeight(600 / 2);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(tf, btn);
        Text text = new Text("");
        vBox.getChildren().add(text);

        stackPane.getChildren().addAll(vBox);
        stackPane.setStyle("-fx-background-color: #3e147f");

        btn.setOnAction(e -> {
            try {
                toServer.writeInt(Integer.parseInt(tf.getText()));
                String status = fromServer.readUTF();
                if (status.equals("Success!")) {
                    primaryStage.setScene(new Scene(nicknamePane(), 600, 600));
                    primaryStage.setTitle("Enter Nickname");

                }else{
                    text.setText("Wrong PIN");
                    text.setFill(Color.RED);
                    start(primaryStage);
                }
                System.out.println(status);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        windows = primaryStage;
        connectToServer();
        primaryStage.setScene(new Scene(stackPane, 600, 600));
        primaryStage.show();
        primaryStage.setTitle("Enter PIN!");




        //root.requestFocus();
    }
}


