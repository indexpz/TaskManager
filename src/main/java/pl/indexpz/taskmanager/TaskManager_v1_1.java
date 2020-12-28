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

public class TaskManager_v1_1 {
    final static String[] OPTION = {"add", "remove", "list", "exit"};
    final static String DIR = "src/main/java/pl/indexpz/taskmanager/tasks.csv";

    public static void main(String[] args) {
        taskManager();
    }

    public static void taskManager() {
        loop(OPTION, arrayCSV(DIR));
    }

    //    główna pętla programu
    private static void loop(String[] options, String[][] operatingArray) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("exit")) {
            showOptions(options);
            input = scanner.nextLine();
            arrayCSV(DIR);
            switch (input) {
                case "add":
                    operatingArray = add(operatingArray);
                    break;
                case "remove":
                    operatingArray = remove(operatingArray);
                    break;
                case "list":
                    showArrayCSV(operatingArray);
                    break;
                case "exit":
                    writeToFile(operatingArray, DIR);
                    System.out.println(RED + "Bye bye");
                    break;
                default:
                    System.out.println(YELLOW + "Please try again");
                    break;
            }
        }
    }

    private static String[][] add(String[][] array) {
        String[] element = new String[3];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        element[0] = scanner.nextLine();
        System.out.println("Please add task due date");
        element[1] = scanner.nextLine();
        System.out.println("Is your task important: true/false");
        while (!scanner.hasNextBoolean()) {
            scanner.nextLine();
            System.out.println("Try again");
        }
        element[2] = scanner.nextLine();

        String[][] newArray = addNewElementToArray(array, element);
        return newArray;
    }

    //    usuwanie elementu z tablicy
    private static String[][] remove(String[][] operatingArray) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the line number to delete task:");

        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.println("Given value is not an integer!");

        }
        int row = scanner.nextInt();
        while (row < 0 || row > operatingArray.length) {
            scanner.nextLine();
            System.out.println("There is no task with this number!");
        }

        return removeElementFromArray(operatingArray, row);

    }

    //    wczytanie pliku csv do tabeli dwuwymiarowej
    private static String[][] arrayCSV(String dir) {
        String[][] arrayFromCSV = new String[0][3];
        Path path = Paths.get(dir);
        if (!Files.exists(path)) {
            System.out.println(YELLOW_BOLD + "File not exist.");
            System.exit(0);
        }
        try {
            for (String line : Files.readAllLines(path)) {
                String[] row = line.split(", ");
                arrayFromCSV = addNewElementToArray(arrayFromCSV, row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        arrayFromCSV = removeComma(arrayFromCSV);
        return arrayFromCSV;
    }

    // dodanie nowego elementu do tablicy
    private static String[][] addNewElementToArray(String[][] array, String[] element) {
        String[][] newArray = Arrays.copyOf(array, array.length + 1);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    private static String[][] removeElementFromArray(String[][] operatingArray, int row) {
        String[][] newShortenArray = new String[operatingArray.length - 1][3];
        for (int i = 0; i < operatingArray.length; i++) {
            if (i < row - 1) {
                newShortenArray[i] = operatingArray[i];
            } else if (i > row - 1) {
                newShortenArray[i - 1] = operatingArray[i];
            }

        }

        return newShortenArray;
    }


    //  Pokazuje wszystkie opcje
    private static void showOptions(String[] options) {
        System.out.println(BLUE_BOLD + "Please select an otpion:");
        for (String optionsToShow : options) {
            System.out.println(RESET + optionsToShow);
        }
    }

    //    wyświetlenie tablicy
    private static void showArrayCSV(String[][] array) {
        for (int row = 0; row < array.length; row++) {
            System.out.print((row + 1) + ": ");
            for (int col = 0; col < array[row].length; col++) {
                System.out.print(array[row][col] + "  ");
            }
            System.out.println();
        }
    }

    //    aktualna tablica
    private static String[][] operatingArray() {
        return arrayCSV(DIR);
    }

    private static void writeToFile(String[][] operatingArray, String dir) {
        Path path = Paths.get(dir);
        operatingArray = addComma(operatingArray);
        List<String> outList = new ArrayList<>();
        for (int row = 0; row < operatingArray.length; row++) {
            String line = String.join(", ", operatingArray[row]);
            outList.add(line);
        }
        try {
            Files.write(path, outList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static String[][] removeComma(String[][] operatingArray) {
        for (int row = 0; row < operatingArray.length; row++) {
            for (int col = 0; col < operatingArray[row].length; col++) {
                if (operatingArray[row][col].contains("'comma'")) {
                    operatingArray[row][col]= operatingArray[row][col].replaceAll("'comma'", ",");
                }
            }
        }
        return operatingArray;
    }

    private static String[][] addComma(String[][] operatingArray) {
        for (int row = 0; row < operatingArray.length; row++) {
            for (int col = 0; col < operatingArray[row].length; col++) {
               if (operatingArray[row][col].contains(",")) {
                   operatingArray[row][col]= operatingArray[row][col].replaceAll(",", "'comma'");
                }
            }
        }
        return operatingArray;
    }
}
