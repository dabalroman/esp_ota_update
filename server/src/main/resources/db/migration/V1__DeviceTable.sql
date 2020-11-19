CREATE TABLE `device`
(
    `id`                   int PRIMARY KEY AUTO_INCREMENT,
    `mac`                  varchar(17),
    `name`                 varchar(128) NOT NULL,
    `software_name_scheme` varchar(128),
    `status`               int,
    `last_checked`         datetime     NOT NULL,
    `last_updated`         datetime     NOT NULL
);

CREATE TABLE `update`
(
    `id`            int PRIMARY KEY AUTO_INCREMENT,
    `timestamp`     datetime NOT NULL,
    `device_id`     int,
    `software_from` int,
    `software_to`   int
);

CREATE TABLE `software`
(
    `id`         int PRIMARY KEY AUTO_INCREMENT,
    `version`    varchar(32) NOT NULL,
    `path`       varchar(128),
    `md5`        varchar(32),
    `created_at` datetime,
    `device_id`  int
);

ALTER TABLE `update`
    ADD FOREIGN KEY (`device_id`) REFERENCES `device` (`id`);

ALTER TABLE `update`
    ADD FOREIGN KEY (`software_from`) REFERENCES `software` (`id`);

ALTER TABLE `update`
    ADD FOREIGN KEY (`software_to`) REFERENCES `software` (`id`);

ALTER TABLE `software`
    ADD FOREIGN KEY (`device_id`) REFERENCES `device` (`id`);
