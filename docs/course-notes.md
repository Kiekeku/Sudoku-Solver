# Original course notes

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
