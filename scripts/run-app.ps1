$ErrorActionPreference = "Stop"

Push-Location (Split-Path -Parent $PSScriptRoot)
try {
    $mainOut = ".tmp_coverage/app"
    New-Item -ItemType Directory -Force -Path $mainOut | Out-Null
    $sources = Get-ChildItem -Path src/main -Recurse -Filter *.java | ForEach-Object FullName

    javac -cp "lib/json-20250107.jar" -d $mainOut $sources
    if ($LASTEXITCODE -ne 0) { throw "Application compilation failed (exit code $LASTEXITCODE)." }

    java -cp "$mainOut;lib/json-20250107.jar" ui.Main
    if ($LASTEXITCODE -ne 0) { throw "Sudoku Solver exited with code $LASTEXITCODE." }
} finally {
    Pop-Location
}
