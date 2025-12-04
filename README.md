# CS401 - The Silver Slayer
![Static Badge](https://img.shields.io/badge/etown-capstone-blue)

[Elizabethtown College](https://www.etown.edu/) | [Computer Science Department](https://www.etown.edu/schools/school-of-engineering-and-computer-science/computer-science/index.aspx) | [Capstone Project](https://github.com/Etown-CS)

A text-based RPG hosted in a to-be-built cybersecurity lab. The game will feature cybersecurity themes and take place across multiple computers running various operating systems. Players will need to perform cybersecurity tasks and/or attacks in order to progress.
More information can be found on the [project wiki](https://github.com/Etown-CS/the-silver-slayer/wiki/Project-Description).

[//]: # (Your audience for the Readme.md are other developers who are joining your team. Specifically, the file should contain detailed instructions that any developer can follow to install, compile, run, and test your project. These are not only useful to new developers, but also to you when you have to re-install everything because your old laptop crashed. Also, the teachers of this class will be following your instructions.)

## Project Overview

### The Homelab Project
This is project #1 that we are attempting to accomplish over the course of the 2 semesters of working on this project.   
- The homelab
This is the beginning of the CS department's own cybersecurity lab, sponsered by netizen.  
Our current project has many facets to it we have multiple windows machines running VMs of ubuntu and windows 11 and connected to the internet with a pfsense router+dhcp server combo assisted by a piHole dns   
We are currently looking to expand our server devices to adding a wuzah server for reporting. We are also looking to add in our network vunerable device that should not be accessable from the internet, which will most likely be a windows vista PC   
Are setup, and the process that we did to accomplish this is all written on the blog.   
- The Blog
The blog is our way of showing the world what we did, and how to understand how to do it again, or continue on with our work.  
#### The Tech Stack
The blog is our own react.js template that uses markdown for post information. It is published to a github pages website.
#### Running locally  
The sight is intended to be used on the web, and primarily viewed there, you can run it locally if desired.  
To run it locally you must.
- install node.js
- clone the git repo locally
- run npm install
- run npm start
After these things it will run on your local system  
we have no API's that we are using to run this.

### The Silver Slayer Cybersecurity Game
The Silver Slayer, is a game that is meant to teach new aspiring cybersecurity students what the basic kinds of attacks there are, how to use them for red team activities and how to guard against them. It is made in the style of a rpg game that is mostly text based, and will run on multiple computers in the homelab. this might be in project #2, but it is not unimportant and will be an excelent training program to go along with the homelab.
#### Tech Stack
The game is mostly run in java, with a small section of it run in C  

#### Gameplay

The Silver Slayer game will 

#### Major Levels

EntryWay

#### Cybersecurity Concepts

#### Running the game locally

In order to build this project, you first have to install:

FOR WINDOWS:
-   [Java](https://www.java.com/en/download/)

FOR LINUX:
- GCC

[//]: # (If possible, list the actual commands you used to install these, so the reader can just cut-n-paste the commands and get everything setup. You only need to add instructions for the OS you are using.)

[//]: # (## Setup\nHere you list all the one-time things the developer needs to do after cloning your repo. Sometimes there is no need for this section, but some apps require some first-time configuration from the developer, for example: setting up a database for running your webapp locally.)

## Running
For the Windows/Java portions, create an executable JAR using Java's compiler:
```
javac Menu.java
jar cfe tss.jar Menu Menu.class
java -jar tss.jar
```
For Linux/C, use GCC to create an executable binary via the provided Makefile:
```
make
```

[//]: # (Specify the commands for a developer to run the app from the cloned repo.)

# Authors

[John McGovern](https://www.linkedin.com/in/john-mcgovern-cs/) | [mcgovernj@etown.edu](mailto:mcgovernj@etown.edu)

[Asher Wayde](https://www.linkedin.com/in/asher-wayde/) | [waydea@etown.edu](mailto:waydea@etown.edu)

[Martin Ratchford](https://www.linkedin.com/in/martin-ratchford-15061724a/) | [ratchfordm@etown.edu](mailto:ratchfordm@etown.edu)
