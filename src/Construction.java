import java.util.Date;

public class Construction {

    private String projectID;
    private String projectName;
    private String projectCategory;
    private String projectLocation;
    private Date startDate;
    private int estimatedTime;
    private Date endDate;
    private double budget;
    private String projectStatus;
    private String progressStatus;
    private String clientName;

    //contructor
    //default
    Construction(){
        projectID = null;
        projectName = null;
        clientName = null;
        projectCategory = null;
        projectLocation = null ;
        startDate = null;
        estimatedTime =0;
        endDate = null;
        budget = 0.00;
        projectStatus =null;
        progressStatus = null;
    }
    //normal
    Construction(String pi, String pj,  String pc, String pl, Date sd, int et, Date ed, double b, String ps, String pros ,String c){
        projectID = pi;
        projectName = pj;
        clientName = c;
        projectCategory = pc;
        projectLocation = pl ;
        startDate = sd;
        estimatedTime = et;
        endDate = ed;
        budget = b;
        projectStatus =ps;
        progressStatus = pros;
    }


    //getter method
    public String getProjectID(){
        return projectID;
    }
    public String getProjectName(){
        return projectName;
    }
    public String getProjectCategory(){
        return projectCategory;
    }
    public String getProjectLocation(){
        return projectLocation;
    }
    public Date getStartDate(){
        return startDate;
    }
    public int getEstimatedTime(){
        return estimatedTime;
    }
    public Date getEndDate(){
        return endDate;
    }
    public double getBudget(){
        return budget;
    }
    public String getProjectStatus(){
        return projectStatus;
    }
    public String getProgressStatus(){
        return progressStatus;
    }

    //setter method
    public void setProjectID(String pi){
        projectID = pi;
    }
    public void setClient(String c){
        clientName = c;
    }
    public void setProjectCategory(String pc){
        projectCategory = pc;
    }
    public void setProjectLocation(String pl){
        projectLocation = pl;
    }
    public void setStartDate(Date sd){
        startDate = sd;
    }
    public void setEstimatedTime(int e){
        estimatedTime = e;
    }
    public void setEndDate(Date ed){
        endDate = ed;
    }
    public void setBudget(double b){
        budget = b;
    }
    public void setProjectStatus(String ps){
        projectStatus = ps;
    }
    public void setProgressStatus(String pros){
        progressStatus = pros;
    }

    //print method
    public String toString(){
        return "\nProject ID: " + projectID +"\nClient: "+ clientName+ "\nProject Category: " +
                projectCategory + "\nProject Location: " +projectLocation +
                "\nProject Start Date: " +startDate + "\n Estimated time: " +estimatedTime +
                "\nProject End Date: " +endDate + "\nBudget: " +
                budget + "\nProject Status: " +projectStatus + "\nProgress Status : " + projectStatus;
    }
}
