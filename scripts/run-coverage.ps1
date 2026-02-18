$ErrorActionPreference = "Stop"

$repoRoot = Split-Path -Parent $PSScriptRoot
Set-Location $repoRoot

$tmpDir = ".tmp_coverage"
$mainOut = Join-Path $tmpDir "main"
$testOut = Join-Path $tmpDir "test"
$execFile = Join-Path $tmpDir "jacoco.exec"
$reportDir = "coverage"

Remove-Item -Recurse -Force $tmpDir, $reportDir -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path $mainOut, $testOut, $reportDir | Out-Null

$mainSources = Get-ChildItem -Recurse src/main -Filter *.java | ForEach-Object FullName
$testSources = Get-ChildItem -Recurse src/test -Filter *.java | ForEach-Object FullName

javac -cp "lib/json-20250107.jar" -d $mainOut $mainSources
javac -cp "$mainOut;lib/json-20250107.jar;lib/junit-platform-console-standalone-1.10.2.jar" -d $testOut $testSources

java "-javaagent:lib/jacocoagent.jar=destfile=$execFile,append=false" -jar lib/junit-platform-console-standalone-1.10.2.jar `
    --class-path "$mainOut;$testOut;lib/json-20250107.jar" `
    --scan-class-path

java -jar lib/jacococli.jar report $execFile `
    --classfiles $mainOut `
    --sourcefiles src/main `
    --xml "$reportDir/jacoco.xml" `
    --csv "$reportDir/jacoco.csv" `
    --html "$reportDir/html"

[xml]$xml = Get-Content "$reportDir/jacoco.xml"

function Show-Coverage($counterType) {
    $counter = $xml.report.counter | Where-Object { $_.type -eq $counterType }
    $missed = [int]$counter.missed
    $covered = [int]$counter.covered
    $total = $missed + $covered

    if ($total -eq 0) {
        Write-Host "$counterType coverage: N/A"
        return
    }

    $pct = [math]::Round((100.0 * $covered) / $total, 2)
    Write-Host "$counterType coverage: $pct% ($covered/$total)"
}

Write-Host ""
Show-Coverage "INSTRUCTION"
Show-Coverage "BRANCH"
Show-Coverage "LINE"
Show-Coverage "METHOD"
Show-Coverage "CLASS"
Write-Host "HTML report: $reportDir/html/index.html"
