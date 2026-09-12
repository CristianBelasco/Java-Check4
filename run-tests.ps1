$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$tempClasses = Join-Path $env:TEMP 'cp4-java-classes'
$driverJar = Join-Path $root 'lib/jdbc17.jar'

if (Test-Path $tempClasses) {
    Remove-Item -Recurse -Force $tempClasses
}
New-Item -ItemType Directory -Path $tempClasses | Out-Null

$sourceFiles = @(
    Get-ChildItem -Path (Join-Path $root 'src/main/java') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
    Get-ChildItem -Path (Join-Path $root 'src/test/java') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
)

& javac -cp $driverJar -d $tempClasses $sourceFiles
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

& java -cp "$tempClasses;$driverJar" com.fiap.dao.FilmeDAOImplTest
exit $LASTEXITCODE
