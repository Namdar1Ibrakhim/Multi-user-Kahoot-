package com.example.projectnetwork;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Server extends Application {
    private static String namez;
    final static List<Question> qtovopr = new ArrayList<>();
    public Stage stage;
    int parol;

    public String getName() {
        return namez;
    }

    public void setName(String name) {
        this.namez = name;
    }

    public void addQuestion(Question qar) {
        qtovopr.add(qar);
    }

    public Server loadFromFile(String flu) throws InvalidQuizFormatException, Exception {
        List<String> sad;
        Server quizhop = new Server();

        try {
            if (flu == null || flu == "") throw new InvalidQuizFormatException();
            File aqwr = new File(flu);
            if (!aqwr.isFile()) throw new InvalidQuizFormatException();
            setName(aqwr.getName());
            Scanner inp = new Scanner(aqwr);
            String pr = "";

            if (!inp.hasNextLine()) throw new InvalidQuizFormatException();

            while (inp.hasNextLine()) {
                String dd = inp.nextLine();
                pr += (dd + "\n");
            }

            inp.close();
            if (pr == "" || pr == null) throw new InvalidQuizFormatException();

            sad = Arrays.asList(pr.split("\n\n"));
            Collections.shuffle(sad);

            for (int i = 0; i < sad.size(); i++) {
                String[] qeq = sad.get(i).split("\n");

                if (qeq[0].contains("{blank}")) {
                    Fillin fillerq = new Fillin();
                    fillerq.setDescription(qeq[0]);
                    fillerq.setAnswer(qeq[1]);
                    quizhop.addQuestion(fillerq);

                } else {
                    Test testrre = new Test(qeq.length - 1);

                    testrre.setDescription(qeq[0]);
                    testrre.setAnswer(qeq[1]);

                    String rok = "";
                    for (int j = 1; j < qeq.length; j++) {
                        rok += qeq[j] + "\n";
                    }
                    String[] oper = rok.split("\n");
                    testrre.setOptions(oper);
                    quizhop.addQuestion(testrre);
                }
            }

        } catch (InvalidQuizFormatException e) {
            System.out.println("Error!");
        }
        return quizhop;
    }

    public String toString() {
        if (qtovopr.size() != 0) {
            String quizne = "";
            quizne = getName();
            quizne = quizne.replace(".txt", "");
            this.namez = quizne;
            return quizne;
        } else {
            return null;
        }
    }
    List<String> ote = new ArrayList<>();
    public Scene lastScene() {
        for (Socket sce: sockets) {
            try {
                DataInputStream in = new DataInputStream(sce.getInputStream());
                ote.add(in.readUTF());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(ote);
        StackPane stackPane = new StackPane();
        VBox vBox = new VBox();
        for (int i = 0; i < ote.size(); i++) {
            vBox.getChildren().add(new Text(ote.get(i)));
        }
        stackPane.getChildren().add(vBox);
        Scene scene = new Scene(stackPane, 443, 570);
        return scene;
    }
    public Scene ballscen(int iio){
        String addas = "";
        for (Socket sce: sockets) {
            try {
                DataInputStream in = new DataInputStream(sce.getInputStream());
                String ase = in.readUTF();
                addas+=ase+"\n";

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        final int[] ii = {iio};
        final int[] seco = {4};
        Label kik = new Label("00:30");
        KeyFrame ktr = new KeyFrame(Duration.millis(1000), event -> {
            kik.setText(String.format("00:%02d", --seco[0]));
            if (kik.getText().equals("00:05")) {
                kik.setTextFill(Color.RED);
            }
            if (seco[0] == 0) {
                try {
                    if (ii[0] < qtovopr.size()-1){
                        for (Socket sce: sockets) {
                            DataOutputStream out = new DataOutputStream(sce.getOutputStream());
                            String as = "nex";
                            out.writeUTF(as);
                        }
                        ii[0]++;
                        stage.setScene(igra(ii[0]));
                    } else {
                        stage.setScene(lastScene());

                    }
                }catch (Exception e){

                } catch (InvalidQuizFormatException e) {
                    e.printStackTrace();
                }
            }});
        Timeline t = new Timeline(ktr);
        t.setCycleCount(4);
        t.play();
        StackPane stackPane = new StackPane();
        Text tt = new Text(addas);
        tt.setStyle("-fx-font-size: 30");
//        if(Integer.parseInt(addas.split(": ")[1])==0){
//            tt.setFill(Color.RED);
//        }else{
//            tt.setFill(Color.GREEN);
//        }
        stackPane.getChildren().add(tt);
        Scene scene = new Scene(stackPane, 443, 570);
        return scene;
    }

    Timeline t;
    ///////////////////
    public Scene igra(int io) throws Exception, InvalidQuizFormatException {
        Scene scenee ;
        mPw.play();
        /**/ImageView ioo = new ImageView(new File("C:\\Users\\ibrah\\IdeaProjects\\ProjectFX\\src\\main\\java\\com\\example\\projectfx\\2.png").toURI().toString());
        ioo.setFitWidth(335);
        ioo.setFitHeight(184);
        ioo.setLayoutX(54);
        ioo.setLayoutY(14);

        Text kak12 = new Text();
        kak12.setLayoutX(40);
        kak12.setLayoutY(227);

        Rectangle[] kvadpr = new Rectangle[4];
        (kvadpr[0] = new Rectangle()).setFill(Color.RED);
        (kvadpr[1] = new Rectangle()).setFill(Color.BLUE);
        (kvadpr[2] = new Rectangle()).setFill(Color.ORANGE);
        (kvadpr[3] = new Rectangle()).setFill(Color.GREEN);
        for (int i = 0; i < kvadpr.length; i++) {
            kvadpr[i].setWidth(161);
            kvadpr[i].setHeight(120);
            kvadpr[i].setArcHeight(30);
            kvadpr[i].setArcWidth(30);
        }

        TextField textoh = new TextField();
        textoh.setLayoutX(113);
        textoh.setLayoutY(298);
        textoh.setPrefWidth(216);

        Button buttoes = new Button("Next");
        buttoes.setLayoutX(370);
        buttoes.setLayoutY(530);
        if (io == qtovopr.size() - 1) {
            buttoes.setText("Complete");
        }

        Text str4 = new Text();
        str4.setLayoutX(11);
        str4.setLayoutY(18);

        String otvi = qtovopr.get(io).getDescription();

        if (otvi.contains("{blank}")) {
            for (Socket sce: sockets) {
                DataOutputStream out = new DataOutputStream(sce.getOutputStream());
                out.writeUTF("f");
            }
            final int[] seco = {30};
            Label kik = new Label("00:30");
            KeyFrame ktr = new KeyFrame(Duration.millis(1000), event -> {
                kik.setText(String.format("00:%02d", --seco[0]));
                if (kik.getText().equals("00:05")) {
                    kik.setTextFill(Color.RED);
                }
                if (seco[0] == 0) {
                    stage.setScene(ballscen(io));
                    seco[0]=30;
                    t.stop();
                }});
            t = new Timeline(ktr);
            t.setCycleCount(30);
            t.play();


            HBox bl = new HBox(10);
            bl.setAlignment(Pos.CENTER);
            bl.getChildren().addAll(kik);
            bl.setLayoutX(210);
            bl.setLayoutY(543);
            System.out.println("Отправлен вопрос Fillin");
            Fillin fillkrch = (Fillin) qtovopr.get(io);

            for (Socket sce: sockets) {
                DataOutputStream out = new DataOutputStream(sce.getOutputStream());
                out.writeUTF(fillkrch.getAnswer());
            }
            System.out.println("Отправлен ответ");
            Pane nado = new Pane();
            kak12.setText(io + 1 + "). " + fillkrch.getDescription().replace("{blank}", "_____"));
            kak12.setWrappingWidth(400);

            str4.setText(io + 1 + "");

            nado.getChildren().addAll(ioo, kak12, textoh, buttoes, str4, bl);

            scenee = new Scene(nado, 443, 570);

            buttoes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.setScene(ballscen(io));
                    seco[0]=30;
                }
            });
        } else {
            for (Socket sce: sockets) {
                DataOutputStream out = new DataOutputStream(sce.getOutputStream());
                out.writeUTF("t");
            }
            final int[] seco = {30};
            Label kik = new Label("00:30");
            KeyFrame ktr = new KeyFrame(Duration.millis(1000), event -> {
                kik.setText(String.format("00:%02d", --seco[0]));
                if (kik.getText().equals("00:05")) {
                    kik.setTextFill(Color.RED);
                }
                if (seco[0] == 0) {
                    stage.setScene(ballscen(io));
                    seco[0]=30;
                    t.stop();
                }});
            t = new Timeline(ktr);
            t.setCycleCount(30);
            t.play();


            HBox bl = new HBox(10);
            bl.setAlignment(Pos.CENTER);
            bl.getChildren().addAll(kik);
            bl.setLayoutX(210);
            bl.setLayoutY(543);
            System.out.println("Отправлен вопрос Test");
            ImageView imageView12 = new ImageView(new File("C:\\Users\\ibrah\\IdeaProjects\\ProjectFX\\src\\main\\java\\com\\example\\projectfx\\logo.png").toURI().toString());
            imageView12.setFitWidth(335);
            imageView12.setFitHeight(184);
            imageView12.setLayoutX(54);
            imageView12.setLayoutY(14);

            Test testew = (Test) qtovopr.get(io);
            Pane paner = new Pane();
            kak12.setText(io + 1 + "). " + testew.getDescription());
            kak12.setWrappingWidth(400);
            GridPane grad = new GridPane();

            for (Socket sce: sockets) {
                DataOutputStream out = new DataOutputStream(sce.getOutputStream());
                out.writeInt(testew.labelsse.size());
                out.writeUTF(testew.getAnswer());

            }
            System.out.println("Отправлено кол-во вариантов ответов ");
            System.out.println("Отправлен ответ");

            ToggleGroup tog = new ToggleGroup();
            RadioButton[] raBut9 = new RadioButton[testew.labelsse.size()];
            for (int j = 0; j < raBut9.length; j++) {
                raBut9[j] = new RadioButton(testew.getOptionAt(j));
                raBut9[j].setWrapText(true);
                raBut9[j].setToggleGroup(tog);
                raBut9[j].setTextFill(Color.WHITE);
            }

            int clu = 0;
            for (int j = 0; j < 2; j++) {
                for (int o = 0; o < 2; o++) {
                    if (clu != testew.labelsse.size()) {
                        grad.add(kvadpr[clu], o, j);
                        grad.add(raBut9[clu], o, j);
                        clu++;
                    }
                }
            }
            String itv = "";
            for (int i = 0; i < testew.labelsse.size(); i++) {
                itv+=testew.getOptionAt(i)+ ";";
            }
            System.out.println(itv);
            for (Socket sce: sockets) {
                DataOutputStream out = new DataOutputStream(sce.getOutputStream());
                out.writeUTF(itv);
            }
            System.out.println("Вар отв");

            grad.setLayoutX(57);
            grad.setLayoutY(268);
            grad.setPrefWidth(354);
            grad.setPrefHeight(205);

            str4.setText(io + 1 + "");

            paner.getChildren().addAll(imageView12, kak12, grad, buttoes, str4, bl);
            scenee = new Scene(paner, 443, 570);

            buttoes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.setScene(ballscen(io));
                    seco[0]=30;
                }});
        }
        return scenee;
    }

    String nickgamers = "";
    int ioe = -1;
    MediaPlayer mPw;
    List<String> list = new ArrayList<>();
    Scene scenibra;
    String pathfile;
    String nick;
    ArrayList<Socket> sockets = new ArrayList<>();

    /////     ///////////////     ////////
    @Override
    public void start(Stage obwstage) throws Exception {
        /**/obwstage.getIcons().add(new Image("C:\\Users\\ibrah\\IdeaProjects\\ProjectFX\\src\\main\\java\\com\\example\\projectfx\\k.png"));
        if (ioe == -1) {
            obwstage.setTitle("Kahoot!!");
            /**/File filesalam = new File("C:\\Users\\ibrah\\IdeaProjects\\ProjectFX\\src\\main\\java\\com\\example\\projectfx\\kahoot_music.mp3");
            Media mediibra = new Media(filesalam.toURI().toString());
            mPw = new MediaPlayer(mediibra);

            Pane sc1 = new Pane();
            /**/ImageView sim = new ImageView(new File("C:\\Users\\ibrah\\IdeaProjects\\ProjectFX\\src\\main\\java\\com\\example\\projectfx\\1.jpg").toURI().toString());
            sim.setFitWidth(400);
            sim.setFitHeight(280);
            sim.setLayoutX(20);
            sim.setLayoutY(14);
            Text textclient = new Text("Введите пароль для участников   :");
            textclient.setLayoutX(180);
            textclient.setLayoutY(430);
            textclient.setFill(Color.BROWN);

            Button glb = new Button("Choose a file:");
            glb.setLayoutX(180);
            glb.setLayoutY(360);

            Text wel = new Text("Welcome to Kahoot!!");
            wel.setLayoutX(168);
            wel.setLayoutY(330);

            TextField gamer = new TextField();
            gamer.setLayoutX(150);
            gamer.setLayoutY(450);
            gamer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    parol = Integer.parseInt(gamer.getText());
                }
            });

            Button checkgame = new Button("GamerScore");
            checkgame.setLayoutX(180);
            checkgame.setLayoutY(520);
            checkgame.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    Rectangle rectangle = new Rectangle(300, 400);
                    rectangle.setLayoutX(70);
                    rectangle.setLayoutY(100);
                    rectangle.setFill(Color.WHITE);
                    rectangle.setStroke(Color.BLACK);
                    rectangle.setStrokeWidth(5);

                    Button ex = new Button("X");
                    ex.setStyle("-fx-background-color:red");
                    ex.setLayoutX(350);
                    ex.setLayoutY(98);

                    VBox top = new VBox();
                    top.setLayoutX(75);
                    top.setLayoutY(100);
                    if (list.size() != 0) {
                        for (int i = 0; i < list.size(); i++) {
                            top.getChildren().add(new Text((String) list.get(i)));
                        }
                    } else if (list.size() == 0) {
                        top.getChildren().add(new Text("Никто не проходил тест"));
                    }
                    ex.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            sc1.getChildren().removeAll(rectangle, ex, top);
                        }
                    });
                    sc1.getChildren().addAll(rectangle, ex, top);
                }
            });


            Button outer = new Button("Exit");
            outer.setTextFill(Color.WHITE);
            outer.setStyle("-fx-background-color:red;" + "-fx-background-radius: 60em; " + "-fx-min-width: 40px; " + "-fx-min-height: 40px; " + "-fx-max-width: 40px; " + "-fx-max-height: 40px;");
            outer.setLayoutX(12);
            outer.setLayoutY(525);
            outer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    obwstage.close();
                }
            });

            sc1.getChildren().addAll(wel, sim, glb, outer, gamer, textclient, checkgame);
            scenibra = new Scene(sc1, 443, 570);
            obwstage.setScene(scenibra);
            obwstage.show();

            glb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (parol != 0) {
                        FileChooser fileChoosertr = new FileChooser();
                        File filennei = fileChoosertr.showOpenDialog(obwstage);
                        if (filennei != null) {
                            try {
                                pathfile = filennei.getAbsolutePath();
                                ioe++;
                                start(obwstage);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Text tt1rex = new Text("File not found");
                            tt1rex.setLayoutX(187);
                            tt1rex.setLayoutY(410);
                            tt1rex.setFill(Color.RED);
                            sc1.getChildren().add(tt1rex);
                        }}
                }
            });
            //////////////      ///////////////

        } else {
            StackPane root = new StackPane();
            //Стек,
            root.setStyle("-fx-background-color: #3e147f");
            System.out.println("Ggg");
            BorderPane borderPanew = new BorderPane();

            Label lbl = new Label("Game PIN:\n" + parol);
            lbl.setTextFill(Color.WHITE); lbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));
            lbl.setAlignment(Pos.CENTER); lbl.setMinWidth(600);
            borderPanew.setTop(lbl);
            root.getChildren().addAll(borderPanew);
            //aa
            Button open = new Button("Start");
            open.setLayoutX(300);open.setLayoutY(400);
            root.getChildren().add(open);
            //.

            new Thread(() -> {
                //Поток
                try {
                    ServerSocket server = new ServerSocket(8008);
                    loadFromFile(pathfile);
                    //Сервер Сокет
                    int clientNo = 1;
                    while (true) {
                        try {
                            System.out.println("Waiting for incomes");
                            sockets.add(server.accept());
                            //Socket socket = server.accept();
                            System.out.println(clientNo + " Client is Connected!");
                            int finalClientNo = clientNo;
                            new Thread(() -> {
                                try {
                                    DataInputStream fromClient = new DataInputStream(sockets.get(finalClientNo -1).getInputStream());
                                    DataOutputStream toClient = new DataOutputStream(sockets.get(finalClientNo-1).getOutputStream());
                                    // while (true) {
                                    int clientPinkod = fromClient.readInt();
                                    ////
                                    if (clientPinkod != parol) {
                                        toClient.writeUTF("Wrong PIN!");
                                        System.out.println("Wrong PIN");
                                    } else {
                                        toClient.writeUTF("Success!");
                                        System.out.println("Success");
                                    }
                                    nick = fromClient.readUTF();
                                    nickgamers += nick + ", ";
                                    ////

                                    Label nLbl = new Label(nickgamers);nLbl.setTextFill(Color.WHITE);
                                    nLbl.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.ITALIC, 20));

                                    Platform.runLater(() -> { borderPanew.setCenter(nLbl);//

                                        open.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent actionEvent) {
                                                try {
                                                    for (Socket sc: sockets) {
                                                        DataOutputStream out = new DataOutputStream(sc.getOutputStream());
                                                        out.writeUTF("Helloo");
                                                        out.writeInt(qtovopr.size());
                                                    }
                                                    obwstage.setScene(igra(0));
                                                    stage = obwstage;

                                                } catch (InvalidQuizFormatException e) {
                                                    e.printStackTrace();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }});} );
                                    //
                                } catch (IOException e) {
                                }}).start();
                            clientNo++;

                        } catch (IOException e) {}
                    }
                } catch (IOException e) {} catch (InvalidQuizFormatException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }).start();

            obwstage.setScene(new Scene(root, 600, 600));
            //Запуск
            obwstage.setTitle("Server");
            obwstage.show();
        }
    }
}
