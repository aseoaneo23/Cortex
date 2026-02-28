package com.cortex.repository;

import com.cortex.model.Knowledge;
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

    private final RowMapper<Knowledge> rowMapper = (rs, rowNum) -> Knowledge.builder()
            .id(rs.getString("id"))
            .type(rs.getString("type"))
            .rawContent(rs.getString("raw_content"))
            .source(rs.getString("source"))
            .status(rs.getString("status"))
            .createdAt(rs.getString("created_at"))
            .build();

    public Knowledge save(Knowledge item) {
        jdbc.update(
                "INSERT INTO inbox_items (id, type, raw_content, source, status, created_at) VALUES (?, ?, ?, ?, ?, ?)",
                item.getId(), item.getType(), item.getRawContent(),
                item.getSource(), item.getStatus(), item.getCreatedAt());
        return item;
    }

    //Se llama igual el model que la tabla

    public List<Knowledge> getAllKnowledge() {
        return jdbc.query(
                "SELECT * FROM Knowledge ",
                rowMapper);
    }

    public Optional<Knowledge> findById(String id) {
        List<Knowledge> results = jdbc.query(
                "SELECT * FROM inbox_items WHERE id = ?",
                rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    public void updateStatus(String id, String status) {
        jdbc.update("UPDATE inbox_items SET status = ? WHERE id = ?", status, id);
    }
}
