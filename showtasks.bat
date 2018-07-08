call runcrud.bat
if "%ERRORLEVEL%" == "0" goto launchbrowser
echo errors in runcrud.bat
goto fail

:launchbrowser
echo.
echo launching browser
start firefox.exe  http://localhost:8080/crud/v1/task/getTasks
if "%ERRORLEVEL%" == "0" goto finish

:fail
echo.
echo there were errors

:finish
echo.
echo program finished work