package ch.kbw.totoluto.bertrandparadox.method.model;

import javafx.geometry.Point2D;

/**
 * @author totoluto (Dev alias)
 * @version 15.11.2022
 */

public class Circle {
    private double radius;
    private Point2D pos;
    private Point2D mid;

    public Circle(double radius){
        this.radius = radius;
    }

    public void setPos(Point2D pos) {
        this.pos = new Point2D(pos.getX() / 2, pos.getY() / 2);
        this.mid = new Point2D(pos.getX() - radius * 3, pos.getY() - radius * 2.02);
    }
    public Point2D getPos() {
        return pos;
    }
    public double getDiameter(){
        return radius * 2;
    }
    public double getRadius() {
        return radius;
    }

    public Point2D getMid() {
        return mid;
    }
}
