package main;

public class Point3D {
    private int x;
    private int y;
    private int z;

    //constructor
    public Point3D(int initX, int initY, int initZ){
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
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
    public void setZ(int z) {
        this.z = z;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getZ() {
        return z;
    }
}
