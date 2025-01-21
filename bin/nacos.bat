@echo off
set NACOS_HOME=E:\Utils\nacos\nacos-server-2.3.0

REM 启动Nacos
echo 启动Nacos...
%NACOS_HOME%\bin\startup.cmd -m standalone

echo Nacos已启动
pause
