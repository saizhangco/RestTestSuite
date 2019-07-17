package nckm.school.dao;

import java.util.List;

import nckm.school.model.Student;

public interface StudentDao {

	boolean add(Student student);

	boolean remove(int id);

	boolean update(int id, Student student);

	Student get(int id);

	List<Student> get();
}
