package nckm.school.dao;

import java.util.List;

import nckm.school.model.Teacher;

public interface TeacherDao {

	boolean add(Teacher teacher);
	boolean remove(int id);
	boolean update(int id, Teacher teacher);
	Teacher get(int id);
	List<Teacher> get();
}
