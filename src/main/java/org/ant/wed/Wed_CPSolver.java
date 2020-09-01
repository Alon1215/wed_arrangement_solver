package org.ant.wed;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

// [START import]
import com.google.ortools.constraintsolver.RoutingSearchParameters;
import com.google.ortools.sat.*;
;
// [END import]

/**
 * WEDDING TABLE ARRANGEMENT - LOGIC & METHODS
 * This class contains the logic of the reduction & solution we have implemented.
 * We use @Invitee as an object represents in invited person.
 *
 * @author Alon Michaeli
 * @version 1.0
 * @Wed_src contains the methods and logic to solution
 * @since 2020-08-24
 */


enum Category {
    FAMILY, FRIENDS, WORK;
}

/**
 * Logic of solution.
 * Test purpose only.
 */
public class Wed_CPSolver {
    static {
        System.loadLibrary("jniortools");
    }

    //input:
    private List<Invitee> inviteList;
    private int[][] edgesMatrix;
    private List<Integer> tables;
    //constants:
    private int invitedNumber;
    private int tablesNum;
    //vars:
    private IntVar[][][] vars;
    private IntVar[][] exps;
    //model:
    private CpModel model;


    public Wed_CPSolver(List<Invitee> inviteList, List<Integer> tables) {
        this.inviteList = inviteList;
        this.edgesMatrix = new int[inviteList.size()][inviteList.size()];
        this.tables = tables;
        this.invitedNumber = inviteList.size();
        this.tablesNum = tables.size();
        this.vars = new IntVar[tablesNum][invitedNumber][invitedNumber];
        this.exps = new IntVar[invitedNumber][tablesNum];

    }

    //Getters and setters:
    //@TODO: add if needed


    /**
     * Run Algorithm on the given invite list & tables
     */
    public void run() {
        if (invitedNumber < inviteList.size()) {
            System.err.println("Insufficient number of chairs");
            return;
        }

        // Create the model.
        this.model = new CpModel();


        // Create the variables & constraints:
        setupModel2();

        // Create a solver and solve the model.
        CpSolver solver = new CpSolver();
        solver.getParameters().setNumSearchWorkers(8);
        System.out.println(solver.getParameters().getNumSearchWorkers());
        System.out.println("Solver -> start");
        CpSolverStatus status = solver.solve(model);

        if (status == CpSolverStatus.FEASIBLE || status == CpSolverStatus.OPTIMAL) {
            printSolution(solver);
        } else {
            System.err.println("No feasible solution");
        }

        //---------------------------------------------------------------------------------------
    }

    private void printSolution(CpSolver solver) {
        System.out.println("//----------//\nSolution found!\n\n" +
                "Number of guests: " + inviteList.size() +
                "\nNumber of tables: " + tables.size() +
                "\nFree seats: " + (getNumOfChairs() - inviteList.size()) +
                "\nObjective value: " + solver.objectiveValue() +
                "\n//----------//\n");

        System.out.println();
        int counter;
        System.out.println("Table arrangement:\n");
        for (int k = 0; k < tables.size(); k++) {
            counter = 0;
            System.out.println("Table #" + k + ", max capacity: " + tables.get(k) + " contains invitees:\n");
            for (int i = 0; i < inviteList.size(); i++) {
                if (solver.value(exps[i][k]) == 1) {
                    System.out.print(inviteList.get(i).getName() + "\t");
                    //System.out.print(i + "\t");
                    counter++;
                    if ((counter) % 10 == 0) System.out.println();
                }
            }
            System.out.println("\n\n" + counter + "/" + tables.get(k) + "\n");
        }
    }


