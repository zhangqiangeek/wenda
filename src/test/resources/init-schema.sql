DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id`            INT          NOT NULL AUTO_INCREMENT,
  `title`         VARCHAR(255) NOT NULL,
  `content`       TEXT         NULL,
  `user_id`       INT          NOT NULL,
  `created_date`  DATETIME     NOT NULL,
  `comment_count` INT          NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `date_index` (`created_date` ASC)
);

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id`       INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name`     VARCHAR(64)      NOT NULL DEFAULT '',
  `password` VARCHAR(128)     NOT NULL DEFAULT '',
  `salt`     VARCHAR(32)      NOT NULL DEFAULT '',
  `head_url` VARCHAR(256)     NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;