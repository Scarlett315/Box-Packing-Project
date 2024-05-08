package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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

    //finds the best bounding box for a specific order of placing down the boxes
    private CalcBox[] findBestPrmtn(){
        int smallestSA;
        ArrayList<CalcBox> pastBoxes = new ArrayList<CalcBox>();
        CalcBox bestBox = new CalcBox();
        CalcBox[] finalBoxes = new CalcBox[boxArr.length + 1]; //final array of boxes w/ positions to be returned

        //sort the box list by volume, convert into ArrayList to sort and then back into Array (intuitive but not correct)
        /*
        ArrayList<CalcBox> boxArrLi =  new ArrayList<CalcBox>(Arrays.asList(boxArr));
        boxArrLi.sort(Comparator.comparingInt((CalcBox o) -> o.calculateVolume()));
        for (int i = 0; i < boxArr.length; i++){
            boxArr[i] = boxArrLi.get(i);
        }
        */


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

            //box a = box to be placed; box b = the current box that is already placed that a is compared against
            //for each box that has already been placed
            for (CalcBox b:pastBoxes){
                //System.out.println("Current box 'b': " + b.toString());
                for (CalcBox aOrientation:aOrients){ //for each orientation of a/the box to be placed
                    //System.out.println("Current 'a' Box Orientation: " + aOrientation.toString());
                    for (int i = 0; i < 8; i++){ //point on a
                        for (int j = 0; j < 8; j++){ //point b
                            aOrientation.moveBox(i, b.getPointArr()[j]); //moves the 'i' point on box a to the 'j' point on box b
                            //System.out.println("'a' has been moved; new box: " + aOrientation.toString());

                            //making a BoxArray with the boxes already placed + the placed box
                            CalcBox[] combined = new CalcBox[pastBoxes.size() + 1];
                            for (int k = 0; k <= combined.length-2; k++) {
                                //System.out.println("Past box: " + pastBoxes.get(k));
                                combined[k] = pastBoxes.get(k);
                            }
                            combined[combined.length-1] = aOrientation;

                            BoxArray boxes = new BoxArray(combined);

                            //makes sure the new box is not overlapping with past boxes
                            boolean overlap = false;
                            for (CalcBox pastBox:pastBoxes){
                                if (pastBox.isOverlap(aOrientation)){
                                    overlap = true;
                                }
                            }

                            //System.out.println("Overlapping?: " + overlap + " / SA: " + boxes.calcBoundingBox().calculateSA());
                            //if the boxes are not overlapping & the surface area is smaller than the current smallest value
                            if (!overlap && boxes.calcBoundingBox().calculateSA() < smallestSA){
                                smallestSA = boxes.calcBoundingBox().calculateSA(); //changes smallestSA
                                bestBox = new CalcBox(boxes.calcBoundingBox());
                                //System.out.println("Best Box changed: " + bestBox.toString());
                                bestPosA = new CalcBox(aOrientation); //saves the best orientation of a
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

    //finds the best box! :D
    public CalcBox[] findBestBox(){
        Permute permute = new Permute(); //I don't think this is the right way but it works
        CalcBox[][] allPermutations = permute.getPermutations(boxArr);

        //initial values
        int smallestSA = Integer.MAX_VALUE;
        CalcBox[] finBoxes = new CalcBox[boxArr.length + 1];

        for (CalcBox[] permutation:allPermutations){
            BoxArray arr = new BoxArray(permutation);

            CalcBox[] best = arr.findBestPrmtn();
            if (best[0].calculateSA() < smallestSA){
                System.out.println("Best order of boxes changed");
                smallestSA = best[0].calculateSA();
                finBoxes = best;
            }
        }
        return finBoxes;
    }

    //Array functionality


    //Getters & Setters
    public CalcBox[] getBoxArr() {
        return boxArr;
    }

    public void setBoxArr(CalcBox[] newBoxArr) {
        boxArr = newBoxArr;
    }
}
