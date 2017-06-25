# Arduino + REST
Application for sending commands to arduino through a REST service.

This is a simple application for sending commands from a REST service to a arduino.
It is composed by a Spring Boot REST service, some python code and the arduino code.

It works as follow:
The REST service stores a command value that can be modified through the /SetCommand.
The Python code is actively checking if there is a command to execute, if there is, it loads the command and send the instruction to Arduino through serial.
The arduino code handle the specific command execution.

I have tested this by deploying the REST service to google app engine, running the python code in a raspberry pi and connecting an arduino board to the raspberry pi.

Hope this is useful for someone. It was a fun weekend project. I may build on this on the future :)