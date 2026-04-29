INSERT IGNORE INTO search_term (word, frequency)
VALUES ('apple', 10),
       ('application', 8),
       ('apply', 6),
       ('appetite', 3),
       ('apt', 2),
       ('ball', 9),
       ('batman', 7),
       ('band', 5),
       ('battle', 4),
       ('cat', 8),
       ('catch', 6),
       ('camera', 5)
ON DUPLICATE KEY UPDATE frequency = VALUES(frequency);
