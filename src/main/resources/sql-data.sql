INSERT INTO `ims`.`customers` (`first_name`, `surname`) VALUES ('jordan', 'harrison');
INSERT INTO `ims`.`customers` (`first_name`, `surname`) VALUES ('steve', 'jones');

INSERT INTO `ims`.`items` (`item_name`, `price`) VALUES ('book', 6.99);
INSERT INTO `ims`.`items` (`item_name`, `price`) VALUES ('PC', 549.99);

INSERT INTO `ims`.`order_customer` (`customer_id`) VALUES (2);
INSERT INTO `ims`.`order_customer` (`customer_id`) VALUES (1);

INSERT INTO `ims`.`order_products` (`id`,`item_id`,`quantity`) VALUES (1,1,2);
INSERT INTO `ims`.`order_products` (`id`,`item_id`,`quantity`) VALUES (1,2,5);

INSERT INTO `ims`.`order_products` (`id`,`item_id`,`quantity`) VALUES (2,2,1);
