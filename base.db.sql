BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "ProfesorSubject" (
	"idp"	INTEGER,
	"ids"	INTEGER,
	FOREIGN KEY("idp") REFERENCES "User"("id"),
	FOREIGN KEY("ids") REFERENCES "Subject"("id")
);
CREATE TABLE IF NOT EXISTS "Class" (
	"id"	INTEGER,
	"start"	TIME,
	"end"	TIME,
	"period"	INTEGER,
	"Classroom"	INTEGER,
	"Subject"	INTEGER,
	"Type"	INTEGER,
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
INSERT INTO "ProfesorSubject" VALUES (1,1);
INSERT INTO "ProfesorSubject" VALUES (2,1);
INSERT INTO "ProfesorSubject" VALUES (NULL,NULL);
INSERT INTO "ProfesorSubject" VALUES (NULL,NULL);
INSERT INTO "ProfesorSubject" VALUES (NULL,NULL);
INSERT INTO "ProfesorSubject" VALUES (NULL,NULL);
INSERT INTO "ProfesorSubject" VALUES (NULL,NULL);
INSERT INTO "ProfesorSubject" VALUES (NULL,NULL);
INSERT INTO "ProfesorSubject" VALUES (NULL,NULL);
INSERT INTO "ProfesorSubject" VALUES (NULL,NULL);
INSERT INTO "ProfesorSubject" VALUES (NULL,NULL);
INSERT INTO "ProfesorSubject" VALUES (1,2);
INSERT INTO "User" VALUES (0,'E','E','E','asd','e',1598997600000,1);
INSERT INTO "User" VALUES (1,'Emir ','Kurtovic','','asdasdsda','',1598997600000,1);
INSERT INTO "User" VALUES (2,'','','','','','','');
INSERT INTO "User" VALUES (3,'','','','','','','');
INSERT INTO "User" VALUES (5,'','','','','','','');
INSERT INTO "Classroom" VALUES (1,'2-0',50);
INSERT INTO "Classroom" VALUES (2,'2-a',60);
INSERT INTO "Classroom" VALUES (3,'asd',500);
INSERT INTO "Subject" VALUES (1,'Fizika');
INSERT INTO "Subject" VALUES (2,'Matematika');
INSERT INTO "Subject" VALUES (3,'asd');
COMMIT;
