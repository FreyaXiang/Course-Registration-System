import java.util.*;
import java.io.*;

public abstract class User implements Serializable {
    protected String username;
    protected String password;
    private String firstName;
    private String lastName;

    public User (String username, String password, String firstName, String lastName) {
        this.username=username;
        this.password=password;
        this.firstName=firstName;
        this.lastName=lastName;
    }
    
    //get methods
    public String getUsername() {
    	return this.username;
    }
    
    public String getPassword() {
    	return this.password;
    }
    
    public String getFirstName() {
    	return this.firstName;
    }
    
    public String getLastName() {
    	return this.lastName;
    }
    
    
    //set methods 
    public abstract void setUsername();
    public abstract void setPassword();
    
    public void setFirstName(String newName) {
    	this.firstName=newName;
    }
    
    public void setLastName(String newName) {
    	this.lastName=newName;
    }
    
    //launch
    public static void launch() {
    	//read the csv file when launching the program for the very first time
    	File studentFile=new File("Students.ser");
    	File courseFile=new File("Courses,ser");
    	if (!(studentFile.exists() || courseFile.exists())) {
    		Pool.storeCoursesInfo();
    	} else {  
    		//from the second time, deserialization
    		Pool.deserialCourse();
    		Pool.deserialStudent();
    	}

    }
    
    //prompt user for basic user information when logging into the system
    public static String[] enterInfo(){
    	Scanner input=new Scanner(System.in);
    	
    	//Admin information
    	//create an array of size 4
    	//array[0]=first name, array[1]=last name, array[2]=username, array[3]=password
    	String[] adminInfo=new String[4];
    	
    	//Student information
    	//create an array of size 3
    	//array[0]=studentID, array[1]=username, array[2]=password
    	String[] studentInfo=new String[3];
    	
    	//identity verification
    	int identity=0;
    	
    	while (true) {
    		System.out.println("Are you administrator or student?");
        	System.out.println("If you are an administrator, enter 1");
        	System.out.println("If you are a student, enter 2");
        	try {
        		identity=input.nextInt();
        		if (identity!=1 && identity!=2) {
        			System.out.println("Invalid input: integer 1 or 2 required.\nPlease try again.\n");
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
    	
    	String username="";
    	String pwd="";
    	
    	//if user is admin
    	if (identity==1) {
    		//prompt user for first name and last name
        	String firstName="";
        	String lastName="";
        	System.out.print("Enter your first name: ");
        	firstName=input.next();
    		System.out.print("Enter your last name: ");
    		lastName=input.next();
    		//store first name and last name into array
    		adminInfo[0]=firstName;
    		adminInfo[1]=lastName;
    		
    		//prompt user for username and password
    		do {
    			System.out.print("Enter username: ");
        		username=input.next();
            	System.out.print("Enter password: ");
            	pwd=input.next();
            	if (!(username.equals("Admin") && pwd.equals("Admin001"))) {
            		System.out.println("The username/password combination is not valid.\nTry again.\n");
            	}
    		} while (!(username.equals("Admin") && pwd.equals("Admin001")));
    			
        	//store username and password into array
            adminInfo[2]=username;
            adminInfo[3]=pwd;
            
            	
            return adminInfo;
            	
    	//if user is student	
    	} else {
    		while (true) {
    			//prompt student information
    			System.out.print("Enter your student ID: ");
    			String studentID=input.next();
    			System.out.print("Enter username: ");
    			username=input.next();
    			System.out.print("Enter password: ");
    			pwd=input.next();

    			//find student in student arrayList
    			for (Student s: Pool.studentList) {
    				//if able to find the student
    				if (s.getID().equals(studentID) && s.getUsername().equals(username) && s.getPassword().equals(pwd)) {
    					
                        //first time
    					if (username.contentEquals("Student") && pwd.contentEquals("Student001")) {  //reset username and password
    						System.out.println("Please reset username and password.");
    						s.setUsername();
    						s.setPassword();
    						studentInfo[0]=studentID;
    						studentInfo[1]=s.getUsername();
    						studentInfo[2]=s.getPassword();
    						System.out.println("Success: new username/password already reset");
    						return studentInfo;
    						
                        //not first time
    					} else {
    						//store student info into array
    						studentInfo[0]=studentID;
    						studentInfo[1]=username;
    						studentInfo[2]=pwd;
    						return studentInfo;
    					}
    				} 
    			}
    			
    			//if not able to find student
    			System.out.println("Your ID/username/password combination is not valid.");
    			System.out.println("Please note that if this is the first time you log into this system,");
    			System.out.println("the default username is 'Student', default password is 'Student001'.");
    			System.out.println("You will be able to resert your username and password once login. \nPlease try again.\n");
    		}
    	}
    	
    }
    
    
    //exit the system and serialize two arrayLists
    public static void exit() {
    	Pool.serialCourse();
    	Pool.serialStudent();
    }
    
    
    //Other methods
    
    //check if a course is in the course arrayList (method overload)
    //check by courseID
    public static int isInCourseList(String courseID) {
    	for (Course c: Pool.courseList ) {
    		if (c.getID().contentEquals(courseID)) {
    			return Pool.courseList.indexOf(c);
    		}
    	}
    	return -1;
    }
    
    //check by course name, course ID, and section number
    public static int isInCourseList(String name, String courseID, int sectionNum) {
    	for (Course c: Pool.courseList ) {
    		if (c.getID().contentEquals(courseID) &&c.getName().contentEquals(name) && c.getSectionNum()==sectionNum) {
    			return Pool.courseList.indexOf(c);
    		}
    	}
    	return -1;
    }
    
    
    //check if a student is in the student arrayList by student first name, last name, and ID
    public static int isInStudentList(String firstName, String lastName, String ID) {
    	for (Student s: Pool.studentList ) {
    		if (s.getFirstName().contentEquals(firstName) && s.getLastName().contentEquals(lastName) && s.getID().contentEquals(ID)) {
    			return Pool.studentList.indexOf(s);
    		}
    	}
    	return -1;
    } 

}



