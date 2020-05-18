import java.util.*;
import java.io.*;

public class Admin extends User implements AdminInterface, Serializable {

    //constructor
	//admin username: Admin
	//admin password: Admin001
	//assume valid username/password input
    public Admin (String username, String password, String firstName, String lastName){
    		super(username, password, firstName, lastName);
    }
    
    //override setUsername
    public void setUsername() {
    	System.out.println("You are not allowed to reset username");
    }
    
    //override setPassword 
    public void setPassword() {
    	System.out.println("You are not allowed to reset password");
    }

    //Course Management Methods

    //create a course
     public void createCourse() {
    	 Scanner input=new Scanner(System.in);
    	 
    	 System.out.println("Enter name of the new course: ");
    	 String name=input.nextLine();
    	 System.out.println("Enter ID of the new course: ");
    	 String ID=input.nextLine();
    	 System.out.println("Enter section number of the new course: ");
    	 int sectionNum=0;
    	 while (true) {
    		 try {
    			 sectionNum=input.nextInt();
    			 break;
    		 } catch (InputMismatchException e) {
				 System.out.println("Invalid input: an integer required.\nPlease try again.\n");
				 input.nextLine();
			 }
    	 }
    	 input.nextLine();
    	 
    	 //check if the course has exists
    	 int inCourse=isInCourseList(name, ID, sectionNum);
    	 
    	 //if course not exists
    	 if (inCourse==-1) {
    		 System.out.println("Enter instructor name of the new course: ");
        	 String instructor=input.nextLine();
        	 System.out.println("Enter location of the new course: ");
        	 String location=input.nextLine();
        	 System.out.println("Enter maximum student number of the new course: ");
        	 int maxStudent=0;
        	 while (true) {
        		 try {
        			 maxStudent=input.nextInt();
        			 break;
        		 } catch (InputMismatchException e) {
    					System.out.println("Invalid input: an integer required.\nPlease try again.\n");
    					input.nextLine();
    			 }
        	 }
        	 input.nextLine();
        	 
        	 //create new course
             Course newCourse=new Course(name, ID, maxStudent, instructor, sectionNum, location);
             Pool.courseList.add(newCourse);
             System.out.println("Creation succeeded: the course has been added to course list.");
    	 } else {
    		 System.out.println("Creation failed: course already exists");
    	 }
    	 
    }
     

    //delete a course by ID and section number
	public void deleteCourse() {
		Scanner input = new Scanner(System.in);

		System.out.println("Enter name of the course: ");
		String name = input.nextLine();
		System.out.println("Enter ID of the course: ");
		String ID = input.nextLine();
		System.out.println("Enter section number of the course: ");
		int sectionNum = 0;
		while (true) {
			try {
				sectionNum = input.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input: an integer required.\nPlease try again.\n");
				input.nextLine();
			}
		}
		input.nextLine();
		
		//check if course exists
		 int result=isInCourseList(name, ID, sectionNum);
		
		//if course does not exist
		if (result == -1) {
			System.out.println("Deletion failed: cannot find the course in course list");
		} else {
			// if exists

			// 1. delete the course in each student's registered course list
			for (String s : Pool.courseList.get(result).getnameList()) {
				String[] studentInfo = s.split("\t");
				for (Student stu : Pool.studentList) {
					if (stu.getID().equals(studentInfo[2])) {
						stu.getRegisteredCourse().remove(Pool.courseList.get(result));
					}
				}
			}

			// 2. remove course
			Pool.courseList.remove(Pool.courseList.get(result));
			
			System.out.println("Success!");

		}
	}
	

