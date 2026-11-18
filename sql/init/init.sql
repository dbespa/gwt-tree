CREATE TABLE nodes (
id BIGSERIAL PRIMARY KEY,
parentId BIGINT,
name VARCHAR(30),
ip VARCHAR(15),
port INTEGER,
FOREIGN KEY (parentId) REFERENCES nodes(id) ON DELETE CASCADE
);

INSERT INTO nodes
VALUES
(0, NULL, 'rootNode', '127.0.0.1', 1111),
(1, 0, 'node-1', '127.0.1.1', 11),
(2, 0, 'node-2', '127.0.1.2', 12),
(3, 2, 'node-3', '127.0.1.21', 121),
(4, 1, 'node-4', '127.0.1.11', 111)
