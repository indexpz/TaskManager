package pl.indexpz.taskmanager.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static pl.coderslab.ConsoleColors.RED;

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



    //  Główna pętla z wyborem polecenia / Main loop with choisen option
//    private static void mainLoopOption(String choiceOption, String[][] currentTaskList) {
//        if (choiceOption.equals(OPTIONS[0])) {
//            currentTaskList = addNewTask(currentTaskList);
//            showOptions();
//            mainLoopOption(insert(),currentTaskList(DATABASE_FILE));
//        } else if (choiceOption.equals(OPTIONS[1])) {
//            currentTaskList = removeFromArray(currentTaskList);
//            showOptions();
//            mainLoopOption(insert(),currentTaskList(DATABASE_FILE));
//        } else if (choiceOption.equals(OPTIONS[2])) {
//            printTaskList(currentTaskList);
//            showOptions();
//            mainLoopOption(insert(),currentTaskList(DATABASE_FILE));
//        } else if (choiceOption.equals(OPTIONS[3])) {
//            getFileFromArray(currentTaskList, DATABASE_FILE);
//            System.out.println(RED + "Bye bye :)");
//        } else {
//            System.out.println("Wrong method. Try again:");
//            mainLoopOption(insert(),currentTaskList(DATABASE_FILE));
//
//        }

//    }

}
