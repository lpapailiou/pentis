# pentis

A game like __tetris__, but with five blocks instead of four per shape by default.  

## Table of Contents
1. [About the game](#about-the-game)  
2. [Purpose of this project](#purpose-of-this-project) 
2. [Project structure](#project-structure)  
3. [How to get it](#how-to-get-it)  
		
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
* __m__: switch color mode		
  
Every time the level increases, speed will also increase a little. 
The shapes are generated on the fly, so the blocks will 'cluster'. This means, that it is more likely that compact 
shapes appear.  
If you want to get the game more annoying, you can add more blocks in the ``Settings`` class (which is located in the ``util`` 
package). There, also the board size (default 17x10 blocks) may be adjusted.

## Purpose of this project
This project ist mainly for playing around with Java. I tried to use as basic structures as possible 
(e.g. using two dimensional int arrays instead of custom classes for shapes).

## Project structure

* ``application``         this package contains the main method (in ``Pentis.java``), and gui related code
* ``logic``               here most part of the game logic is in
* ``util``                 helper classes

## How to get it

Clone the repository with:

    git clone https://github.com/lpapailiou/pentis your-target-path

For further help, click [here](https://gist.github.com/lpapailiou/d4d63338ccb1413363970ac571aa71c9).

    java -jar pentis.jar
    
Please make sure you execute it from the correct directory. The naming of the Jar file may vary.
