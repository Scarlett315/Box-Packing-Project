package main;

public class CalcBox {
    private double length;
    private double width;
    private double height;
    private Point3D startPoint;
    private Point3D[] pointArr = new Point3D[8];

    //constructors
    public CalcBox(double initL, double initW, double initH, Point3D initS){
        length = initL;
        width = initW;
        height = initH;
        startPoint = initS;

        pointArr = this.calculateBoxPoints();
    }
    public CalcBox(){};

    public CalcBox(CalcBox box){
        length = box.getLength();
        width = box.getWidth();
        height = box.getHeight();
        startPoint = box.getStartPoint();
        pointArr = calculateBoxPoints();
    }

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
        return "Length: " + length + ", Width: " + width + ", Height: " + height + ", Bottom left corner: " + startPoint.toString()
                + "\n   Surface Area: " + this.calculateSA() + "\n   Volume: "+ this.calculateVolume() + "\n";
    }


    public double calculateVolume(){ //calculates the volume of one box
        return length * width * height;
    }

    public double calculateSA(){ //calculates the SA of one box
        return (2 * length * width) +  (2 * length * height) + (2 * width * height);
    }

    //Recalculate all points if the box is moved/dimensions changed
    private Point3D[] calculateBoxPoints(){
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
        }
        else if (pointIndex == 1){
            startPoint = new Point3D(targetPoint.getX()-width, targetPoint.getY(),targetPoint.getZ());
        }
        else if (pointIndex == 2){
            startPoint = new Point3D(targetPoint.getX(), targetPoint.getY()-length,targetPoint.getZ());
        }
        else if (pointIndex == 3){
            startPoint = new Point3D(targetPoint.getX() - width, targetPoint.getY() - length,targetPoint.getZ());
        }
        else if (pointIndex == 4){
            startPoint = new Point3D(targetPoint.getX(), targetPoint.getY(),targetPoint.getZ() - height);
        }
        else if (pointIndex == 5){
            startPoint = new Point3D(targetPoint.getX() - width, targetPoint.getY(),targetPoint.getZ() - height);
        }
        else if (pointIndex == 6){
            startPoint = new Point3D(targetPoint.getX(), targetPoint.getY() - length,targetPoint.getZ() - height);
        }
        else if (pointIndex == 7){
            startPoint = new Point3D(targetPoint.getX()-width, targetPoint.getY() - length,targetPoint.getZ() - height);
        }
        pointArr = calculateBoxPoints();
    }

    //checks if 2 boxes are equal (length, width, height, startPoint are the same)
    public boolean equals(CalcBox otherBox){
        return length == otherBox.getLength() && height == otherBox.getHeight() && width == otherBox.getWidth() && startPoint == otherBox.getStartPoint();
    }

    //checks if 2 boxes are overlapping (in space)
    public boolean isOverlap(CalcBox b) {
        if (this.equals(b)){ //I think the check would return false if they are literally the same box (because it's > not >=)
            return true;
        }

        //coordinates of the top right corner (greatest x, y, z values of each box)
        Point3D farthestA = this.getPointArr()[7];
        Point3D farthestB = this.getPointArr()[7];

        //check for overlap in all dimensions
        boolean xOverlap = (this.getStartPoint().getX() < farthestB.getX()) && (farthestA.getX() > b.getStartPoint().getX());
        boolean yOverlap = (this.getStartPoint().getY() < farthestB.getY()) && (farthestA.getY() > b.getStartPoint().getY());
        boolean zOverlap = (this.getStartPoint().getZ() < farthestB.getZ()) && (farthestA.getZ() > b.getStartPoint().getZ());

        return xOverlap && yOverlap && zOverlap;
    }


    //Getters & Setters!
    public double getHeight() {
        return height;
    }
    public double getLength() {
        return length;
    }
    public double getWidth() {
        return width;
    }
    public Point3D getStartPoint() {
        return startPoint;
    }
    public Point3D[] getPointArr() {
        return pointArr;
    }

    //other things change if lwh or start point changes
    public void setHeight(double h) {
        this.height = h;
        pointArr = calculateBoxPoints(); //so points have to be recalculated
    }
    public void setLength(double l) {
        this.length = l;
        pointArr = calculateBoxPoints();
    }
    public void setStartPoint(Point3D startPt) {
        this.startPoint = startPt;
        pointArr = calculateBoxPoints();
    }
    public void setWidth(double w) {
        this.width = w;
        pointArr = calculateBoxPoints();
    }
}
