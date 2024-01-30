CREATE TABLE order_item (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  description varchar(255) DEFAULT NULL,
  amount int(11) NOT NULL,
  order_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (order_id) REFERENCES orders(id)
)