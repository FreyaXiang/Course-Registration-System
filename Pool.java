import java.io.*;
import java.util.*;

public class Pool implements Serializable{

    //ArrayList of Courses
    protected static ArrayList<Course> courseList=new ArrayList<>();

    //ArrayList of students
    protected static ArrayList<Student> studentList=new ArrayList<>();
    
    //read the csv file and store the course information in the arrayList
    public static void storeCoursesInfo(){
    	String fileName = "src/MyUniversityCourses.csv";
		try{
			
			//create the file object
	        File courses = new File(fileName);

	        //read the file
	        Scanner input=new Scanner(courses);
	        
	        //read the first line (the headers in the excel)
	        String course=input.nextLine();
	        
	        //read the rest of the lines
	        while (input.hasNextLine()) {
	        	
	            course=input.nextLine();
	            String[] courseArr=course.split(",");
	            //create course object for each course
	            Course c=new Course(courseArr[0], courseArr[1], Integer.parseInt(courseArr[2]), courseArr[5], Integer.parseInt(courseArr[6]), courseArr[7]);
	            
	            //store the information to the course ArrayList
	            courseList.add(c);
	        }
	        
	        input.close();
		}
		
		catch(FileNotFoundException ex){
			System.out.println( "Unable to open file '" + fileName + "'");
			ex.printStackTrace();
		}

		catch (IOException ex) {
			System.out.println( "Error reading file '" + fileName + "'");
			ex.printStackTrace();
		}
    	
    }
    
    //serialization method for course ArrayList
    public static void serialCourse() {
    	try{
            FileOutputStream fos= new FileOutputStream("Courses.ser");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(courseList);
            oos.close();
            fos.close();
          }catch(IOException ioe){
               ioe.printStackTrace();
           }	
    	
    }
    
    //serialization method for student ArrayList
    public static void serialStudent() {
    	try{
            FileOutputStream fos= new FileOutputStream("Students.ser");
            ObjectOutputStream oos= new ObjectOutputStream(fos);
            oos.writeObject(studentList);
            oos.close();
            fos.close();
          }catch(IOException ioe){
               ioe.printStackTrace();
           }	
    	
    }
    
    //deserialization method for course ArrayList
    public static void deserialCourse() {
    	ArrayList<Course> deCourse=new ArrayList<>();
    	try
        {
            FileInputStream fis = new FileInputStream("Courses.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            deCourse = (ArrayList) ois.readObject();
            courseList=deCourse;
            ois.close();
            fis.close();
         }catch(IOException ioe){
             ioe.printStackTrace();
             return;
          }catch(ClassNotFoundException c){
             System.out.println("Class not found");
             c.printStackTrace();
             return;
          }
    }
    
    //deserialization method for course ArrayList
    public static void deserialStudent() {
    	ArrayList<Student> deStudent=new ArrayList<>();
    	try
        {
            FileInputStream fis = new FileInputStream("Students.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            deStudent = (ArrayList) ois.readObject();
            studentList=deStudent;
            ois.close();
            fis.close();
         }catch(IOException ioe){
             ioe.printStackTrace();
             return;
          }catch(ClassNotFoundException c){
             System.out.println("Class not found");
             c.printStackTrace();
             return;
          }
    }
    
}