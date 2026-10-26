Set-StrictMode -Version Latest
$ErrorActionPreference = 'Stop'

function Find-ShopJdk {
    $candidates = New-Object System.Collections.Generic.List[string]
    if ($env:JAVA_HOME) { $candidates.Add($env:JAVA_HOME) }
    $compiler = Get-Command javac -ErrorAction SilentlyContinue
    if ($compiler) {
        $candidates.Add((Split-Path (Split-Path $compiler.Source -Parent) -Parent))
    }
    $localJdks = Join-Path $env:USERPROFILE '.jdks'
    if (Test-Path -LiteralPath $localJdks) {
        foreach ($jdk in Get-ChildItem -LiteralPath $localJdks -Directory | Sort-Object Name -Descending) {
            $candidates.Add($jdk.FullName)
        }
    }
    foreach ($candidate in $candidates) {
        if ((Test-Path -LiteralPath (Join-Path $candidate 'bin\javac.exe')) -and
            (Test-Path -LiteralPath (Join-Path $candidate 'bin\java.exe'))) {
            return $candidate
        }
    }
    throw 'JDK 17 or newer was not found. Install/select a JDK and set JAVA_HOME, or run Main in IntelliJ IDEA.'
}
