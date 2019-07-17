@echo off

set Root=.

rem .\src
if exist %Root%\src (
	echo %Root%\src                exist
) else (
	md %Root%\src
	echo %Root%\src                created
)

rem .\src\main
if exist %Root%\src\main (
	echo %Root%\src\main           exist
) else (
	md %Root%\src\main
	echo %Root%\src\main           created
)

rem .\src\main\java
if exist %Root%\src\main\java (
	echo %Root%\src\main\java      exist
) else (
	md %Root%\src\main\java
	echo %Root%\src\main\java      created
)

rem .\src\main\resources
if exist %Root%\src\main\resources (
	echo %Root%\src\main\resources exist
) else (
	md %Root%\src\main\resources
	echo %Root%\src\main\resources created
)

rem .\src\test
if exist %Root%\src\test (
	echo %Root%\src\test           exist
) else (
	md %Root%\src\test
	echo %Root%\src\test           created
)

rem .\src\test\java
if exist %Root%\src\test\java (
	echo %Root%\src\test\java      exist
) else (
	md %Root%\src\test\java
	echo %Root%\src\test\java      created
)

rem .\src\test\resources
if exist %Root%\src\test\resources (
	echo %Root%\src\test\resources exist
) else (
	md %Root%\src\test\resources
	echo %Root%\src\test\resources created
)

mvn dependency:resolve

