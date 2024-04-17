package main;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Box;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

//X = width, Y = length, Z = height
public class SimpleBox extends Application{
    @Override
    public void start(Stage stage) {
        //Colors
        PhongMaterial blue = new PhongMaterial();
        blue.setDiffuseColor(Color.BLUE); // Set diffuse color (the main color of the object)

        PhongMaterial red = new PhongMaterial();
        red.setDiffuseColor(Color.RED);

        PhongMaterial black = new PhongMaterial();
        black.setDiffuseColor(Color.BLACK);

        //Calculations
        CalcBox myCalcBox1 = new CalcBox(8, 3, 3, new Point3D(0, 0, 0));
        CalcBox myCalcBox2 = new CalcBox(5, 12, 13, new Point3D(0, 0, 0));
        //myCalcBox1.moveBox(0, myCalcBox2.getPointArr()[4]);
        CalcBox bounding = new TwoBoxes(myCalcBox1, myCalcBox2).calcBoundingBox();
        TwoBoxes test = new TwoBoxes(myCalcBox1, myCalcBox2);

        /*
        Box display1 = new displayBox(myCalcBox1).getBox();
        display1.setMaterial(blue);
        Box display2 = new displayBox(myCalcBox2).getBox();
        display2.setMaterial(red);
        Box display3 = new displayBox(bounding).getBox();
        display3.setMaterial(black);
        System.out.println(bounding.toString());
        System.out.println(myCalcBox1.toString());
        System.out.println(myCalcBox2.toString());
         */


        CalcBox[] best = test.findBestBox();
        Box display1 = new displayBox(best[1]).getBox(); //a
        display1.setMaterial(blue);
        Box display2 = new displayBox(best[2]).getBox(); //b
        display2.setMaterial(red);
        Box display3 = new displayBox(best[0]).getBox(); //bounding
        display3.setMaterial(black);

        //Lighting
        AmbientLight light = new AmbientLight(Color.WHITE);


        //Creating a Group object
        Group root = new Group(light, display1, display2, display3);

        //Creating a scene object
        Scene scene = new Scene(root, 800, 800);

        //camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateX(15);
        camera.setTranslateY(-20);
        camera.setTranslateZ(-15);

        Rotate cameraRotateX = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
        Rotate cameraRotateY = new Rotate(0, 0, 0, 0, Rotate.Y_AXIS);
        cameraRotateX.setAngle(-35);
        cameraRotateY.setAngle(-35);
        camera.getTransforms().addAll(cameraRotateY, cameraRotateX);

        camera.setFieldOfView(60);

        scene.setCamera(camera);

        //Setting title to the Stage
        stage.setTitle("Test");

        //Adding scene to the stage
        stage.setScene(scene);

        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String[] args){
        javafx.application.Application.launch(args);

    }
}