    /**
     * Setup solver vars and constraints:
     * Constraint_1: I_kij = I_kji (I_kii =  0 for every i)
     * Constraint_2: table size = max residents in table k
     * Constraint_3: each i seats exactly in one table
     * Constraint_4: I_k = 1 <=> for all: q != k, I_qij = 0
     * set model to maximize the objective expression
     */
    private void setupModel2() {
        evalEdgesMatrix();

        //Create vars:
        //Create constraints 1, 1.1:
        // @TODO: check i=j case
        for (int k = 0; k < tablesNum; k++) {
            for (int i = 0; i < invitedNumber; i++) {
                // I_ik = is i seats in table k:
                exps[i][k] = model.newBoolVar("I_" + i + "_" + k);

                for (int j = i; j < invitedNumber; j++) {

                    if (i != j) {
                        vars[k][i][j] = this.model.newBoolVar("I_" + k + "^" + i + "^" + j);
                        //vars[k][j][i] = vars[k][i][j];
                    } else {
                        //Constraint_1.1: for every i, I_kii = 0
                        vars[k][i][j] = model.newConstant(0);
                    }

                }
            }


            // Constraint_2: table size = max residents in table k
            model.addLessOrEqual(LinearExpr.sum(allKExps(k)), tables.get(k));
        }

        //Constraint_3: each i seats exactly in one table @TODO: check if needed, when constraint 4 is active
        for (int i = 0; i < invitedNumber; i++) {
            model.addEquality(LinearExpr.sum(exps[i]), 1);
        }


        //region old Contraint 4
//        for (int i = 0; i < invitedNumber; i++) {
//            for (int j = i + 1; j < invitedNumber; j++) {
//                for (int k = 0; k < tablesNum; k++) {
//                    IntVar[] arr = new IntVar[]{exps[i][k], vars[k][i][j]};
//                    int[][] forbiddenAss = new int[][]{{0,1}};
//                    try {
//                        model.addForbiddenAssignments(arr, forbiddenAss);
////                        model.addAllowedAssignments(new IntVar[]{exps[i][k], vars[k][i][j]}, new int[][]{{0,0},{1,1},{1,0}});
//                    } catch (CpModel.WrongLength wrongLength) {
//                        System.err.println("ERROR IN addAllowedAssignments(), constraint 4");
//                        wrongLength.printStackTrace();
//                    }
//                }
//            }
//        }
        //endregion

        //   Constraint_4: I_ik = 1 & I_jk = 1  <=>   I_kij = 1
        for (int i = 0; i < invitedNumber; i++) {
            for (int j = i + 1; j < invitedNumber; j++) {
                for (int k = 0; k < tablesNum; k++) {
                    if (i != j) {
                        model.addProductEquality(vars[k][i][j], new IntVar[]{exps[i][k], exps[j][k]});
                    }
                }
            }
        }


        // Set model's linear expression to maximize
        setMaxEquation();

    }


    /**
     * @return Capacity (thus, sum of tables sizes)
     */
    private int getNumOfChairs() {
        int ret = 0;
        for (int i = 0; i < tables.size(); i++) {
            ret += tables.get(i);
        }
        return ret;
    }

    /**
     * Iterates over the invited list, evaluate all edges & update edgesMatrix
     */
    public void evalEdgesMatrix() {
        int listSize = inviteList.size();

        for (int i = 0; i < listSize; i++) {
            Invitee curr = inviteList.get(i);

            for (int j = i + 1; j < listSize; j++) {
                if (edgesMatrix[i][j] == 0) {
                    //Edge hasn't been values yet
                    edgesMatrix[i][j] = curr.evalEdge(inviteList.get(j));
                    edgesMatrix[j][i] = edgesMatrix[i][j];
                }
            }
        }
    } // @TODO: fix & implement plus 1, if needed.


