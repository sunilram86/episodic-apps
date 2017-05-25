create table viewings (
  id    bigint     not null    auto_increment  primary key,
  user_id bigint not null,
  show_id bigint not null,
  episode_id bigint  not NULL,
  timecode int not null,
  updated_at datetime not null,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (show_id) REFERENCES Shows(id),
  FOREIGN KEY (episode_id) REFERENCES episodes(id)
);