    //edit a course
    public void editCourse() {
    	//ask the admin which information to change
    	Scanner input=new Scanner(System.in);
    	
    	//choose a course and prompt the user for course info
    	System.out.println("Which course do you want to edit?");
    	System.out.println("(Enter course name, ID and section number)");
    	
    	System.out.println("Enter name of the course: ");
		String name = input.nextLine();
		System.out.println("Enter ID of the course: ");
		String ID = input.nextLine();
		System.out.println("Enter section number of the course: ");
		int sectionNum = 0;
		while (true) {
			try {
				sectionNum = input.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input: an integer required.\nPlease try again.\n");
				input.nextLine();
			}
		}
		input.nextLine();
		
		//check if the course exists
		int result=isInCourseList(name, ID, sectionNum);
		
		//if course does not exist
		if (result==-1) {
			System.out.println("Edition failed: cannot find the course in course list");
		} else {
			//if course exist

			//choose specific course information change
			int choice=0;
			while (true) {
				try {
					System.out.println("What kind of change do you want to make?");
			    	System.out.println("Enter 1 to change maximum students");
			    	System.out.println("Enter 2 to add/remove students to/from a course:");
			    	System.out.println("Enter 3 to change course instructor");
			    	System.out.println("Enter 4 to change course section number");
			    	System.out.println("Enter 5 to change course location");
			    	
			    	choice=input.nextInt();
			    	
			    	if (!(choice==1 || choice==2 || choice==3 || choice==4 || choice==5)) {
			    		System.out.println("Invalid input: integer 1,2,3,4,5 required\nPlease try agin.\n");
			    		continue;
			    	} else {
				    	break;
			    	}
					
				} catch (InputMismatchException e) {
					System.out.println("Invalid input: an integer required.\nPlease try again.\n");
					input.nextLine();
				}
	    	
			} 
			input.nextLine();
	    	
	    	//edit course
	    	//change maximum students
	    	if (choice==1) {
	    		System.out.println("Enter the new maximum students:");
	    		int newMax=0;
	    		while (true) {
	    			try {
	    				newMax = input.nextInt();
	    				break;
	    			} catch (InputMismatchException e) {
	    				System.out.println("Invalid input: an integer required.\nPlease try again.\n");
	    				input.nextLine();
	    			}
	    		}
	    		
	    		if (newMax==Pool.courseList.get(result).getMax()) {
	    			System.out.println("Change failed: same maximum student number");
	    		} else {
	    			Pool.courseList.get(result).setMax(newMax);
	    			System.out.println("Change success!");
	    		}
	    		input.nextLine();
	    		
	        //add/remove a student to/from a course
	    	} else if (choice==2) {
	    		//prompt admin for student info
	    		System.out.println("Enter the student information:");
	    		System.out.println("Student first name: ");
	    		String firstName=input.nextLine();
	    		System.out.println("Student last name: ");
	    		String lastName=input.nextLine();
	    		System.out.println("Student ID: ");
	    		String studentID=input.nextLine();
	    		
				// check if student exists
	    		int isStudent=isInStudentList(firstName,lastName,studentID);
	    		
	    		//if student does not exist
	    		if (isStudent==-1) {
	    			System.out.println("Change failed: unable to find student");
	    		} else {
	    			//if student exist
	    			String action="";
		    		do {
		    			System.out.println("If you want to add this student to this course, enter +");
			    		System.out.println("If you want to remove this student from this course, enter -");
			    		System.out.println("Your choice:");
			    		action=input.nextLine();
			    		if (!(action.equals("+") || action.contentEquals("-"))) {
			    			System.out.println("Invalid input: + or - required");
			    		}
		    		} while (!(action.equals("+") || action.contentEquals("-")));

					if (action.contentEquals("+")) {
						if (Pool.studentList.get(isStudent).getRegisteredCourse().contains(Pool.courseList.get(result))) {
							System.out.println("Failed: student already in this course");
						} else {
							//if current student number = maximum student number
							if (Pool.courseList.get(result).getCurrent()==Pool.courseList.get(result).getMax()) {
								System.out.println("Failed: course is full");
							} else {
							//if seats available
							Pool.courseList.get(result).setNameList(firstName, lastName, studentID, action);
							Pool.studentList.get(isStudent).getRegisteredCourse().add(Pool.courseList.get(result));
							System.out.println("Change success: student already added to the course");
							}
						}
					} else {
						if (!Pool.studentList.get(isStudent).getRegisteredCourse().contains(Pool.courseList.get(result))) {
							System.out.println("Failed: students not in this course");
						} else {
							Pool.courseList.get(result).setNameList(firstName, lastName, studentID, action);
							System.out.println("Change success: student already removed from the course");
							Pool.studentList.get(isStudent).getRegisteredCourse().remove(Pool.courseList.get(result));
						}
					}
	    		}
			// change course instructor
			} else if (choice == 3) {
				System.out.println("Enter the new instructor name:");
				String newInstructor = input.nextLine();
				if (newInstructor.contentEquals(Pool.courseList.get(result).getInstructor())) {
					System.out.println("Failed: same instructor name");
				} else {
					Pool.courseList.get(result).setInstructor(newInstructor);
					System.out.println("Change success!");
				}

			// change course section number
			} else if (choice == 4) {
				int newSecNum = 0;
				System.out.println("Enter the new section number for this course:");
				while (true) {
					try {
						newSecNum = input.nextInt();
						break;
					} catch (InputMismatchException e) {
						System.out.println("Invalid input: an integer required.\nPlease try again.\n");
						input.nextLine();
					}
				}
				input.nextLine();
				
				if (newSecNum==Pool.courseList.get(result).getSectionNum()) {
					System.out.println("Failed: same section number");
				} else {
					Pool.courseList.get(result).setSectionNum(newSecNum);
					System.out.println("Change success!");
				}

			// change course location
			} else if (choice == 5) {
				System.out.println("Enter the new location:");
				String newLocation = input.nextLine();
				if (newLocation.contentEquals(Pool.courseList.get(result).getLocation())) {
					System.out.println("Failed: same location");
				} else {
					Pool.courseList.get(result).setLocation(newLocation);
					System.out.println("Change success!");
				}
				
	    	}
		}
    }
    

