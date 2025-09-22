import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

public class Header {
    public BackButton backButton;
    public TextField header;
    public HBox hbox;
    public Region spacer, spacer2;
    public Rectangle invisSpacer;
    // Generates a header given the header text and what type of header needs to be generated
    public Header(String headerTxt) {
        // Generating header text field
        header = new TextField(headerTxt);
        header.setEditable(false);
        header.getStyleClass().add("header");
        // Setting the header text to a reasonable size
        header.setPrefColumnCount(headerTxt.length() / 2 + 4);
        // Generating a back button
        backButton = new BackButton();
        // Generating spacers for the header
        spacer = new Region();
        spacer2 = new Region();
        // This makes sure the regions grow with the window size
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        // Leaving the right most item up to the child classes
        rightItemAndCreate();
        paddingAndAlignment();
    }
    // Creates the HBox and the right most element
    // Either to be overridden or use this default class
    public void rightItemAndCreate(){
        // Generating a spacer so the text is centered/consistent with other headers
        invisSpacer = new Rectangle(51, 51);
        invisSpacer.getStyleClass().add("invisSpacer");
        // Creating the HBox for all elements
        hbox = new HBox(backButton, spacer, header, spacer2, invisSpacer);
    }
    public void paddingAndAlignment(){
        // Adding alignment and padding stuff
        hbox.setPadding(new Insets(10));
        hbox.setAlignment(Pos.CENTER);
    }
}
