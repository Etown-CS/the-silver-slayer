CREATE DATABASE IF NOT EXISTS tss;
USE tss;

CREATE TABLE IF NOT EXISTS enemy (

	enemyID int auto_increment,
    enemy_name varchar(32) not null,
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

-- Normal Enemies
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, mountain) VALUES ("Air Cooler", "default", 4, 1, 4, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, mountain, mine, lair) VALUES ("Banshee", "malware", 9, 9, 1, "Adware", true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, swamp, lair) VALUES ("Bot Swarm", "malware", 30, 1, 20, "Botnet", true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, cave, swamp) VALUES ("Blue Flower", "default", 3, 3, 0, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, village, lake, mountain, cave, mine, desert, swamp) VALUES ("Bug", "default", 3, 1, 0, true, true, true, true, true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, lair) VALUES ("Cleanser", "malware", 1, 999, 0, "Wiper malware", true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, desert) VALUES ("Cyber Scorpion", "special", 5, 9, 2, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, spawn_message, mountain) VALUES ("Dat Boi", "special", 6, 9, 0, "Oh shoot here come dat boi", true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, mine, lair) VALUES ("Dweller", "default", 10, 4, 3, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, lake, swamp) VALUES ("Eel", "default", 5, 3, 1, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, mountain, lair) VALUES ("Facaeless", "malware", 6, 1, 5, "Fileless malware", true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, fracture) VALUES ("Figment", "special", 1, 0, 0, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, desert, swamp) VALUES ("Flashbang", "malware", 3, 7, 1, "Mobile malware", true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, fracture) VALUES ("Glitch", "special", -1, -1, -1, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, lake, mountain, cave, mine, desert, swamp) VALUES ("Gobbler", "default", 5, 3, 2, true, true, true, true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, village, lake, mountain, cave) VALUES ("Gremlin", "default", 4, 1, 1, true, true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, cave, mine) VALUES ("Groundhog", "default", 3, 3, 3, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, mountain) VALUES ("Liquid Cooler", "default", 5, 2, 3, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, lair) VALUES ("Mimic", "malware", 50, 0, 0, "Keylogger", true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, fracture) VALUES ("Memory", "special", 15, 1, 0, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, fracture) VALUES ("Packet", "special", 5, 5, 2, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, mountain, mine) VALUES ("Phantom", "default", 3, 5, 0, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, lake, mountain, cave, mine, desert, swamp, lair) VALUES ("PISMPE", "malware", 2, 0, 0, "Spyware", true, true, true, true, true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, fracture) VALUES ("Polychromatic Flower", "default", 9, 9, 0, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, village, lake, cave, mine, swamp) VALUES ("Rat", "default", 1, 3, 0, true, true, true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, cave, mine, lair) VALUES ("RAT", "malware", 6, 10, 0, "Trojan", true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, village, lake) VALUES ("Red Flower", "default", 3, 3, 0, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, village, lake, mountain, cave, desert) VALUES ("Scavenger", "special", 8, 0, 0, true, true, true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, desert, lair) VALUES ("Scrambler", "special", 10, 1, 0, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, cave, mine) VALUES ("Skeleton", "default", 6, 2, 3, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, lake, mountain, desert, swamp) VALUES ("Thief", "malware", 10, 3, 3, "Ransomware", true, true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, desert, swamp) VALUES ("Tumbler", "default", 8, 2, 2, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, lake, cave, swamp) VALUES ("Waterlogged", "default", 7, 3, 4, true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, lair) VALUES ("Wirefiend", "malware", 5, 5, 3, "Rootkit", true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, note, cave, swamp, lair) VALUES ("Worm", "malware", 4, 1, 5, "Worm", true, true, true);
INSERT INTO enemy (enemy_name, enemy_type, health, attack, defense, mountain, desert) VALUES ("Yellow Flower", "default", 3, 3, 0, true, true);

