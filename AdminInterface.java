public interface AdminInterface {

    //Course Management Methods

    //create a course
    public void createCourse();

    //delete a course
    public void deleteCourse();

    //edit a course
    public void editCourse();

    //display information for a given course
    public void displayInfoByID();

    //register a student
    public void registerStudent();


    //Reports Methods
    
    //view all courses
    public void viewAllCourses();

    //write to a file the list of courses that are full
    public void writeFullToFile();

    //view the names of the students that are registered in a specific course 
    public void viewSpecificCourse();

    //view the list of courses that a given student is registered in
    public void viewSpecificStudent();

    //Sort the courses based on the current number of students registered
    public void sortCourses(); 

}