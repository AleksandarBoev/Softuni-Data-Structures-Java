package implementations;

public class TheMatrix {
    private char[][] matrix;
    private char fillChar;
    private char toBeReplaced;
    private int startRow;
    private int startCol;

    public TheMatrix(char[][] matrix, char fillChar, int startRow, int startCol) {
        this.matrix = matrix;
        this.fillChar = fillChar;
        this.startRow = startRow;
        this.startCol = startCol;
        this.toBeReplaced = this.matrix[this.startRow][this.startCol];
    }

    public void solve() {
        fillMatrix(startRow, startCol);
    }

    public String toOutputString() {
        StringBuilder result = new StringBuilder(matrix.length * matrix[0].length + matrix.length + 1);

        for (int row = 0; row < matrix.length; row++) {
            result.append(matrix[row]).append(System.lineSeparator());
        }

        return result.substring(0, result.lastIndexOf(System.lineSeparator()));
    }

    private void fillMatrix(int row, int col) {
        if (!isSuitablePosition(row, col)) {
            return;
        }

        matrix[row][col] = fillChar;

        fillMatrix(row + 1, col); //up
        fillMatrix(row, col + 1); //right
        fillMatrix(row - 1, col); //down
        fillMatrix(row, col -1); //left
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < matrix.length && col >= 0 && col < matrix[row].length;
    }

    private boolean isSuitableCharacter(int row, int col) {
        return matrix[row][col] == toBeReplaced;
    }

    private boolean isSuitablePosition(int row, int col) {
        return isInBounds(row, col) && isSuitableCharacter(row, col);
    }
}
