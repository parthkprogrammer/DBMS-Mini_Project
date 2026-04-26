package com.sms.dao.impl;

import com.sms.dao.EnrollmentDAO;
import com.sms.model.Enrollment;
import com.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EnrollmentDAOImpl implements EnrollmentDAO {

    @Override
    public void save(Enrollment enrollment) {
        String sql = "INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setDate(3, enrollment.getEnrollmentDate() != null ? Date.valueOf(enrollment.getEnrollmentDate()) : null);
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    enrollment.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Enrollment> findById(Integer id) {
        String sql = "SELECT * FROM enrollments WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEnrollment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Enrollment> findByStudentId(int studentId) {
        return findList(studentId, "student_id");
    }

    @Override
    public List<Enrollment> findByCourseId(int courseId) {
        return findList(courseId, "course_id");
    }

    private List<Enrollment> findList(int id, String column) {
        List<Enrollment> list = new ArrayList<>();
        String sql = "SELECT * FROM enrollments WHERE " + column + " = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToEnrollment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Optional<Enrollment> findByStudentAndCourse(int studentId, int courseId) {
        String sql = "SELECT * FROM enrollments WHERE student_id = ? AND course_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToEnrollment(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Enrollment> findAll() {
        List<Enrollment> enrollments = new ArrayList<>();
        String sql = "SELECT * FROM enrollments";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                enrollments.add(mapResultSetToEnrollment(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    @Override
    public void update(Enrollment enrollment) {
        String sql = "UPDATE enrollments SET student_id = ?, course_id = ?, enrollment_date = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, enrollment.getStudentId());
            stmt.setInt(2, enrollment.getCourseId());
            stmt.setDate(3, enrollment.getEnrollmentDate() != null ? Date.valueOf(enrollment.getEnrollmentDate()) : null);
            stmt.setInt(4, enrollment.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM enrollments WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Enrollment mapResultSetToEnrollment(ResultSet rs) throws SQLException {
        Enrollment enrollment = new Enrollment();
        enrollment.setId(rs.getInt("id"));
        enrollment.setStudentId(rs.getInt("student_id"));
        enrollment.setCourseId(rs.getInt("course_id"));
        
        Date ed = rs.getDate("enrollment_date");
        if (ed != null) enrollment.setEnrollmentDate(ed.toLocalDate());
        
        return enrollment;
    }
}
