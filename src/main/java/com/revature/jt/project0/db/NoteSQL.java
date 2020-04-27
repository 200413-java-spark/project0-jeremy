package com.revature.jt.project0.db;

import com.revature.jt.project0.db.NoteDataSource;
import com.revature.jt.project0.model.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Clob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoteSQL implements NoteDAO<Note> {
    private NoteDataSource ds;
    private static final Logger logger = LoggerFactory.getLogger(NoteSQL.class);

    public NoteSQL(NoteDataSource ds) {
        this.ds = ds;
    }

    private Note extractNoteFromRS(ResultSet rs) throws SQLException {
        Note note = new Note();

        Clob entryClob = rs.getClob("entry");
        String entryString = entryClob.getSubString(1, (int) entryClob.length());

        note.setId(rs.getInt("id"));
        note.setEntry(entryString);
        note.setCategory(rs.getString("category"));
        note.setCreationDateTime(rs.getObject("creationdatetime", LocalDateTime.class));

        return note;
    }

    @Override
    public Note getNote(Integer id) {
        Note result = new Note();
        String sql = "SELECT * FROM notes WHERE id=?";
        try (Connection conn = ds.getConnected();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    result = extractNoteFromRS(rs);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }
        return result;
    }

    @Override
    public List<Note> getAllNotes() {
        List<Note> results = new ArrayList<>();
        String sql = "SELECT * FROM notes";
        try (Connection conn = ds.getConnected();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Note result = extractNoteFromRS(rs);
                results.add(result);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }
        return results;
    }

    @Override
    public List<Note> getNoteByCategory(String category) {
        List<Note> results = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE category LIKE ?";
        try (Connection conn = ds.getConnected();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Note result = extractNoteFromRS(rs);
                    results.add(result);
                }
            }
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }
        return results;
    }

    @Override
    public void insertNote(Note note) {
        if (note.getId() == null) {
            String sql = "INSERT INTO notes (entry, category, creationdatetime) VALUES (?,?,?)";
            try (Connection conn = ds.getConnected();
                    PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, note.getEntry());
                stmt.setString(2, note.getCategory());
                stmt.setObject(3, note.getCreationDateTime());
                stmt.executeUpdate();
                logger.debug("Inserted note");
            } catch (SQLException e) {
                logger.error("SQL Exception", e);
            }
        } else {
            String sql =
                    "INSERT INTO notes (id, entry, category, creationdatetime) VALUES (?,?,?,?)";
            try (Connection conn = ds.getConnected();
                    PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, note.getId());
                stmt.setString(2, note.getEntry());
                stmt.setString(3, note.getCategory());
                stmt.setObject(4, note.getCreationDateTime());
                stmt.executeUpdate();
                logger.debug("Inserted note");
            } catch (SQLException e) {
                logger.error("SQL Exception", e);
            }
        }
    }

    @Override
    public void insertNoteList(List<Note> notes) {
        String sql = "INSERT INTO notes (entry, category, creationdatetime) VALUES (?,?,?)";
        try (Connection conn = ds.getConnected();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            conn.setAutoCommit(false);
            for (Note note : notes) {
                stmt.setString(1, note.getEntry());
                stmt.setString(2, note.getCategory());
                stmt.setObject(3, note.getCreationDateTime());
                stmt.addBatch();
            }
            int n[] = stmt.executeBatch();
            Integer total = Arrays.stream(n).sum();
            System.out.println(total + " records inserted!");
            logger.debug(total + " records inserted into db");
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }
    }

    @Override
    public void updateNote(Note note) {
        String sql = "UPDATE notes SET entry=?, category=?, creationdatetime=? WHERE id=?";
        try (Connection conn = ds.getConnected();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, note.getEntry());
            stmt.setString(2, note.getCategory());
            stmt.setObject(3, note.getCreationDateTime());
            stmt.setInt(4, note.getId());
            int n = stmt.executeUpdate();
            if (n == 0) {
                System.out.println("DB could not be updated with " + note);
                logger.debug("DB could not be updated with " + note);
            }
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }
    }

    @Override
    public void deleteNote(Integer id) {
        String sql = "DELETE FROM notes WHERE id=?";
        try (Connection conn = ds.getConnected(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int n = stmt.executeUpdate();
            if (n == 0) {
                System.out.println("Entry could not be deleted");
                logger.debug("Entry could not be deleted");
            }
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        }
    }

    @Override
    public void nuke() {
        String sql = "DELETE FROM notes";
        try (Connection conn = ds.getConnected(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            logger.error("SQL Exception", e);
        } finally {
            logger.debug("DB NUKED");
        }
    }
}
