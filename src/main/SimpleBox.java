package main;

//X = width, Y = length, Z = height
public class SimpleBox {
    public static void main(String[] args){
        CalcBox myCalcBox1 = new CalcBox(3, 3, 3, new Point3D(0, 0, 0));
        CalcBox myCalcBox2 = new CalcBox(5, 12, 13, new Point3D(0, 0, 0));

        System.out.println(myCalcBox1.pointsToString());
        System.out.println(myCalcBox2.pointsToString());
        System.out.println("The best box is....");
        System.out.println(findBestBox(myCalcBox1, myCalcBox2)[0].pointsToString());
        System.out.println(findBestBox(myCalcBox1, myCalcBox2)[0].toString());
        System.out.println("Points of first box: \n" + findBestBox(myCalcBox1, myCalcBox2)[1].pointsToString());
        System.out.println("Points of second box: \n" + findBestBox(myCalcBox1, myCalcBox2)[2].pointsToString());
    }


    //Calculates a bounding box for any two Boxes
    private static CalcBox calcBoundingBox(CalcBox a, CalcBox b){
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
    private static boolean isOverlap(CalcBox a, CalcBox b) {
        //coordinates of the top right corner (greatest x, y, z values of each box)
        int greaterXa = a.getStartPoint().getX() + a.getWidth();
        int greaterYa = a.getStartPoint().getY() + a.getLength();
        int greaterZa = a.getStartPoint().getZ() + a.getHeight();

        int greaterXb = b.getStartPoint().getX() + b.getWidth();
        int greaterYb = b.getStartPoint().getY() + b.getLength();
        int greaterZb = b.getStartPoint().getZ() + b.getHeight();

        //all 3 have to be true for there to be overlap
        return (a.getStartPoint().getX() <= greaterXb && b.getStartPoint().getX() <= greaterXa //x values are overlapping
                && a.getStartPoint().getY() <= greaterYb && b.getStartPoint().getY() <= greaterYa && //y values are overlapping
                a.getStartPoint().getZ() <= greaterZb && b.getStartPoint().getZ() <= greaterZa); //z values overlapping
    }



    //finds the best box using a greedy algorithm! :D
    private static CalcBox[] findBestBox(CalcBox a, CalcBox b){
        int smallestSA = Integer.MAX_VALUE;
        CalcBox bestBox = new CalcBox();
        CalcBox aPos = a; //positioning of a changes(b doesn't move so it doesn't matter)

        //make 3 orientations of one of the boxes (the other one doesn't matter because symmetry)
        CalcBox a2 = new CalcBox(a.getWidth(), a.getHeight(), a.getLength(), a.getStartPoint());
        CalcBox a3 = new CalcBox(a.getHeight(), a.getLength(), a.getWidth(), a.getStartPoint());
        Point3D originalStart = a.getStartPoint();

        //calculate the bounding box of each alignment
        for (int i = 0; i < 8; i++){ //point on 1st box
            Point3D currentPointB = b.getPointArr()[i];
            for (int j = 0; j < 8; j++){ //point on 2nd box
                a.moveBox(j, b.getPointArr()[i]);

                //if the boxes are not overlapping & the surface area is smaller than the current smallest value
                if (!isOverlap(a, b) && calcBoundingBox(a, b).calculateSA() < smallestSA){
                    bestBox = calcBoundingBox(a, b);
                    aPos = a;
                }

                a2.moveBox(j, b.getPointArr()[i]);
                if (!isOverlap(a2, b) && calcBoundingBox(a2, b).calculateSA() < smallestSA){
                    bestBox = calcBoundingBox(a2, b);
                    aPos = a2;
                }

                a3.moveBox(j, b.getPointArr()[i]);
                if (!isOverlap(a3, b) && calcBoundingBox(a3, b).calculateSA() < smallestSA){
                    bestBox = calcBoundingBox(a3, b);
                    aPos = a3;
                }
            }
        }
        return new CalcBox[]{bestBox, aPos, b};
    }


}
