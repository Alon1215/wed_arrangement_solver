# wed_arrangement_solver
wedding sitting arrangement webApp.
Implementing a new approach to the partition problem, when relationships are evaluated, and the core to the optimal partition.
(Using Java, Spring Boot, MongoDB and React JS).

A summer project to learn different technologies. 
* Current state of project: engine works, website in his first steps.

Problem: Finding the optimal partition of invitees to tables.
Solution: Modeling invitees to a complete graph, when edge's weight
represents the depth of the relationship between two invitees.
Reduction & solution with SAT Solver (Google or-tools).


Completed:
- Engine completed (reduction to Constraints problem, and using Google-ortools). 
- Landing page
- Login works

Not finished:
- Increase engine capabilities to larger inputs (considering changing to an approximation algorithm).
- Input reader (JSON).
- Website and UI. 

