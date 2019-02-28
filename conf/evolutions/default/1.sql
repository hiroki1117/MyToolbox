-- !Ups
CREATE TABLE VISION
(
id SERIAL PRIMARY KEY,
name TEXT NOT NULL CHECK(name!=''),
state INTEGER CHECK(state=1 OR state=2 OR state=3), -- 1:Active 2:Disable 3:Goal
created TIMESTAMP DEFAULT CLOCK_TIMESTAMP()
);

Insert into Vision (name, state) values('playvision', 1);

-- !Downs
DROP TABLE IF EXISTS VISION;
