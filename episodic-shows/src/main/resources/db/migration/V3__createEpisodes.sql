create table episodes (
  id    bigint     not null    auto_increment  primary key,
  show_id bigint,
  season_number int  not null,
  episode_number int  not NULL,
  FOREIGN KEY (show_id) REFERENCES Shows(id)
);