package nckm.school.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Student {

	@JsonProperty(value="Id")
	private int id;
	
	@JsonProperty(value = "Name")
	private String name;
	
	@JsonProperty(value = "Gender")
	private String gender;
	
	@JsonProperty(value = "Age")
	private int age;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
}
