package pl.indexpz.taskmanager.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class copy {
    public static void main(String[] args) {

    }
    //  Czytam z tabicy i zapisuję do pliku - komenda exit/ Read from array and write to the file - the exit command
    public static void getFileFromArray(String[][] taskList, String dbFile) {
        String strLine = "";
        Path path = Paths.get(dbFile);
        List<String> outList = new ArrayList<>();
        for (int i = 0; i < taskList.length; i++) {
            for (int j = 0; j < taskList[i].length; j++) {
                if (j == taskList[i].length - 1 && i == taskList.length - 1) {
                    strLine += taskList[i][j];
                } else if (j < taskList[i].length - 1) {
                    strLine += taskList[i][j] + ", ";
                } else if (j == taskList[i].length - 1) {
                    strLine += taskList[i][j] + "\n";
                }
            }
        }
        outList.add(strLine);
        try {
            Files.write(path, outList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
