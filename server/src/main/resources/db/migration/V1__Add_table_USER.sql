CREATE TABLE user (
  username  VARCHAR(255)  NOT NULL,
  password  VARCHAR(255)  NOT NULL,

  CONSTRAINT user_pk PRIMARY KEY (username)
);
