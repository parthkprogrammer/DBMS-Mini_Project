package com.sms.dao.impl;

import com.sms.dao.CourseDAO;
import com.sms.model.Course;
import com.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDAOImpl implements CourseDAO {

    @Override
    public void save(Course course) {
        String sql = "INSERT INTO courses (course_code, course_name, teacher_id, credits) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            if (course.getTeacherId() > 0) {
                stmt.setInt(3, course.getTeacherId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setInt(4, course.getCredits());
            
            stmt.executeUpdate();
            
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    course.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Course> findById(Integer id) {
        String sql = "SELECT * FROM courses WHERE id = ?";
        return findSingleCourse(sql, id);
    }

    @Override
    public Optional<Course> findByCourseCode(String courseCode) {
        String sql = "SELECT * FROM courses WHERE course_code = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, courseCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCourse(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Course> findByTeacherId(int teacherId) {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses WHERE teacher_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, teacherId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    courses.add(mapResultSetToCourse(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    private Optional<Course> findSingleCourse(String sql, int param) {
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, param);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToCourse(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<Course> findAll() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM courses";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(mapResultSetToCourse(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public void update(Course course) {
        String sql = "UPDATE courses SET course_code = ?, course_name = ?, teacher_id = ?, credits = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            if (course.getTeacherId() > 0) {
                stmt.setInt(3, course.getTeacherId());
            } else {
                stmt.setNull(3, Types.INTEGER);
            }
            stmt.setInt(4, course.getCredits());
            stmt.setInt(5, course.getId());
            
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer id) {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Course mapResultSetToCourse(ResultSet rs) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setCourseCode(rs.getString("course_code"));
        course.setCourseName(rs.getString("course_name"));
        course.setTeacherId(rs.getInt("teacher_id"));
        course.setCredits(rs.getInt("credits"));
        return course;
    }
}
