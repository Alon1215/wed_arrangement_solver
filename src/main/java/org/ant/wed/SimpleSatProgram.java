import com.google.ortools.sat.CpModel;
import com.google.ortools.sat.CpSolver;
import com.google.ortools.sat.CpSolverStatus;
import com.google.ortools.sat.IntVar;

/** Minimal CP-SAT example to showcase calling the solver. */
public class SimpleSatProgram {
    static {
        System.loadLibrary("jniortools");
    }

    public static void main(String[] args) throws Exception {
        // Create the model.
        CpModel model = new CpModel();

        // Create the variables.
        int numVals = 3;

        IntVar x = model.newIntVar(0, numVals - 1, "x");
        IntVar y = model.newIntVar(0, numVals - 1, "y");
        IntVar z = model.newIntVar(0, numVals - 1, "z");

        // Create the constraints.
        model.addDifferent(x, y);

        // Create a solver and solve the model.
        CpSolver solver = new CpSolver();
        CpSolverStatus status = solver.solve(model);

        System.out.println(status);
        if (status == CpSolverStatus.FEASIBLE || status == CpSolverStatus.OPTIMAL) {
            System.out.println("x = " + solver.value(x));
            System.out.println("y = " + solver.value(y));
            System.out.println("z = " + solver.value(z));
        }
    }
}