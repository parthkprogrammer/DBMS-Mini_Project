package com.sms.dao.impl;

import com.sms.dao.GradeDAO;
import com.sms.model.Grade;
import com.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GradeDAOImpl implements GradeDAO {

    @Override
    public void save(Grade grade) {
        String sql = "INSERT INTO grades (enrollment_id, grade_value, grade_letter, attendance, remarks, graded_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, grade.getEnrollmentId());
            if (grade.getGradeValue() != null) {
                stmt.setDouble(2, grade.getGradeValue());
            } else {
                stmt.setNull(2, Types.DECIMAL);
            }
            stmt.setString(3, grade.getGradeLetter());
            if (grade.getAttendance() != null) {
                stmt.setInt(4, grade.getAttendance());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setString(5, grade.getRemarks());
            stmt.setDate(6, grade.getGradedDate() != null ? Date.valueOf(grade.getGradedDate()) : null);
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    grade.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Grade> findById(Integer id) {
        String sql = "SELECT * FROM grades WHERE id = ?";
        return findSingleGrade(sql, id);
    }

    @Override
    public Optional<Grade> findByEnrollmentId(int enrollmentId) {
        String sql = "SELECT * FROM grades WHERE enrollment_id = ?";
        return findSingleGrade(sql, enrollmentId);
    }

    private Optional<Grade> findSingleGrade(String sql, int param) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, param);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToGrade(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Grade> findAll() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT * FROM grades";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                grades.add(mapResultSetToGrade(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    @Override
    public void update(Grade grade) {
        String sql = "UPDATE grades SET enrollment_id = ?, grade_value = ?, grade_letter = ?, attendance = ?, remarks = ?, graded_date = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, grade.getEnrollmentId());
            if (grade.getGradeValue() != null) {
                stmt.setDouble(2, grade.getGradeValue());
            } else {
                stmt.setNull(2, Types.DECIMAL);
            }
            stmt.setString(3, grade.getGradeLetter());
            if (grade.getAttendance() != null) {
                stmt.setInt(4, grade.getAttendance());
            } else {
                stmt.setNull(4, Types.INTEGER);
            }
            stmt.setString(5, grade.getRemarks());
            stmt.setDate(6, grade.getGradedDate() != null ? Date.valueOf(grade.getGradedDate()) : null);
            stmt.setInt(7, grade.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM grades WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Grade mapResultSetToGrade(ResultSet rs) throws SQLException {
        Grade grade = new Grade();
        grade.setId(rs.getInt("id"));
        grade.setEnrollmentId(rs.getInt("enrollment_id"));
        
        double gv = rs.getDouble("grade_value");
        if (!rs.wasNull()) {
            grade.setGradeValue(gv);
        }
        
        grade.setGradeLetter(rs.getString("grade_letter"));
        
        int att = rs.getInt("attendance");
        if (!rs.wasNull()) {
            grade.setAttendance(att);
        }
        
        grade.setRemarks(rs.getString("remarks"));
        
        Date gd = rs.getDate("graded_date");
        if (gd != null) grade.setGradedDate(gd.toLocalDate());
        
        return grade;
    }
}