-- Bosses
INSERT INTO enemy(enemy_name, enemy_type, health, attack, defense, boss, spawn_message, village) VALUES ("First Guardian", "default", 10, 3, 2, true, "tbd", true);
INSERT INTO enemy(enemy_name, enemy_type, health, attack, defense, boss, spawn_message, lake) VALUES ("The Big One", "default", 30, 3, 1, true, "tbd", true);
INSERT INTO enemy(enemy_name, enemy_type, health, attack, defense, boss, spawn_message, mountain) VALUES ("Abominable Snowball", "default", 40, 4, 4, true, "That was your last mistake.", true);
INSERT INTO enemy(enemy_name, enemy_type, health, attack, defense, boss, spawn_message, cave) VALUES ("SkeleTON", "default", 30, 5, 5, true, "tbd", true);
INSERT INTO enemy(enemy_name, enemy_type, health, attack, defense, boss, spawn_message, mine) VALUES ("Last Prospector", "default", 45, 6, 10, true, "tbd", true);
INSERT INTO enemy(enemy_name, enemy_type, health, attack, defense, boss, spawn_message, desert) VALUES ("Firewall", "default", 60, 10, 5, true, "tbd", true);
INSERT INTO enemy(enemy_name, enemy_type, health, attack, defense, boss, spawn_message, swamp) VALUES ("Mudgulper", "default", 30, 10, 10, true, "tbd", true);
INSERT INTO enemy(enemy_name, enemy_type, health, attack, defense, boss, spawn_message, fracture) VALUES ("DISCOMBOBU-INATOR", "default", -1, -1, -1, true, "tbd", true);
INSERT INTO enemy(enemy_name, enemy_type, health, attack, defense, boss, spawn_message, lair) VALUES ("The Silver Slayer", "default", -1, -1, -1, true, '"Let us begin."', true);

CREATE TABLE IF NOT EXISTS item (

	itemID int auto_increment,
    item_name varchar(32) not null,
    item_desc varchar(255) not null,
    item_type varchar(16) not null, -- See /game/src/ItemType.java for valid types. Use all lowercase
    item_value int not null,
    magnitude int not null,
    consumable bool not null,
    is_unique bool not null,
    
    PRIMARY KEY (itemID)

);

INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Silver Spoon", "A shiny, silver spoon.", "weapon", 25, 1, false, true);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Paper Hat", "An origami paper hat. Adds a point to ~style~.", "armor", 5, 1, false, true);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Rock", "An interesting rock. Does nothing.", "junk", 0, 0, false, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Goggles", "A pair of purple swimming goggles. Thankfully, these don't leak.", "key", 0, 0, false, true);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Wooden Club", "A hefty branch from the Mountain's slopes. Bonk!", "weapon", 10, 4, false, true);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Non-Biting Ring", "A golden, fanged ring inset with a blood-red gemstone.", "wearable", 50, 0, false, true);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Cactus Fruit", "A colorful cactus fruit. Watch out for spines!", "health", 15, 3, true, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Mottled Fruit", "A mottled swamp fruit.", "health", 20, 3, true, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Oblong Fruit", "An oblong swamp fruit.", "health", 5, 3, true, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Bulbous Fruit", "A bulbous swamp fruit.", "health", 25, 3, true, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Speckled Fruit", "A speckled swamp fruit.", "health", 1, 3, true, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Star Fruit", "A star-shaped swamp fruit.", "health", 50, 3, true, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Silver Sword", "A sharp, silver sword. The blade is strangely notched, seemingly with intention.", "weapon", 10000, 100, false, true);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Trash", "What even is this?", "junk", 0, 0, false, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Junk", "Literally just junk.", "junk", 0, 0, false, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Rubbish", "A collection of, well, rubbish.", "junk", 0, 0, false, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Expired Something", "An expired morsel of some unknown substance.", "health", 1, -1, true, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Mystery Meat", "This could be anything.", "health", 5, 2, true, false);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Bean", "One, singular bean.", "health", 5, 1, true, true);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Depleted Rod", "A depleted reactor rod. Fragile and unstable.", "weapon", 10, 1, false, true);
INSERT INTO item (item_name, item_desc, item_type, item_value, magnitude, consumable, is_unique) VALUES ("Email", "Its definitely not a scam.", "junk", 0, 0, false, false);

CREATE TABLE IF NOT EXISTS _tss_meta (

	metaID int auto_increment,
    raw_data varchar(255),
    layers int default 0,
    
    PRIMARY KEY (metaID)

);

INSERT INTO _tss_meta (raw_data, layers) VALUES ("Vm0weE1HRXdNVWRXV0d4VVltdHdUMVl3Vm5kVlZscDBaVWRHVjFac2JETlpWVlpQVm14S2RHVkdiR0ZTVmxwb1ZsVmFWMVpWTVVWaGVqQTk=", 8);