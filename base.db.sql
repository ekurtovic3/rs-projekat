BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "Classroom" (
	"id"	INTEGER,
	"name"	TEXT,
	"capacity"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "User" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"email"	TEXT,
	"jmbg"	TEXT,
	"username"	TEXT,
	"dateOfBirth"	TEXT,
	"status"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Classs" (
	"id"	INTEGER,
	"start"	TIME,
	"end"	TIME,
	"period"	INTEGER,
	"Class"	INTEGER,
	"Subject"	INTEGER,
	PRIMARY KEY("id"),
	FOREIGN KEY("Class") REFERENCES "Classroom"("id"),
	FOREIGN KEY("Subject") REFERENCES "Subject"("id")
);
CREATE TABLE IF NOT EXISTS "ProfesorSubject" (
	"id profesor"	INTEGER,
	"id student"	INTEGER,
	FOREIGN KEY("id profesor") REFERENCES "User"("id"),
	FOREIGN KEY("id student") REFERENCES "Subject"("id")
);
CREATE TABLE IF NOT EXISTS "Subject" (
	"id"	INTEGER,
	"name"	TEXT,
	PRIMARY KEY("id")
);
COMMIT;
