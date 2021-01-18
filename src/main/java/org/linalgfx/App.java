package org.linalgfx;

import graphics.CoordinateSystem;
import graphics.Renderable;
import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.skin.ContextMenuSkin;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Affine;
import javafx.stage.Stage;
import math.Complex;
import math.Matrix;
import math.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Canvas canvas;

    @Override
    public void start(Stage stage) throws IOException {
        canvas = new Canvas(1000,600);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        List<Renderable> list = new ArrayList<>();
        Vector vector = new Vector(2, 3);
        double[][] dArr = {
                {0, -1},
                {1, 0}
        };
        Matrix matrix = new Matrix(dArr);
        list.add(vector);
        list.add(new CoordinateSystem(canvas.getWidth(), canvas.getHeight()));

        new Timer().scheduleAtFixedRate(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        vector.applyTransformation(matrix);
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        list.forEach( r ->{
                            r.render(gc);
                        });
                    }
                },
                0,
                1000
        );

        VBox root = new VBox();
        root.getChildren().addAll(canvas);

        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("stylesheets/style.css").toExternalForm());
        stage.setScene(scene);

        System.out.println(new Complex(0, 0));
        stage.show();
    }

    @Override
    public void stop(){
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}