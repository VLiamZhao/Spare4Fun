INSERT INTO user (id, email, enabled, password, role)
VALUES
(1001, 's1', true, '$2a$10$45DrOdWJNWo/SCoKSrZejuW/IfYJq0WiCVqhaHY7YEDgxaucmWq96', 'USER'),
(1002, 's2', true, '$2a$10$NdA2UxeCW07iDdDOQTWXj.rmSk9P1AXhA.KL1.7qGifTIEZBmOoiy', 'USER'),
(1011, 'b1', true, '$2a$10$UGGMkRG7wlNL5taaBfhNYuP6/uUYNM19RUw/q.B.OjuFkeKZdZ8Ou', 'USER'),
(1012, 'b2', true, '$2a$10$ZBBW5HFfOs0PTmDhGXZODu5f0Mn9Ktn55Q93WnqegG28.F20w1aQ6', 'USER');

INSERT INTO location (id, city, country, line_one, line_two, state, zipcode)
VALUES
(1201, 'Milpitas', 'US', '468 DEMPSEY RD UNIT 268', '', 'CA', '95035'),
(1202, 'Milpitas', 'US', '1197 E Calaveras Blvd', '', 'CA', '95035'),
(1211, 'Bellevue', 'US', '1015 111TH PL SE', '', 'WA', '98004'),
(1212, 'Seattle', 'US', '3801 Brooklyn Ave NE', 'Stevens Court C143-1', 'WA', '98105'),
(1213, 'Seattle', 'US', '4147 The Ave', '', 'WA', '98105');

INSERT INTO category (id, category)
VALUES
(1401, 'textbook'),
(1402, 'furniture');

INSERT INTO item_condition(id, label)
VALUES
(1601, 'unused-new'),
(1602, 'used-new'),
(1603, 'used-good'),
(1604, 'used-fair');

INSERT INTO item (id, availability_time, description, enabled, fixed_price, hide_location, listing_price, quantity, quantity_on_hold, quantity_sold, title, category_id, condition_id, location_id, seller_id)
VALUES
(1801, '', 'textbook for CSE351', true, false, false, 80, 2, 0, 0, 'Computer Architecture', 1401, 1602, 1213, 1001),
(1802, '', 'newly bought furniture, urgent', true, true, true, 40, 1, 0, 0, 'Small Refridge', 1402, 1603, 1201, 1002),
(1803, '', 'wooden frame of same size, 2 white (1/2 sold), 1 black', true, true, false, 20, 1, 1, 1, 'Wooden Frame Shelf', 1402, 1603, 1202, 1002);

INSERT INTO offer (id, message, price, quantity, buyer_id, item_id, seller_id)
VALUES
(2001, 'in CSE351 next quarter', 60, 1, 1011, 1801, 1001),
(2002, 'want a white wooden frame', 20, 1, 1012, 1803, 1002),
(2003, 'want a black wooden frame', 20, 1, 1011, 1803, 1002);

INSERT INTO appointment (id, confirmed, buyer_id, confirmed_time_id, item_id, location_id, offer_id, seller_id)
VALUES
(2201, false, 1012, null, 1803, 1202, 2002, 1002),
(2202, false, 1011, null, 1803, 1202, 2003, 1002);

INSERT INTO time_slot (id, end_time, start_time, appointment_id)
VALUES
(2401, '2020-12-26T00:30:00.000Z', '2020-12-26T00:00:00.000Z', 2201),
(2402, '2020-12-26T01:30:00.000Z', '2020-12-26T01:00:00.000Z', 2201),
(2411, '2020-12-31T19:15:00.000Z', '2020-12-31T19:00:00.000Z', 2202),
(2412, '2020-12-31T17:15:00.000Z', '2020-12-31T17:00:00.000Z', 2202);

UPDATE appointment
SET confirmed = true, confirmed_time_id = 2401
WHERE id = 2201;

INSERT INTO payment_order (id, completed, expire_time, final_price, final_quantity, init_time, appointment_id, buyer_id, item_id, offer_id, seller_id)
VALUES
(2601, false, '2020-12-26T00:35:00.000Z', 20, 1, '2020-12-26T00:25:00.000Z', 2201, 1012, 1803, 2002, 1002);

UPDATE payment_order
SET completed = true
WHERE id = 2601;


