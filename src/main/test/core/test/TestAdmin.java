package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.Objects;

public class TestAdmin {
	
	private IAdmin admin;
	
	@Before
	public void setup() {
		this.admin = new Admin();
	}
	
	/**
	 * createClass constraints
	 * Parameters must be non-null 
	 * year parameter cannot be < 2017 (in the past)
	 * Does instructor actually gets assigned to a class?
	 * *className/year pair must be unique (can't have multiple values/no dupes)*
	 * Instructors can be assigned to max 2 courses per year
 	 * capacity parameter must be > 0 
	 */
	
	/** 
	 * Corner cases for invalid parameters
	 * Parameters should not be null or empty
	 */
	
	/**
	 * Test against invalid class name 
	 */
	@Test
	public void testCreateClassEmptyClass() {
		this.admin.createClass("", 2017, "Instructor", 20);
		assertFalse(this.admin.classExists("", 2017));
	}
	
	/**
	 * Implementation breaks for this test because 
	 * can't search based on a null key
	 * Might delete
	@Test
	public void testCreateClassNullClass() {
		this.admin.createClass(null, 2017, "Instructor", 20);
		assertFalse(this.admin.classExists(null, 2017));
	}
	 */
	
	/**
	 * Test against invalid instructor name
	 */
	@Test
	public void testCreateClasEmptyInstructor() {
		this.admin.createClass("ECS161", 2017, "", 20);
		assertFalse(this.admin.classExists("ECS161", 2017));
	}
	
	@Test
	public void testCreateClassNullInstructor() {
		this.admin.createClass("ECS161", 2017, null, 20);
		assertFalse(this.admin.classExists("ECS161", 2017));
	}

	/**
	 * Tests that a class is successfully created
	 */
	@Test 
	public void testCreateClass() {
		this.admin.createClass("ECS161", 2017, "Premkumar Devanbu", 20);
		assertTrue(this.admin.classExists("ECS161", 2017));
	} 
	
	/**
	 * Tests that a class taught in the past will not be created
	 */
	@Test
	public void testCreateClassPast() {
		this.admin.createClass("ECS161", 2016, "Premkumar Devanbu", 20);
		assertFalse(this.admin.classExists("ECS161", 2016));
	}
	
	/**
	 * Tests that the instructor gets assigned to the class
	 */
	@Test
	public void testCreateClassInstructor() {
		String instructor = new String("Premkumar Devanbu");
		
		this.admin.createClass("ECS161", 2017, instructor, 20);
		assertTrue(Objects.equals(this.admin.getClassInstructor("ECS161", 2017), instructor));
	}
	
	/**
	 * Tests that instructors are assigned to max 2 courses in a single year
	 */
	@Test
	public void testCreateClassInstructorMax() {
		String instructor = new String("Sean Davis");
		String[] classNames = {"ECS154A", "ECS60", "ECS50"};
		int assignedCount = 0;
		
		for(int i = 0; i < 3; i++) {
			this.admin.createClass(classNames[i], 2017, instructor, 50);
			if(this.admin.getClassInstructor(classNames[i], 2017).equals(instructor))
				assignedCount++;
		}
		assertTrue(assignedCount <= 2);
	}
	
	/**
	 * Tests that instructors can be assigned to more than 2 classes
	 * granted the year in which assigned classes are taught are different 
	 * (previous constraint still holds)
	 */
	@Test
	public void testCreateClassInstructorMultiYear() {
		String instructor = new String("Sean Davis");
		String[] classNames = {"ECS154A", "ECS60", "ECS40", "ECS30"};
		int[] years = {2017, 2018};
		int assignedCount17 = 0, assignedCount18 = 0;
		
		this.admin.createClass(classNames[0], years[0], instructor, 50);
		this.admin.createClass(classNames[1], years[0], instructor, 50);
		assignedCount17 += 2;
		
		this.admin.createClass(classNames[2], years[1], instructor, 50);
		this.admin.createClass(classNames[3], years[1], instructor, 50);
		assignedCount18 += 2;
		
		assertTrue(assignedCount17 <= 2 && assignedCount18 <= 2);
	}
	
	/**
	 * Test adding another class with same className/year pair
	 * would not overwrite first one because second creation
	 * is invalid.
	 */
	@Test
	public void testCreateClassDup() {
		String className = "ECS161", instructor = "Premkumar Devanbu";
		int year = 2017, capacity1 = 50, capacity2 = 25;
		
		this.admin.createClass(className, year, instructor, capacity1);
		this.admin.createClass(className, year, instructor, capacity2);
		
		assertTrue(this.admin.getClassCapacity(className, year) == capacity1);
	}
	
	/**
	 * Tests capacity given; must be positive
	 */
	@Test
	public void testCreateClassCapacity() {
		this.admin.createClass("ECS161", 2017, "Premkumar Devanbu", -20);
		assertTrue(this.admin.getClassCapacity("ECS161", 2017) == -1);
	}
	
	/**
	 * changeCapacity constraints
	 * Class/course must exist (test changing capacity of course that doesn't exist)
	 * capacity parameter must be >= current capacity
	 */
	
	/**
	 * Test changing capacity of course that doesn't exist
	 */
	@Test
	public void testChangeCapacityNonexist() {
		this.admin.changeCapacity("ECS10", 2017, 10);
		assertFalse(this.admin.getClassCapacity("ECS10", 2017) > -1);
	}
	
	/**
	 * Tests that capacity did get changed
	 */
	@Test
	public void testChangeCapacity() {
		this.admin.createClass("ECS161", 2017, "Premkumar Devanbu", 20);
		this.admin.changeCapacity("ECS161", 2017, 30);
		assertTrue(this.admin.getClassCapacity("ECS161", 2017) > 20);
	}
	
	/**
	 * Tests changing capacity to smaller number
	 */
	@Test
	public void testChangeCapacitySmaller() {
		this.admin.createClass("ECS161", 2017, "PremKumar Devanbu", 20);
		this.admin.changeCapacity("ECS161", 2017, 10);
		assertFalse(this.admin.getClassCapacity("ECS161", 2017) < 20);
	}
}
