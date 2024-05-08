package main;

public class Point3D {
    private double x;
    private double y;
    private double z;

    //constructor
    public Point3D(double initX, double initY, double initZ){
        x = initX;
        y = initY;
        z = initZ;
    }

    //toString returns a normal representation of a 3D Point
    @Override
    public String toString(){
        return "(" + x + ", " + y + ", " + z + ")";
    }

    //Getters & Setters!
    public void setX(double x) {
        this.x = x;
    }
    public void setY(double y){
        this.y = y;
    }
    public void setZ(double z) {
        this.z = z;
    }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public double getZ() {
        return z;
    }
}
