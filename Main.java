import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
    	//launch the system
    	User.launch();
    	
    	//login and run the system
    	run();
    	
    	//exit the system
    	User.exit();
    	
  
	}
    
    public static void run() {

    	//get user information
    	String[] userInfo=User.enterInfo();
    	Scanner input=new Scanner(System.in);
    	
    	//if user is admin
    	if (userInfo.length==4) {
    		//create an admin object
    		Admin admin=new Admin(userInfo[2],userInfo[3],userInfo[0],userInfo[1]);
    		
    		//welcome
    		System.out.println("Welcome, "+admin.getFirstName());
    		
    		//do admin things
    		int choice1=0, choice2=0;
    		
    		boolean stay1=true;
    		
			while (stay1) {

				// course management or reports?
				// validate user input
				while (true) {
					try {
						
						System.out.println("Course Managment, enter 1");
						System.out.println("Reports, enter 2");
						System.out.println("Exit, enter 3");
						choice1 = input.nextInt();
						if (choice1 != 1 && choice1 != 2 && choice1 != 3) {
							System.out.println("Invalid input: integer 1, 2 or 3 required.\nPlease try again.\n");
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

				// if course management
				if (choice1 == 1) {
					boolean stay2 = true;

					while (stay2) {

						// validate user input
						while (true) {
							try {
								System.out.println("Create a new course, enter 1");
								System.out.println("Delete a course, enter 2");
								System.out.println("Edit a course, enter 3");
								System.out.println("Display information for a given course, enter 4");
								System.out.println("Register a student, enter 5");
								System.out.println("Go back to previous step, enter 6");
								choice2 = input.nextInt();
								if (choice2 != 1 && choice2 != 2 && choice2 != 3 && choice2 != 4 && choice2 != 5
										&& choice2 != 6) {
									System.out.println(
											"Invalid input: integer 1, 2, 3, 4, 5 or 6 required.\nPlease try again.\n");
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

						// if create a new course
						if (choice2 == 1) {
							userAction("create", admin);
							System.out.println("\n");

							// if delete a course
						} else if (choice2 == 2) {
							userAction("delete", admin);
							System.out.println("\n");

							// if edit a course
						} else if (choice2 == 3) {
							userAction("edit", admin);
							System.out.println("\n");

							// if display information for a given course
						} else if (choice2 == 4) {
							userAction("display", admin);
							System.out.println("\n");

							// if register a student
						} else if (choice2 == 5) {
							userAction("register", admin);
							System.out.println("\n");

							// if go back to previous page
						} else {
							System.out.println("\n");
							stay2 = false;
						}

					}

					// if reports
				} else if (choice1 == 2) {

					boolean stay2 = true;

					while (stay2) {

						// validate user input
						while (true) {
							try {
								System.out.println("View all courses, enter 1");
								System.out.println("View all courses that are full, enter 2");
								System.out.println("Write to a file the list of courses that are full, enter 3");
								System.out.println(
										"View the names of the students that are registered in a specific course, enter 4");
								System.out.println(
										"View the list of courses that a given student is registered in, enter 5");
								System.out.println("Sort the courses based on the current number of students registered, enter 6");
								System.out.println("Go back to previous step, enter 7");
								choice2 = input.nextInt();
								if (choice2 != 1 && choice2 != 2 && choice2 != 3 && choice2 != 4 && choice2 != 5
										&& choice2 != 6 && choice2 != 7) {
									System.out.println(
											"Invalid input: integer 1, 2, 3, 4, 5, 6 or 7 required.\nPlease try again.\n");
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

						// if view all courses
						if (choice2 == 1) {
							userAction("viewAll", admin);
							System.out.println("\n");

							// if view full courses
						} else if (choice2 == 2) {
							userAction("viewFull", admin);
							System.out.println("\n");

							// if write full courses to file
						} else if (choice2 == 3) {
							userAction("writeToFile", admin);
							System.out.println("\n");

							// if view the names of the students that are registered in a specific course
						} else if (choice2 == 4) {
							userAction("viewStudentInCourse", admin);
							System.out.println("\n");

							// if view the list of courses that a given student is registered in
						} else if (choice2 == 5) {
							userAction("viewAStudent", admin);
							System.out.println("\n");
							
							// if sort the courses based on the current number of students registered 
						} else if (choice2 == 6) {
							userAction("sort", admin);
							System.out.println("\n");

							// if go back to previous page
						} else {
							System.out.println("\n");
							stay2 = false;
						}

					}

					// if exit
				} else if (choice1 == 3) {
					System.out.println("Goodbye, "+admin.getFirstName()+"!");
					stay1=false;
					//go to exit() method
				}

			}

		// if user is student
		} else if (userInfo.length==3) {
			//create a student object;
			Student student=null;
    		Boolean student_found = false;
    		
    		//find student object
    		while(! student_found) {
    			for (Student s: Pool.studentList) {
    				if (s.getID().contentEquals(userInfo[0]) && s.getUsername().contentEquals(userInfo[1]) && s.getPassword().contentEquals(userInfo[2])) {
    					student=s;
    					student_found = true;
    				}
    			}
    		}
    		
    		//welcome
    		System.out.println("Welcome, "+student.getFirstName());
    		
    		//do student things
            int choice=0;
            
            boolean stay=true;

			while (stay) {

				// choose specific action
				// validate user input
				while (true) {
					try {
						System.out.println("View all courses, enter 1");
						System.out.println("View all courses that are not full, enter 2");
						System.out.println("Register in a course, enter 3");
						System.out.println("Withdraw from a course, enter 4");
						System.out.println("View all courses you are registered in  enter 5");
						System.out.println("Exit, enter 6");

						choice = input.nextInt();
						if (choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5 && choice != 6) {
							System.out.println(
									"Invalid input: integer 1, 2, 3, 4, 5 or 6 required.\nPlease try again.\n");
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

				// if view all courses
				if (choice == 1) {
					userAction("viewAll", student);
					System.out.println("\n");

				// if view full courses
				} else if (choice == 2) {
					userAction("viewNotFull", student);
					System.out.println("\n");

				// if register in a course
				} else if (choice == 3) {
					userAction("register", student);
					System.out.println("\n");

				// if withdraw from a course
				} else if (choice == 4) {
					userAction("withdraw", student);
					System.out.println("\n");

				// if view all courses you are register in
				} else if (choice == 5) {
					userAction("viewMyCourse", student);
					System.out.println("\n");

				// if exit
				} else if (choice == 6) {
					System.out.println("Goodbye, "+student.getFirstName()+"!");
					stay=false;
					//go to exit() method;
				}

			}
    	}
    }
    
    
    public static void userAction(String action, User u) {
    	Scanner input=new Scanner(System.in);
    	boolean continueInput = true;
		String answer;

		do {
			//if user is admin
			if (u instanceof Admin) {
				Admin admin=(Admin)u;
				
				//course management 
				if (action.equals("create")) {
					admin.createCourse();
				} else if (action.equals("delete")) {
					admin.deleteCourse();
				} else if (action.equals("edit")) {
					admin.editCourse();
				} else if (action.equals("display")) {
					admin.displayInfoByID();
				} else if (action.equals("register")) {
					admin.registerStudent();
					
				//reports
				} else if (action.equals("viewAll")) {
					admin.viewAllCourses();
				} else if (action.equals("viewFull")) {
					admin.viewFull();
				} else if (action.equals("writeToFile")) {
					admin.writeFullToFile();
				} else if (action.equals("viewStudentInCourse")) {
					admin.viewSpecificCourse();
				} else if (action.equals("viewAStudent")) {
					admin.viewSpecificStudent();
				} else if (action.contentEquals("sort")) {
					admin.sortCourses();
				}
			
			//if user is a student
			} else if (u instanceof Student) {
				Student student=(Student)u;
				
				//reports 
				if (action.equals("viewAll")) {
					student.viewAllCourses();
				} else if (action.equals("viewNotFull")) {
					student.viewNotFull();
				} else if (action.equals("register")) {
					student.registerCourse();
				} else if (action.equals("withdraw")) {
					student.withdrawCourse();
				} else if (action.equals("viewMyCourse")) {
					student.viewRegistered();
				}
			}
			
			do {
				System.out.println("Do you want to try another time? (Enter yes or no)");
				answer = input.nextLine();
				if (!(answer.equals("yes") || answer.contentEquals("no"))) {
					System.out.println("Invalid input: 'yes' or 'no' required");
				}
			} while (!(answer.equals("yes") || answer.contentEquals("no")));
			
			if (answer.equals("no")) {
				continueInput=false;
			}

		} while (continueInput);
    	
    }
}