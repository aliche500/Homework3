package core.test;

import core.api.*;
import core.api.impl.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestStudent {
	
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
	 * registerForClass constraints
	 * Parameters should be non-null and non-empty
	 * className/year pair must exist
	 * class has not met enrollment capacity
	 */
	
	/**
	 * Test against null and empty parameters
	 *
	@Test
	public void testRegisterForClassStudentNull() {
		
	}
	
	@Test
	public void testRegisterForClassClassNull() {
	
	}
	
	@Test
	public void testRegisterForClassYearNull() {
	
	}
	 */
	@Test
	public void testRegisterForClassStudentEmpty() {
		String instructorName = "Premkumar Devanbu", className = "ECS161";
		int year = 2017, capacity = 50;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.student.registerForClass("", className, year);
		assertFalse(this.student.isRegisteredFor("", className, year));
	}
	
	@Test
	public void testRegisterForClassClassEmpty() {
		String instructorName = "Premkumar Devanbu", className = "ECS161",
				studentName = "Stewie Griffen";
		int year = 2017, capacity = 50;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.student.registerForClass(studentName, "", year);
		assertFalse(this.student.isRegisteredFor(studentName, "", year));
	}
	
	/**
	 * Tests against registering for a class taught in the past
	 */
	@Test
	public void testRegisterForClassBadYear() {
		String instructorName = "Premkumar Devanbu", className = "ECS161", 
				studentName = "Stewie Griffen";
		int capacity = 50, badYear = 2015;
		
		this.admin.createClass(className, badYear, instructorName, capacity);
		this.student.registerForClass(studentName, className, badYear);
		assertFalse(this.student.isRegisteredFor(studentName, className, badYear));
	}
	
	/**
	 * Test that registerForClass works
	 */
	@Test
	public void testRegisterForClass() {
		String instructorName = "Premkumar Devanbu", className = "ECS161", 
				studentName = "Stewie Griffen";
		int year = 2017, capacity = 50;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.student.registerForClass(studentName, className, year);
		assertTrue(this.student.isRegisteredFor(studentName, className, year));
	}
	
	/**
	 * Tests that the class to be registered exists
	 */
	@Test 
	public void testRegisterForClassNonExist() {
		String studentName = "Stewie Griffen", badClassName = "ECS10";
		int badYear = 2015;
		
		this.student.registerForClass(studentName, badClassName, badYear);
		assertFalse(this.student.isRegisteredFor(studentName, badClassName, badYear));
	}
	
	/**
	 * Tests against allowing enrollment if class capacity is met
	 */
	@Test
	public void testRegisterForClassCapped() {
		String[] studentNames = {"Stewie Griffen", "Brian Griffen", "Chris Griffen",
				"Meg Griffen", "Lois Griffen", "Peter Griffen", "Glenn Quagmire"
		};
		String instructorName = "Sean Davis", className = "ECS60";
		int year = 2017, capacity = 6;
		
		this.admin.createClass(className, year, instructorName, capacity);
		for(int i = 0; i < 7; i++) {
			this.student.registerForClass(studentNames[i], className, year);
		}
		assertFalse(this.student.isRegisteredFor("Glenn Quagmire", className, year));
	}
	
	/**
	 * dropClass constraints
	 * className/year pair must exist
	 * student must be registered for the class/already enrolled
	 * "Class has not ended" -> cannot drop a class taught in the past?
	 */
	
	/**
	 * Test that dropClass works
	 */
	@Test
	public void testDropClass() {
		String instructorName = "Christopher Nitta", className = "ECS160",
				studentName = "Stewie Griffen";
		int year = 2017, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.student.registerForClass(studentName, className, year);
		this.student.dropClass(studentName, className, year);
		assertFalse(this.student.isRegisteredFor(studentName, className, year));
	}
	
	/**
	 * Test that class to be dropped exists
	 */
	@Test
	public void testDropClassNonExist() {
		String badClassName = "ECS40", studentName = "Stewie Griffen";
		int badYear = 2013;
		
		this.student.registerForClass(studentName, badClassName, badYear);
		this.student.dropClass(studentName, badClassName, badYear);
		assertFalse(this.student.isRegisteredFor(studentName, badClassName, badYear));
	}
	
	/**
	 * Tests that the student is first registered/enrolled before dropping 
	 */
	@Test
	public void testDropClassEnrolled() {
		String instructorName = "Christopher Nitta", className = "ECS160",
				studentName = "Stewie Griffen";
		int year = 2017, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.student.dropClass(studentName, className, year);
		assertFalse(this.student.isRegisteredFor(studentName, className, year));
	}
	
	/**
	 * Tests that the class to be dropped has not ended yet
	 * (Cannot drop a class that was taught in the past).
	 * Assuming that "classes that have ended" refer to classes taught earlier than 2017 
	 */
	@Test
	public void testDropClassEnded() {
		String instructorName = "Christopher Nitta", className = "ECS160",
				studentName = "Stewie Griffen";
		int badYear = 2016, capacity = 20;
		
		this.admin.createClass(className, badYear, instructorName, capacity);
		this.student.registerForClass(studentName, className, badYear);
		this.student.dropClass(studentName, className, badYear);
		assertTrue(!(this.admin.classExists(className, badYear)) &&
				!(this.student.isRegisteredFor(studentName, className, badYear)));
	}
	
	/**
	 * submitHomework constraints
	 * answerString cannot be null or empty
	 * homework must exist/added
	 * student is registered for the class
	 * year must == current year
	 * className/year pair must exist
	 */

	/**
	 * Tests against null and empty parameters

	@Test
	public void testSubmitHomeworkStudentNull() {
		String instructorName = "Christopher Nitta", className = "ECS165",
				homework = "Homework 1", answer = "HW1_StewieG",
				studentName = "Stewie Griffen";
		int year = 2017, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework(null, homework, answer, className, year);
		assertFalse(this.student.hasSubmitted(null, homework, className, year));
	}
	 */
	
	@Test
	public void testSubmitHomeworkHomeworkNull() {
		String instructorName = "Christopher Nitta", className = "ECS165",
				homework = "Homework 1", answer = "HW1_StewieG",
				studentName = "Stewie Griffen";
		int year = 2017, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework(studentName, null, answer, className, year);
		assertFalse(this.student.hasSubmitted(studentName, null, className, year));
	}
	
	@Test
	public void testSubmitHomeworkAnswerNull() {
		String instructorName = "Christopher Nitta", className = "ECS165",
				homework = "Homework 1", studentName = "Stewie Griffen";
		int year = 2017, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework(studentName, homework, null, className, year);
		assertFalse(this.student.hasSubmitted(studentName, homework, className, year));
	}
	
	@Test
	public void testSubmitHomeworkStudentEmpty() {
		String instructorName = "Christopher Nitta", className = "ECS165",
				homework = "Homework 1", answer = "HW1_StewieG",
				studentName = "Stewie Griffen";
		int year = 2017, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework("", homework, answer, className, year);
		assertFalse(this.student.hasSubmitted("", homework, className, year));
	}
	
	@Test
	public void testSubmitHomeworkHomeworkEmpty() {
		String instructorName = "Christopher Nitta", className = "ECS165",
				homework = "Homework 1", answer = "HW1_StewieG",
				studentName = "Stewie Griffen";
		int year = 2017, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework(studentName, "", answer, className, year);
		assertFalse(this.student.hasSubmitted(studentName, "", className, year));
	}
	
	@Test
	public void testSubmitHomeworkAnswerEmpty() {
		String instructorName = "Christopher Nitta", className = "ECS165",
				homework = "Homework 1", studentName = "Stewie Griffen";
		int year = 2017, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework(studentName, homework, "", className, year);
		assertFalse(this.student.hasSubmitted(studentName, homework, className, year));
	}
	
	/**
	 * Tests that the homework is added/assigned before submissions can occur
	 */
	@Test
	public void testSubmitHomeworkAdded() {
		String instructorName = "Christopher Nitta", className = "ECS165",
				homework = "Homework 2", answer = "HW2_StewieG",
				studentName = "Stewie Griffen";
		int year = 2017, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework(studentName, homework, answer, className, year);
		assertFalse(this.student.hasSubmitted(studentName, homework, className, year));
	}
	
	/**
	 * Test that the student is registered for the class before homework submissions
	 */
	@Test
	public void testSubmitHomeworkRegistered() {
		String instructorName = "Christopher Nitta", className = "ECS165",
				homework = "Homework 1", answer = "HW1_StewieG",
				studentName = "Stewie Griffen";
		int year = 2017, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.submitHomework(studentName, homework, answer, className, year);
		assertFalse(this.student.hasSubmitted(studentName, homework, className, year));
	}
	
	/**
	 * Tests that the year for the submitted homework is the current year
	 */
	@Test
	public void testSubmitHomeworkCurrentYear() {
		String instructorName = "Christopher Nitta", className = "ECS165",
				homework = "Homework 1", answer = "HW1_StewieG",
				studentName = "Stewie Griffen";
		int year = 2018, capacity = 20;
		
		this.admin.createClass(className, year, instructorName, capacity);
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework(studentName, homework, answer, className, year);
		assertFalse(this.student.hasSubmitted(studentName, homework, className, year));
	}
	
	/**
	 * Test that the className/year pair exists before submitting homework
	 */
	public void testSubmitHomeworkNonExist() {
		String instructorName = "Phil Rogaway", className = "ECS123",
				homework = "Homework 1", answer = "HW1_StewieG",
				studentName = "Stewie Griffen";
		int year = 2015;
		
		this.instructor.addHomework(instructorName, className, year, homework);
		this.student.registerForClass(studentName, className, year);
		this.student.submitHomework(studentName, homework, answer, className, year);
		assertFalse(this.student.hasSubmitted(studentName, homework, className, year));
	}
}
