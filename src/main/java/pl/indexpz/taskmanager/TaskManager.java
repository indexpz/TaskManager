package pl.indexpz.taskmanager;

import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Scanner;

import static pl.coderslab.ConsoleColors.*;

public class TaskManager {

    private static String[] OPTIONS = {"add", "remove", "list", "exit"};
    private static String[] INFO_STR = {"Please select an option:"};
    private static String DATA_BASE_FILE = "src/main/java/pl/indexpz/taskmanager/tasks.csv";


    public static void main(String[] args) {
        taskManager();
    }

    public static void taskManager() {
        showOptions();
        mainLoopOption(choiceOption());
    }

    //  Czytam z pliku i tworzę tablicę/ Read from file and create array
    private static String[][] getArrayFromFile(String fileLocalisation) {
        String[][] listArray = new String[4][3];
        Path path = Paths.get(fileLocalisation);
        String[] array = new String[1];
        int rowCount = 1;
        if (Files.exists(path)) {
            try {
                for (String line : Files.readAllLines(path)) {
                    ArrayUtils.;
                    listArray[rowCount] = line.split(",");
                    System.out.println(Arrays.toString(listArray[rowCount]));
                    rowCount++;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("File not exist");
        }

        return listArray;
    }

    //    Główna pętla z wyborem polecenia / Main loop with choisen option
    private static void mainLoopOption(String choiceOption) {
        if (choiceOption.equals(OPTIONS[0])) {
            System.out.println("Wykonuję add");
            showOptions();
        } else if (choiceOption.equals(OPTIONS[1])) {
            System.out.println("Wykonuję remove");
            showOptions();
        } else if (choiceOption.equals(OPTIONS[2])) {
            System.out.println("Wykonuję list");
            getArrayFromFile(DATA_BASE_FILE);
            showOptions();
        } else if (choiceOption.equals(OPTIONS[3])) {
            System.out.println("Kończę program");
            showOptions();
        } else {
            System.out.println("Wrong method. Try again:");
        }

    }

    //    Pozwala wybrać opcję / Choice option
    private static String choiceOption() {
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
