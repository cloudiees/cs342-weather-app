import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BackButton extends Button {
    // Generates a back button
    public BackButton() {
        super();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("back.png")));
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        this.setGraphic(imageView);

    }
}
