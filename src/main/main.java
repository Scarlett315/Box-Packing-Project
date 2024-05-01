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

        PhongMaterial black = new PhongMaterial();
        black.setDiffuseColor(Color.BLACK);

        PhongMaterial[] colors = new PhongMaterial[]{black, red, blue, green, purple};

        //Calculations
        CalcBox myCalcBox1 = new CalcBox(3, 3, 3, new Point3D(0, 0, 0));
        CalcBox myCalcBox2 = new CalcBox(6, 6, 6, new Point3D(-4, 4, 3));
        CalcBox myCalcBox3 = new CalcBox(8, 8, 8, new Point3D(-10, 2, -0));
        CalcBox myCalcBox4 = new CalcBox(5, 5, 5, new Point3D(-10, 2, -0));
        BoxArray myBoxArray = new BoxArray(new CalcBox[]{myCalcBox1, myCalcBox2, myCalcBox3, myCalcBox4});

        CalcBox[] best = myBoxArray.findBestBox();

        Box bestBox = new DisplayBox(best[0]).getBox();
        bestBox.setMaterial(colors[0]);
        Box a = new DisplayBox(best[1]).getBox();
        a.setMaterial(colors[1]);
        Box b = new DisplayBox(best[2]).getBox();
        b.setMaterial(colors[2]);
        Box c = new DisplayBox(best[3]).getBox();
        c.setMaterial(colors[3]);
        Box d = new DisplayBox(best[4]).getBox();
        d.setMaterial(colors[4]);


        //Lighting
        AmbientLight light = new AmbientLight(Color.WHITE);


        //Creating a Group object
        Group root = new Group(light, bestBox, a, b, c, d);

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
