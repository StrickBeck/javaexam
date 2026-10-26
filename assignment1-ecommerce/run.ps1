$ErrorActionPreference = 'Stop'
. (Join-Path $PSScriptRoot 'scripts\java-tools.ps1')
$jdkPath = Find-ShopJdk
$archive = Join-Path $PSScriptRoot 'dist\ict-shop.jar'
if (-not (Test-Path -LiteralPath $archive)) { & (Join-Path $PSScriptRoot 'build.ps1') }
[Console]::InputEncoding = New-Object System.Text.UTF8Encoding
[Console]::OutputEncoding = New-Object System.Text.UTF8Encoding
& (Join-Path $jdkPath 'bin\java.exe') '-Dfile.encoding=UTF-8' -jar $archive
if ($LASTEXITCODE -ne 0) { throw 'Application exited with an error.' }
