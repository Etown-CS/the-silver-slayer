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
    village bool default false,
    lake bool default false,
    mountain bool default false,
    cave bool default false,
    mine bool default false,
    desert bool default false,
    swamp bool default false,
    fracture bool default false,
    lair bool default false,
    
    PRIMARY KEY (enemyID)

);

INSERT INTO enemy (name, enemy_type, health, attack, defense, mountain) VALUES ("Air Cooler", "default", 4, 1, 4, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, mountain, mine, lair) VALUES ("Banshee", "malware", 9, 9, 1, "Adware", true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, swamp, lair) VALUES ("Bot Swarm", "malware", 30, 1, 20, "Botnet", true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, cave, swamp) VALUES ("Blue Flower", "default", 3, 3, 0, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, village, lake, mountain, cave, mine, desert, swamp) VALUES ("Bug", "default", 3, 1, 0, true, true, true, true, true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, lair) VALUES ("Cleanser", "malware", 1, 999, 0, "Wiper malware", true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, desert) VALUES ("Cyber Scorpion", "special", 5, 9, 2, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, spawn_message, mountain) VALUES ("Dat Boi", "special", 6, 9, 0, "Oh shoot here come dat boi", true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, mine, lair) VALUES ("Dweller", "default", 10, 4, 3, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, lake, swamp) VALUES ("Eel", "default", 5, 3, 1, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, mountain, lair) VALUES ("Facaeless", "malware", 6, 1, 5, "Fileless malware", true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, fracture) VALUES ("Figment", "special", 1, 0, 0, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, desert, swamp) VALUES ("Flashbang", "malware", 3, 7, 1, "Mobile malware", true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, fracture) VALUES ("Glitch", "special", -1, -1, -1, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, lake, mountain, cave, mine, desert, swamp) VALUES ("Gobbler", "default", 5, 3, 2, true, true, true, true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, village, lake, mountain, cave) VALUES ("Gremlin", "default", 4, 1, 1, true, true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, mountain) VALUES ("Liquid Cooler", "default", 5, 2, 3, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, lair) VALUES ("Mimic", "malware", 50, 0, 0, "Keylogger", true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, fracture) VALUES ("Memory", "special", 15, 1, 0, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, fracture) VALUES ("Packet", "special", 5, 5, 2, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, mountain, mine) VALUES ("Phantom", "default", 3, 5, 0, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, lake, mountain, cave, mine, desert, swamp, lair) VALUES ("PISMPE", "malware", 2, 0, 0, "Spyware", true, true, true, true, true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, fracture) VALUES ("Polychromatic Flower", "default", 9, 9, 0, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, village, lake, cave, mine, swamp) VALUES ("Rat", "default", 1, 3, 0, true, true, true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, cave, mine, lair) VALUES ("RAT", "malware", 6, 10, 0, "Trojan", true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, village, lake) VALUES ("Red Flower", "default", 3, 3, 0, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, village, lake, mountain, cave, desert) VALUES ("Scavenger", "special", 8, 0, 0, true, true, true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, desert, lair) VALUES ("Scrambler", "special", 10, 1, 0, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, cave, mine) VALUES ("Skeleton", "default", 6, 2, 3, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, lake, mountain, desert, swamp) VALUES ("Thief", "malware", 10, 3, 3, "Ransomware", true, true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, desert, swamp) VALUES ("Tumbler", "default", 8, 2, 2, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, lake, cave, swamp) VALUES ("Waterlogged", "default", 7, 3, 4, true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, lair) VALUES ("Wirefiend", "malware", 5, 5, 3, "Rootkit", true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, note, cave, swamp, lair) VALUES ("Worm", "malware", 4, 1, 5, "Worm", true, true, true);
INSERT INTO enemy (name, enemy_type, health, attack, defense, mountain, desert) VALUES ("Yellow Flower", "default", 3, 3, 0, true, true);

INSERT INTO enemy(name, enemy_type, health, attack, defense, boss, spawn_message, village) VALUES ("First Guardian", "default", 10, 3, 2, true, "tbd", true);
INSERT INTO enemy(name, enemy_type, health, attack, defense, boss, spawn_message, lake) VALUES ("The Big One", "default", 30, 3, 1, true, "tbd", true);
INSERT INTO enemy(name, enemy_type, health, attack, defense, boss, spawn_message, mountain) VALUES ("Abominable Snowball", "default", 40, 4, 4, true, "tbd", true);
INSERT INTO enemy(name, enemy_type, health, attack, defense, boss, spawn_message, cave) VALUES ("SkeleTON", "default", 30, 5, 5, true, "tbd", true);
INSERT INTO enemy(name, enemy_type, health, attack, defense, boss, spawn_message, mine) VALUES ("Last Prospector", "default", 45, 6, 10, true, "tbd", true);
INSERT INTO enemy(name, enemy_type, health, attack, defense, boss, spawn_message, desert) VALUES ("Firewall", "default", 60, 10, 5, true, "tbd", true);
INSERT INTO enemy(name, enemy_type, health, attack, defense, boss, spawn_message, swamp) VALUES ("Mudgulper", "default", 30, 10, 10, true, "tbd", true);
INSERT INTO enemy(name, enemy_type, health, attack, defense, boss, spawn_message, fracture) VALUES ("DISCOMBOBU-INATOR", "default", -1, -1, -1, true, "tbd", true);
INSERT INTO enemy(name, enemy_type, health, attack, defense, boss, spawn_message, lair) VALUES ("The Silver Slayer", "default", -1, -1, -1, true, '"Let us begin."', true);

CREATE TABLE IF NOT EXISTS item (

	itemID int auto_increment,
    name varchar(32) not null,
    item_type varchar(16) not null, -- See /game/src/ItemType.java for valid types
    magnitude int,
    consumable bool not null,
    is_unique bool not null,
    info varchar(255),
    
    PRIMARY KEY (itemID)

);

INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("57396329", "key", 0, false, true, "Is this what you're looking for?");

INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Silver Spoon", "weapon", 1, false, true, "A shiny, silver spoon.");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Paper Hat", "armor", 1, false, true, "An origami paper hat. Adds a point to ~style~.");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Rock", "junk", 0, false, false, "A cool rock. Does nothing.");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Goggles", "key", 0, false, true, "A pair of purple, plastic swimming goggles. Thankfully, they don't leak.");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Non-biting Ring", "wearable", 1, false, true, "An iron ring with a bloodred gemstone. Does not bite.");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Cactus Fruit", "health", 3, true, false, "A colorful cactus fruit. Pull out the spines first!");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Mottled Swamp Fruit", "health", 3, true, false, "A mottled fruit from the swamp's woodland.");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Oblong Swamp Fruit", "health", -2, true, false, "An oblong fruit from the swamp's woodland.");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Bulbous Swamp Fruit", "health", 4, true, false, "A bulbous fruit from the swamp's woodland.");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Speckled Swamp Fruit", "health", -3, true, false, "A speckled fruit from the swamp's woodland.");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("Star Swamp Fruit", "health", 7, true, false, "A star-shaped fruit from the swamp's woodland.");
INSERT INTO item (name, item_type, magnitude, consumable, is_unique, info) VALUES ("The Silver Sword", "weapon", 99, false, true, "A sharp, silver sword taken from a mighty foe. The blade is strangely notched, and the pattern appears to be intentionally engraved.");