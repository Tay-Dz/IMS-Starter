INSERT INTO `customers` (`first_name`, `surname`) VALUES ('jordan', 'harrison');
INSERT INTO `customers` (`first_name`, `surname`) VALUES ('steve', 'smith');

INSERT INTO `ims`.`items` (`item_name`, `price`) VALUES ('book', 6.99);
INSERT INTO `ims`.`items` (`item_name`, `price`) VALUES ('tv', 199.99);

INSERT INTO `ims`.`order_customer` (`customer_id`) VALUES (1);

INSERT INTO `ims`.`order_products` (`id`,`item_id`,`quantity`) VALUES (1,1,2);
INSERT INTO `ims`.`order_products` (`id`,`item_id`,`quantity`) VALUES (1,2,1);

INSERT INTO `ims`.`accounts` (`user_name`, `password`,`is_admin`) VALUES ('root', 'root',true);