    /**
     * "Flattening" each array to a 1 dimensional array.
     * Afterwards create the linear expression (by scalar product).
     * set model to maximize the expression.
     */
    private void setMaxEquation() {

        IntVar[] linearVars = new IntVar[tablesNum * invitedNumber * invitedNumber];
        int[] linearEdges = new int[tablesNum * invitedNumber * invitedNumber];
        Vector<IntVar> tempVars = new Vector<>();
        Vector<Integer> tempCoef = new Vector<>();

        for (int k = 0; k < tablesNum; k++) {
            for (int i = 0; i < this.invitedNumber; i++) {
                for (int j = i; j < this.invitedNumber; j++) {
                    tempVars.addElement(vars[k][i][j]);
                    tempCoef.addElement(edgesMatrix[i][j]);

//                    //int position = k * (invitedNumber * invitedNumber) + (i * invitedNumber) + j;
//                        linearVars[position] = vars[k][i][j];
//                        linearEdges[position] = edgesMatrix[i][j];
                }
            }
        }
        int[] temp = new int[tempCoef.size()];
        for (int i = 0; i < tempCoef.size(); i++) {
            temp[i] = tempCoef.get(i);
        }

        // set equation to be maximized
        LinearExpr maxLinearExp = LinearExpr.scalProd(tempVars.toArray(new IntVar[tempVars.size()]), temp);

        //LinearExpr maxLinearExp = LinearExpr.scalProd(linearVars,linearEdges);
        model.maximize(maxLinearExp);
    }


    /**
     * @param i given id (i) of invitee
     * @param k given table's index
     * @return array with I_ik, and all edges of i which are not in table k
     * Set the allowed assignment, which represents that i seats only by people in table k
     */
    private IntVar[] not_i_k_array(int i, int k) {

        IntVar[] ret = new IntVar[invitedNumber * (tablesNum - 1) + 1];
        ret[0] = exps[i][k];

        for (int q = 0; q < k; q++) {
            if (invitedNumber >= 0) System.arraycopy(vars[q][i], 0, ret, 1 + q * invitedNumber, invitedNumber);
        }

        for (int q = k + 1; q < tablesNum; q++) {
            if (invitedNumber >= 0)
                System.arraycopy(vars[q][i], 0, ret, 1 + (q - 1) * invitedNumber, invitedNumber);
        }


        return ret;
    }

    /**
     * @return an array filled with 0, and first cell 1,
     * which represents an legal assignment of the variables
     */
    private int[][] not_i_k_ass() {

        int[] ret = new int[invitedNumber * (tablesNum - 1) + 1];
        Arrays.fill(ret, 0); //@TODO: check if needed since initial value is 0
        ret[0] = 1;

        return new int[][]{ret};
    }

    /**
     * @param k given table's index
     * @return array of all I_ik
     */

    private IntVar[] allKExps(int k) {
        IntVar[] ret = new IntVar[invitedNumber];
        for (int i = 0; i < invitedNumber; i++) {
            ret[i] = exps[i][k];
        }

        return ret;
    }


    /**
     * init test,
     * with data of tables and Invite list.
     *
     * @return A wedding problem solver instance, with invite and tables lists
     */
    public static Wed_CPSolver initTest() {
        List<Invitee> inviteLst = initTestParticipants(2, 3, 8, 12);
//        List<Integer> tableLst = new ArrayList<Integer>(Arrays.asList(30, 3, 3, 3, 3, 3,3, 3, 3, 4));
        List<Integer> tableLst = new ArrayList<Integer>(Arrays.asList(4, 4, 6, 6, 3, 2));

        return new Wed_CPSolver(inviteLst, tableLst);
    }

    private static List<Invitee> initTestParticipants(int a, int b, int c, int d) {
        List<Invitee> inviteLst = new ArrayList<>();
        for (int i = 0; i < a; i++) {
            inviteLst.add(new Invitee("G_" + i, i, true, Category.FAMILY, "Goldberg's", false));
        }
        for (int i = 0; i < b; i++) {
            inviteLst.add(new Invitee("M_" + i, i, false, Category.FAMILY, "Michaeli'", false));
        }
        for (int i = 0; i < c; i++) {
            inviteLst.add(new Invitee("T_" + i, i, true, Category.WORK, "TSA Corporations", false));
        }
        for (int i = 0; i < d; i++) {
            inviteLst.add(new Invitee("D_" + i, i, false, Category.FRIENDS, "Military duty", false));
        }
        return inviteLst;
    }


}


