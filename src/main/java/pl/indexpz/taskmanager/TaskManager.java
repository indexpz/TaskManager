package pl.indexpz.taskmanager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static pl.coderslab.ConsoleColors.*;

public class TaskManager {

    final static String[] OPTIONS = {"add", "remove", "list", "exit"};
    final static String[] INFO_STR = {"Please select an option:"};
    final static String DATABASE_FILE = "src/main/java/pl/indexpz/taskmanager/tasks.csv";

    public static void main(String[] args) {
        taskManager(DATABASE_FILE);
    }

    public static void taskManager(String databaseFile) {
        showOptions();
        mainLoopOption(currentTaskList(DATABASE_FILE));
    }


    //  Główna pętla z wyborem polecenia / Main loop with choisen option
    private static void mainLoopOption(String[][] currentTaskList) {
//

        String answer = insert();
        while (!answer.equals(OPTIONS[3])) {

            if (answer.equals(OPTIONS[0])) {
                currentTaskList = addNewTask(currentTaskList);
//                System.out.print(Arrays.toString(currentTaskList));
                showOptions();
                answer = insert();
            } else if (answer.equals(OPTIONS[1])) {
                currentTaskList = removeFromArray(currentTaskList);
                showOptions();
                answer = insert();
            } else if (answer.equals(OPTIONS[2])) {
                printTaskList(currentTaskList);
                showOptions();
                answer = insert();
            } else if (answer.equals(OPTIONS[3])) {
                getFileFromArray(currentTaskList, DATABASE_FILE);
                System.out.println(RED + "Bye bye :)");
            } else {
                System.out.println("Wrong method. Try again:");
                answer = insert();
            }

        }

    }

    //  Czytam z pliku i tworzę tablicę/ Read from file and create array
    private static String[][] getArrayFromFile(String dbFile) {
        String[][] taskList = new String[0][3];
        Path path = Paths.get(dbFile);
        int countRow = 0;
        if (Files.exists(path)) {
            try {
                for (String line : Files.readAllLines(path)) {
                    String[] array = line.split(", ");
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

    // Dodawanie zadania / Add new task
    private static String[][] addNewTask(String[][] taskList) {
        String[] taskRowArray = new String[3];

        System.out.println("Enter the subject of the task:");
        taskRowArray[0] = insert();
        System.out.println("Enter an end date (YYYY-MM-DD):");
        taskRowArray[1] = insert();
        System.out.println("Is the task important?");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextBoolean()) {
            scanner.nextLine();
            System.out.println("Wrong parameter was given (true or false)");
        }
        taskRowArray[2] = scanner.nextLine();

        return insertRow(taskList, taskList.length, taskRowArray);
    }

    // Usuwa podany wiersz z tablicy dwuwymiarowej / Removes the specified row from a two-dimensional array
    private static String[][] removeFromArray(String[][] taskList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the line number to delete task:");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("There is no task with this number or the given value is not an integer!");
        }

        int rowNumber = scanner.nextInt();
        if (rowNumber < 0 || rowNumber > taskList.length) {
            System.out.println("There is no task with this number!");
            return taskList;
        }
        return removeRow(taskList, rowNumber);
    }

    //  Wyświetla listę / Print list
    private static void printTaskList(String[][] taskList) {
        for (int i = 0; i < taskList.length; i++) {
            System.out.print((i + 1) + ": ");
            for (int j = 0; j < taskList[i].length; j++) {
                System.out.print(taskList[i][j] + " ");
            }
            System.out.println();
        }
    }

    //  Czyta z tabicy i zapisuję do pliku - komenda exit/ Read from array and write to the file - the exit command
    //  poprawione
    public static void getFileFromArray(String[][] taskList, String dbFile) {
        Path path = Paths.get(dbFile);
        List<String> outList = new ArrayList<>();
        for (int row = 0; row < taskList.length; row++) {
            String line = "";
            for (int column = 0; column < taskList[row].length; column++) {
                if (column == taskList[row].length - 1) {
                    line += taskList[row][column];
                } else {
                    line += taskList[row][column] + ", ";
                }
            }
            outList.add(line);
            try {
                Files.write(path, outList);
            } catch (IOException e) {
                e.printStackTrace();
            }
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


    //  Usuwa wiersz z tablicy dwuwymiarowej /Removes a row from a two-dimensional array
    //  poprawione
    public static String[][] removeRow(String[][] taskList, int rowToRemove) {
        rowToRemove = rowToRemove - 1;
        String[][] realCoppyArray = new String[taskList.length - 1][taskList[0].length];
        for (int row = 0; row < taskList.length; row++) {
            if (row < rowToRemove) {
                realCoppyArray[row] = taskList[row];
            } else if (row > rowToRemove) {
                realCoppyArray[row - 1] = taskList[row];
            }
        }
        return realCoppyArray;
    }

    //  Pozwala wybrać opcję / Choice option
    private static String insert() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    //  Pokazuje opcje do wyboru / Show options to choice
    private static void showOptions() {
        System.out.println(BLUE + INFO_STR[0]);
        System.out.println(RESET + OPTIONS[0]);
        System.out.println(RESET + OPTIONS[1]);
        System.out.println(RESET + OPTIONS[2]);
        System.out.println(RESET + OPTIONS[3]);
    }

    // Bieżaca tablica / current array
    private static String[][] currentTaskList(String dbFile) {
        return getArrayFromFile(DATABASE_FILE);
    }


}