create table stock_fb_volume(
id int PRIMARY KEY  auto_increment,
symbol char(6),
TRADE_TYPE int,
VOLUME_INC int,
PRICE DOUBLE,
PRICE_PRE DOUBLE,
PRICE_INC DOUBLE,
DATE_STR CHAR(11),
TRADE_TYPE_STR VARCHAR(10))ENGINE=INNODB