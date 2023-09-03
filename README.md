## Description of project

This project is the result of the 10-week course Object-Oriented Programming Project (CSE1105), part of TU Delft's Computer Science and Enginerring BSc.
The name of the app is Talio, a task manager web application. See the [backlog](docs/Backlog-Final.pdf) for more information on the targets of the development process and the application's intended use.
Please note that the backlog has not been finished throughout the course. The team has completed the Basic Requirements and most of the MultiBoard requirement.

The development team consists of 6 first year students, working with frameworks like SpringBoot and JavaFX. 

## Group members

| Profile Picture | Name              | Email |
|---|-------------------|---|
| ![](https://secure.gravatar.com/avatar/2ab54421f7b1903ffe735fc21fe4e573?s=192&d=identicon) | Stephanie Gilbert | s.e.gilbert@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/2098d0371c0ab84348f2c5e63c7d3435?s=192&d=identicon) | Petra Gulyas      | P.Gulyas@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/b1d8a7c7aa2111de4f9b38a4f65fbf0d?s=192&d=identicon) | Jan de Munck      | j.c.demunck@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/8e9dcfabb9fafe002d978ab82ff1e48f?s=192&d=identicon) | Andrei Chiru      | A.Chiru@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/63033fd5555e82dfc1b87e8a3c7e381d?s=192&d=identicon) | Razvan Dinu | R.D.Dinu-1@student.tudelft.nl |
| ![](https://secure.gravatar.com/avatar/240f1f197a7f904e8e6c8300a01aeb22?s=192&d=identicon) | Stephan Popov     | S.K.Popov@student.tudelft.nl |

## How to run it
First, make sure the project has been built, either manually or by the IDE.
```
$ gradlew build
```

Next, start up the server on a free port:

Run [server/src/main/java/server/Main.java](server/src/main/java/server/Main.java). 
Any number of instances can be open at once, as long as the IDE is set to allow this.
Once prompted with choosing a port, input the port number and click 'launch'.
Leaving the input empty will open a connection to port 8080.
The terminal will show the port used, the user's IP address and an admin key.

Now, to run the application, run [client/src/main/java/client/TaskListMain.java](client/src/main/java/client/TaskListMain.java).
You will be prompted with choosing a server to connect to.
Using the IP in the terminal, or connecting to localhost, one can connect to any active address and active port.
This includes different machines on the same network.
Leaving the inputs empty will default to the values of their placeholders ('localhost' and '8080' respectively).
Again, any number of instances can be open if the IDE allows it.
App instances on the same server act as different users that share a common task board, therefore the contents will be synced.

To use admin functionality, one must input the admin key when prompted.
