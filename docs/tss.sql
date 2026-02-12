CREATE DATABASE IF NOT EXISTS tss;
USE tss;

CREATE TABLE IF NOT EXISTS enemy (

	enemyID int auto_increment,
    name varchar(32) not null,
    enemy_type varchar(16) not null, -- should be "default", "special", or "malware"
    health int not null,
    attack int not null,
    defense int not null,
    boss bool default false,
    spawn_message varchar(255),
    note varchar(255),
    
    PRIMARY KEY (enemyID)

);

INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Air Cooler", "default", 4, 1, 4);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("Banshee", "malware", 9, 9, 1, "Adware");
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("Bot Swarm", "malware", 30, 1, 20, "Botnet");
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Blue Flower", "default", 3, 3, 0);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Bug", "default", 3, 1, 0);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("Cleanser", "malware", 1, 999, 0, "Wiper malware");
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Cyber Scorpion", "special", 5, 9, 2);
INSERT INTO enemy (name, enemy_type, health, attack, defense, spawn_message) VALUES ("Dat Boi", "special", 6, 7, 0, "Oh shoot here come dat boi");
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Dweller", "default", 10, 4, 3);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Eel", "default", 5, 3, 1);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("Facaeless", "malware", 6, 1, 5, "Fileless malware");
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Figment", "special", 1, 0, 0);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("Flashbang", "malware", 3, 7, 1, "Mobile malware");
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Glitch", "special", -1, -1, -1);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Gobbler", "default", 5, 3, 2);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Gremlin", "default", 4, 1, 1);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Liquid Cooler", "default", 5, 2, 3);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("Mimic", "malware", 50, 0, 0, "Keylogger");
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Memory", "special", 15, 1, 0);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Packet", "special", 5, 5, 2);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Phantom", "default", 3, 5, 0);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("PISMPE", "malware", 2, 0, 0, "Spyware");
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Polychromatic Flower", "default", 9, 9, 0);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Rat", "default", 1, 3, 0);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("RAT", "malware", 6, 10, 0, "Trojan");
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Red Flower", "default", 3, 3, 0);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Scavenger", "special", 8, 0, 0);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Scrambler", "special", 10, 1, 0);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Skeleton", "default", 6, 2, 3);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("Thief", "malware", 10, 3, 3, "Ransomware");
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Tumbler", "default", 8, 2, 2);
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Waterlogged", "default", 7, 3, 4);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("Wirefiend", "malware", 5, 5, 3, "Rootkit");
INSERT INTO enemy (name, enemy_type, health, attack, defense, note) VALUES ("Worm", "malware", 4, 1, 5, "Worm");
INSERT INTO enemy (name, enemy_type, health, attack, defense) VALUES ("Yellow Flower", "default", 3, 3, 0);

CREATE TABLE IF NOT EXISTS item (

	itemID int auto_increment,
    name varchar(32) not null,
    item_type varchar(16) not null, -- See /game/src/ItemType.java for valid types
    magnitude int,
    consumable bool not null,
    info varchar(255),
    
    PRIMARY KEY (itemID)

);

INSERT INTO item (name, item_type, magnitude, consumable, info) VALUES ("The Silver Sword", "weapon", 99, false, "A sharp, silver sword taken from a mighty foe. The blade is strangely notched, and the pattern appears to be intentionally engraved.");