package canvas3d;

import canvas2d.CanvasRenderer2D;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import math3d.Vector3;


public class CanvasPane3D extends Pane {

    private final Canvas canvas;
    private boolean mouseIsPressed;
    private final double mouseSensitivity = 0.004;
    private double previousX;
    private double previousY;

    public CanvasPane3D(double width, double height) {
        canvas = new Canvas(width, height);
        //canvas.setFocusTraversable(true);
        canvas.addEventFilter(MouseEvent.ANY, (e) -> canvas.requestFocus());

        getChildren().add(canvas);
        canvas.setOnScroll(scrollHandler);
        canvas.setOnKeyPressed(keyDownHandler);
        canvas.setOnKeyReleased(keyUpHandler);
        canvas.setOnMouseDragged(mouseHandler);
        canvas.setOnMousePressed(event -> {
            previousX = event.getX();
            previousY = event.getY();
            this.mouseIsPressed = true;
        });
        canvas.setOnMouseReleased(event -> {
            this.mouseIsPressed = false;
        });
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    protected void layoutChildren() {
//        canvas.setOnMousePressed(startDragEvent);
//        canvas.setOnMouseDragged(endDragEvent);
//        canvas.setOnScroll(scrollEvent);

        super.layoutChildren();
        final double x = snappedLeftInset();
        final double y = snappedTopInset();

        final double w = snapSizeX(getWidth()) - x - snappedRightInset();
        final double h = snapSizeY(getHeight()) - y - snappedBottomInset();
        canvas.setLayoutX(x);
        canvas.setLayoutY(y);
        canvas.setWidth(w);
        canvas.setHeight(h);


        CanvasRenderer2D.accountForChanges();
    }


    private EventHandler<MouseEvent> mouseHandler = mouseEvent -> {
        double movementX = (mouseEvent.getX() - previousX) * mouseSensitivity;
        double movementY = (mouseEvent.getY() - previousY) * mouseSensitivity;

        //set yaw
        CanvasRenderer3D.getCamera().rotateY(-movementX);
        //CanvasRenderer3D.getCamera().rotateOwnRight(movementY);

//        CanvasRenderer3D.getCamera().setPosition(Vector3.rotate(CanvasRenderer3D.getCamera().right, CanvasRenderer3D.getCamera().position, movementY*0.01));

        //CanvasRenderer3D.getCamera().pointAt(Vector3.ZERO());

        //TODO fix camera pitching
        //CanvasRenderer3D.getCamera().setForward(Vector3.rotate(CanvasRenderer3D.getCamera().right, CanvasRenderer3D.getCamera().forward, movementY));

        previousX = mouseEvent.getX();
        previousY = mouseEvent.getY();
    };

    private EventHandler<KeyEvent> keyDownHandler = keyEvent ->{
        //System.out.println("Character: " + keyEvent.getCode());

        if(keyEvent.getCode().equals(KeyCode.SPACE))
            CanvasRenderer3D.getCamera().UP = true;

        else if(keyEvent.getCode().equals(KeyCode.SHIFT))
            CanvasRenderer3D.getCamera().DOWN = true;

        else if(keyEvent.getCode().equals(KeyCode.D))
            CanvasRenderer3D.getCamera().RIGHT = true;

        else if(keyEvent.getCode().equals(KeyCode.A))
            CanvasRenderer3D.getCamera().LEFT = true;

        else if(keyEvent.getCode().equals(KeyCode.W))
            CanvasRenderer3D.getCamera().FORWARD = true;

        else if(keyEvent.getCode().equals(KeyCode.S))
            CanvasRenderer3D.getCamera().BACK = true;

        else if(keyEvent.getCode().equals(KeyCode.CONTROL))
            CanvasRenderer3D.getCamera().CONTROL = true;
    };

    private EventHandler<KeyEvent> keyUpHandler = keyEvent ->{
        //System.out.println("Character: " + keyEvent.getCode());

        if(keyEvent.getCode().equals(KeyCode.SPACE))
            CanvasRenderer3D.getCamera().UP = false;

        else if(keyEvent.getCode().equals(KeyCode.SHIFT))
            CanvasRenderer3D.getCamera().DOWN = false;

        else if(keyEvent.getCode().equals(KeyCode.D))
            CanvasRenderer3D.getCamera().RIGHT = false;

        else if(keyEvent.getCode().equals(KeyCode.A))
            CanvasRenderer3D.getCamera().LEFT = false;

        else if(keyEvent.getCode().equals(KeyCode.W))
            CanvasRenderer3D.getCamera().FORWARD = false;

        else if(keyEvent.getCode().equals(KeyCode.S))
            CanvasRenderer3D.getCamera().BACK = false;

        else if(keyEvent.getCode().equals(KeyCode.CONTROL))
            CanvasRenderer3D.getCamera().CONTROL = false;
    };

    private EventHandler<ScrollEvent> scrollHandler = scrollEvent ->{
        CanvasRenderer3D.getCamera().position.scale(1 - scrollEvent.getDeltaY() / 300);
    };
}
