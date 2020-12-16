package pl.indexpz.taskmanager.test;

public class RemoveRow {
    public static void main(String[] args) {

    }

    public static String[][] removeRow(String[][] taskList, int rowToRemove) {
        rowToRemove = rowToRemove - 1;
        String[][] realCoppyArray = new String[taskList.length - 1][taskList[0].length];
        for (int row = 0; row < taskList.length; row++) {
            for (int column = 0; column < taskList[0].length; column++) {
                if (row < rowToRemove) {
                    realCoppyArray[row][column] = taskList[row][column];
                } else if (row > rowToRemove) {
                    realCoppyArray[row - 1][column] = taskList[row][column];
                }
            }
        }
        return realCoppyArray;
    }
}
