package sample;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Controller {
    @FXML
    private Pane rootPane;

    @FXML
    private void flyMyBoy(ActionEvent e) {
        Button bt = (Button) e.getSource();
        Rectangle rect2 = new Rectangle();
        rect2.setX(bt.getLayoutX());
        rect2.setY(bt.getLayoutY());
        rect2.setWidth(bt.getWidth());
        rect2.setHeight(bt.getHeight());
        rootPane.getChildren().add(rect2);
        double xStart = rect2.getX() + rect2.getWidth() / 2;
        double yStart = rect2.getY() + rect2.getHeight() / 2;
        double yEnd = rootPane.getHeight() + rect2.getHeight() / 2;
        Line line = new Line(xStart, yStart, xStart, yEnd);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setPath(line);
        pathTransition.setNode(rect2);
        pathTransition.play();
    }
}
