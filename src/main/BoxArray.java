package main;

import java.util.ArrayList;
import java.util.Arrays;

public class BoxArray {
    private CalcBox[] boxArr;

    public BoxArray(CalcBox[] initBoxArr){
        boxArr = initBoxArr;
    }

    //Calculates a bounding box for an array of Boxes
    public CalcBox calcBoundingBox(){
        int greatestX = Integer.MIN_VALUE;
        int greatestY = Integer.MIN_VALUE;
        int greatestZ = Integer.MIN_VALUE;

        int leastX = Integer.MAX_VALUE;
        int leastY = Integer.MAX_VALUE;
        int leastZ = Integer.MAX_VALUE;


        //greedy algorithm to find the greatest & least x, y, z of all the boxes
        for (CalcBox box:boxArr){
            Point3D[] boxPoints = box.getPointArr();

            //greatest x, y, z of a box = coordinates of the top right corner
            greatestX = Math.max(greatestX, boxPoints[7].getX());
            greatestY = Math.max(greatestY, boxPoints[7].getY());
            greatestZ = Math.max(greatestZ, boxPoints[7].getZ());

            leastX = Math.min(leastX, boxPoints[0].getX());
            leastY = Math.min(leastY, boxPoints[0].getY());
            leastZ = Math.min(leastZ, boxPoints[0].getZ());
        }

        //length, width, height = difference between greatest and least y, x, z respectively
        Point3D start = new Point3D(leastX, leastY, leastZ); //start point = least x, y, z coordinate (bottom left)
        int l = greatestY - leastY;
        int w = greatestX - leastX;
        int h = greatestZ - leastZ;

        return new CalcBox(l, w, h, start);
    }

    //finds the best box! :D
    public CalcBox[] findBestBox(){
        int smallestSA;
        ArrayList<CalcBox> pastBoxes = new ArrayList<CalcBox>();
        CalcBox bestBox = new CalcBox();
        CalcBox[] finalBoxes = new CalcBox[boxArr.length + 1]; //final array of boxes w/ positions to be returned

        //sort the box list by volume (avoids some problems & looks better)


        boxArr[0].moveBox(0, new Point3D(0, 0, 0)); //moves first box to origin
        finalBoxes[1] = new CalcBox(boxArr[0]);
        //places the boxes one by one (optimize -- 5 nested for loops is probably wayy too slow to run)
        for (int h = 1; h < boxArr.length; h++){
            smallestSA = Integer.MAX_VALUE; //resets every time a new box is placed
            CalcBox a = boxArr[h];
            pastBoxes.add(boxArr[h-1]);

            CalcBox bestPosA = new CalcBox(); //positioning of a changes(b doesn't move so it doesn't matter)

            //make 3 orientations of the box to be placed
            CalcBox[] aOrients = new CalcBox[]{new CalcBox(a), new CalcBox(a.getWidth(), a.getHeight(), a.getLength(), a.getStartPoint()), new CalcBox(a.getHeight(), a.getLength(), a.getWidth(), a.getStartPoint())};

            //for each box that has already been placed
            for (CalcBox b:pastBoxes){
                System.out.println("Current box 'b': " + b.toString());
                for (CalcBox aOrientation:aOrients){ //for each orientation of the box to be placed
                    System.out.println("Current 'a' Box Orientation: " + aOrientation.toString());
                    for (int i = 0; i < 8; i++){ //point on a
                        for (int j = 0; j < 8; j++){ //point b
                            aOrientation.moveBox(i, b.getPointArr()[j]); //moves the 'i' point on box a to the 'j' point on box b
                            //System.out.println("'a' has been moved; new box: " + aOrientation.toString());

                            CalcBox[] combined = new CalcBox[pastBoxes.size() + 1];
                            for (int k = 0; k <= combined.length-2; k++) {
                                //System.out.println("Past box: " + pastBoxes.get(k));
                                combined[k] = pastBoxes.get(k);
                            }
                            combined[combined.length-1] = aOrientation;

                            BoxArray boxes = new BoxArray(combined);


                            //if the boxes are not overlapping & the surface area is smaller than the current smallest value
                            boolean overlap = false;
                            for (CalcBox pastBox:pastBoxes){
                                if (pastBox.isOverlap(aOrientation)){
                                    overlap = true;
                                }
                            }
                            System.out.println("Overlapping?: " + overlap + " / SA: " + boxes.calcBoundingBox().calculateSA());

                            if (!overlap && boxes.calcBoundingBox().calculateSA() < smallestSA){
                                smallestSA = boxes.calcBoundingBox().calculateSA();
                                bestBox = new CalcBox(boxes.calcBoundingBox());
                                System.out.println("Best Box changed: " + bestBox.toString());
                                bestPosA = new CalcBox(aOrientation);
                            }
                        }
                    }
                }
            }
            boxArr[h] = new CalcBox(bestPosA);
            finalBoxes[h+1] = new CalcBox(bestPosA);
            System.out.println("Final position of the box: " + bestPosA);
        }
        finalBoxes[0] = new CalcBox(bestBox);
        System.out.println("** The best boxes are...");
        for (CalcBox b :finalBoxes){
            System.out.println(b);
        }

        return finalBoxes; //returns the best box & orientations of all boxes
    }

    //Getters & Setters
    public CalcBox[] getBoxArr() {
        return boxArr;
    }

    public void setBoxArr(CalcBox[] newBoxArr) {
        boxArr = newBoxArr;
    }
}
