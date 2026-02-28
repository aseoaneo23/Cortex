CREATE TABLE IF NOT EXISTS inbox_items (
    id          TEXT PRIMARY KEY,
    type        TEXT NOT NULL,
    raw_content TEXT NOT NULL,
    source      TEXT NOT NULL DEFAULT 'manual',
    status      TEXT NOT NULL DEFAULT 'pending',
    created_at  TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS notes (
    id              TEXT PRIMARY KEY,
    inbox_item_id   TEXT,
    title           TEXT NOT NULL,
    summary         TEXT,
    content         TEXT,
    category        TEXT,
    tags            TEXT DEFAULT '[]',
    created_at      TEXT NOT NULL,
    FOREIGN KEY (inbox_item_id) REFERENCES inbox_items(id)
);
