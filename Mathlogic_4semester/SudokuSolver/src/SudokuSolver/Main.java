package SudokuSolver;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.ParseFormatException;
import org.sat4j.reader.Reader;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.TimeoutException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

    public static String IN_FILENAME = "input.txt";
    public static String OUT_FILENAME = "result.txt";

    public static void main(String[] args) {
        List<Triple> inputData = Fileworker.getInput(IN_FILENAME);
        BoolGenerator generator = new BoolGenerator(inputData);
        generator.generate();
        ISolver solver = SolverFactory.newDefault();
        solver.setTimeout(3600); // 1 hour timeout
        Reader reader = new DimacsReader(solver);
        try {
            IProblem problem = reader.parseInstance("boolLanguage.txt");
            if (problem.isSatisfiable()) {
                System.out.println("Solution exists! See 'result.txt'");
                int[] res = problem.model();
                Fileworker.printResult(res, OUT_FILENAME);
            } else {
                System.out.println("No solution!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ContradictionException e) {
            System.out.println("Unsatisfiable (trivial)!");
        } catch (TimeoutException e) {
            System.out.println("Timeout, sorry!");
        }
    }
}
