package com.example.projectnetwork;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class QuizMaker extends Application {

    private static String namez;
    final static List<Question> qtovopr = new ArrayList<>();


    public String getName() {
        return namez;
    }

    public void setName(String name) {
        this.namez = name;
    }

    public void addQuestion(Question qar) {
        qtovopr.add(qar);
    }

    public QuizMaker loadFromFile(String flu) throws InvalidQuizFormatException, Exception {
        List<String> sad = new ArrayList<>();
        QuizMaker quizhop = new QuizMaker();

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
    int ioe = -1;
    String parol;
    String pazh;
    List<String> list = new ArrayList<>();
    ArrayList<String> pne = new ArrayList<>();
    @Override
    public void start(Stage stage) throws Exception {
        QuizMaker usequiz = new QuizMaker();
        stage.getIcons().add(new Image("C:\\Users\\ibrah\\IdeaProjects\\ProjectFX\\src\\main\\java\\com\\example\\projectfx\\k.png"));
        if (ioe == -1) {
            stage.setTitle("Kahoot!!");
            File filesalam = new File("C:\\Users\\ibrah\\IdeaProjects\\ProjectFX\\src\\main\\java\\com\\example\\projectfx\\kahoot_music.mp3");
            Media mediibra = new Media(filesalam.toURI().toString());

            Pane sc1 = new Pane();
            ImageView sim = new ImageView(new File("C:\\Users\\ibrah\\IdeaProjects\\ProjectFX\\src\\main\\java\\com\\example\\projectfx\\1.jpg").toURI().toString());
            sim.setFitWidth(400);
            sim.setFitHeight(280);
            sim.setLayoutX(20);
            sim.setLayoutY(14);
            Text textclient = new Text("Введите пароль для участников:");
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
                    parol = gamer.getText();
                }
            });

            Button zap = new Button("Open Server");
            zap.setLayoutX(353);
            zap.setLayoutY(535);

            Button outer = new Button("Exit");
            outer.setTextFill(Color.WHITE);
            outer.setStyle("-fx-background-color:red;" + "-fx-background-radius: 60em; " + "-fx-min-width: 40px; " + "-fx-min-height: 40px; " + "-fx-max-width: 40px; " + "-fx-max-height: 40px;");
            outer.setLayoutX(12);
            outer.setLayoutY(525);
            outer.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    stage.close();
                }
            });

            sc1.getChildren().addAll(wel, sim, glb, zap, outer, gamer, textclient);
            Scene scenibra = new Scene(sc1, 443, 570);
            stage.setScene(scenibra);
            stage.show();
            new Thread(()-> {

            }).start();
            glb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    if (parol != null) {
                        FileChooser fileChoosertr = new FileChooser();
                        File filennei = fileChoosertr.showOpenDialog(stage);
                        if (filennei != null) {
                            try {
                                pazh = filennei.getAbsolutePath();
                                usequiz.loadFromFile(pazh);

                                for (int i = 0; i < qtovopr.size(); i++) {
                                    pne.add(qtovopr.get(i).toString()+"\n");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            } catch (InvalidQuizFormatException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Text tt1rex = new Text("File not found");
                            tt1rex.setLayoutX(187);
                            tt1rex.setLayoutY(410);
                            tt1rex.setFill(Color.RED);
                            sc1.getChildren().add(tt1rex);

                        }
                    }
                }
            });

            zap.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        ServerSocket serverSocket = new ServerSocket(8081);
                        while (true){
                            Socket socket = serverSocket.accept();
                            Thread thread = new Thread(new ClientHandler(socket));
                            thread.start();

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    public static void mainzap(String[] args) throws Exception {
        launch();
    }

    private class ClientHandler implements Runnable {
        Socket socket;
        public ClientHandler(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            try {
                int scolco = 0;
                ObjectOutputStream obj = new ObjectOutputStream(socket.getOutputStream());
                obj.writeUTF(parol);
                obj.writeUTF(pazh);
                obj.writeObject(pne);
                obj.flush();
                ObjectInputStream inj = new ObjectInputStream(socket.getInputStream());
                String[] br;
                int[] ar;
                String name;

                br = (String[]) inj.readObject();
                ar = (int[]) inj.readObject();
                name =inj.readUTF();

                for (int j = 0; j < qtovopr.size(); j++) {
                    String otvi = qtovopr.get(j).getDescription();
                    if (otvi.contains("{blank}")) {
                        Fillin fillinsr = (Fillin)qtovopr.get(j);
                        if (br[j] != null) {
                            if (br[j].equals(fillinsr.getAnswer())) {
                                scolco++;
                            }
                        }
                    } else {
                        Test testvyy = (Test) qtovopr.get(j);
                        if (ar[j] != -1) {
                            if (testvyy.getOptionAt(ar[j]).equals(testvyy.getAnswer())) {
                                scolco++;
                            }
                        }
                    }
                }
                list.add(name + ": " + scolco + "/" + qtovopr.size() + " - ");
                obj.writeObject(list);
                obj.write(scolco);
                obj.flush();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }}}