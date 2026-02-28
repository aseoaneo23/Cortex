package com.cortex.repository;

import com.cortex.model.InboxItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InboxRepository {

    private final JdbcTemplate jdbc;

    public InboxRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<InboxItem> rowMapper = (rs, rowNum) -> InboxItem.builder()
            .id(rs.getString("id"))
            .type(rs.getString("type"))
            .rawContent(rs.getString("raw_content"))
            .source(rs.getString("source"))
            .status(rs.getString("status"))
            .createdAt(rs.getString("created_at"))
            .build();

    public InboxItem save(InboxItem item) {
        jdbc.update(
                "INSERT INTO inbox_items (id, type, raw_content, source, status, created_at) VALUES (?, ?, ?, ?, ?, ?)",
                item.getId(), item.getType(), item.getRawContent(),
                item.getSource(), item.getStatus(), item.getCreatedAt());
        return item;
    }

    public List<InboxItem> findAllPending() {
        return jdbc.query(
                "SELECT * FROM inbox_items WHERE status = 'pending' ORDER BY created_at DESC",
                rowMapper);
    }

    public Optional<InboxItem> findById(String id) {
        List<InboxItem> results = jdbc.query(
                "SELECT * FROM inbox_items WHERE id = ?",
                rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public void updateStatus(String id, String status) {
        jdbc.update("UPDATE inbox_items SET status = ? WHERE id = ?", status, id);
    }

    public void delete(String id) {
        jdbc.update("DELETE FROM inbox_items WHERE id = ?", id);
    }
}
