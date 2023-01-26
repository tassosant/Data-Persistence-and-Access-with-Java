CREATE TABLE superheroes_powers(superhero_id integer REFERENCES superhero(Id),
							   power_id integer REFERENCES power(Id),
							   PRIMARY KEY (superhero_id, power_id)
							  );
			