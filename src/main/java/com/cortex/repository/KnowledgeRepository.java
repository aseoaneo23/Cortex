package com.cortex.repository;

import com.cortex.enums.KnowledgeStatus;
import com.cortex.model.Knowledge;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class KnowledgeRepository {

    private final JdbcTemplate jdbc;

    public KnowledgeRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }
    private final RowMapper<Knowledge> rowMapper = (rs, rowNum) -> Knowledge.builder()
            .id(rs.getLong("id"))
            .title(rs.getString("title"))
            .content(rs.getString("content"))
            .createdAt(rs.getString("created_at"))
            .status(rs.getInt("status"))
            .topic(rs.getString("topic"))
            .build();

    public List<Knowledge> findAll() {
        return jdbc.query(
                "SELECT * FROM Knowledge",
                rowMapper);
    }

    @Transactional
    public Knowledge save(Knowledge item) {
        jdbc.update(
                "INSERT INTO Knowledge (id, title, content, created_at, status, topic) VALUES (?, ?, ?, ?, ?, ?)",
                item.getId(), item.getTitle(), item.getContent(),
                item.getCreatedAt(), item.getStatus(), item.getTopic());
        return item;
    }

    public List<Knowledge> findByStatus(KnowledgeStatus status) {
        return jdbc.query(
                "SELECT * FROM Knowledge WHERE status = ? ORDER BY created_at DESC",
                rowMapper,
                status.name());
    }

    public Optional<Knowledge> findById(String id) {
        List<Knowledge> results = jdbc.query(
                "SELECT * FROM Knowledge WHERE id = ?",
                rowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
    }

    @Transactional
    public void updateStatus(String id, String status) {
        jdbc.update("UPDATE Knowledge SET status = ? WHERE id = ?", status, id);
    }
}
