package main;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.*;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

//X = width, Y = length, Z = height
public class main extends Application{
    @Override
    public void start(Stage stage) {
        //Colors
        PhongMaterial blue = new PhongMaterial();
        blue.setDiffuseColor(Color.BLUE);

        PhongMaterial red = new PhongMaterial();
        red.setDiffuseColor(Color.RED);

        PhongMaterial green = new PhongMaterial();
        green.setDiffuseColor(Color.GREEN);

        PhongMaterial purple = new PhongMaterial();
        purple.setDiffuseColor(Color.PURPLE);

        PhongMaterial teal = new PhongMaterial();
        teal.setDiffuseColor(Color.TEAL);

        PhongMaterial black = new PhongMaterial();
        black.setDiffuseColor(Color.BLACK);

        PhongMaterial[] colors = new PhongMaterial[]{black, red, blue, green, purple, teal};

        //Input boxes here
        /*
        CalcBox a = new CalcBox(2, 5, 5, new Point3D(0, 0, 0));
        CalcBox b = new CalcBox(2, 7, 7, new Point3D(0, 0, 0));
        CalcBox c = new CalcBox(4, 4, 4, new Point3D(0, 0, 0));
        CalcBox d = new CalcBox(2, 8, 4, new Point3D(0, 0, 0));
        CalcBox e = new CalcBox(2, 2, 2, new Point3D(0, 0, 0));
         */

        //Preset Boxes for fair
        CalcBox a = new CalcBox(2,  10, 10, new Point3D(0, 0, 0));
        CalcBox b = new CalcBox(5, 5, 5, new Point3D(0, 0, 0));
        CalcBox c = new CalcBox(6.5, 6.5, 4, new Point3D(0, 0, 0));
        CalcBox d = new CalcBox(2.5, 10, 5, new Point3D(0, 0, 0));
        CalcBox e = new CalcBox(4, 4, 4, new Point3D(0, 0, 0));

        BoxArray myBoxArray = new BoxArray(new CalcBox[]{a, b, c, d, e});



        CalcBox[] best = myBoxArray.findBestBox();

        //Creating a Group object
        Group root = new Group();


        for (int i = 0; i < best.length; i++){
            Box display = new DisplayBox(best[i]).getBox();
            display.setMaterial(colors[i]);
            root.getChildren().add(display);
        }

        //Lighting
        AmbientLight light = new AmbientLight(Color.WHITE);
        root.getChildren().add(light);

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
