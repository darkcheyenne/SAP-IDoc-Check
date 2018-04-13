package me.eberli;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Andreas
 */
public class Util {

    static void createDestinationDataFile(String destinationName, Properties connectProperties) throws FileNotFoundException, IOException {
        File destCfg = new File(destinationName + ".jcoDestination");
        try {
            FileOutputStream fos = new FileOutputStream(destCfg,
                    false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException("Unable to create the destination files", e);
        }
    }

    public static ObservableList<IDocStatus> getDestinations() {
        ObservableList<IDocStatus> returnList = FXCollections.observableArrayList();
        File folder = new File(".");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                //System.out.println("File " + listOfFiles[i].getName());
                if (listOfFiles[i].getName().contains("jcoDestination")) {
                    String systemName = listOfFiles[i].getName().replace(".jcoDestination", "");
                    IDocStatus newStatus = new IDocStatus();
                    newStatus.setSystemname(systemName);
                    returnList.add(newStatus);
                }
            } else if (listOfFiles[i].isDirectory()) {
                //System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        return returnList;
    }
}
