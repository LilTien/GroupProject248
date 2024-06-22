import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class QueueMain {

    public static void main(String[] args) throws IOException {
        boolean cont = true;
        Scanner userIn = new Scanner(System.in);
        boolean userInput = false;
        int userInInt  = 0 ;
        Queue constructionQ = new Queue();
        while (!userInput){
            System.out.println("File name : ");
            String fileName = userIn.next();
            constructionQ =  fileToQueue(fileName);
            if (constructionQ == null){
                System.err.println("Error to open the file");
            }else{
                userInput = true;
            }
        }

        while(cont){
            System.out.println("Please Choose: ");
            System.out.println("1. VIEW PROJECT ");
            System.out.println("2. ADD PROJECT ");
            System.out.println("3. REMOVE PROJECT ");
            System.out.println("4. SPLIT PROJECT ");
            System.out.println("5. SEARCH AND UPDATE PROJECT ");
            System.out.println("6. COUNT PROJECT BASED ON REGION ");
            System.out.println("7. Calculate total budget on specific region");
            userInInt = userIn.nextInt();
            userIn.nextLine();

            switch (userInInt){
                case 1:
                    System.out.println("-----------------------");
                    System.out.println("VIEW PROJECT");
                    System.out.println("-----------------------");
                    displayQueue(constructionQ);
                    break;
                case 2:
                    System.out.println("-----------------------");
                    System.out.println("ADD PROJECT");
                    System.out.println("-----------------------");
                    addData(constructionQ);
                    break;
                case 3:
                    System.out.println("-----------------------");
                    System.out.println("REMOVE PROJECT");
                    System.out.println("-----------------------");
                    removeData(constructionQ);
                    break;
                case 4:
                    System.out.println("-----------------------");
                    System.out.println("SPLIT PROJECT BASED ON BUDGET");
                    System.out.println("-----------------------");
                    splitData(constructionQ);
                    break;
                case 5:
                    System.out.println("-----------------------");
                    System.out.println("SEARCH AND UPDATE PROJECT");
                    System.out.println("-----------------------");
                    System.out.println("Do you want to search and update of search only: ");
                    System.out.println("Do you want to search by :");
                    System.out.println("1. Project ID");
                    System.out.println("2. Client Name");
                    System.out.println("3. Project Location");
                    System.out.print("Your option: ");
                    int searchOption = userIn.nextInt();
                    userIn.nextLine();
                    System.out.print("Enter key word: ");
                    String key = userIn.nextLine();
                    findData(constructionQ, searchOption, key, true);
                    break;
                case 6:
                    System.out.println("-----------------------");
                    System.out.println("COUNT PROJECT BASED ON REGION");
                    System.out.println("-----------------------");
                    countData(constructionQ);
                    break;
                case 7:
                    System.out.println("-----------------------");
                    System.out.println("CALCULATE TOTAL BUDGET BASED ON REGION");
                    System.out.println("-----------------------");
                    System.out.print("\nEnter Region: ");
                    String region = userIn.nextLine();
                    double total = totalBudget(constructionQ, region);
                    System.out.println("Total budget on " +  region + " : " + total);
                    break;
                default:
                    System.err.println("Wrong input");
            }
            cont = getYesOrNo("Do you want to continue");
        }


    }

    //file to list function
    public static Queue fileToQueue(String fileName) throws IOException{
        File constructionFile = new File(fileName) ;
        Queue constructionQ = new Queue();
        try{
            Scanner fileScan = new Scanner(constructionFile);

            while(fileScan.hasNext()){
                String line = fileScan.nextLine();
                Scanner lineScan = new Scanner(line);
                lineScan.useDelimiter(";");
                String projectId = lineScan.next();
                char proCat = lineScan.next().charAt(0);
                String proLoc = lineScan.next();
                //start date
                String stringStartDate = lineScan.next();
                Scanner dateScan = new Scanner(stringStartDate);
                dateScan.useDelimiter("-");
                int year = dateScan.nextInt();
                int month = dateScan.nextInt();
                int day = dateScan.nextInt();
                Date startDate = new Date(year-1900, month ,day);
                int estimatedDate = lineScan.nextInt();
                //end date
                String stringEndDate = lineScan.next();
                dateScan = new Scanner(stringEndDate);
                dateScan.useDelimiter("-");
                year = dateScan.nextInt();
                month = dateScan.nextInt();
                day = dateScan.nextInt();
                Date endDate = new Date(year - 1900 , month, day);
                double budget = lineScan.nextDouble();
                String projectStatus = lineScan.next();
                String projectProgress = lineScan.next();
                String clientName = lineScan.next();
                Construction tempCons = new Construction(projectId, proCat, proLoc, startDate,
                        estimatedDate, endDate, budget, projectStatus,
                        projectProgress, clientName);
                constructionQ.enqueue(tempCons);
                //System.out.println((Construction)constructionList.getFirst());
            }

        }catch (Error e){
            System.err.println("Can't open the file");
        }
        return constructionQ;
    }
    //find data function
    public static boolean findData (Queue q,int field , String key , boolean display) throws IOException{

        boolean found = false;
        String [] stringField = {"Project ID", "Client Name", "Project Location"};

        Queue updateQ = new Queue();
        Queue tempQ = new Queue();

        while (!q.isEmpty()){
            Construction obj = q.dequeue();
            tempQ.enqueue(obj);
            switch(field){
                case 1:
                    if(obj.getProjectID().contains(key.toUpperCase())){
                        found = true;
                        if (display){
                            System.out.println(obj);
                        }
                    }
                    break;
                case 2:
                    String clientName = obj.getClientName();
                    clientName= clientName.toLowerCase();
                    if(clientName.contains(key.toLowerCase())){
                        found = true;
                        if (display){
                            System.out.println(obj);
                        }
                    }
                    break;
                case 3:
                    String location = obj.getProjectLocation().toLowerCase();
                    if(location.contains(key.toLowerCase())){
                        found = true;
                        if (display ){
                            System.out.println(obj);
                        }
                    }
                    break;
                default:
                    System.err.println("Wrong input !!!");
                    return false;
            }
            //System.out.println(count++);
            if(found){
                updateQ.enqueue(obj);
            }
        }
        //put back to data to original queue
        tempToOriginalQueue(tempQ, q);
        System.out.println(!found ? "\n\nThere is no match data" : "");
        if(display){
            System.out.println("\n\nAll "+stringField[field-1] +" : " + key);
            boolean update = getYesOrNo("Do you want to update the data");
            Scanner userIn = new Scanner(System.in);
            if(update){
                System.out.print("Project ID you want to update : ");
                String updateKey = userIn.next();
                updateQueue(q , updateKey );
            }
        }
        return found;
    }
    public static double totalBudget(Queue q, String region){
        Queue tempQ = new Queue();
        double totalBudget =0;
        while(!q.isEmpty()){
            Construction obj = q.dequeue();
            if(obj.getProjectLocation().toLowerCase().contains(region.toLowerCase())){
                totalBudget += obj.getBudget();
            }
            tempQ.enqueue(obj);
        }
        tempToOriginalQueue(tempQ, q);
        return totalBudget;
    }
    public static void removeData(Queue q) throws IOException {
        Scanner userIn = new Scanner(System.in);
        boolean isRemove = false;
        Queue tempQ = new Queue();
        Construction removeData = null;

        displayQueue(q);

        System.out.println("Enter the project ID you want to remove : ");
        String nameKey = userIn.nextLine();
        isRemove = getYesOrNo("Are you sure you want to remove the data?");

        if(isRemove){
            if(!q.isEmpty()) {
                while (!q.isEmpty()) {
                    Construction obj = q.dequeue();
                    if(!obj.getProjectID().equalsIgnoreCase(nameKey)){
                        tempQ.enqueue(obj);
                    }
                }
            }else{
                System.out.println("The List is empty");
            }

            tempToOriginalQueue(tempQ, q);
            writeQueuetoFile(q, "constructionData.txt");
        }
    }
    public static void splitData(Queue q)throws IOException{
        Queue belowQ = new Queue();
        Queue aboveQ = new Queue();
        System.out.print("What is the budget you want to split: ");
        Scanner userIn = new Scanner(System.in);
        double budget = userIn.nextDouble();
        System.out.print("Name for file RM"+ budget + " above : ");
        String firstFile = userIn.next();
        System.out.print("Name for file RM" + budget + " below: ");
        String secondFile = userIn.next();

        Queue tempQ = new Queue();
        boolean first = false, second = false;

        while(!q.isEmpty()){
            Construction obj = q.dequeue();
            if(obj.getBudget() >= budget){
                aboveQ.enqueue(obj);
                first = true;
            }else{
                belowQ.enqueue(obj);
                second = true;
            }
            tempQ.enqueue(obj);
        }
        if(first){
            writeQueuetoFile(aboveQ, firstFile);
        }
        if(second){
            writeQueuetoFile(belowQ, secondFile);
        }
        tempToOriginalQueue(tempQ, q);
    }
    public static void countData (Queue q) throws IOException{

        int count = 0;
        Scanner in = new Scanner(System.in);

        System.out.println("Based on Location ");
        System.out.println("Enter the Location you want to find : ");
        String location = in.nextLine();
        while (!q.isEmpty()){
            Construction obj = q.dequeue();
            if (obj.getProjectLocation().toLowerCase().contains(location.toLowerCase())){
                count++;
            }
        }

        System.out.println("--------------------------------");
        System.out.println("\t" + location + "\t");
        System.out.println("--------------------------------");
        System.out.println("\nThe total project in " +location + ":" + count);
        System.out.println("--------------------------------");

    }
    public static void addData(Queue q) throws IOException {
        try {

            Scanner userInput = new Scanner(System.in);

            System.out.println("Project ID:");
            String projectID = userInput.nextLine();
            System.out.println("\nClient Name: ");
            String clientName = userInput.nextLine();
            System.out.println("\nProject Category: ");
            char projectCategory = userInput.next().charAt(0);
            userInput.nextLine();
            System.out.println("\nProject Location: ");
            String projectLocation = userInput.nextLine();
            System.out.println("\nStart Date: ");
            Date startDate = getDates();
            System.out.println("\nEnd Date: ");
            Date endDate = getDates();
            System.out.println("\nBudget: ");
            double budget = userInput.nextDouble();
            userInput.nextLine();
            System.out.println("\nProject Status: ");
            String projectStatus = userInput.nextLine();
            System.out.println("\nProgress Status: ");
            String progressStatus = userInput.nextLine();

            int estimatedTime = estimatedDate(startDate, endDate);

            Construction const1 = new Construction(projectID, projectCategory, projectLocation, startDate, estimatedTime, endDate, budget, projectStatus.toUpperCase(), progressStatus.toUpperCase(), clientName);

            System.out.println("\nData that you entered is : ");
            System.out.println("=============================");
            System.out.println("Project ID : " + projectID);
            System.out.println("Project Category : " + projectCategory);
            System.out.println("Project Location : " + projectLocation);
            System.out.println("Start Date : " + startDate);
            System.out.println("End Date : " + endDate);
            System.out.println("Budget : " + budget);
            System.out.println("Project Status : " + projectStatus);
            System.out.println("Progress Status : " + progressStatus);
            System.out.println("Client Name : " + clientName);

            boolean isAdd = getYesOrNo("Are you sure you want to add?");

            boolean alreadyHave = findData(q, 1, projectID, false);
            if (!alreadyHave) {
                if (isAdd ) {
                    q.enqueue(const1);
                    writeQueuetoFile(q, "constructionData.txt");
                    System.out.println("Data has successfully been added into the file.");

                } else {
                    System.err.println("Error to put the file!!! ");
                }
            }else {
                System.out.println("Data already have!!!");
            }
        } catch (Error e) {
            System.err.println("File can't be read.");
        }
    }
    public static void tempToOriginalQueue(Queue temp, Queue ori){
        while (!temp.isEmpty()){
            Construction tempConst = temp.dequeue();
            ori.enqueue(tempConst);
        }
    }
    public static void updateQueue(Queue q , String projectID) throws IOException{
        //P001;C;Bukit Bintang, Kuala Lumpur;2024-05-15;90;2024-08-13;150000;approve;completed;Abdul
        boolean find = false;
        try {

            Queue tempQ = new Queue();
            while(!q.isEmpty()){
                Construction obj = q.dequeue();
                //System.out.println(obj);
                if(obj.getProjectID().equalsIgnoreCase(projectID)){
                    try {
                        System.out.println("\nData you want to update:\n" + obj);
                        boolean proCategory, proLoc, starDate , endDate, budget, proStatus, progStatus, clientName;
                        proCategory = getYesOrNo("Do you want to update the Project Category");
                        proLoc =  getYesOrNo("Do you want to update the ProjectLocation");
                        starDate =  getYesOrNo("Do you want to update the Start Date & end date");
                        budget =  getYesOrNo("Do you want to update the Budget");
                        proStatus =  getYesOrNo("Do you want to update the Project Status");
                        progStatus =  getYesOrNo("Do you want to update the Prograss Status");
                        clientName =  getYesOrNo("Do you want to update the Client Name");

                        Scanner userIn = new Scanner(System.in);
                        //String concat = projectID + ";";
                        if(proCategory){
                            System.out.print("New Category : ");
                            char category = userIn.next().charAt(0);
                            obj.setProjectCategory(category);
                        }
                        if(proLoc){
                            System.out.print("New Project Location : ");
                            String location = userIn.nextLine();
                            obj.setProjectLocation(location);
                        }
                        Date newDate = new Date();
                        if(starDate){
                            System.out.println("New start date : ");
                            newDate = getDates();
                            obj.setStartDate(newDate);
                            System.out.println("New End Date: ");
                            newDate = getDates();
                            obj.setEndDate(newDate);
                        }
                        obj.setEstimatedTime(estimatedDate(obj.getStartDate(), obj.getEndDate()));
                        if (budget){
                            System.out.print("New Budget: ");
                            double newBudget = userIn.nextDouble();
                            userIn.nextLine();
                            obj.setBudget(newBudget);
                        }
                        if(progStatus){
                            System.out.print("New progress status: ");
                            String newProg = userIn.next();
                            obj.setProgressStatus(newProg);
                        }
                        if(proStatus){
                            System.out.print("New project status : ");
                            String newProStat = userIn.next();
                            obj.setProjectStatus(newProStat);
                        }
                        if(clientName){
                            System.out.print("New client name: ");
                            String newClient = userIn.nextLine();
                            obj.setClient(newClient);
                        }
                        find = true;
                    }catch (Error e){
                        System.err.println(e);
                    }
                }
                tempQ.enqueue(obj);
            }
            tempToOriginalQueue(tempQ, q);

        }catch(Error e){
            System.err.println("Have a problem !! try again later.... ");
        }
        if(!find){
            System.err.println("Cannot find project ID you want!! please try again");
        }else{
            writeQueuetoFile(q, "constructionData.txt");
        }

    }
    public static void writeQueuetoFile(Queue q, String fileName) throws  IOException{
        FileWriter fileWrite = new FileWriter(fileName);
        BufferedWriter bufferFile = new BufferedWriter(fileWrite);
        try{
            Queue tempQ = new Queue();
            while(!q.isEmpty()){
                Construction obj = q.dequeue();
                String concat = obj.getProjectID()+ ";" + obj.getProjectCategory()+";"+ obj.getProjectLocation()+";"+
                        dateToString(obj.getStartDate()) + ";" + obj.getEstimatedTime() + ";" + dateToString(obj.getEndDate())+";"+
                        obj.getBudget() + ";" + obj.getProjectStatus() + ";" + obj.getProgressStatus() + ";" + obj.getClientName();
                bufferFile.write(concat);
                bufferFile.newLine();
                tempQ.enqueue(obj);
            }
            tempToOriginalQueue(tempQ, q);
            bufferFile.flush();
            fileWrite.close();
            bufferFile.close();
            System.out.println("Done update ~~");
        }catch (Error e){
            System.err.println("Cannot update the file!!!");
        }
    }
    public static void displayQueue (Queue q){
        Queue tempQ = new Queue();
        while (!q.isEmpty()){
            Construction obj = q.dequeue();
            System.out.println(obj);
            tempQ.enqueue(obj);
        }
        tempToOriginalQueue(tempQ, q);
    }
    public static String dateToString(Date d){
        String year = String.valueOf(d.getYear() + 1900);
        String month = String.valueOf(d.getMonth());
        String date = String.valueOf(d.getDate());
        String stringDate = "";
        stringDate += year+"-"+month+"-"+date ;
        return stringDate;
    }
    public static int estimatedDate(Date start, Date end){
        int year = end.getYear() - start.getYear() ;
        return year;
    }
    public static boolean getYesOrNo (String message){
        Scanner userIn = new Scanner(System.in);
        System.out.print(message + " (y/n) : ");
        char yesOrNo = userIn.next().charAt(0);
        if(yesOrNo == 'y' || yesOrNo == 'Y'){
            return true;
        }else{
            return false;
        }
    }
    public static Date getDates(){
        Scanner userIn = new Scanner(System.in);
        int year, month , day;
        System.out.print("Year: ");
        year = userIn.nextInt()-1900;
        userIn.nextLine();
        System.out.print("Month : ");
        month = userIn.nextInt();
        userIn.nextLine();
        System.out.println("Day : ");
        day = userIn.nextInt();
        userIn.nextLine();
        Date tarikh = new Date(year, month, day);
        return tarikh;
    }

}