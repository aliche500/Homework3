package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IInstructor;
import core.api.impl.Instructor;
import core.api.IStudent;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestInstructor {
	
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;
	
	@Before
	public void setup() {
		this.admin = new Admin();
		this.instructor = new Instructor();
		this.student = new Student();
	}
	
	/**
	 * addHomework constraints
	 * homeworkName must be non-null and non-empty
	 * instructorName must be the same as the instructor for the class
	 * className/year pair must exist
	 */
	
	/**
	 * Tests that addHomework works
	 */
	@Test
	public void testAddHomework() {
		this.admin.createClass("ECS161", 2017, "Premkumar Devanbu", 50);
		this.instructor.addHomework("Premkumar Devanbu", "ECS161", 2017, "Assignment 1");
		assertTrue(this.instructor.homeworkExists("ECS161", 2017, "Assignment 1"));
	}
	/**
	 * Tests against null and empty strings for homeworkName
	@Test
	public void testAddHomeworkNull() {
		this.admin.createClass("ECS60", 2017, "Matt Butner", 60);
		this.instructor.addHomework("Matt Butner", "ECS60", 2017, null);
		assertFalse(this.instructor.homeworkExists("ECS60", 2017, null));
	}
	 */
	
	@Test
	public void testAddHomeworkEmpty() {
		this.admin.createClass("ECS60", 2017, "Matt Butner", 60);
		this.instructor.addHomework("Matt Butner", "ECS60", 2017, "");
		assertFalse(this.instructor.homeworkExists("ECS60", 2017, ""));
	}
	
	/**
	 * Checks that instructor who assigns homework is also the instructor assigned to the class
	 */
	@Test
	public void testAddHomeworkInstructor() {
		this.admin.createClass("ECS60", 2017, "Matt Butner", 60);
		this.instructor.addHomework("Sean Davis", "ECS60", 2017, "Program 1");
		assertFalse(this.instructor.homeworkExists("ECS60", 2017, "Program 1"));
	}
	
	/**
	 * Checks that instructor parameter is non-null and non-empty
	 */
	@Test 
	public void testAddHomeworkInstructorNull() {
		this.admin.createClass("ECS60", 2017, "Matt Butner", 60);
		this.instructor.addHomework(null, "ECS60", 2017, "Program 1");
		assertFalse(this.instructor.homeworkExists("ECS60", 2017, "Program 1"));
	}
	
	@Test 
	public void testAddHomeworkInstructorEmpty() {
		this.admin.createClass("ECS60", 2017, "Matt Butner", 60);
		this.instructor.addHomework("", "ECS60", 2017, "Program 1");
		assertFalse(this.instructor.homeworkExists("ECS60", 2017, "Program 1"));
	}
	
	/**
	 * Tests against adding homework to a nonexistent class
	 */
	@Test
	public void testAddHomeworkNonexist() {
		this.instructor.addHomework("Sean Davis", "ECS30", 2017, "Program 1");
		assertFalse(this.instructor.homeworkExists("ECS30", 2017, "Program 1"));
	}
	
	/**
	 * assignGrade constraints
	 * Parameters should be non-null and non-empty
	 * instructorName must be the same as the instructor for the class
	 * student must be enrolled in the class
	 * student must have submitted the homework
	 * grade value must be between 0-100
	 * homework must exist/has been assigned
	 * className/year pair must exist
	 */
	
	/**
	 * Tests assignGrade works
	 */
	@Test
	public void testAssignGrade() {
		this.admin.createClass("ECS161", 2017, "Premkumar Devanbu", 50);
		this.instructor.addHomework("Premkumar Devanbu", "ECS161", 2017, "Assignment 1");
		this.student.registerForClass("Stewie Griffen", "ECS161", 2017);
		this.student.submitHomework("Stewie Griffen", "Assignment 1", "Assign1_StewieG", "ECS161", 2017);
		this.instructor.assignGrade("Premkumar Devanbu", "ECS161", 2017, "Assignment 1", "Stewie Griffen", 100);
		assertTrue(this.instructor.getGrade("ECS161", 2017, "Assignment 1", "Stewie Griffen") > -1);
	}
	
	/**
	 * Tests against null and empty parameters
	 */
	@Test
	public void testAssignGradeInstructorNull() {
		this.admin.createClass("ECS60", 2017, "Matt Butner", 50);
		this.instructor.addHomework("Matt Butner", "ECS60", 2017, "Program 1");
		this.student.registerForClass("Stewie Griffen", "ECS60", 2017);
		this.student.submitHomework("Stewie Griffen", "Program 1", "Prog1_StewieG", "ECS60", 2017);
		this.instructor.assignGrade(null, "ECS60", 2017, "Program 1", "Stewie Griffen", 100);
		assertFalse(this.instructor.getGrade("ECS60", 2017, "Program 1", "Stewie Griffen") > -1);
	}
	
	@Test
	public void testAssignGradeInstructorEmpty() {
		this.admin.createClass("ECS60", 2017, "Matt Butner", 50);
		this.instructor.addHomework("Matt Butner", "ECS60", 2017, "Program 1");
		this.student.registerForClass("Stewie Griffen", "ECS60", 2017);
		this.student.submitHomework("Stewie Griffen", "Program 1", "Prog1_StewieG", "ECS60", 2017);
		this.instructor.assignGrade("", "ECS60", 2017, "Program 1", "Stewie Griffen", 100);
		assertFalse(this.instructor.getGrade("ECS60", 2017, "Program 1", "Stewie Griffen") > -1);
	}
	
	/**
	@Test
	public void testAssignGradeHomeworkNull() {
		this.admin.createClass("ECS60", 2017, "Matt Butner", 50);
		this.instructor.addHomework("Matt Butner", "ECS60", 2017, "Program 1");
		this.student.registerForClass("Stewie Griffen", "ECS60", 2017);
		this.student.submitHomework("Stewie Griffen", "Program 1", "Prog1_StewieG", "ECS60", 2017);
		this.instructor.assignGrade("Matt Butner", "ECS60", 2017, null, "Stewie Griffen", 100);
		assertFalse(this.instructor.getGrade("ECS60", 2017, "Program 1", "Stewie Griffen") > -1);
	}
	*/
	
	/**
	@Test
	public void testAssignGradeHomeworkEmpty() {
		this.admin.createClass("ECS60", 2017, "Matt Butner", 50);
		this.instructor.addHomework("Matt Butner", "ECS60", 2017, "Program 1");
		this.student.registerForClass("Stewie Griffen", "ECS60", 2017);
		this.student.submitHomework("Stewie Griffen", "Program 1", "Prog1_StewieG", "ECS60", 2017);
		this.instructor.assignGrade("Matt Butner", "ECS60", 2017, "", "Stewie Griffen", 100);
		assertFalse(this.instructor.getGrade("ECS60", 2017, "Program 1", "Stewie Griffen") > -1);
	}
	*/
	
	/**
	 * Test that instructorName is the same as the instructor assigned to the class
	 */
	@Test
	public void testAssignGradeInstructor() {
		this.admin.createClass("ECS161", 2017, "Premkumar Devanbu", 50);
		this.instructor.addHomework("Premkumar Devanbu", "ECS161", 2017, "Assignment 1");
		this.student.registerForClass("Stewie Griffen", "ECS161", 2017);
		this.student.submitHomework("Stewie Griffen", "Assignment 1", "Assign1_StewieG", "ECS161", 2017);
		this.instructor.assignGrade("Christopher Nitta", "ECS161", 2017, "Assignment 1", "Stewie Griffen", 80);
		assertFalse(this.instructor.getGrade("ECS161", 2017, "Assignment 1", "Stewie Griffen") == 80);
	}
	
	/**
	 * Test that the student is enrolled in the class before assigning grade
	 */
	@Test
	public void testAssignGradeStudent() {
		this.admin.createClass("ECS161", 2017, "Premkumar Devanbu", 50);
		this.instructor.addHomework("Premkumar Devanbu", "ECS161", 2017, "Program 1");
		this.student.submitHomework("Meg Griffen", "Program 1", "Prog1_MegG", "ECS161", 2017);
		this.instructor.assignGrade("Premkumar Devanbu", "ECS161", 2017, "Program 1", "Meg Griffen", 100);
		assertFalse(this.instructor.getGrade("ECS161", 2017, "Program 1", "Meg Griffen") == 100);
	}
	
	/**
	 * Test that the student has submitted the homework
	 */
	@Test
	public void testAssignGradeSubmitted() {
		String instructorName = "Christopher Nitta", className = "ECS150", homework = "Program 1",
				studentName = "Chris Griffen";
		int year = 2017, capacity = 50, grade = 100;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.registerForClass(studentName, className, year);
		this.instructor.assignGrade(instructorName, className, year, homework, studentName, grade);
		assertFalse(this.instructor.getGrade(className, year, homework, studentName) == 100);
	}
	
	/**
	 * Test that grade percentage assigned is in between 0-100
	 */
	@Test
	public void testAssignGradePercent() {
		String instructorName = "Christopher Nitta", className = "ECS150", homework = "Program 1",
				answer = "Prog1_ChrisG", studentName = "Chris Griffen";
		int year = 2017, capacity = 50, badGrade = -100;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework(studentName, homework, answer, className, year);
		this.instructor.assignGrade(instructorName, className, year, homework, studentName, badGrade);
		assertTrue(this.instructor.getGrade(className, year, homework, studentName) == -1);
	}
	
	/**
	 * Test that homework has been added/assigned before assigning grade
	 */
	@Test
	public void testAssignGradeAdded() {
		String instructorName = "Christopher Nitta", className = "ECS150", homework = "Program 2",
				answer = "Prog2_ChrisG", studentName = "Chris Griffen";
		int year = 2017, capacity = 50, grade = 100;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework(studentName, homework, answer, className, year);
		this.instructor.assignGrade(instructorName, className, year, homework, studentName, grade);
		assertTrue(this.instructor.getGrade(className, year, homework, studentName) == -1);
	}

}
