package graphics;

import exceptions.IllegalNumberOfDimensionsException;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import math.*;

import java.text.DecimalFormat;


public abstract class CanvasRenderer{
    private static Canvas canvas;
    private static GraphicsContext graphicsContext;
    private static double offsetX;
    private static double offsetY;
    public static double unitSize;
    private static double baseSpacing;
    public static long deltaTime;





    public static void start(){
        Matrix matrix = new Matrix(0, 1, -1, 0);
        Vector vector = new Vector(2, 2);
        Line line = new Line(-2, 0, 2, 2);

        Grid grid = new Grid(0, 0, 5, 5, 1, 1);
        LineSegment lineSegment = new LineSegment(-2, 0, -5, -5);

        DefinedVariables.add(vector, "vector");
        DefinedVariables.add(line, "line");
        DefinedVariables.add(grid, "grid");
        DefinedVariables.add(lineSegment, "linesegment");
        DefinedVariables.add(new Variable<Matrix>(matrix, "m"));


        accountForChanges();


        AnimationTimer animationTimer = new AnimationTimer() {
            long lastFrameTime;
            @Override
            public void handle(long now) {
                deltaTime = (now - lastFrameTime) / 1000000;
                graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                CanvasRenderer.drawLines();
                DefinedVariables.getRenderableVariables().forEach(r -> {
                    r.getRenderable().render(graphicsContext, r.getName(), r.getPaint());
                });

                lastFrameTime = now;

                DefinedVariables.updateText();
            }
        };
        animationTimer.start();
    }


    public static void setGraphicsContext(GraphicsContext graphicsContext) {
        CanvasRenderer.graphicsContext = graphicsContext;
    }

    public static void setCanvas(Canvas canvas) {
        CanvasRenderer.canvas = canvas;
    }

    public static double getUnitSize(){
        return unitSize;
    }

    public static void setUnitSize(double i){
        unitSize = i;
    }

    public static double getCanvasWidth() {
        return canvas.getWidth();
    }

    public static double getCanvasHeight() {
        return canvas.getHeight();
    }

    public static Point toCanvasPoint(Point point) throws IllegalNumberOfDimensionsException {
        return new Point(toCanvasX(point.getElement(0)), toCanvasY(point.getElement(1)));
    }

    //public static Point fromCanvasPoint(Point point) throws IllegalNumberOfDimensionsException{ }

    public static double toCanvasX(double x){
        return getCanvasWidth()/2 + offsetX + x * unitSize;
    }

    public static double toCanvasY(double y){
        return getCanvasHeight() / 2 + offsetY - y * unitSize;
    }

    public static double fromCanvasX(double x){
        return (x - offsetX - getCanvasWidth()/2) / unitSize;
    }

    public static double fromCanvasY(double y){
        return - (y - offsetY - getCanvasHeight()/2) / unitSize;
    }





    public static void accountForChanges(){
        //oppdaterer alle linjer
        for(Variable<Renderable> variable : DefinedVariables.getRenderableVariables())
            if(variable.getVariable() instanceof Line)
                ((Line) variable.getVariable()).updateCanvasPoints();
    }




    public static void changeOffsetX(double x){
        offsetX += x;
    }
    public static void changeOffsetY(double y){
        offsetY += y;
    }
    public static void scaleUnitSize(double s) {
        unitSize *= s;

        if (unitSize == 0)
            unitSize = Double.MIN_VALUE;
    }

    public static double getOffsetX(){
        return offsetX;
    }

    public static double getOffsetY(){
        return offsetY;
    }


    public static void drawLines(){
        baseSpacing = 40;

        double spacing =  baseSpacing * Math.pow(2, Utils.log2(unitSize/baseSpacing) - Math.floor(Utils.log2(unitSize/baseSpacing)));

        long n = -Math.round(Math.floor(Utils.log2(unitSize/baseSpacing)));

        double originX = toCanvasX(0);
        double originY = toCanvasY(0);

        double dX = originX % spacing;
        double dY = originY % spacing;

        graphicsContext.setStroke(Paint.valueOf("black"));
        graphicsContext.setLineWidth(1);

        graphicsContext.strokeLine(originX, 0, originX, getCanvasHeight());
        graphicsContext.strokeLine(0, originY, getCanvasWidth(), originY);

        double x = dX;
        double y = dY;

        graphicsContext.setStroke(Paint.valueOf("grey"));
        graphicsContext.setFill(Paint.valueOf("black"));
        graphicsContext.setLineWidth(0.5);
        while(x <= getCanvasWidth()){
            graphicsContext.fillText(stringifyPowerOf2(fromCanvasX(x), n), x, originY-4);
            graphicsContext.strokeLine(x, 0, x, getCanvasHeight());
            x+=spacing;
        }


        while(y <= getCanvasHeight()){
            graphicsContext.fillText(stringifyPowerOf2(fromCanvasY(y), n), originX+6, y);
            graphicsContext.strokeLine(0, y, getCanvasWidth(), y);
            y+=spacing;
        }
    }

    private static DecimalFormat df = new DecimalFormat("0.00");

    private static String stringifyPowerOf2(double d, long n){
        int k = (int) Math.round(d / Math.pow(2, n));

        if(k == 0)
            return "";
        //return k + ", "+n + ", d:" + df.format(d);

        if(n >= 0)
            return "" + (int) (k*Math.pow(2, n));

        int denominator = (int) Math.pow(2, -n);
        int gcd = Math.abs(Utils.gcd(k, denominator));
        denominator = denominator/gcd;
        if(denominator == 1)
            return ""+k/gcd;

        return "" + k/gcd + "/" + (int) Math.pow(2, -n) / gcd;
    }


    public static boolean insideCanvas(Point point){
        Point actual = CanvasRenderer.toCanvasPoint(point);
        if(actual.getElement(0) < 0 || actual.getElement(0) > CanvasRenderer.getCanvasWidth() || actual.getElement(1) < 0 || actual.getElement(1) > CanvasRenderer.getCanvasHeight())
            return false;
        return true;
    }


}