    //display information for a given course
    public void displayInfoByID() {
    	Scanner input=new Scanner(System.in);
    	System.out.println("Enter course ID: ");
    	String courseID=input.nextLine();
    	int inCourse=isInCourseList(courseID);
    	
		// if the course exists
		if (inCourse != -1) {
			System.out.println("Course Name: " + Pool.courseList.get(inCourse).getName());
			System.out.println("Course ID: " + Pool.courseList.get(inCourse).getID());
			System.out.println("Course Maximum Students: " + Pool.courseList.get(inCourse).getMax());
			System.out.println("Current Student Number: " + Pool.courseList.get(inCourse).getCurrent());
			System.out.println("Current Student(s): ");
			for (String s : Pool.courseList.get(inCourse).getnameList()) {
				System.out.println(s);
			}
			System.out.println("Course Intructor: " + Pool.courseList.get(inCourse).getInstructor());
			System.out.println("Course Section Number: " + Pool.courseList.get(inCourse).getSectionNum());
			System.out.println("Course Location: " + Pool.courseList.get(inCourse).getLocation() + "\n");

		} else {
			System.out.println("Display failed: course does not exist");
		}
    	
    	
    }
    

    //register a student
    public void registerStudent() {
    	Scanner input=new Scanner(System.in);
    	System.out.println("Enter student first name: ");
    	String firstName=input.nextLine();
    	System.out.println("Enter student last name: ");
    	String lastName=input.nextLine();
    	
    	//create student object
        Student newStudent=new Student("Student", "Student001", firstName, lastName);
        //add to list
        Pool.studentList.add(newStudent);
        System.out.println("Success: "+firstName+" "+lastName+"(ID: "+newStudent.getID()+")"+" already added to student list");
    }


    //Reports Methods
    //view all courses
    public void viewAllCourses() {
    	for (Course c: Pool.courseList) {
    			System.out.println("Course Name: "+c.getName());
    			System.out.println("Course ID: "+c.getID());
    			System.out.println("Course Maximum Students: "+c.getMax());
    			System.out.println("Current Student Number: "+c.getCurrent());
    			System.out.println("Current Student(s): ");
    			for (String s: c.getnameList()) {
    				System.out.println(s);
    			}
    			System.out.println("Course Intructor: "+c.getInstructor());
    			System.out.println("Course Section Number: "+c.getSectionNum());
			    System.out.println("Course Location: " + c.getLocation() + "\n");
		}
	}

