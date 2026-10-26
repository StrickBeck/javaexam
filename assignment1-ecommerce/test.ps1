$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot 'scripts\java-tools.ps1')
& (Join-Path $PSScriptRoot 'build.ps1')
$jdkPath = Find-ShopJdk
$mainClasses = Join-Path $PSScriptRoot 'build\classes'
$testClasses = Join-Path $PSScriptRoot 'build\test-classes'
New-Item -ItemType Directory -Force -Path $testClasses | Out-Null
$tests = @(Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'src\test\java') -Filter '*.java' -Recurse | ForEach-Object FullName)
& (Join-Path $jdkPath 'bin\javac.exe') --release 17 -encoding UTF-8 -Xlint:all -cp $mainClasses -d $testClasses $tests
if ($LASTEXITCODE -ne 0) { throw 'Test compilation failed.' }
& (Join-Path $jdkPath 'bin\java.exe') -cp "$mainClasses;$testClasses" edu.ict.shop.ShopTests
if ($LASTEXITCODE -ne 0) { throw 'Tests failed.' }
