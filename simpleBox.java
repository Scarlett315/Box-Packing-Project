//import javafx.scene.shape;
//X = width, Y = length, Z = height
public class simpleBox {
    public static void main(String[] args){
        Box myBox1 = new Box(3, 3, 3, new Point3D(0, 0, 0));
        Box myBox2 = new Box(3, 3, 3, new Point3D(0, 0, 0));

        System.out.println(myBox1.pointsToString());
        System.out.println(myBox2.pointsToString());
        System.out.println(isOverlap(myBox1, myBox2));
    }


    //Calculates a bounding box for any two Boxes
    private static Box calcBoundingBox(Box a, Box b){
        Point3D[] aPoints = a.getPointArr();
        Point3D[] bPoints = b.getPointArr();

        int greatestX = Integer.MIN_VALUE;
        int greatestY = Integer.MIN_VALUE;
        int greatestZ = Integer.MIN_VALUE;

        int leastX = Integer.MAX_VALUE;
        int leastY = Integer.MAX_VALUE;
        int leastZ = Integer.MAX_VALUE;

        //loop through each set of points, find the greatest and least x, y, z values
        for (Point3D i:aPoints){
            greatestX = Math.max(greatestX, i.getX());
            greatestY = Math.max(greatestY, i.getY());
            greatestZ = Math.max(greatestZ, i.getZ());

            leastX = Math.min(leastX, i.getX());
            leastY = Math.min(leastY, i.getY());
            leastZ = Math.min(leastZ, i.getZ());
        }

        for (Point3D j:bPoints){ //same thing for box b points
            greatestX = Math.max(greatestX, j.getX());
            greatestY = Math.max(greatestY, j.getY());
            greatestZ = Math.max(greatestZ, j.getZ());

            leastX = Math.min(leastX, j.getX());
            leastY = Math.min(leastY, j.getY());
            leastZ = Math.min(leastZ, j.getZ());
        }


        //length, width, height = difference between greatest and least y, x, z respectively
        Point3D start = new Point3D(leastX, leastY, leastZ); //start point = least x, y, z coordinate (bottom left)
        int l = greatestY - leastY;
        int w = greatestX - leastX;
        int h = greatestZ - leastZ;

        return new Box(l, w, h, start);
    }

    //checks if 2 boxes are overlapping
    private static boolean isOverlap(Box a, Box b) {
        //other end? of the point coming from startPoint of each box
        int greaterXa = a.getStartPoint().getX() + a.getWidth();
        int greaterYa = a.getStartPoint().getY() + a.getLength();
        int greaterZa = a.getStartPoint().getZ() + a.getHeight();

        int greaterXb = b.getStartPoint().getX() + b.getWidth();
        int greaterYb = b.getStartPoint().getY() + b.getLength();
        int greaterZb = b.getStartPoint().getZ() + b.getHeight();

        //all 3 have to be true for there to be overlap
        return (a.getStartPoint().getX() < greaterXb && b.getStartPoint().getX() < greaterXa //x values are overlapping
                && a.getStartPoint().getY() < greaterYb && b.getStartPoint().getY() < greaterYa && //y values are overlapping
                a.getStartPoint().getZ() < greaterZb && b.getStartPoint().getZ() < greaterZa); //z values overlapping
    }




    //OLD OVERLAP FUNCTION --> DOESN'T WORK
    /*
    private static boolean isOverlap(Box a, Box b){
        int strikes = 0; //2 dimensions must be overlapping for the boxes to be overlapping
        Point3D[] aPoints = a.getPointArr();


        //the other end of each line from startPoint
        int greaterX = b.getStartPoint().getX() + b.getWidth();
        int greaterY = b.getStartPoint().getY() + b.getLength();
        int greaterZ = b.getStartPoint().getZ() + b.getHeight();

        //checks each point for overlap
        for (Point3D i:aPoints) {
            strikes = 0;
            if (i.getX() < greaterX && i.getX() > b.getStartPoint().getX()) {
                strikes++;
                if (i.getY() < greaterY && i.getY() > b.getStartPoint().getY()) {
                    strikes++;
                    if(i.getZ() < greaterZ && i.getZ() > b.getStartPoint().getZ()){
                        strikes++;
                    }
                }
            }

            if (strikes >= 2){ //2 strikes and you're out :D
                return true;
            }
        }
        return false;
    }
    */


    //actual thing
    /*
    private Box findBestBox(Box a, Box b){
        int smallestSA = 0;

        //make 3 orientations of one of the boxes (the other one doesn't matter because symmetry)
        Box a2 = new Box(a.getWidth(), a.getHeight(), a.getLength(), a.getStartPoint());
        Box a3 = new Box(a.getHeight(), a.getLength(), a.getWidth(), a.getStartPoint());
        Point3D originalStart = a.getStartPoint();

        //calculate the bounding box of each alignment
        for (int i = 0; i < 8; i++){ //point on 1st box
            Point3D currentPointB = b.getPointArr()[i];

            for (int j = 0; j < 8; j++){ //point on 2nd box

            }
        }


        //return best
    }

     */
}
