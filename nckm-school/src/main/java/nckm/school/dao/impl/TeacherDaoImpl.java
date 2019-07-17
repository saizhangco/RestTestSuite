package nckm.school.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import nckm.school.dao.TeacherDao;
import nckm.school.model.Teacher;

@Component
public class TeacherDaoImpl implements TeacherDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public boolean add(Teacher teacher) {
		String sql = "insert into Teacher(Name, Gender, Age) values(?, ?, ?);";
		try {
			jdbcTemplate.update(sql, teacher.getName(), teacher.getGender(), teacher.getAge());
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
	public boolean update(int id, Teacher teacher) {
		return false;
	}

	@Override
	public Teacher get(int id) {
		return null;
	}

	@Override
	public List<Teacher> get() {
		return null;
	}

}
