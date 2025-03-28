# Sudoku Solver 

## Introduction

I want to build a program that takes in an unsolved sudoku board, solves it, and stores both boards together as a pairing for future reference. If the inputted board lines up with an already solved and stored board, it will just output the stored solution instead. I want the user to be able to name, delete, and add notes to each board pairing. This will help anyone who wants to confirm if a solution is correct or help store certain baords and strategies on how to solve each stored board. This interests me because I enjoy playing sudoku, and I enjoy solving complicated and intricate problems, such as sudoku.

## User Stories
***Phase 2 User Stories***
- As a user, I want to be able to input an unsolved sudoku board
- As a user, I want to be able to view the solution to the inputted sudoku board
- As a user, I want to be able to save the solution and unsolved version together for future reference
- As a user, I want to be able to view all saved board pairings
- As a user, I want to be able to delete, name, and add a note to each saved board pairing
- As a user, I want to be able to save my board pairings (if I want to)
- As a user, I want to be able to load all of my board pairings (if I want to)

## Instructions for End User

- You can generate the first required action related to the user story "save the solution and unsolved version together for future reference" by clicking on the "import board and solve" button, filling in the unsolved sudoku, clicking solve, and clicking yes when prompted to save the board
- You can generate the second required action related to the user story "view all saved board pairings" by clicking on the "View Saved Boards" button on the main menu
- You can locate my visual component by clicking on the "import board and solve" buttton.
- You can save the state of my application by clicking on the "Save Boards to File" button
- You can reload the state of my application by clicking on the "Load Boards from File" button

## Phase 4: Task 2:
Event Log:

Fri Mar 28 15:03:56 PDT 2025

Loaded boards.

Fri Mar 28 15:03:56 PDT 2025

Board pairing saved.

Fri Mar 28 15:03:56 PDT 2025

Board pairing saved.

Fri Mar 28 15:04:00 PDT 2025

Board solved.

Fri Mar 28 15:04:05 PDT 2025

Board pairing saved.

Fri Mar 28 15:04:22 PDT 2025

Edited name of a board.

Fri Mar 28 15:04:22 PDT 2025

Edited note of a board.

Fri Mar 28 15:04:28 PDT 2025

Saved boards.


## Phase 4: Task 3:
One thing that I could have done to improve the program would be to have a seperate class in my model that represents all of my stored board pairings. When writing my code, I noticed that my UI needed a lot of references to my model and it needed to perform some logic that it was not supposed to need. This could be solved by creating a new class in model that represents my list of an arbitrary amount of board pairings. This new class would deal with the logic that my UI is currently doing, making my program more layered. Since my saved boards are the same everywhere in the program, this new class could be designed as a singleton as well. Another thing that I would do would be to remove the Cell class entirely and make boards 2d arrays of integers. This would keep the functionality of the program the same while making my code more readable. 

## Sources
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo