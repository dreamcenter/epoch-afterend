CREATE DATABASE IF NOT EXISTS epoch;
USE epoch;

/*******************CREATE:API_TABLE*************************/
#DROP TABLE api;

CREATE TABLE IF NOT EXISTS api
(
    id INT primary key auto_increment,
    master VARCHAR(16) NOT NULL,
    permission TINYINT NOT NULL,
    name CHAR(20) NOT NULL,
    method TINYINT NOT NULL DEFAULT 0,
    url VARCHAR(255) NOT NULL,
    description VARCHAR(50) NOT NULL,
    type VARCHAR(30) NOT NULL,
    param TEXT
);

INSERT INTO api VALUES
(id,'root',1,'老黄历',0,'http://81.70.80.152/epoch/api/third/date','获取老黄历的各种描述信息',
 'application/json;charset=utf8','none'),
(id,'root',1,'舔狗日记',0,'http://81.70.80.152/epoch/api/third/flatterer','获取一条舔狗日记',
 'application/json;charset=utf8','none'),
(id,'root',1,'彩虹屁',0,'http://81.70.80.152/epoch/api/third/rainbow','获取一条彩虹屁',
 'application/json;charset=utf8','none'),
(id,'root',1,'精美文句',0,'http://81.70.80.152/epoch/api/third/gold','获取一条各种书刊杂志的优美句子',
 'application/json;charset=utf8','none'),
(id,'root',1,'公网ip',0,'http://81.70.80.152/epoch/api/third/ip/number','获取客户端的公网ip地址',
 'application/json;charset=utf8','none'),
(id,'root',1,'地理信息',0,'http://81.70.80.152/epoch/api/third/ip/detail','获取指定ip的地理信息',
 'application/json;charset=utf8','ip=(传入ip值)'),
(id,'root',1,'天气预报',0,'http://81.70.80.152/epoch/api/third/weather','获取指定ip的天气信息',
 'application/json;charset=utf8','ip=(传入ip值)');


/*********************CREATE:ACCOUNT_TABLE***********************/
# DROP TABLE account

CREATE TABLE IF NOT EXISTS account(
      id INT primary key auto_increment,
      nickname CHAR(20) NOT NULL UNIQUE,
      password CHAR(32) NOT NULL
);


/*********************CREATE:ACCOUNT_INFO_TABLE***********************/
#DROP table account_info;

CREATE TABLE IF NOT EXISTS account_info(
    id INT PRIMARY KEY AUTO_INCREMENT,
    cid INT UNIQUE NOT NULL,
    mail CHAR(30),
    avatar CHAR(30),
    visible TINYINT NOT NULL DEFAULT 1,
    FOREIGN KEY (cid) REFERENCES account(id)
)


# ********************************** TEST ************************************#

# *** api ***
SELECT * FROM api;
DELETE FROM api WHERE id=10;
UPDATE api set master='' WHERE id = 7 ;

# *** account ***
SELECT * FROM account;
SELECT password FROM account WHERE id=1;

# *** account_info ***
SELECT * FROM account_info;

# ******** complex-test ++
SELECT * FROM account
    LEFT JOIN account_info ai
    on account.id = ai.cid;
INSERT INTO account_info(cid)
    (SELECT id FROM account ORDER BY id ASC);
SELECT account.id,nickname,mail,avatar,visible FROM account,account_info
    WHERE account.id = account_info.cid and nickname='root';
