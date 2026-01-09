# CS401 - The Silver Slayer
![Static Badge](https://img.shields.io/badge/etown-capstone-blue)

[Elizabethtown College](https://www.etown.edu/) | [Computer Science Department](https://www.etown.edu/schools/school-of-engineering-and-computer-science/computer-science/index.aspx) | [Capstone Project](https://github.com/Etown-CS)

A text-based RPG hosted in a WIP cybersecurity lab. The game will feature cybersecurity themes and take place across multiple computers running various operating systems. Players will need to perform cybersecurity tasks and/or attacks in order to progress.
Detailed information can be found on the [project wiki](https://github.com/Etown-CS/the-silver-slayer/wiki/Project-Description).

[//]: # (Your audience for the Readme.md are other developers who are joining your team. Specifically, the file should contain detailed instructions that any developer can follow to install, compile, run, and test your project. These are not only useful to new developers, but also to you when you have to re-install everything because your old laptop crashed. Also, the teachers of this class will be following your instructions.)
## Project Overview
### Homelab
This is the first piece of what we will be accomplishing over the course of our two semesters of working on this project.

This is the beginning of Elizabethtown College's own cybersecurity lab. Our project has many facets. We have multiple machines running VMs and connected to the internet via a [pfSense](https://www.pfsense.org/) router and DHCP server combo, further assisted by a [Pi-hole](https://pi-hole.net/) DNS server. We are currently looking to expand our quantity of devices by adding a [Wazuh](https://wazuh.com/) server. Our setup and the processes we went through to accomplish everything are all written on the [blog](https://etown-cs-homelab.github.io/) (detailed below).
### The Silver Slayer
The Silver Slayer is a game that is meant to teach aspiring cybersecurity students what the basic kinds of attacks there are, how to perform them for red team activities, and how to guard against them. It is made primarily in the style of a text-based RPG, and it will run on multiple computers throughout our Homelab. This might be project two, but it is not unimportant and will be an excelent training program to go along with the lab. The game itself is split into several parts, some of which are coded in Java and others in C.

In The Silver Slayer, the player travels throughout multiple different areas, discovering locations, items, and enemies. The main goal of this game is to get to the Lair; this is the location of the final boss (The Silver Slayer). Even though the main goal is to beat this boss, the adventure doesn't stop there. After defeating The Silver Slayer, the player can still travel to past location and uncover even more. Instructions on how to play are embedded into the game.

#### Building the Game

**Windows:**

Firstly, install [Java](https://www.java.com/en/download/). Then create an executable JAR using Java's compiler:
```
javac Menu.java
jar cfe tss.jar Menu Menu.class
java -jar tss.jar
```
**Linux:**

Ensure that [GCC](https://gcc.gnu.org/) is installed, and then use it to create an executable binary via the provided Makefile:
```
make
```
Theoretically, any C compiler could be used, although the Makefile only supports GCC.

[//]: # (If possible, list the actual commands you used to install these, so the reader can just cut-n-paste the commands and get everything setup. You only need to add instructions for the OS you are using.)

[//]: # (## Setup\nHere you list all the one-time things the developer needs to do after cloning your repo. Sometimes there is no need for this section, but some apps require some first-time configuration from the developer, for example: setting up a database for running your webapp locally.)

[//]: # (Specify the commands for a developer to run the app from the cloned repo.)
### Blog
The blog is our way of showing the world what we've done. It will also help explain how we did what we did and allow people to continue on with our work. The blog is written in our own React.js template that uses Markdown for post formatting. This blog is published on Github pages.

The site is intended to be hosted and viewed on the web, and can be viewed at [https://etown-cs-homelab.github.io/](https://etown-cs-homelab.github.io/). However, you can run it locally if desired. The instructions for doing so can be found on the blog's [repository](https://github.com/etown-cs-homelab/etown-cs-homelab.github.io).
## Authors

[John McGovern](https://www.linkedin.com/in/john-mcgovern-cs/) | [mcgovernj@etown.edu](mailto:mcgovernj@etown.edu)

[Asher Wayde](https://www.linkedin.com/in/asher-wayde/) | [waydea@etown.edu](mailto:waydea@etown.edu)

[Martin Ratchford](https://www.linkedin.com/in/martin-ratchford-15061724a/) | [ratchfordm@etown.edu](mailto:ratchfordm@etown.edu)

Sponsored by [Netizen](https://www.netizen.net/)
