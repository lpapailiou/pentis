# pentis

A game like __tetris__, but with five blocks instead of four per figure.

## Table of Contents
1. [About the game](#about-the-game)  
2. [Purpose of this project](#purpose-of-this-project) 
2. [Project structure](#project-structure)  
3. [How to get it](#how-to-get-it)  
	3.1 [How to import it to Intellij IDE](#how-to-import-it-to-intellij-ide)   
	3.2 [How to import it to Eclipse IDE](#how-to-import-it-to-eclipse-ide)   
4. [How to run it](#how-to-run-it)  
	4.1 [Within the IDE](#within-the-ide)  
	4.2 [From a Jar file](#from-a-jar-file)  
		4.2.1 [Build Jar in Intellij IDE](#build-jar-in-intellij-ide)    
		4.2.2 [Build Jar in Eclipse IDE](#build-jar-in-eclipse-ide)  
		4.2.3 [Execute Jar](#execute-jar)  
		
## About the game
![screenshot of pentis](https://github.com/lpapailiou/pentis/blob/master/src/main/resources/pentis_screenshot.png)

The game is purely controlled by the keyboard.
* __arrow left__: moves a shape one block distance to the left
* __arrow right__: moves a shape one block distance to the right
* __arrow up__: rotates shape clockwise
* __arrow down__: moves a shape one block distance down
* __space__: drops shape to the bottom		
* __p__: pauses game		
* __y__ or __enter__: when a game over occurs, a new game will be started by pressing these keys		
* __n__: when a game over occurs, the application can be left with this key		

## Purpose of this project
This project ist mainly for playing around with Java. I tried to use as basic structures as possible 
(e.g. using two dimensional int arrays instead of custom classes for shapes).

## Project structure

* ``application``         this package contains the main method (in ``Pentis.java``), and gui related code
* ``logic``               here most part of the game logic is in
* ``util``                helper classes

## How to get it

Clone the repository with:

    git clone https://github.com/lpapailiou/pentis your-target-path

Originally, the project was developed with IDE. It also runs with Eclipse.

### How to import it to Intellij IDE
1. Go to ``File > New``
2. Pick ``Maven > Project from Existing Sources...``
3. Now, navigate to the directory you cloned it to
4. Select the ``pom.xml`` file and click ``OK``
5. The project will be opened and build

### How to import it to Eclipse IDE
1. Go to ``File > Import``
2. Pick ``Maven > Existing Maven Project``
3. Now, navigate to the directory you cloned it to
4. Pick the root directory ``pentis`` and click ``Finish``
5. The project will be opened and build

## How to run it

### Within the IDE
You can directly run it within the IDE.

In case you experience weird UI behavior, it may be a DPI scaling issue known to occur with Windows 10 notebooks.
To fix it, do following steps:
1. Find the ``java.exe`` the game is running with (check Task Manager)
2. Rightclick on the ``java.exe`` and go to ``Properties``
3. Open the ``Compability`` tab
4. Check ``Override high DPI scaling behavior``
5. Choose ``System`` for ``Scaling performed by:``
6. Run the game again

### From a Jar file
You can download the Jar file directly from the [artifacts folder](https://github.com/lpapailiou/pentis/tree/master/out/artifacts/pentis_jar). Alternatively, you can build it yourself.

#### Build Jar in Intellij IDE 
1. Go to ``File > Project Structure...``
2. Go to the ``Artifacts`` tab and add a new ``Jar > From module with dependencies`` entry
3. Select the main class ``Pentis`` (here, the main class is in)
4. Click ``Ok`` twice
5. Go to ``Build > Build Artifacts...``
6. Select ``Build``
7. The Jar file is now added to the ``target`` folder within the project structure

#### Build Jar in Eclipse IDE
1. Right click on the project
2. Choose ``Export``
3. Go to ``Java > Runnable JAR file``
4. Click ``Next``
5. Launch configuration: choose ``Pentis`` (here, the main class is in)
6. Export destination: the place you want to save the Jar
7. Choose ``Extract required libraries into generated JAR``
8. Click ``Finish`` to start the Jar generation

#### Execute Jar
Double click on the Jar file directly. 
If nothing happens, you might need to add Java to your PATH variable.

Alternatively, you can start the Jar file from the console with:

    java -jar pentis.jar
    
Please make sure you execute it from the correct directory. The naming of the Jar file may vary.