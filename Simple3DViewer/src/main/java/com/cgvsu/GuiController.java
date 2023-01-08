package com.cgvsu;

import com.cgvsu.objreader.Triangulation;
import com.cgvsu.render_engine.RenderEngine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.vecmath.Vector3f;

import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private final ArrayList<Model> mesh = new ArrayList<>();

    private File classicFile;

    private final Camera camera = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (!mesh.isEmpty()) {
                for (int i = 0; i < mesh.size(); i++) {
                    RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh.get(i), (int) width, (int) height);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        classicFile = file;
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());


        try {
            String fileContent = Files.readString(fileName);
            mesh.add(ObjReader.read(fileContent));

            ObservableList<String> langs = FXCollections.observableArrayList(fileName.toString());

            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    private void onSaveModelMenuItemClick() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        File file = fileChooser.showSaveDialog((Stage) canvas.getScene().getWindow());

        if (file == null) {
            return;
        }

        ObjWriter.writeToFile(mesh.get(0), file);
    }


    @FXML
    private void triangulationButton() {
        for (int i = 0; i < mesh.size(); i++) {
            mesh.get(i).polygons = Triangulation.triangulatePolygons(mesh.get(i).polygons);
        }
    }

    @FXML
    private void deleteOneButton() {
        if (!mesh.isEmpty()) {
            mesh.remove(0);
        }
    }

    @FXML
    private void deleteTwoButton() {
        if (!mesh.isEmpty()) {
            if (mesh.size() >= 2) {
                mesh.remove(1);
            }
        }
    }

    @FXML
    private void deleteThreeButton() {
        if (!mesh.isEmpty()) {
            if (mesh.size() >= 3) {
                mesh.remove(2);
            }
        }
    }

    @FXML
    private void deleteFourButton() {
        if (!mesh.isEmpty()) {
            if (mesh.size() >= 4) {
                mesh.remove(3);
            }
        }
    }

    @FXML
    private void deleteFiveButton() {
        if (!mesh.isEmpty()) {
            if (mesh.size() >= 5) {
                mesh.remove(4);
            }
        }
    }


    @FXML
    private void deleteAll() {
        mesh.clear();
    }



    @FXML
    public void classicButton() {
        File file = classicFile;
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh.add(ObjReader.read(fileContent));
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }
}