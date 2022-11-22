package ch.kbw.totoluto.bertrandparadox.method.model;

import javafx.geometry.Point2D;

/**
 * @author totoluto (Dev alias)
 * @version 15.11.2022
 */

public class Triangle {
    private double length;
    private double height;

    private Point2D cornerA;
    private Point2D cornerB;
    private Point2D cornerC;

    public Triangle(){

    }

    public void constructInCircle(Circle circle){
        this.height = 1.5 * circle.getRadius();
        this.length = 2 * height / Math.sqrt(3);

        this.cornerA = new Point2D(circle.getPos().getX() - (length / 2), height + circle.getPos().getY() - circle.getRadius());
        this.cornerB = new Point2D(circle.getPos().getX() + (length / 2), height + circle.getPos().getY() - circle.getRadius());
        this.cornerC = new Point2D(circle.getPos().getX(), circle.getPos().getY() - circle.getRadius());
    }

    public double getLength() {
        return length;
    }

    public double getHeight() {
        return height;
    }

    public Point2D getCornerA() {
        return cornerA;
    }

    public Point2D getCornerB() {
        return cornerB;
    }

    public Point2D getCornerC() {
        return cornerC;
    }

    public double[] getXPos(){
        return new double[] {getCornerA().getX(), getCornerB().getX(), getCornerC().getX()};
    }

    public double[] getYPos(){
        return new double[] {getCornerA().getY(), getCornerB().getY(), getCornerC().getY()};
    }
}
