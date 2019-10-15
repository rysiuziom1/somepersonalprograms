package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Controller {
    @FXML
    private Button startButton;

    @FXML
    private ImageView buttonImageView;

    @FXML
    private void clickButton(ActionEvent e) {
        Image image = buttonImageView.getImage();
        if (image.getUrl().contains("play"))
            image = new Image("file:src\\sample\\stop.png");
        else
            image = new Image("file:src\\sample\\play.png");
        buttonImageView.setImage(image);


    }

}
