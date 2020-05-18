import java.util.*;
import java.io.*;

class Course implements Comparable<Course>, Serializable {

    //basic information 
    private final String name;
    private final String ID;
    private int maxStudent;
    private int currentStudent;
    //the name list of a course contains all the students' first name, last name and ID;
    private ArrayList<String> nameList=new ArrayList<>();
    private String instructor;
    private int sectionNum;
    private String location;

    //constructor
    protected Course(String name, String ID, int maxStudent, String instructor, int sectionNum, String location) {
        this.name=name;
        this.ID=ID;
        this.maxStudent=maxStudent;
        this.instructor=instructor;
        this.sectionNum=sectionNum;
        this.location=location;
        currentStudent=0;
    }
    
    //get methods
    public String getName() {
    	return this.name;
    }
    
    public String getID() {
    	return this.ID;
    }
    
    public int getMax() {
    	return this.maxStudent;
    }
    
    public int getCurrent() {
    	return this.currentStudent;
    }
    
    public ArrayList<String> getnameList() {
    	return this.nameList;
    }
    
    public String getInstructor() {
    	return this.instructor;
    }
    
    public int getSectionNum() {
    	return this.sectionNum;
    }
    
    public String getLocation() {
    	return this.location;
    }
    
    //set methods:except for name and ID
    public void setMax(int newMax) {
    	this.maxStudent=newMax;
    }
    
    public void setCurrent(int newCurrent) {
    	currentStudent=newCurrent;
    }
    
    public void setNameList(String firstName, String lastName, String ID, String action) { //add or remove students
    	                                                //"+" for add and "-" for remove
    	
    	String nameAndID=firstName+"\t"+lastName+"\t"+ID;
    	
    	//add the student to the course
    	if (action.contentEquals("+")) {
    		if (nameList.contains(nameAndID)) {
    			System.out.println("The student is already in the course!");
    	    } else {
    	    	nameList.add(nameAndID);
    	    	this.currentStudent++;
    	    }
    	//remove the student from the course
    	} else if (action.contentEquals("-")) {
    		if (!nameList.contains(nameAndID)) {
    			System.out.println("The students is not in the course!");
    	    } else {
    	    	nameList.remove(nameAndID);
    	    	this.currentStudent--;
    	    }
    	}
    }
    
    public void setInstructor(String newInstr) {
    	this.instructor=newInstr;
    }
    
    public void setSectionNum(int newNum) {
    	this.sectionNum=newNum;
    }
    
    public void setLocation(String newLocation) {
    	this.location=newLocation;
    }
     
    
    //override method from Comparable interface
    public int compareTo(Course c) {
        if (this.currentStudent==c.currentStudent) {
            return 0;
        } else if (this.currentStudent>c.currentStudent) {
            return 1;
        } else {
            return -1;
        }
    }

}