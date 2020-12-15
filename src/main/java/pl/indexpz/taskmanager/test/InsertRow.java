package pl.indexpz.taskmanager.test;

public class InsertRow {
    public static void main(String[] args) {

        String[][] test = {{"a1", "a2"}, {"b1", "b2"}};
        String[] insert = {"c1", "c2"};
        int row = 2;
        test = insertRow(test, row, insert);
        for (int i = 0; i < test.length; i++) {
            for (int j = 0; j < test[i].length; j++) {
                System.out.print(test[i][j] +" ");

            }
            System.out.println();

        }

    }
    private static String[][] insertRow(String[][] listArray, int row, String[] data) {
        String[][] outArray = new String[listArray.length + 1][];
        for (int i = 0; i < row; i++) {
            outArray[i] = listArray[i];
        }
        outArray[row] = data;
        for (int i = row + 1; i < outArray.length; i++) {
            outArray[i] = listArray[i - 1];
        }
        return outArray;
    }
}
