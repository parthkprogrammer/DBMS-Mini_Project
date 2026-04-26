package com.sms.dao.impl;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;
import com.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAOImpl implements StudentDAO {

    @Override
    public void save(Student student) {
        String sql = "INSERT INTO students (user_id, first_name, last_name, date_of_birth, email, enrollment_date) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            if (student.getUserId() > 0) {
                stmt.setInt(1, student.getUserId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            stmt.setDate(4, student.getDateOfBirth() != null ? Date.valueOf(student.getDateOfBirth()) : null);
            stmt.setString(5, student.getEmail());
            stmt.setDate(6, student.getEnrollmentDate() != null ? Date.valueOf(student.getEnrollmentDate()) : null);
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    student.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Student> findById(Integer id) {
        String sql = "SELECT * FROM students WHERE id = ?";
        return findSingleStudent(sql, id);
    }

    @Override
    public Optional<Student> findByUserId(int userId) {
        String sql = "SELECT * FROM students WHERE user_id = ?";
        return findSingleStudent(sql, userId);
    }

    @Override
    public Optional<Student> findByEmail(String email) {
        String sql = "SELECT * FROM students WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToStudent(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private Optional<Student> findSingleStudent(String sql, int param) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, param);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToStudent(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET user_id = ?, first_name = ?, last_name = ?, date_of_birth = ?, email = ?, enrollment_date = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (student.getUserId() > 0) {
                stmt.setInt(1, student.getUserId());
            } else {
                stmt.setNull(1, Types.INTEGER);
            }
            stmt.setString(2, student.getFirstName());
            stmt.setString(3, student.getLastName());
            stmt.setDate(4, student.getDateOfBirth() != null ? Date.valueOf(student.getDateOfBirth()) : null);
            stmt.setString(5, student.getEmail());
            stmt.setDate(6, student.getEnrollmentDate() != null ? Date.valueOf(student.getEnrollmentDate()) : null);
            stmt.setInt(7, student.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setUserId(rs.getInt("user_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        
        Date dob = rs.getDate("date_of_birth");
        if (dob != null) student.setDateOfBirth(dob.toLocalDate());
        
        student.setEmail(rs.getString("email"));
        
        Date ed = rs.getDate("enrollment_date");
        if (ed != null) student.setEnrollmentDate(ed.toLocalDate());
        
        return student;
    }
}
