package SudokuSolver;

public class Triple {

    private int first;
    private int second;
    private int third;

    public Triple(int first, int second, int third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public int getFirst() {
        return first;
    }

    public int getSecond() {
        return second;
    }

    public int getThird() {
        return third;
    }

    public int convertThis() {
        return first * 9 * 9 + second * 9 + (third - 1) + 1;
    }

    public static int convert(int first, int second, int third) {
        return first * 9 * 9 + second * 9 + (third - 1) + 1;
    }

    public static Triple decode(int src) {
        if (src > 0) {
            src--;
            return new Triple(src / 81, (src % 81) / 9, (src % 9) + 1);
        } else {
            return null;
        }
    }
}
