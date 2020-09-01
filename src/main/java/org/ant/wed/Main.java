package org.ant.wed;



public class Main {
//    static {
//        System.loadLibrary("jniortools");
//    }
//    public static MPSolver solver = MPSolver.createSolver("SimpleMipProgram", "CBC");




    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Wed_CPSolver wedding = Wed_CPSolver.initTest();

        wedding.run();
        long endTime = System.nanoTime();
        long runTime = endTime - startTime;
        double seconds = ((double) (((double)runTime)/1000000000.0));
        double minutes = seconds/60.0;
        System.out.println(String.format("total run time: %f minutes",minutes));
//        for ( int[] arr : wedding.getEdgesMatrix() ) {
//            System.out.println(Arrays.toString(arr));
//
//        }
    }

}



//package com.example.wed_logic;
//
//
//        import java.util.ArrayList;
//        import java.util.Arrays;
//        import java.util.List;
//
//// [START import]
//        import com.google.ortools.linearsolver.MPConstraint;
//        import com.google.ortools.linearsolver.MPObjective;
//        import com.google.ortools.linearsolver.MPSolver;
//        import com.google.ortools.linearsolver.MPVariable;
//// [END import]
//
///**
// * WEDDING TABLE ARRANGEMENT - LOGIC & METHODS
// * This class contains the logic of the reduction & solution we have implemented.
// * We use @Invitee as an object represents in invited person.
// * @Wed_src contains the methods and logic to solution
// *
// * @author  Alon Michaeli
// * @version 1.0
// * @since   2020-08-24
// */
//
//
//enum Category
//{
//    FAMILY, FRIENDS, WORK;
//}
//
///**
// * Logic of solution.
// * Test purpose only.
// */
//public class Wed_solver {
//    static {
//        System.loadLibrary("jniortools");
//    }
//
//
//    private List<Invitee> inviteList;
//    private int[][] edgesMatrix;
//    private List<Integer> tables;
//    MPVariable[][][] vars;
//    private MPSolver solver = MPSolver.createSolver("SimpleMipProgram", "CBC");
//
//    public Wed_solver(List<Invitee> inviteList, List<Integer> tables) {
//        this.inviteList = inviteList;
//        this.edgesMatrix = new int[inviteList.size()][inviteList.size()];
//        this.tables = tables;
//
//    }
//
//    //Getters and setters:
//    public List<Invitee> getInviteList() {
//        return inviteList;
//    }
//    public void setInviteList(List<Invitee> inviteList) {
//        this.inviteList = inviteList;
//    }
//    public int[][] getEdgesMatrix() {
//        return edgesMatrix;
//    }
//    public void setEdgesMatrix(int[][] edgesMatrix) {
//        this.edgesMatrix = edgesMatrix;
//    }
//    public List<Integer> getTables() {
//        return tables;
//    }
//    public void setTables(List<Integer> tables) {
//        this.tables = tables;
//    }
//
//
//    /**
//     * Run Algorithm on the given invite list & tables
//     */
//    public void run(){
//        MPObjective objective = setupSolver();
//
//        final MPSolver.ResultStatus resultStatus = solver.solve();
//
//        if (resultStatus == MPSolver.ResultStatus.OPTIMAL) {
//            displaySolution(objective);
//        } else {
//            System.err.println("The problem does not have an optimal solution.");
//        }
//    }
//
//    /**
//     * Setup solver vars and constraints
//     * @return the objective equation (MPObjective parameter) to the run() method
//     */
//    private MPObjective setupSolver() {
//        evalEdgesMatrix();
//        int n = inviteList.size();
//        int tablesNum = tables.size();
//        int maxCapacity = getNumOfChairs();
//        this.vars = new MPVariable[tablesNum][n][maxCapacity];
//
//
//        // Creates variables & create constraints for sizes of tables:
//        // bound = a full clique in table's size
//        for (int k = 0; k < tablesNum; k++) {
//            int currCapacity = tables.get(k);
//            MPConstraint constraint_1 = solver.makeConstraint(currCapacity*(currCapacity-1), currCapacity*(currCapacity-1), "");
//
//            for (int i = 0; i < n; i++) {
//                for (int j = i + 1; j < maxCapacity; j++) {
//
//                    // Create variables I_kij:
//                    vars[k][i][j] = solver.makeIntVar(0.0, 1, "");
//
//                    // Set coefficient for the respective constraint:
//                    constraint_1.setCoefficient(vars[k][i][j],1);
//                }
//
//
//            }
//
//        }
//
//        // Create constraints:
//        // constraint_2: each participants seats at least in 1 table
//        // constraint_3: for given i & j, max sum of all I_kij is 1 @TODO: check if needed
//        for (int i = 0; i < n; i++) {
//            MPConstraint constraint_2 = solver.makeConstraint(1, Double.POSITIVE_INFINITY, "");
//
//            for (int j = i + 1; j < maxCapacity; j++) {
//                MPConstraint constraint_3 = solver.makeConstraint(0, 1, "");
//
//                for (int k = 0; k < tablesNum; k++) {
//                    constraint_2.setCoefficient(vars[k][i][j],1);
//                    constraint_3.setCoefficient(vars[k][i][j],1);
//                }
//            }
//        }
//
//
//        MPObjective objective = solver.objective();
//        for (int i = 0; i < n; i++) {
//            for (int j = i + 1; j < n; j++) {
//                //int edgeVal = inviteList.get(j).evalEdge(inviteList.get(j)); @TODO: decide if to evalute here, or in evalEdgesMatrix()
//                int edgeVal = edgesMatrix[i][j];
//                for (int k = 0; k < tablesNum; k++) {
//                    objective.setCoefficient(vars[k][i][j], edgeVal);
//                }
//            }
//
//            for (int j = n; j < maxCapacity; j++) {
//
//                for (int k = 0; k < tablesNum; k++) {
//                    objective.setCoefficient(vars[k][i][j], 0);
//                }
//            }
//
//        }
//        objective.setMaximization();
//        return objective;
//    }
//
//    private int getNumOfChairs() {
//        int ret = 0;
//        for (int i = 0; i <tables.size() ; i++) {
//            ret += tables.get(i);
//        }
//        return ret;
//    }
//
//
//    /**
//     * Iterates over the invited list, evaluate all edges & update edgesMatrix
//     */
//    public void evalEdgesMatrix() {
//        int listSize = inviteList.size();
//
//        for (int i=0; i < listSize; i++){
//            Invitee curr = inviteList.get(i);
//
//            for (int j = i + 1; j < listSize; j++){
//                if (edgesMatrix[i][j] == 0) {
//                    //Edge hasn't been values yet
//                    edgesMatrix[i][j] = curr.evalEdge(inviteList.get(j));
//                    edgesMatrix[j][i] = edgesMatrix[i][j];
//                }
//            }
//        }
//    } // @TODO: fix & implement plus 1, if needed.
//
//    /**
//     * init test,
//     * with data of tables and Invite list.
//     * @return A wedding problem solver instance, with invite and tables lists
//     */
//    public static Wed_solver initTest() {
//        List<Invitee> inviteLst = new ArrayList<Invitee>();
//        List<Integer> tableLst = new ArrayList<Integer>(Arrays.asList(5, 5, 5, 5, 5, 7));
//
//        for (Integer i = 0; i < 5 ; i++ ){
//            inviteLst.add(new Invitee("G_" + i,i,true, Category.FAMILY,"Goldberg's", false ));
//        }
//        for (Integer i = 0; i < 10 ; i++ ){
//            inviteLst.add(new Invitee("M_" + i,i,false, Category.FAMILY,"Michaeli'", false ));
//        }
//        for (Integer i = 0; i < 7 ; i++ ){
//            inviteLst.add(new Invitee("T_" + i,i,true, Category.WORK,"TSA Corporations", false ));
//        }
//        for (Integer i = 0; i < 8 ; i++ ){
//            inviteLst.add(new Invitee("D_" + i,i,false, Category.FRIENDS,"Military duty", false ));
//        }
//
//        return new Wed_solver(inviteLst, tableLst);
//    }
//
//
//
//    private void displaySolution(MPObjective objective) {
//        System.out.println("//----------//\nSolution found!\n\n" +
//                "Number of guests: " + inviteList.size() +
//                "\nNumber of tables: " + tables.size() +
//                "\n//----------//\n");
//
//        System.out.println("Objective value = " + objective.value());
//
//        System.out.println();
//        System.out.println("Problem solved in " + solver.wallTime() + " milliseconds");
//        System.out.println("Problem solved in " + solver.iterations() + " iterations");
//        System.out.println("Problem solved in " + solver.nodes() + " branch-and-bound nodes");
//        System.out.println();
//
//        System.out.println("Table arrangement:\n");
//        for (int k = 0; k < tables.size(); k++) {
//            int counter = 0;
//            System.out.println("Table #" + k + ", max capacity: "+ tables.get(k)  + " (" +tables.get(k)*(tables.get(k)-1) + " edges) contains edges:\n");
//
//            int numOfGuests = inviteList.size();
//
//            for (int i = 0; i < numOfGuests; i++) {
//                for (int j = i + 1; j < numOfGuests; j++) {
//
//                    if (vars[k][i][j].solutionValue() == 1) {
//                        counter++;
//                        System.out.print("  (" + inviteList.get(i).getName() + ", " + inviteList.get(j).getName() + ")");
//
//                        if ((counter + 1) % 4 == 0) {
//                            System.out.println(",");
//                        }
//                    }
//                }
//
////                for (int j = numOfGuests; j < getNumOfChairs(); j++) {
////
////                    if (vars[k][i][j].solutionValue() == 1) {
////                        counter++;
////                        System.out.print("  (" + inviteList.get(i).getName() + ", DUMMY )");
////
////                        if ((counter + 1) % 4 == 0) {
////                            System.out.println(",");
////                        }
////                    }
////                }
//
//
//            }
//
//            System.out.println("\n\nTotal of edges in the table: " + counter + "\n");
//        }
//    }
//
//}







