CREATE DATABASE baribhara_renting;

USE baribhara_renting;

CREATE TABLE client (
    client_id VARCHAR(32) UNIQUE NOT NULL,
    password VARCHAR(64) NOT NULL,
    name VARCHAR(64),
    phone INT(10),
    email VARCHAR(64),
    address VARCHAR(64),
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE owner (
    owner_id VARCHAR(32) UNIQUE NOT NULL,
    password VARCHAR(64) NOT NULL,
    name VARCHAR(64),
    phone INT(10),
    email VARCHAR(64),
    address VARCHAR(64)
);

CREATE TABLE admin (
    admin_id VARCHAR(32) UNIQUE NOT NULL,
    password VARCHAR(64) NOT NULL
);

CREATE TABLE property (
    property_id INT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(64),
    value INT,
    area DECIMAL(8,2),
    rooms INT,
    rating DECIMAL(3,2)
);

CREATE TABLE payment (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    client_id VARCHAR(32),
    property_id INT,
    payment_type VARCHAR(32),
    status VARCHAR(32),
    discount DECIMAL(10, 2),
    approved_by VARCHAR(32),
    FOREIGN KEY (client_id) REFERENCES client(client_id),
    FOREIGN KEY (property_id) REFERENCES property(property_id),
    FOREIGN KEY (approved_by) REFERENCES admin(admin_id)
);

CREATE TABLE owners_leases_property (
    owner_id VARCHAR(32),
    property_id INT,
    PRIMARY KEY (owner_id, property_id),
    FOREIGN KEY (owner_id) REFERENCES owner(owner_id),
    FOREIGN KEY (property_id) REFERENCES property(property_id)
);

CREATE TABLE client_rents_property (
    client_id VARCHAR(32),
    property_id INT,
    start_date DATE,
    end_date DATE,
    payment_id INT,
    PRIMARY KEY (client_id, property_id),
    FOREIGN KEY (client_id) REFERENCES client(client_id),
    FOREIGN KEY (property_id) REFERENCES property(property_id),
    FOREIGN KEY (payment_id) REFERENCES payment(transaction_id)
);

CREATE TABLE client_rates_property (
    client_id VARCHAR(32),
    property_id INT,
    rating DECIMAL(3, 2),
    PRIMARY KEY (client_id, property_id),
    FOREIGN KEY (client_id) REFERENCES client(client_id),
    FOREIGN KEY (property_id) REFERENCES property(property_id)
);

ALTER TABLE client_rents_property
ADD COLUMN payment_approved BOOLEAN DEFAULT FALSE;


INSERT INTO `admin` (`admin_id`, `password`) VALUES
('admin', '$2y$10$iqezQbbFUgnM1bPiS/ODH.Ebp8O3AeTKPeuZmOSfP1iKY5753jEQ2'),
('xxx', '$2y$10$psRZyZGO0WpxW9MPdlB6We5IJfVfUK07GuDFg.oUIc5OvQwTA4cDy');

INSERT INTO `client` (`client_id`, `password`, `name`, `phone`, `email`, `address`, `registration_date`) VALUES
('adam', 'oogabooga', 'Adam Jucas', 1123123, 'adam@den.ovh', '123 superhotel', '2024-12-19 17:58:49'),
('adam2', '$2y$10$1f/.XSSq9u53NPxsxL33VOFjhEasjxpfgMFHfTbajuizBDdXfD7vq', NULL, NULL, NULL, NULL, '2024-12-19 18:39:40'),
('adam3', '$2y$10$89p3hQ4KDHSnrQDnUcoese/8kgln.EVpSGU4UyzJTbH2mkx0YwcWq', NULL, NULL, NULL, NULL, '2024-12-19 18:41:05'),
('adam4', '$2y$10$oH81y1Wf5LTDzMkCBBqZQODOA/SUhz6qMBzp5pJ1vZ7Ot4MknHXt2', NULL, NULL, NULL, NULL, '2024-12-19 18:41:54'),
('admin', '$2y$10$TAILYH0bC1Hg4o4np1XYFOphAmteMz5VMGTFq7b5QWBLHkvPTb/rK', NULL, NULL, NULL, NULL, '2024-12-19 18:51:46'),
('blyat', '$2y$10$Y6i5iPyYQDbwjeyjOSu7ueSvkj//nn3WGiBQp40QJLYBS8k5Ywjr6', NULL, NULL, NULL, NULL, '2024-12-19 18:18:14'),
('meme', '$2y$10$iqezQbbFUgnM1bPiS/ODH.Ebp8O3AeTKPeuZmOSfP1iKY5753jEQ2', NULL, NULL, NULL, NULL, '2024-12-19 18:46:03'),
('mukto', '$2y$10$Yxg.TQmJRvr/0zCNvyssr.obc0KpLGYz4TV36BNHWnPEVfLMjT1y2', NULL, NULL, NULL, NULL, '2024-12-19 19:54:31'),
('shaitan', '$2y$10$HMrJPUru6Xkq4oxfSgTe9uPJwm/VyP1OGRtjUayb7J8Tu6uBVrxRC', NULL, NULL, NULL, NULL, '2024-12-19 20:26:14'),
('user', '$2y$10$Zk0M69JBkSWFoTNZNSDsT.pbY1AkDOGLiaQcGJOfnIGmCdfS5MDB2', NULL, NULL, NULL, NULL, '2024-12-19 19:15:57');

INSERT INTO `owner` (`owner_id`, `password`, `name`, `phone`, `email`, `address`) VALUES
('abrar', '$2y$10$jBS90NqhAI1PKVlDwrSt0Oo0mr5wB0wHvkGHVPH6hqKGxvsLdTkHy', NULL, NULL, NULL, NULL),
('bariwala', '$2y$10$3igBSX8I91cChKiOAy/FfuZtwr0hHKeXsaoHbnvkSlQrHj6sgc6qC', NULL, NULL, NULL, NULL);

INSERT INTO `property` (`property_id`, `address`, `value`, `area`, `rooms`, `rating`) VALUES
(3, '789 Pine Road, Ogdenville', 900, 600, 1, 4.2),
(1, '123 Maple Street, Springfield', 1200, 850, 2, 4.5),
(5, '202 Cedar Street, Capital City', 1300, 950, 2, 4.4),
(2, '456 Oak Avenue, Shelbyville', 1500, 1200, 3, 4.7),
(4, '101 Birch Lane, North Haverbrook', 2000, 1500, 4, 4.9);

