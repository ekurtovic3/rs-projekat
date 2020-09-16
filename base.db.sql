BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "ProfesorSubject" (
	"idp"	INTEGER,
	"ids"	INTEGER,
	FOREIGN KEY("idp") REFERENCES "User"("id"),
	FOREIGN KEY("ids") REFERENCES "Subject"("id")
);
CREATE TABLE IF NOT EXISTS "Class" (
	"id"	INTEGER,
	"start"	INTEGER,
	"end"	INTEGER,
	"period"	INTEGER,
	"Classroom"	INTEGER,
	"Subject"	INTEGER,
	"Type"	INTEGER,
	"Date"  DATE ,
	PRIMARY KEY("id"),
	FOREIGN KEY("Classroom") REFERENCES "Classroom"("id"),
	FOREIGN KEY("Subject") REFERENCES "Subject"("id")
);
CREATE TABLE IF NOT EXISTS "User" (
	"id"	INTEGER,
	"name"	TEXT,
	"surname"	TEXT,
	"email"	TEXT,
	"jmbg"	TEXT,
	"username"	TEXT,
	"dateOfBirth"	DATE,
	"status"	INTEGER,
	"password" TEXT,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Classroom" (
	"id"	INTEGER,
	"name"	TEXT,
	"capacity"	INTEGER,
	PRIMARY KEY("id")
);
CREATE TABLE IF NOT EXISTS "Subject" (
	"id"	INTEGER,
	"name"	TEXT,
	PRIMARY KEY("id")
);

COMMIT;
