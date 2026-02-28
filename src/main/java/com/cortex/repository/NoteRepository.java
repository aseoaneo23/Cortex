package com.cortex.repository;

import com.cortex.model.Note;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NoteRepository {

    private final JdbcTemplate jdbc;

    public NoteRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Note> rowMapper = (rs, rowNum) -> Note.builder()
            .id(rs.getString("id"))
            .inboxItemId(rs.getString("inbox_item_id"))
            .title(rs.getString("title"))
            .summary(rs.getString("summary"))
            .content(rs.getString("content"))
            .category(rs.getString("category"))
            .tags(rs.getString("tags"))
            .createdAt(rs.getString("created_at"))
            .build();

    public Note save(Note note) {
        jdbc.update(
                "INSERT INTO notes (id, inbox_item_id, title, summary, content, category, tags, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                note.getId(), note.getInboxItemId(), note.getTitle(),
                note.getSummary(), note.getContent(), note.getCategory(),
                note.getTags(), note.getCreatedAt());
        return note;
    }

    public List<Note> findAll() {
        return jdbc.query(
                "SELECT * FROM notes ORDER BY created_at DESC",
                rowMapper);
    }

    public List<Note> search(String query) {
        String like = "%" + query + "%";
        return jdbc.query(
                "SELECT * FROM notes WHERE title LIKE ? OR tags LIKE ? OR content LIKE ? OR summary LIKE ? ORDER BY created_at DESC",
                rowMapper, like, like, like, like);
    }

    public void delete(String id) {
        jdbc.update("DELETE FROM notes WHERE id = ?", id);
    }
}
