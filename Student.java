import java.util.*;
import java.io.*;

public class Student extends User implements StudentInterface, Serializable {
	
    private String ID;
    private ArrayList<Course> registeredCourses=new ArrayList<>();
    
    
    //student default username: Student
    //student default password: Student001
    //student will be able to reset their username and passwords
    public Student(String username, String password, String firstName, String lastName) {
    	super(username, password, firstName, lastName);
    	
    	//generate student ID
    	//Example: fg13
    	//Student first name starts with F , last name starts with G, and 
    	//this is the 13th "fg" combination in our student list
    	//Student/admin cannot reset ID
    	String firstTwoChar=firstName.substring(0,1).toLowerCase()+lastName.substring(0,1).toLowerCase();
    	int sameNameCount=1;
    	for (Student s: Pool.studentList) {
    		if (s.ID.substring(0,2).contentEquals(firstTwoChar)) {
    			sameNameCount++;
    		}
    	}
        this.ID=firstTwoChar+sameNameCount;
    }
    
    //get methods
    public String getID() {
    	return this.ID;
    }
    
    public ArrayList<Course> getRegisteredCourse() {
    	return this.registeredCourses;	
    }
    
    //override setUsername
    //override setPassword
    public void setUsername() {  //length of username should be more than 4 
    	Scanner input=new Scanner(System.in);
    	String username="";
    	
    	do {
    		System.out.println("Enter your new username: ");
    		username=input.next();
    		if (username.length()<=4) {
    			System.out.println("Length of username should be more than 4 letters/numbers. Please try agin.\n");
    		}
    		if (username.contentEquals(this.getUsername())) {
    			System.out.println("New username is the same as your original one. Please try again.");
    		}
    	} while (username.length()<=4 || username.contentEquals("Student"));
    	this.username=username;
    }
    
    public void setPassword() {
    	Scanner input=new Scanner(System.in);
    	String original="";
    	String newPwd="";
    	
    	//verify identity
    	do {
    		System.out.println("Enter your original password: ");
    		original=input.next();
    		if (!(original.contentEquals(this.getPassword()))) {
    			System.out.println("Incorrect password. Please try agin.\n");
    		}
    	} while (!(original.contentEquals(this.getPassword())));
    	
    	//reset password: password should be more than 7 letters/numbers
    	do {
    		System.out.println("Enter your new password: ");
    		newPwd=input.next();
    		if (newPwd.length()<=7) {
    			System.out.println("Length of password should be more than 7 letters/numbers. Please try agin.\n");
    		}
    		if (newPwd.equals(this.getPassword())) {
    			System.out.println("New password is the same as your original one. Please try agin.\n");
    		}
    	} while (newPwd.length()<=7 || newPwd.equals(this.getPassword()));
    	
    	this.password=newPwd;
    }
    
    
    //Course Management
    
    public void viewAllCourses() {
    	for (Course c: Pool.courseList) {
			System.out.println("Course Name: "+c.getName());
			System.out.println("Course ID: "+c.getID());
			System.out.println("Course Maximum Students: "+c.getMax());
			System.out.println("Current Student Number: "+c.getCurrent());
			System.out.println("Course Intructor: "+c.getInstructor());
			System.out.println("Course Section Number: "+c.getSectionNum());
			System.out.println("Course Location: "+c.getLocation()+"\n");
	    }
    }
    
	// view all courses that are not full
	public void viewNotFull() {
		for (Course c : Pool.courseList) {
			if (c.getCurrent() < c.getMax()) {
				System.out.println("Course Name: " + c.getName());
				System.out.println("Course ID: " + c.getID());
				int available=c.getMax()-c.getCurrent();
				System.out.println("Available seats: "+available);
				System.out.println("Course Intructor: " + c.getInstructor());
				System.out.println("Course Section Number: " + c.getSectionNum());
				System.out.println("Course Location: " + c.getLocation() + "\n");
			}
		}
	}
    
