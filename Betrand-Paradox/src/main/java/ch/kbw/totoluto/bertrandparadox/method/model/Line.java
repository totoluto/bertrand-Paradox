package ch.kbw.totoluto.bertrandparadox.method.model;

import javafx.geometry.Point2D;

public class Line {
    private Point2D startCord;
    private Point2D endCord;

    public double getLength(){
        return Math.sqrt((Math.pow(startCord.getX() - endCord.getX(), 2) + Math.pow(startCord.getY() - endCord.getY(), 2)));
    }

    public Point2D getStartCord() {
        return startCord;
    }

    public void setStartCord(Point2D startCord) {
        this.startCord = startCord;
    }

    public Point2D getEndCord() {
        return endCord;
    }

    public void setEndCord(Point2D endCord) {
        this.endCord = endCord;
    }
}
