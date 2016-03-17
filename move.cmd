ECHO moving source
REM move annotation source
xcopy C:\Users\dennis\Documents\NetBeansProjects\annotation\src\* C:\dev\ds-visualizer\annotation\src /s /i
xcopy C:\Users\dennis\Documents\NetBeansProjects\annotation\pom.xml C:\dev\ds-visualizer\annotation /Y
REM move annotation test source
xcopy C:\Users\dennis\Documents\NetBeansProjects\annotation-test\src\* C:\dev\ds-visualizer\annotation-test\src /s /i
xcopy C:\Users\dennis\Documents\NetBeansProjects\annotation-test\pom.xml C:\dev\ds-visualizer\annotation-test /Y
REM move web tool

xcopy C:\dev\kandidat\public\web_tool\* C:\dev\ds-visualizer\web-tool /s /i
