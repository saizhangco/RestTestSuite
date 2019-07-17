package nckm.school.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import nckm.school.dao.StudentDao;
import nckm.school.model.Student;

@Component
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean add(Student student) {
		String sql = "insert into Student(Name, Gender, Age) values(?, ?, ?)";
		try {
			jdbcTemplate.update(sql, student.getName(), student.getGender(), student.getAge());
		} catch (DataAccessException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean remove(int id) {
		return false;
	}

	@Override
	public boolean update(int id, Student student) {
		return false;
	}

	@Override
	public Student get(int id) {
		return null;
	}

	@Override
	public List<Student> get() {
		return null;
	}

}
