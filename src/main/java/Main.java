import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Creating a Box
        Box box = new Box(100, 100, 100);

        // Creating material
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLUE);
        box.setMaterial(material);

        // Creating rotation
        Rotate rotateX = new Rotate(20, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(20, Rotate.Y_AXIS);
        box.getTransforms().addAll(rotateX, rotateY);

        // Creating a Group object
        Group root = new Group(box);

        // Creating a scene object
        Scene scene = new Scene(root, 600, 400, true);
        scene.setFill(Color.LIGHTGRAY);

        // Setting camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-300);
        scene.setCamera(camera);

        // Setting title to the Stage
        primaryStage.setTitle("3D Rotation in JavaFX");

        // Adding scene to the stage
        primaryStage.setScene(scene);

        // Displaying the contents of the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

