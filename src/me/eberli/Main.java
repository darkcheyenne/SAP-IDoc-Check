package me.eberli;

import com.sap.conn.jco.JCoException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author Andreas
 */
public class Main extends Application {

    // Deklaration der globalen Variablen
    private ObservableList<IDocStatus> sapServerList;
    private TableView<IDocStatus> idocStatusTableView;
    private TableColumn iDocsPendingCol;

    @Override
    public void start(Stage primaryStage) {
        // Deklaration der lokalen Variablen
        Scene scene;
        final VBox vbox;
        final Label titelLabel;
        TextArea LogOutputTA;
        Button updateBtn;
        
        // Instanzierung der globalen Variablen
        sapServerList = Util.getDestinations();
        idocStatusTableView = new TableView();

        // Instanzierung der lokalen Variablen
        LogOutputTA = TextAreaBuilder.create()
                .prefWidth(500)
                .prefHeight(200)
                .wrapText(true)
                .build();
        scene = new Scene(new Group());

        // Konfiguration des Hauptfensters
        primaryStage.setTitle("IDoc Check (Version 0.11)");
        primaryStage.setWidth(535);
        primaryStage.setHeight(780);

        // Konfiguration des Layout Managers
        vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));

        titelLabel = new Label("IDoc Check (Version 0.11)");
        titelLabel.setFont(new Font("Arial", 20));

        // Konfiguration der Log-Output-Box
        ConsoleLog.outputLog = LogOutputTA;

        // Konfiguration des Update-Buttons
        updateBtn = new Button("Update");
        updateBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                updateData();
            }
        });

        // Konfiguration der Tabelle der Systeme
        konfigurationDerIdocTabelle();

        // Hinzufügen der Element zum Layout Manager
        vbox.getChildren().addAll(titelLabel, updateBtn, idocStatusTableView, LogOutputTA);

        // HInzufügen von Layout-Manager zu Fenster
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        // Anzeigen des ganzen Fensters
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("bal.png")));
        primaryStage.show();
    }

    private void konfigurationDerIdocTabelle() {
        idocStatusTableView.setEditable(false);
        idocStatusTableView.setMinSize(450, 450);

        TableColumn systemNameCol = new TableColumn("System");
        systemNameCol.setCellValueFactory(new PropertyValueFactory<IDocStatus, String>("Systemname"));
        TableColumn status30Col = new TableColumn("Status 53");
        status30Col.setCellValueFactory(new PropertyValueFactory<IDocStatus, Integer>("Status53"));
        TableColumn status64Col = new TableColumn("Status 64");
        status64Col.setCellValueFactory(new PropertyValueFactory<IDocStatus, Integer>("Status64"));
        iDocsPendingCol = new TableColumn("Warten auf Job");
        iDocsPendingCol.setCellValueFactory(new PropertyValueFactory<IDocStatus, Integer>("PendingIDocs"));

        idocStatusTableView.getColumns().addAll(systemNameCol, status30Col, status64Col, iDocsPendingCol);

        idocStatusTableView.setItems(sapServerList);

        idocStatusTableView.getSortOrder().add(iDocsPendingCol);
    }

    private void updateData() {
        sapServerList.removeAll(sapServerList);
        ConsoleLog.resetEventlog();

        sapServerList = Util.getDestinations();

        for (IDocStatus iDocStatus : sapServerList) {
            Integer currentNumer = 0;
            try {
                currentNumer = ReadTable.rfcCountTableEDIDS_Stat(iDocStatus.getSystemname(), "STATUS = 53");
            } catch (JCoException ex) {
                ConsoleLog.printEvent(ex.toString());
                currentNumer = -2;
            }
            //currentNumer = 99;
            iDocStatus.setStatus53(currentNumer);
        }

        for (IDocStatus iDocStatus : sapServerList) {
            Integer currentNumer = 0;
            try {
                currentNumer = ReadTable.rfcCountTableEDIDS_Stat(iDocStatus.getSystemname(), "STATUS = 64");
            } catch (JCoException ex) {
                ConsoleLog.printEvent(ex.toString());
                currentNumer = -2;
            }
            //currentNumer = 99;
            iDocStatus.setStatus64(currentNumer);
        }
        idocStatusTableView.setItems(sapServerList);
        idocStatusTableView.getSortOrder().add(iDocsPendingCol);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
