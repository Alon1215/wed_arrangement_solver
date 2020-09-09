# wed_arrangement_solver
wedding sitting arrangement webApp.
Implementing a new approach to the partition problem, when relationships are evaluated, and the core to the optimal partition.
(Using Java, Spring Boot, MongoDB and React JS).

A summer project to learn different technologies. 
* Current state of the project: engine works, website in his first steps.

Problem: Finding the optimal partition of invitees to tables.
Solution: Modeling invitees to a complete graph, when edge's weight
represents the depth of the relationship between two invitees.
Reduction & solution with SAT Solver (Google or-tools).

Completed:
- Engine completed (reduction to Constraints problem, and using Google-ortools). 
- Landing page
- Login works

Not finished:
- Increase engine capabilities to larger inputs (current capabillity <= 20, considering changing to an approximation algorithm).
- Input reader (JSON).
- Website and UI. 


# Logic of algorithm:
Input: an invite list (with different fields of information for every invitee), and a list of tables (by sizes).

Varibles: for each invitee i, and for every table K, I_ik = Indictor ([0,1]) if person i sits in table k.
 In addition, for every two distinct invitees i,j and table k, I_ijk = I_ik * I_jk.
 
Constraints: 
 * Constraint_1: I_ik := invitee i seats in table k (1 if so, else 0).
 * Constraint_2: table size = number of chairs by the table (max capacity).
 * Constraint_3: each i seats exactly in 1 table
 * Constraint_4: I_ik = 1 & I_jk = 1  <=> I_kij = 1 
  (I_kii =  0 for every i)

Objective function (to be maximized) : 
 - Sum of: 
- for every i in invite list: 
- for every j in invite list:
- for every k in tables list: 
- e_ij * (I_ik * I_jk)


