@echo off
setlocal enabledelayedexpansion

::chemin vers la source du projet
set "source=C:\Users\lenovo\Documents\ITU\cours\L2\M Tsinjo\2D-Packing\2D-Packing\web";

::dossier qui va contenir temporairement les fichier à deployer
set "COMPILE_PATH=C:\Users\lenovo\Documents\ITU\cours\L2\M Tsinjo\2D-Packing\out\production\2D-Packing";

::emplacement du fichier XML du projet
set "servlet_api=C:\Server\Tomcat\lib\servlet-api.jar";


::chemin vers la destination du deployement
set "destination=C:\Server\Tomcat\webapps\packing\";

if exist "%destination%" (
    rd /s /q "%destination%"
)

xcopy /s /e "%COMPILE_PATH%" "%source%\WEB-INF\classes"
xcopy /s /e "%source%" "%destination%"
::xcopy /s /e "%driver%" "%destination%\WEB-INF\lib"
::xcopy /s /e "%servlet_api%" "%destination%\WEB-INF\lib"

echo Deployement  complete!
