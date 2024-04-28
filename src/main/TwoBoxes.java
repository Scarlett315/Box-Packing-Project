//THIS CLASS IS UNUSED --> used to test logic before making BoxArray
package main;

public class TwoBoxes {
    private CalcBox a;
    private CalcBox b;

    public TwoBoxes(CalcBox initA, CalcBox initB){
        a = initA;
        b = initB;
    }

    //Calculates a bounding box for any two Boxes
    public CalcBox calcBoundingBox(){
        Point3D[] aPoints = a.getPointArr();
        Point3D[] bPoints = b.getPointArr();

        int greatestX = Math.max(aPoints[7].getX(), bPoints[7].getX());
        int greatestY = Math.max(aPoints[7].getY(), bPoints[7].getY());
        int greatestZ = Math.max(aPoints[7].getZ(), bPoints[7].getZ());

        int leastX = Math.min(aPoints[0].getX(), bPoints[0].getX());
        int leastY = Math.min(aPoints[0].getY(), bPoints[0].getY());
        int leastZ = Math.min(aPoints[0].getZ(), bPoints[0].getZ());

        //length, width, height = difference between greatest and least y, x, z respectively
        Point3D start = new Point3D(leastX, leastY, leastZ); //start point = least x, y, z coordinate (bottom left)
        int l = greatestY - leastY;
        int w = greatestX - leastX;
        int h = greatestZ - leastZ;

        return new CalcBox(l, w, h, start);
    }

    //checks if 2 boxes are overlapping
    private boolean isOverlap() {
        if (a.equals(b)){ //I think the check would return false if they are literally the same box (because it's > not >=)
            return true;
        }

        //coordinates of the top right corner (greatest x, y, z values of each box)
        Point3D farthestA = a.getPointArr()[7];
        Point3D farthestB = b.getPointArr()[7];

        //check for overlap in all dimensions
        boolean xOverlap = (a.getStartPoint().getX() < farthestB.getX()) && (farthestA.getX() > b.getStartPoint().getX());
        boolean yOverlap = (a.getStartPoint().getY() < farthestB.getY()) && (farthestA.getY() > b.getStartPoint().getY());
        boolean zOverlap = (a.getStartPoint().getZ() < farthestB.getZ()) && (farthestA.getZ() > b.getStartPoint().getZ());

        return xOverlap && yOverlap && zOverlap;
    }



    //finds the best box! :D
    public CalcBox[] findBestBox(){
        int smallestSA = Integer.MAX_VALUE;
        CalcBox bestBox = new CalcBox();
        CalcBox bestA = new CalcBox(); //positioning of a changes(b doesn't move so it doesn't matter)

        //make 3 orientations of one of the boxes (the other one doesn't matter because symmetry)
        CalcBox[] aOrients= new CalcBox[]{a, new CalcBox(a.getWidth(), a.getHeight(), a.getLength(), a.getStartPoint()), new CalcBox(a.getHeight(), a.getLength(), a.getWidth(), a.getStartPoint())};

        //calculate the bounding box of each alignment
        for (CalcBox aOrientation:aOrients){
            for (int i = 0; i < 8; i++){ //point on a
                for (int j = 0; j < 8; j++){ //point b
                    aOrientation.moveBox(i, b.getPointArr()[j]); //moves the 'i' point on box a to the 'j' point on box b
                    TwoBoxes boxes = new TwoBoxes(aOrientation, b);

                    //if the boxes are not overlapping & the surface area is smaller than the current smallest value
                    if (!boxes.isOverlap() && boxes.calcBoundingBox().calculateSA() < smallestSA){
                        smallestSA = boxes.calcBoundingBox().calculateSA();
                        bestBox = boxes.calcBoundingBox();
                        bestA = new CalcBox(boxes.getA());

                        //testing: delete later
                        System.out.println("best box changed: " + bestBox.toString());
                        System.out.println("a: " + bestA.toString());
                    }
                }
            }
        }

        return new CalcBox[]{bestBox, bestA, b}; //returns the best box, the best orientation of a, and b
    }

    //Getters & Setters
    public CalcBox getA() {
        return a;
    }

    public CalcBox getB() {
        return b;
    }

    public void setA(CalcBox a) {
        this.a = a;
    }

    public void setB(CalcBox b) {
        this.b = b;
    }
}