    //register in a course
    public void registerCourse() {
    	Scanner input=new Scanner(System.in);
    	
    	//ask student for course information
    	System.out.println("Please enter the course information");
		System.out.print("Enter name of the course: ");
		String name = input.nextLine();
		System.out.print("Enter ID of the course: ");
		String ID = input.nextLine();
		System.out.print("Enter section number of the course: ");
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
		int result1=User.isInCourseList(name, ID, sectionNum);
		
		//if course does not exists
		if (result1==-1) {
			System.out.println("Failed: unable to find course");
		//if course exists
		} else {
			
			while (true) {
				//ask for student info
				System.out.println("Enter your first name:");
				String firstName=input.nextLine();
				System.out.println("Enter your last name: ");
				String lastName=input.nextLine();
				System.out.println("Enter you ID: ");
				String studentID=input.nextLine();

				//if student name is not correct
				if (!(firstName.contentEquals(this.getFirstName()) && lastName.contentEquals(this.getLastName()) && studentID.contentEquals(this.ID))) {
					System.out.println("Failed: This is not you.\nPlease enter your information again.\n");
					continue;
					
					//student exists
				} else {
					//check if the course is already chosen 
					int result2=isInRegistered(this.getRegisteredCourse(), name);
					//if course chosen
					if (result2!=-1) {
						System.out.println("Failed: course already chosen.");
						
					//if course not chosen
					} else {
						//check if course is full
						if (Pool.courseList.get(result1).getMax()==Pool.courseList.get(result1).getCurrent()) {

							System.out.println("Failed: course is full");
						} else {
							//add the course for student
							this.registeredCourses.add(Pool.courseList.get(result1));
							//add student to course name list
							Pool.courseList.get(result1).setNameList(firstName, lastName, studentID, "+");
							System.out.println("Success!");
						}
					}
					break;
				}
			}
		}
    }

    //withdraw a course
    public void withdrawCourse() {
    	Scanner input=new Scanner(System.in);
    	
    	while (true) {
    		//ask for student info
    		System.out.println("Enter your first name:");
    		String firstName=input.nextLine();
    		System.out.println("Enter your last name: ");
    		String lastName=input.nextLine();
    		System.out.println("Enter you ID: ");
    		String studentID=input.nextLine();

    		//if student does not exists
    		if (!(firstName.contentEquals(this.getFirstName()) && lastName.contentEquals(this.getLastName()) && studentID.contentEquals(this.ID))) {
    			System.out.println("Failed: this is not you.\nPlease enter your information again.\n");
    			continue;
    		} else {

    			//ask student for course information
    			System.out.print("Enter name of the course you want to withdraw: ");
    			String name = input.nextLine();

    			//check if the course is in student course list
    			int result=isInRegistered(this.getRegisteredCourse(),name);

    			//if course not in student course list
    			if (result==-1) {
    				System.out.println("Failed: unable to find this course in your course list.\n");
    				
    				//if course in student course list
    			} else {
    				//withdraw
    				//1. remove the student name in the course name list
    				this.registeredCourses.get(result).setNameList(this.getFirstName(), this.getLastName(), ID, "-");
    				//2. remove the course in the student course list
    				this.registeredCourses.remove(this.registeredCourses.get(result));
    				System.out.println("Success!\n");
    			}
    			break;
    		}
		
		}
    }
		
		
    //view registered courses
    public void viewRegistered() {
    	if (this.getRegisteredCourse().isEmpty()) {
    		System.out.println("You course list is empty\n");
    	} else {
    		for (int i=0; i<this.getRegisteredCourse().size(); i++) {
    			int courseNum=i+1;
    			System.out.println("Course "+courseNum+":");
    			System.out.println("Course Name: "+this.getRegisteredCourse().get(i).getName());
    			System.out.println("Course ID: "+this.getRegisteredCourse().get(i).getID());
    			System.out.println("Section Number: "+this.getRegisteredCourse().get(i).getSectionNum()+"\n");
    		}
    	}
    }
    
    //other methods
    
    //check if a course is in registered course list
    public static int isInRegistered(ArrayList<Course> registered, String name) {
		for (Course c: registered) {
			if (c.getName().contentEquals(name)) {
				return registered.indexOf(c);
			}
		}
		return -1;
    }
    

}