	// view all courses that are full
	public void viewFull() {
		int count=0;
		for (Course c : Pool.courseList) {
			if (c.getCurrent() == c.getMax()) {
				System.out.println("Course Name: " + c.getName());
				System.out.println("Course ID: " + c.getID());
				System.out.println("Course Intructor: " + c.getInstructor());
				System.out.println("Course Section Number: " + c.getSectionNum());
				System.out.println("Course Location: " + c.getLocation() + "\n");
				count++;
			}
		}
		
		//if no course is full, print the result
		if (count==0) {
			System.out.println("No course is full.");
		}
	}

    //write to a file the list of courses that are full
    public void writeFullToFile(){
    	String fileName="FullCourses.csv";
    	
    	try {
    		//create a file
    		File fullCourses=new File(fileName);
    		PrintWriter output=new PrintWriter(fullCourses);
    		
    		//write the header
    		output.println("Course_Name,Course_Id,Maximum_Students,Section_Number,Instructor,Location");
    		
    		
    		//write full courses information to the file
    		for (Course c: Pool.courseList) {
    			if (c.getCurrent()==c.getMax()) {
    				output.print(c.getName()+",");
        			output.print(c.getID()+",");
        			output.print(c.getMax()+",");
        			output.print(c.getSectionNum()+",");
        			output.print(c.getInstructor()+",");
        			output.print(c.getLocation()+",\n");
    			}
    		}
    		
    		System.out.println("Success: already written to file!");
    		//close the file
    		output.close();	
    		
    	} catch(FileNotFoundException ex){
    		System.out.println( "Unable to open file '" + fileName + "'");
    		ex.printStackTrace();
    		
    	} catch (IOException ex) {
    		System.out.println( "Error writing to file '" + fileName + "'");
    		ex.printStackTrace();
    	}
    	
    }

 
	// view the names of the students that are registered in a specific course
	public void viewSpecificCourse() {
		
		//ask admin for basic course information
		Scanner input = new Scanner(System.in);

		System.out.println("Enter name of the course: ");
		String name = input.nextLine();
		System.out.println("Enter ID of the course: ");
		String ID = input.nextLine();
		System.out.println("Enter section number of the course: ");
		int sectionNum = 0;
		while (true) {
			try {
				sectionNum = input.nextInt();
				break;
			} catch (InputMismatchException e) {
				System.out.println("Invalid input: an integer required.\nPlease try again.\n");
				input.nextLine();
			}
		}
		input.nextLine();
		
		//search for course and print out student names
		//check if course exists
		int result=isInCourseList(name, ID, sectionNum);
		
		if (result==-1) {
			System.out.println("Failed: cannot find course in course list");
		} else {
			for (String s : Pool.courseList.get(result).getnameList()) {
				System.out.println(s);
			}
		}
	}
	

	// view the list of courses that a given student is registered in
    public void viewSpecificStudent() {
    	//prompt admin for student info
    	Scanner input=new Scanner(System.in);
		System.out.println("Enter the student information:");
		System.out.println("Student first name: ");
		String firstName=input.nextLine();
		System.out.println("Student last name: ");
		String lastName=input.nextLine();
		System.out.println("Student ID: ");
		String studentID=input.nextLine();
    	
		//check if student exists
		int result=isInStudentList(firstName, lastName, studentID);
		
		if (result==-1) {
			//if student does not exist
			System.out.println("Failed: cannot find student in student list");
		} else {
			// if student exist
			System.out.println("First name: " + Pool.studentList.get(result).getFirstName());
			System.out.println("Last name: " + Pool.studentList.get(result).getLastName());
			System.out.println("Student ID: " + Pool.studentList.get(result).getID() + "\n");
			System.out.println("Course Information: \n");

			for (int i = 0; i < Pool.studentList.get(result).getRegisteredCourse().size(); i++) {
				int courseNum = i + 1;
				System.out.println("Course " + courseNum + ":");
				System.out.println("Course Name: " + Pool.studentList.get(result).getRegisteredCourse().get(i).getName());
				System.out.println("Course ID: " + Pool.studentList.get(result).getRegisteredCourse().get(i).getID());
				System.out.println("Section Number: " + Pool.studentList.get(result).getRegisteredCourse().get(i).getSectionNum() + "\n");
			}
		}
		
    }

    
    //Sort the courses based on the current number of students registered
    public void sortCourses() {
    	Collections.sort(Pool.courseList);
    	System.out.println("Success: courses already sorted!");
    } 
    
    
}