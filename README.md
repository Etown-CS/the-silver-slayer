# CS401 - The Silver Slayer
![Static Badge](https://img.shields.io/badge/etown-capstone-blue)

[Elizabethtown College](https://www.etown.edu/) | [Computer Science Department](https://www.etown.edu/schools/school-of-engineering-and-computer-science/computer-science/index.aspx) | [Capstone Project](https://github.com/Etown-CS)

A text-based RPG hosted in a WIP cybersecurity lab. The game features cybersecurity themes and take place across multiple computers running various operating systems. Players need to perform cybersecurity tasks and/or attacks in order to progress.
Detailed information can be found on the [project wiki](https://github.com/Etown-CS/the-silver-slayer/wiki/Project-Description).

## Project Overview
### Home Lab
The first piece of what we've be accomplishing over the course of our two semesters of working on this project.

This is the beginning of Elizabethtown College's own cybersecurity lab. Our project has many facets. We have multiple machines running VMs and connected to the internet via a [pfSense](https://www.pfsense.org/) router and DHCP server combo, further assisted by a [Pi-hole](https://pi-hole.net/) DNS server. We've also acquired two servers, one running Linux with [Wazuh](https://wazuh.com/) and [MySQL](https://www.mysql.com/), and the other running Windows Server 2019 and an active directory. Our setup and the processes we went through to accomplish everything are all described on the [blog](https://etown-cs-homelab.github.io/) (detailed below).
### The Silver Slayer
The Silver Slayer is a game that is meant to teach aspiring cybersecurity students what the basic kinds of attacks are, how to perform them for red team activities, and how to guard against them. It is made primarily in the style of a text-based RPG, and it runs on multiple computers throughout our home lab. This might be project two, but it is not unimportant and will be an excellent training program to go along with the lab. The game itself is split into two parts, coded in Java and in C respectively.

In The Silver Slayer, the player travels throughout multiple different areas, discovering locations, items, and enemies. The main goal of this game is to get to the Lair; this is the location of the final boss (The Silver Slayer). Even though the main goal is to beat this boss, the adventure doesn't stop there. After defeating The Silver Slayer, the player can still travel to past locations and uncover even more. Instructions on how to play are embedded into the game.
#### Building the Game
**Java Part:**

Firstly, install [Java](https://www.java.com/en/download/). Then create an executable JAR using Java's compiler. Note that the main class is contained in TheSilverSlayer.java.

**Linux:**

Ensure that [GCC](https://gcc.gnu.org/) is installed, and then use it to create an executable binary via the provided Makefile:
```
make
```
Theoretically, any C compiler could be used, although the Makefile only supports GCC.
### Blog
The blog is our way of showing the world what we've done. It also helps explain how we did what we did and allows people to continue on with our work. The blog is written in our own React.js template that uses Markdown for post formatting. This blog is published on Github pages.

The site is intended to be hosted and viewed on the web, and can be viewed at [https://etown-cs-homelab.github.io/](https://etown-cs-homelab.github.io/). However, you can run it locally if desired. The instructions for doing so can be found on the blog's [repository](https://github.com/etown-cs-homelab/etown-cs-homelab.github.io).
## Authors

[John McGovern](https://www.linkedin.com/in/john-mcgovern-cs/) | [mcgovernj@etown.edu](mailto:mcgovernj@etown.edu)

[Asher Wayde](https://www.linkedin.com/in/asher-wayde/) | [waydea@etown.edu](mailto:waydea@etown.edu)

[Martin Ratchford](https://www.linkedin.com/in/martin-ratchford-15061724a/) | [ratchfordm@etown.edu](mailto:ratchfordm@etown.edu)

## Support

Advisor: [Dr. Jingwen Wang](https://www.linkedin.com/in/jingwen-jessica-wang/)

Sponsor: [Netizen](https://www.netizen.net/)
