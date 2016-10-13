CREATE TABLE transponder (
    Transponder BIGINT NOT NULL,
    Name        VARCHAR(60),
    CONSTRAINT pk_transponder PRIMARY KEY (Transponder)
);

CREATE TABLE laps (
    LapId         SERIAL NOT NULL,
    Transponder   BIGINT NOT NULL,
    Lapnr         INT NOT NULL,
    Laptime       BIGINT NOT NULL,
    Modtime       TIMESTAMP NOT NULL DEFAULT current_timestamp,
    CONSTRAINT pk_lap PRIMARY KEY (LapId)
);

CREATE TABLE bestMinutes (
    BestMinutesId SERIAL NOT NULL,
    Transponder   BIGINT NOT NULL,
    NrOfLaps      INTEGER,
    TotalTime     BIGINT,
    LapIDs        integer ARRAY,
    Modtime       TIMESTAMP NOT NULL DEFAULT current_timestamp,
    CONSTRAINT pk_bestMinutes PRIMARY KEY (BestMinutesId)
);

CREATE TABLE bestLaps (
    BestLapsId  SERIAL NOT NULL,
    Transponder BIGINT NOT NULL,
    NrOfLaps    INTEGER,
    TotalTime   BIGINT,
    LapIDs      integer ARRAY,
    Modtime     TIMESTAMP NOT NULL DEFAULT current_timestamp,
    CONSTRAINT pk_bestLaps PRIMARY KEY (BestLapsId)
);