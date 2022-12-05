<h1 align="center"> Blossom Storm </h1>

## Table of Contents

* [General info](#general-info)
* [Technologies](#technologies)
* [Contents](#content)
* [Features](#features)
* [Read More](#read-more)
* [Developers](#developers)

## General Info
A narrative-driven tactical RPG taking place in modern Tokyo. The gameplay consists of simple cutscenes (similar to visual novels) and battles. Battles occur on square grids with various terrain features, such as buildings and different terrain surfaces. The player and enemy team (controlled by the computer) take turns moving units on the battlefield to attack each other while attempting to accomplish a broader objective.


## Technologies
Technologies used for this project:
* Java
* JavaFX

## Content
Content of the project folder:

```
 Top level of project folder:
├── .idea
├── .mvm
├── src
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md

It has the following subfolders and files:
├── .idea
	/.gitignore
	/checkstyle-idea.xml
	/misc.xml
	/vcs.xml    
├── .mvm
	/maven-wrapper.jar
	/maven-wrapper.properties
└── src
   └── java
	   └── ca.bcit.comp2522.termproject.td
		   └── driver
			/BlossomStorm
		   ├── enums
			/Affiliation
			/ArmourType
			/CurrentTurn
			/DamageType
			/ProjectileSize
			/Terrain
			/TurnState
			/Weather
		   ├── interfaces
			/Attacker
			/Combatant
			/Drawable
			/Usable
		   ├── items
			/Item
			/MedPack
		   ├── map
			/GameMap
			/Tile
			/TileHighlight
		   ├── unit
			/Unit
		   └── weapon
			/Firearm
			/Trap
			/Weapon
		/CutsceneManager
		/GameManager
		/UIManager
		/Vector2D
	/module-info.java            
```


## Features
Unlike many games of this genre, characters in Blossom Storm have access to a wide assortment of modern weapons and vehicles. Firearms will be the primary method of attacking; due to this, most combat in this game will be at range. Accuracy will be a significant factor in the player’s strategy: attacking distance, gun class, environmental conditions, fire type, and more will determine a character’s accuracy. Additionally, multi-hit attacks will play a large part in combat as fully automatic weapons are commonplace.

## Read More
Read the project plans and documentation here:
[Blossom Storm Documentation](https://docs.google.com/document/d/1ajxo79C1yCrWTRonEz8J--nV4USNBrs82EkxQ73g82c/edit?usp=sharing)
## Developers
*Nathan Ng* (https://github.com/nng32)

*Toco Tachibana* (https://github.com/toco-t)  
