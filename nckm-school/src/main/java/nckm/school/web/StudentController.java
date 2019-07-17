package nckm.school.web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nckm.school.dao.StudentDao;
import nckm.school.model.Student;

@RestController
@RequestMapping(value = "/student")
public class StudentController {
	
	@Autowired
	private StudentDao studentDao;

	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody Student student, HttpServletResponse response) {
		if( student == null ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);	// 400
			return;
		}
		boolean result = studentDao.add(student);
		if(!result) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);	// 500
		} else {
			response.setStatus(HttpServletResponse.SC_CREATED);	// 201
		}
	}
	
	@RequestMapping(value = "/students", method = RequestMethod.POST)
	public int addAll(@RequestBody List<Student> students, HttpServletResponse response) {
		int count = 0;
		if( students == null || students.size() <= 0 ) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return -1;
		}
		for( Student student : students ) {
			boolean result = studentDao.add(student);
			if( result ) {
				count++;
			}
		}
		if( count <= 0 ) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} else {
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		return count;
	}
}
