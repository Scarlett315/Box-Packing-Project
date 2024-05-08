package main;

import java.util.ArrayList;

public class Permute {
    private ArrayList<CalcBox[]> allPermutations = new ArrayList<>();

    //recursive function to calculate every order the boxes can be placed
    private void permute(java.util.List<CalcBox> arr, int k){
        for(int i = k; i < arr.size(); i++){
            java.util.Collections.swap(arr, i, k);
            permute(arr, k+1);
            java.util.Collections.swap(arr, k, i); //swaps back/backtracks
        }

        if (k == arr.size() -1){
            CalcBox[] finArr = new CalcBox[arr.size()];
            for (int i = 0; i < arr.size(); i++){
                finArr[i] = arr.get(i);
            }

            allPermutations.add(finArr);
        }
    }

    public CalcBox[][] getPermutations(CalcBox[] boxes){
        ArrayList<CalcBox> boxArrLi = new ArrayList<>();
        //converts box array into ArrayList to permute
        for (int i = 0; i < boxes.length; i++){
            boxArrLi.add(i, boxes[i]);
        }

        this.permute(boxArrLi, 0);

        //converts back into array to return
        CalcBox[][] finArr = new CalcBox[allPermutations.size()][boxes.length];
        for (int i = 0; i < allPermutations.size(); i++){
            finArr[i] = allPermutations.get(i);
        }

        return finArr;
    }

}
