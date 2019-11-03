package sample;

import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Controller {
    @FXML
    private Pane rootPane;

    @FXML
    private void flyMyBoy(ActionEvent e) {
        Button bt = (Button) e.getSource();
        Rectangle rect = new Rectangle();
        ImageView imageView = (ImageView)bt.getChildrenUnmodifiable().get(0);
        rect.setX(imageView.getLayoutX());
        rect.setY(imageView.getLayoutY());
        rect.setWidth(imageView.getFitWidth());
        rect.setHeight(imageView.getFitHeight());

        Image image = imageView.getImage();
        rect.setFill(new ImagePattern(image));
        rootPane.getChildren().add(rect);

        ScaleTransition st = new ScaleTransition(Duration.seconds(3), rect);
        st.setToX(1.1);
        st.setToY(1.1);

        double xStart = rect.getX() + rect.getWidth() / 2;
        double yStart = rect.getY() + rect.getHeight() / 2;
        double yEnd = yStart + rootPane.getHeight() + rect.getHeight() / 2;
        Line line = new Line(xStart, yStart, xStart, yEnd);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.seconds(1));
        pathTransition.setPath(line);
        pathTransition.setNode(rect);

        SequentialTransition sequentialTransition = new SequentialTransition(st, pathTransition);
        sequentialTransition.play();
    }
}
