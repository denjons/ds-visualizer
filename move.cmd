ECHO moving source
REM move annotation source
xcopy C:\Users\dennis\Documents\NetBeansProjects\annotation\src\* C:\dev\ds-visualizer\annotation\src /s /i
xcopy C:\Users\dennis\Documents\NetBeansProjects\annotation\pom.xml C:\dev\ds-visualizer\annotation /Y
REM move annotation test source
xcopy C:\Users\dennis\Documents\NetBeansProjects\annotation-test\src\* C:\dev\ds-visualizer\annotation-test\src /s /i
xcopy C:\Users\dennis\Documents\NetBeansProjects\annotation-test\pom.xml C:\dev\ds-visualizer\annotation-test /Y
REM move web tool

xcopy C:\dev\kandidat\public\web_tool\css\* C:\dev\ds-visualizer\web-tool\css /s /i
xcopy C:\dev\kandidat\public\web_tool\js\markup\* C:\dev\ds-visualizer\web-tool\js\markup /s /i
xcopy C:\dev\kandidat\public\web_tool\js\utils\* C:\dev\ds-visualizer\web-tool\js\utils /s /i
xcopy C:\dev\kandidat\public\web_tool\js\visualizers\* C:\dev\ds-visualizer\web-tool\js\visualizers /s /i

xcopy C:\dev\kandidat\public\web_tool\js\graphics\graph.js C:\dev\ds-visualizer\web-tool\js\graphics /Y
xcopy C:\dev\kandidat\public\web_tool\js\graphics\grid.js C:\dev\ds-visualizer\web-tool\js\graphics /Y

xcopy C:\dev\kandidat\public\web_tool\index.html* C:\dev\ds-visualizer\web-tool /Y