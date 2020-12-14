package pl.indexpz.taskmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static pl.coderslab.ConsoleColors.*;

public class TaskManager {

    private static String[] OPTIONS = {"add", "remove", "list", "exit"};
    private static String[] INFO_STR = {"Please select an option:"};
    private static String DATABASE_FILE = "src/main/java/pl/indexpz/taskmanager/tasks.csv";
    private static String[][] currentTaskList = getArrayFromFile(DATABASE_FILE);;


    public static void main(String[] args) {
        taskManager(DATABASE_FILE);
    }

    public static void taskManager(String databaseFile) {
        showOptions();
        mainLoopOption(insert());
    }

    // Dodawanie zadania / Add new task
    private static String[][] addNewTask(String[][] taskList) {
        String[][] taskListAdded = taskList;




        return taskListAdded;
    }

    //    Główna pętla z wyborem polecenia / Main loop with choisen option
    private static void mainLoopOption(String choiceOption) {
        if (choiceOption.equals(OPTIONS[0])) {
            System.out.println("Wykonuję add");
            showOptions();
            mainLoopOption(insert());
        } else if (choiceOption.equals(OPTIONS[1])) {
            System.out.println("Wykonuję remove");
            showOptions();
            mainLoopOption(insert());
        } else if (choiceOption.equals(OPTIONS[2])) {
            printTaskList(currentTaskList);
            showOptions();
            mainLoopOption(insert());
        } else if (choiceOption.equals(OPTIONS[3])) {
            System.out.println(RED + "Bye bye :)");
        } else {
            System.out.println("Wrong method. Try again:");
            mainLoopOption(insert());

        }

    }

    //  Czytam z pliku i tworzę tablicę/ Read from file and create array
    private static String[][] getArrayFromFile(String fileLocalisation) {
        String[][] taskList = new String[1][3];
        Path path = Paths.get(fileLocalisation);
        String[] array = new String[3];
        int countRow = 1;
        if (Files.exists(path)) {
            try {
                for (String line : Files.readAllLines(path)) {
                    array = line.split(", ");
                    taskList = insertRow(taskList, countRow, array);
                    countRow++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not exist");
        }
//        printTaskList(taskList);
        return taskList;
    }

    //        Wyświetla listę / Print list
    private static void printTaskList(String[][] taskList) {
        for (int i = 0; i < taskList.length; i++) {
            for (int j = 0; j < taskList[i].length; j++) {
                System.out.print(i + ": " + taskList[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Dodawanie wiersza do tablicy dwuwymiarowej / Adding new row to array two dimensional
    private static String[][] insertRow(String[][] taskList, int row, String[] data) {
        String[][] outArray = new String[taskList.length + 1][];
        for (int i = 0; i < row; i++) {
            outArray[i] = taskList[i];
        }
        outArray[row] = data;
        for (int i = row + 1; i < outArray.length; i++) {
            outArray[i] = taskList[i - 1];
        }
        return outArray;
    }


    //    Pozwala wybrać opcję / Choice option
    private static String insert() {
        Scanner scanner = new Scanner(System.in);
        String chosenOption = scanner.nextLine();
        return chosenOption;
    }

    //  Pokazuje opcje do wyboru / Show options to choice
    private static void showOptions() {
        System.out.println(BLUE + INFO_STR[0]);
        System.out.println(RESET + OPTIONS[0]);
        System.out.println(RESET + OPTIONS[1]);
        System.out.println(RESET + OPTIONS[2]);
        System.out.println(RESET + OPTIONS[3]);
    }
}
