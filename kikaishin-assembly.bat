@echo off
echo Kikaishin is being assembled...

set artifactId=%1
set version=%2
set jarName=%artifactId%-%version%.jar

set distFolder=dist
if exist target\%distFolder% rmdir /s /q target\%distFolder%

REM #########################################
REM ####        STEPS DECLARATION        ####
REM #########################################

set "createConf= mkdir target\%distFolder%\config"
set "createProps= type NUL > target\%distFolder%\config\application.properties"
set "copyJar= xcopy /Y target\%jarName% target\%distFolder%\%jarName%*"

REM #########################################
REM ####         STEPS EXECUTION         ####
REM #########################################

%createConf% && %createProps% && %copyJar% && (echo ########################################################^

###/ THIS IS TEMPLATE FOR CONFIGURING KIKAISHIN API /###^

########################################################^

^

#####################^

### DATABASE DETAILS:^

#####################^

kikaishin.database.name=^

kikaishin.database.dns=^

kikaishin.database.port=^

### If not blank, should conform to get request parameters standard and start with character ?. eg. ?key1=1^

kikaishin.database.parameters=^

kikaishin.database.username=^

kikaishin.database.password=^

### Recommended value: none. If database creation needed recommended is create or update.^

kikaishin.database.ddl.auto=none^

kikaishin.database.show.sql=false^

^

########################^

### APPLICATION DETAILS:^

########################^

kikaishin.application.server.port=^

kikaishin.application.context-path=^

^

####################^

### LOGGING DETAILS:^

####################^

### Recommended value: info. Max logging level is trace.^

kikaishin.application.logging.level=info^

kikaishin.application.third.logging.level=info^

kikaishin.application.logging.file.name.pattern=logs/kikaishin.logfile.%%d{yyyy-MM-dd}.log^

kikaishin.security.logging.level=off^

kikaishin.application.logging.encoder.pattern=%%d{yyyy-MM-dd HH:mm:ss.SSS} [%%thread] %%-5level %%logger{36} - %%msg%%n) > target\%distFolder%\config\application.properties

