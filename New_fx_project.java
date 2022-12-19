package baber;

//import java.awt.Color;
//import java.awt.Label;
import static java.awt.PageAttributes.ColorType.COLOR;
import java.util.Random;
import java.util.concurrent.Semaphore;
import javafx.application.Application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javafx.animation.TranslateTransition;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import static javafx.application.Application.launch;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Translate;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

/**
 *
 * @author USER
 */
public class New_fx_project extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, 900, 600,Color.CADETBLUE);

        
        Label l1=new Label();
        
      /*     Button btn = new Button();
        btn.setLayoutX(120);
        btn.setLayoutY(350);
        btn.setText("click here");
        */
        
        
        
        //text feild
        TextField ta =new TextField();

       ta.setLayoutX(140);
       ta.setLayoutY(100);
       ta.setPrefWidth(150);
       ta.setPrefHeight(50);
       
          TextField chairT =new TextField();

       chairT.setLayoutX(140);
       chairT.setLayoutY(250);
       chairT.setPrefWidth(150);
       chairT.setPrefHeight(50);
       
       
       
          TextField clientT =new TextField();

       clientT.setLayoutX(140);
       clientT.setLayoutY(400);
       clientT.setPrefWidth(150);
       clientT.setPrefHeight(50);
       
       
          TextField ta_sleepT =new TextField();
       ta_sleepT.setLayoutX(640);
       ta_sleepT.setLayoutY(109);
       ta_sleepT.setPrefWidth(150);
       ta_sleepT.setPrefHeight(50);
       
       
          TextField ta_workingT =new TextField();
       ta_workingT.setLayoutX(640);
       ta_workingT.setLayoutY(250);
       ta_workingT.setPrefWidth(150);
       ta_workingT.setPrefHeight(50);
       
       
          TextField client_watingT =new TextField();
       client_watingT.setLayoutX(640);
       client_watingT.setLayoutY(400);
       client_watingT.setPrefWidth(150);
       client_watingT.setPrefHeight(50);
       
        /*  TextField txt7 =new TextField();
       txt7.setLayoutX(640);
       txt7.setLayoutY(420);
       txt7.setPrefWidth(150);
       txt7.setPrefHeight(50);
       */
 
       
       
       
        
      /*  btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
            l1.setText("welcom MRS" +txtname.getText());
            }
        }); */
      
    /*  btn.setOnAction((ActionEvent) -> {
          l1.setText("welcom MRS" +txtname.getText());
      });*/
        
       // root.getChildren().add(btn);
        Label lb1 = new Label("Barbers");
        

        lb1.setLayoutX(50);
        lb1.setLayoutY(109);
        lb1.setTooltip(new Tooltip("no. of barbers you want"));
        lb1.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 22));
        
        
         Label chair = new Label("Chairs");

        chair.setLayoutX(50);
        chair.setLayoutY(250);
        chair.setTooltip(new Tooltip("no. of chairs you want"));
        chair.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 22));
        
        
         Label lb3 = new Label("clients");

        lb3.setLayoutX(50);
        lb3.setLayoutY(400);
        lb3.setTooltip(new Tooltip("no. of clients you want"));
        lb3.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 22));
        
        
        Label lb4 = new Label("Barbers "
                + "sleeping");

        lb4.setLayoutX(435);
        lb4.setLayoutY(109);
        lb4.setTooltip(new Tooltip("no. of barbers that are sleeping"));
        lb4.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 22));
        
        
        Label lb5 = new Label("Barbers"
                + "working");

        lb5.setLayoutX(450);
        lb5.setLayoutY(255);
        lb5.setTooltip(new Tooltip("no. of barbers that are working"));
        lb5.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 22));
        
        
        Label lb6 = new Label("clients waiting");

        lb6.setLayoutX(450);
        lb6.setLayoutY(405);
        lb6.setTooltip(new Tooltip("no. of clients that are waiting on chairs"));
        lb6.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 22));
              int numberofbA = Integer.parseInt(ta.getText());
                                 int numberofclient = Integer.parseInt(clientT.getText());
                                        int numberofchairs = Integer.parseInt(chairT.getText());
        
        
      /*  Label lb7 = new Label("clients come later");

        lb7.setLayoutX(410);
        lb7.setLayoutY(425);
        lb7.setTooltip(new Tooltip("no. of barbers you want"));
        lb7.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 22));
        */
        
        
         Button bt1=new Button("Run project");
         bt1.setLayoutX(330);
         bt1.setLayoutY(510);
         bt1.setPrefSize(200, 50);
         bt1.setTextFill(Color.BLACK);
         
          EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                          



             SignalSemaphore wakeup = new SignalSemaphore();
        Semaphore chairs = new Semaphore(numberofchairs);
        Semaphore available = new Semaphore(numberofbA);
        Random studentWait = new Random();

        System.out.println(numberofbA - available.availablePermits());

        for (int i = 0; i < numberofclient; i++) {

            Thread student = new Thread(new student(studentWait.nextInt(20), wakeup, chairs, available, i + 1));
            student.start();
        }
        for (int i = 0; i < numberofbA; i++) {

            // Create and start TA Thread.
            Thread ta = new Thread(new braberAssistant(wakeup, chairs, available,i + 1,numberofchairs));
            ta.start();
        }

        // int stdtCame = ((numberofbA - available.availablePermits()) + (numberofchairs - available.availablePermits()));

        System.out.println(numberofbA - available.availablePermits());
        System.out.println(available.availablePermits());
        //        System.out.println(numberofclient - stdtCame);
        System.out.println(Integer.toString(numberofbA - available.availablePermits()));

        Thread print = new Thread() {
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                    }
                    ta_workingT.setText(String.valueOf(numberofbA - available.availablePermits()));
                    ta_sleepT.setText(String.valueOf(available.availablePermits()));
                    client_watingT.setText(String.valueOf(numberofchairs - chairs.availablePermits()));
                 //   client_laterT.setText(String.valueOf(numberofclient - ((numberofbA - available.availablePermits()) + (numberofchairs - chairs.availablePermits()))));
                }
            }
        };
       print.start();
            }
                    

        };
          
             bt1.setOnAction(event);

       root.getChildren().add(bt1);
        
        
        
        
        
        
        
        
        
    /*    Button bt1=new Button("translate transition");
       root.getChildren().add(bt1);
        TranslateTransition tt= new TranslateTransition(Duration.seconds(1000),bt1);
      
        tt.setToX(200);
        tt.setCycleCount(2);
       tt.play(); */
       
       
       root.getChildren().add(ta);
        root.getChildren().add(chairT);
         root.getChildren().add(clientT);
          root.getChildren().add(ta_sleepT);
          root.getChildren().add(ta_workingT);
          root.getChildren().add(client_watingT);
        //  root.getChildren().add(txt7);
       root.getChildren().add(l1);
       root.getChildren().add(lb1);
         root.getChildren().add(chair);
           root.getChildren().add(lb3);
           root.getChildren().add(lb4);
             root.getChildren().add(lb5);
             root.getChildren().add(lb6);
           // root.getChildren().add(lb7);
        primaryStage.setTitle("sleeping barber problem");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
       
    }

}
