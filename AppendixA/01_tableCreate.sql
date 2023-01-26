CREATE TABLE SuperHero (Id serial NOT NULL, 
						Name varchar(50),
						Alias varchar(50),
						Origin varchar(255),
					    PRIMARY KEY(Id)
					   );

CREATE TABLE Assistant (Id serial NOT NULL, 
						Name varchar(50) NOT NULL UNIQUE,
					    PRIMARY KEY(Id)
					   );

CREATE TABLE Power (Id serial NOT NULL, 
						Name varchar(50),
						Description varchar(255),
					    PRIMARY KEY(Id)
					   );
