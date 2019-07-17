package nckm.school.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nckm.school.dao.TeacherDao;
import nckm.school.model.Teacher;

@RestController
@RequestMapping(value = "/teacher")
public class TeacherController {

	@Autowired
	private TeacherDao teacherDao;

	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody Teacher teacher, HttpServletResponse response) {
		if (teacher == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		boolean result = teacherDao.add(teacher);
		if (!result) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
	}

	@RequestMapping(value = "/teachers", method = RequestMethod.POST)
	public Integer addAll(@RequestBody List<Teacher> teachers, HttpServletResponse response) {
		if (teachers == null || teachers.size() <= 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return 0;
		}
		int count = 0;
		for (Teacher teacher : teachers) {
			boolean result = teacherDao.add(teacher);
			System.out.print(result);
			System.out.println(teacher);
			if (result) {
				count++;
			}
		}
		if (count > 0) {
			response.setStatus(HttpServletResponse.SC_CREATED);
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		return count;
	}
}
