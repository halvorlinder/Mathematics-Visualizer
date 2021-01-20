package math;

import graphics.CoordinateSystem;
import graphics.Renderable;
import javafx.scene.canvas.GraphicsContext;

import java.util.function.Function;
import java.util.function.IntToDoubleFunction;
import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class Complex implements Renderable {
    private double re;
    private double im;
    private double length;
    private double angle;

    public Complex(double re, double im){
        this.re = re;
        this.im = im;
        setPolarFromStandard();
    }

    public void add(Complex other){
        re += other.re;
        im += other.im;
    }

    public void multiply(Complex other){
        re = re*other.re - im*other.im;
        im = re*other.im + im*other.re;
    }

    public void pow(int exponent){
        length = Math.pow(length, exponent);
        angle = angle * exponent;
        setStandardFromPolar();
    }


    public String asPolar(){

        return length + "e^i*" + angle;
    }

    public void setPolarFromStandard(){
        length = Math.sqrt(Math.abs(re) + Math.abs(im));
        if(re >= 0)
            angle = Math.atan(im / re);
        else
            angle = Math.atan(im / re) + Math.PI;
    }

    public void setStandardFromPolar(){
        re = length * Math.cos(angle);
        im = length * Math.sin(angle);
    }


    @Override
    public String toString(){
        return re + " + " + im + "i";
    }


    public double getRe() {
        return re;
    }

    public void setRe(double re) {
        this.re = re;
    }

    public double getIm() {
        return im;
    }

    public void setIm(double im) {
        this.im = im;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public Point getCanvasPoint(){
        return CoordinateSystem.toCanvasPoint(new Point(re, im));
    }

    @Override
    public void render(GraphicsContext gc){
        Point p = getCanvasPoint();
        gc.fillRect(p.getElement(0) - 5, p.getElement(1)- 5, 10,10);
    }
}

