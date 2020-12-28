package pl.indexpz.taskmanager;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.routines.DateValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static pl.coderslab.ConsoleColors.*;

public class TaskManager {

    final static String[] OPTIONS = {"add", "remove", "list", "exit"};
    final static String[] INFO_STR = {"Please select an option:"};
    final static String DATABASE_FILE = "src/main/java/pl/indexpz/taskmanager/tasks.csv";
    final static String[] HEADER = {"Tasks", "Data", "Is it important"};
    final static String DATAPATTERN = "yyyy-MM-dd";


    public static void main(String[] args) {
        taskManager();
    }

    public static void taskManager() {
        showOptions();
        mainLoopOption(currentTaskList());
    }

    //  Pokazuje opcje do wyboru / Show options to choice
    private static void showOptions() {
        System.out.println(BLUE + INFO_STR[0]);
        System.out.println(RESET + OPTIONS[0]);
        System.out.println(RESET + OPTIONS[1]);
        System.out.println(RESET + OPTIONS[2]);
        System.out.println(RESET + OPTIONS[3]);
    }

    //  Główna pętla z wyborem polecenia / Main loop with choisen option
    private static void mainLoopOption(String[][] currentTaskList) {
        String answer = insert();
        while (!answer.equals(OPTIONS[3])) {
            showOptions();

            if (answer.equals(OPTIONS[0])) {
                currentTaskList = addNewTask(currentTaskList);
                answer = insert();
            } else if (answer.equals(OPTIONS[1])) {
                currentTaskList = removeFromArray(currentTaskList);
                answer = insert();
            } else if (answer.equals(OPTIONS[2])) {
                printTaskList(currentTaskList);
                answer = insert();
            } else {
                System.out.println("Wrong method. Try again:");
                answer = insert();
            }

            if (answer.equals(OPTIONS[3])) {
                getFileFromArray(currentTaskList, DATABASE_FILE);
                System.out.println(RED + "Bye bye :)");
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
        taskList = commaFromFileToArray(taskList);
        return taskList;
    }

    // Dodawanie zadania / Add new task
    private static String[][] addNewTask(String[][] taskList) {
        String[] taskRowArray = new String[3];
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the subject of the task:");
        taskRowArray[0] = insert();

        System.out.println("Enter an end date (YYYY-MM-DD):");
        String date = scanner.nextLine();
//        while (!isValid(date)){
//            date = scanner.nextLine();
//            System.out.println("Wrong data pattern was given (YYYY-MM-DD)");
//        }

        taskRowArray[1] = date;


        System.out.println("Is the task important?");
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
        int maxLenghtCell = maxLenghtTaskCell(taskList);
        int lenghtCell = 0;
        int intSpaceToAdd = maxLenghtCell - lenghtCell;
        System.out.println(BLUE + "    " + HEADER[0] + spaceToAdd(maxLenghtCell - HEADER[0].length()) + HEADER[1] + "         " + HEADER[2]);
        for (int i = 0; i < taskList.length; i++) {

            if (i >= 0 && i < 9) {
                System.out.print(RESET + " " + (i + 1) + ": ");
            } else {
                System.out.print(RESET + (i + 1) + ": ");
            }
            for (int j = 0; j < taskList[i].length; j++) {
                lenghtCell = lenghtTaskCell(taskList, i, 0);
                intSpaceToAdd = maxLenghtCell - lenghtCell;
                if (taskList[i][taskList[i].length - 1].equals("true") && j == 0) {
                    System.out.print(PURPLE + taskList[i][j] + spaceToAdd(intSpaceToAdd));

                } else if (taskList[i][taskList[i].length - 1].equals("false") && j == 0) {
                    System.out.print(CYAN + taskList[i][j] + spaceToAdd(intSpaceToAdd));
                } else if (j == 1) {
                    System.out.print(taskList[i][j] + "   ");
                } else {
                    System.out.print(taskList[i][j]);
                }


            }
            System.out.println();
        }
        System.out.println();
    }

    //  Czyta z tabicy i zapisuję do pliku - komenda exit/ Read from array and write to the file - the exit command
    public static void getFileFromArray(String[][] taskList, String dbFile) {
        taskList = commaFromArrayToFile(taskList);
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

        }
        try {
            Files.write(path, outList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Dodawanie wiersza do tablicy dwuwymiarowej / Adding new row to array two dimensional
    private static String[][] insertRow(String[][] taskList, int newRow, String[] data) {
        String[][] outArray = new String[taskList.length + 1][];
//        kopiujemy stare wiersze
        for (int row = 0; row < newRow; row++) {
            outArray[row] = taskList[row];
        }
//        dodajemy nowy wiersz
        outArray[newRow] = data;
        for (int row = newRow + 1; row < outArray.length; row++) {
            outArray[row] = taskList[row - 1];
        }
        return outArray;
    }

    //  Usuwa wiersz z tablicy dwuwymiarowej /Removes a row from a two-dimensional array
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

    // Bieżąca tablica / current array
    private static String[][] currentTaskList() {
        return getArrayFromFile(DATABASE_FILE);
    }


    // Formatowanie wyświetlania tabeli
    private static int maxLenghtTaskCell(String[][] taskList) {
        int max = 0;
        for (int row = 0; row < taskList.length; row++) {
            if (taskList[row][0].length() > max) {
                max = taskList[row][0].length();
            }
        }
        return max;
    }

    private static int lenghtTaskCell(String[][] taskList, int row, int column) {
        int lenght = 0;
        for (int i = 0; i < taskList.length; i++) {
            lenght = taskList[row][column].length();
        }
        return lenght;
    }

    private static String spaceToAdd(int number) {
        String sum = "";
        for (int i = 0; i <= number + 2; i++) {
            sum += " ";
        }
        return sum;
    }


    //    Konwertowanie przecinka przy zapisie do tabeli
    private static String[][] commaFromFileToArray(String[][] tasklist) {
        String[][] workingArray = new String[tasklist.length][tasklist[0].length];
        for (int row = 0; row < tasklist.length; row++) {
            for (int column = 0; column < tasklist[row].length; column++) {
                workingArray[row][column] = tasklist[row][column].replaceAll("'comma'", ",");
            }
        }
        return workingArray;
    }

    //    Konwertowanie przecinka przy zapisie do pliku na 'comma'
    private static String[][] commaFromArrayToFile(String[][] tasklist) {
        String[][] workingArray = new String[tasklist.length][tasklist[0].length];
        for (int row = 0; row < tasklist.length; row++) {
            for (int column = 0; column < tasklist[row].length; column++) {
                workingArray[row][column] = tasklist[row][column].replaceAll(",", "'comma'");
            }
        }
        return workingArray;
    }

//public static Date validate(String value){
//
//}

    public static boolean isValid(String date) {

        boolean valid = false;

        try {

            // ResolverStyle.STRICT for 30, 31 days checking, and also leap year.
            LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd").withResolverStyle(ResolverStyle.STRICT)
            );

            valid = true;

        } catch (DateTimeParseException e) {
            e.printStackTrace();
            valid = false;
        }

        return valid;
    }

}