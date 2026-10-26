$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot 'scripts\java-tools.ps1')
$jdkPath = Find-ShopJdk
$classes = Join-Path $PSScriptRoot 'build\classes'
$dist = Join-Path $PSScriptRoot 'dist'
$jarTemp = Join-Path $PSScriptRoot 'build\tmp'
$projectFolder = [System.IO.Path]::GetFullPath($PSScriptRoot)
$classesFolder = [System.IO.Path]::GetFullPath($classes)
if (-not $classesFolder.StartsWith($projectFolder + [System.IO.Path]::DirectorySeparatorChar, [System.StringComparison]::OrdinalIgnoreCase)) { throw 'Unsafe compiler output folder.' }
if (Test-Path -LiteralPath $classesFolder) { Remove-Item -LiteralPath $classesFolder -Recurse -Force }
New-Item -ItemType Directory -Force -Path $classes, $dist, $jarTemp | Out-Null
$sources = @(Get-ChildItem -LiteralPath (Join-Path $PSScriptRoot 'src\main\java') -Filter '*.java' -Recurse | ForEach-Object FullName)
& (Join-Path $jdkPath 'bin\javac.exe') --release 17 -encoding UTF-8 -Xlint:all -d $classes $sources
if ($LASTEXITCODE -ne 0) { throw 'Compilation failed.' }
& (Join-Path $jdkPath 'bin\jar.exe') "-J-Djava.io.tmpdir=$jarTemp" --create --file (Join-Path $dist 'ict-shop.jar') --main-class edu.ict.shop.Main -C $classes .
if ($LASTEXITCODE -ne 0) { throw 'JAR creation failed.' }
Write-Host 'Build successful: dist\ict-shop.jar'
