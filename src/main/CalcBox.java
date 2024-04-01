package main;

public class CalcBox {
    private int length;
    private int width;
    private int height;
    private Point3D startPoint;
    private Point3D[] pointArr = new Point3D[8];

    //constructors
    public CalcBox(int initL, int initW, int initH, Point3D initS){
        length = initL;
        width = initW;
        height = initH;
        startPoint = initS;

        pointArr = calculateBoxPoints(initS, initL, initW, initH);
    }
    public CalcBox(){};

    //outputs the point array in a more readable way
    public String pointsToString(){
        String out = "";

        for (int i = 0; i < 8; i++){
            out = out.concat("\n").concat(pointArr[i].toString());
        }

        return out;
    }

    @Override
    public String toString(){
        return "Length: " + length + ", Width: " + width + ", Height" + height + ", Bottom left corner" + startPoint.toString();
    }


    public int calculateVolume(){ //calculates the volume of one box
        return length * width * height;
    }

    public int calculateSA(){ //calculates the SA of one box
        return (2 * length * width) +  (2 * length * height) + (2 * width * height);
    }

    //Recalculate all points if the box is moved/dimensions changed
    private Point3D[] calculateBoxPoints(Point3D start, int l, int w, int h){
        //box points
        Point3D Point2;
        Point3D Point3;
        Point3D Point4;
        Point3D Point5;
        Point3D Point6;
        Point3D Point7;
        Point3D Point8;

        //X = width, Y = length, Z = height
        Point2 = new Point3D(startPoint.getX() + width, startPoint.getY(), startPoint.getZ());
        Point3 = new Point3D (startPoint.getX(), startPoint.getY() + length, startPoint.getZ());
        Point4 = new Point3D (startPoint.getX() + width, startPoint.getY() + length, startPoint.getZ());

        Point5 = new Point3D (startPoint.getX(), startPoint.getY(), startPoint.getZ() + height);
        Point6 = new Point3D (startPoint.getX() + width, startPoint.getY(), startPoint.getZ() + height);
        Point7 = new Point3D (startPoint.getX(), startPoint.getY() + length, startPoint.getZ() + height);
        Point8 = new Point3D (startPoint.getX() + width, startPoint.getY() + length, startPoint.getZ() + height);

        return new Point3D[]{startPoint, Point2, Point3, Point4, Point5, Point6, Point7, Point8};
    }

    //Moves a box based on a point
    public void moveBox(int pointIndex, Point3D targetPoint){ //point is the index of the point in the point array
        Point3D currentPoint = this.getPointArr()[pointIndex];

        //calculates & sets start point relative to each point; there was probably a better way to do this
        if (pointIndex == 0){
            startPoint = targetPoint;
            pointArr = calculateBoxPoints(targetPoint, length, width, height);
        }
        else if (pointIndex == 1){
            startPoint = new Point3D(targetPoint.getX()-width, targetPoint.getY(),targetPoint.getZ());
            pointArr = calculateBoxPoints(startPoint, length, width, height);
        }
        else if (pointIndex == 2){
            startPoint = new Point3D(targetPoint.getX(), targetPoint.getY()-length,targetPoint.getZ());
            pointArr = calculateBoxPoints(startPoint, length, width, height);
        }
        else if (pointIndex == 3){
            startPoint = new Point3D(targetPoint.getX() - width, targetPoint.getY() - length,targetPoint.getZ());
            pointArr = calculateBoxPoints(startPoint, length, width, height);
        }
        else if (pointIndex == 4){
            startPoint = new Point3D(targetPoint.getX(), targetPoint.getY(),targetPoint.getZ() - height);
            pointArr = calculateBoxPoints(startPoint, length, width, height);
        }
        else if (pointIndex == 5){
            startPoint = new Point3D(targetPoint.getX() - width, targetPoint.getY(),targetPoint.getZ() - height);
            pointArr = calculateBoxPoints(startPoint, length, width, height);
        }
        else if (pointIndex == 6){
            startPoint = new Point3D(targetPoint.getX(), targetPoint.getY() - length,targetPoint.getZ() - height);
            pointArr = calculateBoxPoints(startPoint, length, width, height);
        }
        else if (pointIndex == 7){
            startPoint = new Point3D(targetPoint.getX()-width, targetPoint.getY() - length,targetPoint.getZ() - height);
            pointArr = calculateBoxPoints(startPoint, length, width, height);
        }

    }


    //Getters & Setters!
    public int getHeight() {
        return height;
    }
    public int getLength() {
        return length;
    }
    public int getWidth() {
        return width;
    }
    public Point3D getStartPoint() {
        return startPoint;
    }
    public Point3D[] getPointArr() {
        return pointArr;
    }

    //other things change if lwh or start point changes
    public void setHeight(int h) {
        this.height = h;
        pointArr = calculateBoxPoints(startPoint, length, width, height); //so points have to be recalculated
    }
    public void setLength(int l) {
        this.length = l;
        pointArr = calculateBoxPoints(startPoint, length, width, height);
    }
    public void setStartPoint(Point3D startPt) {
        this.startPoint = startPt;
        pointArr = calculateBoxPoints(startPoint, length, width, height);
    }
    public void setWidth(int w) {
        this.width = w;
        pointArr = calculateBoxPoints(startPoint, length, width, height);
    }
}
