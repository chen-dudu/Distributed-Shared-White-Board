# Distributed Shared Whiteboard

## Description
Shared whiteboard allows multiple users to draw simultaneously on a canvas.  
Some core features include:
- predefined shapes for users to draw: line, circle, oval, rectangle
- insert text
- 13 built-in colors for users to select. Additional colors can be used by providing
the RGB values
- users can chat in the system  
- privileged users can save/open the whiteboard on local machine
- privileged users can kick out other non-privileged users
- privileged users can close the whiteboard sharing
## Execution
### RMIRegistry
As the system uses java RMI to handle remote communication, the rmiregistry needs
to be started first, before running the whiteboard server.  
To start the rmiregistry on the server machine,  
Windows
```
start rmiregistry (or javaw rmiregistry)
```
Solaris OS or Linux
```
rmiregistry &
```
By default, the registry uses port 1099. To use a different port,
Windows
```
start rmiregistry 2001
```
Solaris OS or Linux
```
rmiregistry 2001 &
```
### Whiteboard server
To run the Whiteboard server,
```
java -jar Server.jar
```

### Create whiteboard
To create a whiteboard on the server,
```
java -jar CreateWhiteBoard.jar <serverIPAddress> <serverPort>
```
### Join whiteboard
To join an existing whiteboard,
```
java -jar JoinWhiteBoard.jar <serverIPAddress> <serverPort>
```
