# Sudoku Solver

I built this Java Swing desktop app to solve Sudoku puzzles and save the original puzzle alongside its solution. The solver uses backtracking, and saved puzzles can be named, annotated, and loaded from a local JSON file.

## Run the app

Use PowerShell on Windows with a graphical desktop and a JDK on your PATH. The project was verified with JDK 21. From the repository root, compile and launch the app with the bundled JSON library.

```powershell
$sources = Get-ChildItem -Path src/main -Recurse -Filter *.java | ForEach-Object FullName
New-Item -ItemType Directory -Force .tmp_coverage/main | Out-Null
javac -cp "lib/json-20250107.jar" -d .tmp_coverage/main $sources
if ($LASTEXITCODE -ne 0) { throw "Compilation failed." }
java -cp ".tmp_coverage/main;lib/json-20250107.jar" ui.Main
```

1. Select **Input Board & Solve**, enter the known digits, and leave unknown cells blank.
2. Select **Solve**. When a solution appears, you can save the puzzle and solution together with a name and an optional note.
3. Open **View Saved Boards** to revisit, edit, or delete a saved pair.
4. Use **Save Boards to File** and **Load Boards from File** to write or restore your collection.

## Test Coverage

From the repository root, run the bundled JUnit and JaCoCo tools through PowerShell. The full suite needs a graphical desktop because some tests instantiate Swing windows.

```powershell
.\scripts\run-coverage.ps1
```

On JDK 21, all **47 tests passed** across the solver, JSON persistence, and Swing UI.

| JaCoCo metric | Coverage |
| --- | ---: |
| Lines | 93.11% |
| Branches | 93.56% |
| Instructions | 93.42% |

The script generates an HTML report at `coverage/html/index.html`, along with `coverage/jacoco.xml` and `coverage/jacoco.csv`.

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
