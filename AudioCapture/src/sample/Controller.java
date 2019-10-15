package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Controller {
    @FXML
    private Button startButton;

    @FXML
    private ImageView buttonImageView;

    @FXML
    private Slider slider;

    @FXML
    private TextField directoryTextField;

    @FXML
    private Pane mainPane;

    boolean correct = false;

    private File wavFile;
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private TargetDataLine dataLine;

    private AudioFormat getAudioFormat() {
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

    class StartRecording implements Runnable {
        @Override
        public void run() {
            try {
                AudioFormat audioFormat = getAudioFormat();
                DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormat);

                //checks if system supports the data line
                if (!AudioSystem.isLineSupported(info)) {
                    System.out.println("DUPA, NIE MA WSPARCIA LINII PRZESYLOWEJ");
                    System.exit(0);
                }
                dataLine = (TargetDataLine) AudioSystem.getLine(info);
                dataLine.open(audioFormat);
                dataLine.start();
                System.out.println("Start capturing...");
                AudioInputStream ais = new AudioInputStream(dataLine);
                System.out.println("Start recording...");
                AudioSystem.write(ais, fileType, wavFile);
            } catch (LineUnavailableException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    class StopRecording implements Runnable {
        @Override
        public void run() {
            dataLine.stop();
            dataLine.close();
            System.out.println("Recording done");
        }
    }

    @FXML
    private void clickButton() {
        Image image = buttonImageView.getImage();
        if (image.getUrl().contains("play")) {
            image = new Image("file:src\\sample\\stop.png");
            slider.setDisable(true);
            Path path = Paths.get(directoryTextField.getText());
            if (slider.getValue() == 1) {
                path = Paths.get(directoryTextField.getText(), "correct");
                File f = path.toFile();
                if (!f.isDirectory())
                    f.mkdir();
            } else if (slider.getValue() == 0) {
                path = Paths.get(directoryTextField.getText(), "incorrect");
                File f = path.toFile();
                if (!f.isDirectory())
                    f.mkdir();
            }
            wavFile = Paths.get(path.toAbsolutePath().toString(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd_MM_yy HH_mm_ss")) + ".wav").toFile();
            Thread t = new Thread(new StartRecording());
            t.start();
        } else {
            image = new Image("file:src\\sample\\play.png");
            slider.setDisable(false);
            Thread t2 = new Thread(new StopRecording());
            t2.start();
        }
        buttonImageView.setImage(image);
    }

    @FXML
    private void initialize() throws IOException {
        Path pathToFileWithDefaultPath = Paths.get("src", "sample", "default_path.txt");
        File file = pathToFileWithDefaultPath.toAbsolutePath().toFile();
        BufferedReader br = new BufferedReader(new FileReader(file));
        Path defaultPath = Paths.get(br.readLine());
        directoryTextField.setText(defaultPath.toString());
    }

    @FXML
    public void chooseDirectory() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File f = Paths.get(directoryTextField.getText()).toFile();
        if (!f.isDirectory())
            f.mkdir();
        System.out.println(f.getAbsolutePath());
        directoryChooser.setInitialDirectory(Paths.get(directoryTextField.getText()).toFile());
        Stage stage = (Stage) mainPane.getScene().getWindow();
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null)
            directoryTextField.setText(selectedDirectory.getAbsolutePath());
    }
}
