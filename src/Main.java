import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static int currentNumber =0;
    public static void main(String[] args) throws IOException {

        Scanner userIn = new Scanner(System.in);
        boolean userInput = false;
        int userInInt  = 0 ;
        LinkedList constructionList = new LinkedList();
        while (!userInput){
            System.out.print("File name : ");
            String fileName = userIn.next();

            constructionList =  fileToList(fileName);
            if (constructionList == null){
                System.err.println("Error to open the file");
            }else{
                userInput = true;
            }
        }




        System.out.println("Please Choose: ");
        System.out.println("1. View data ");
        System.out.println("2. Add data ");
        System.out.println("3. Remove data ");
        System.out.println("4. Split data ");
        System.out.println("5. Search and update data ");
        userInInt = userIn.nextInt();
        userIn.nextLine();

        switch (userInInt){
            case 1:
                System.out.println("-----------------------");
                System.out.println("View data");
                System.out.println("-----------------------");
                displayList(constructionList);
                break;
            case 2:
                System.out.println("-----------------------");
                System.out.println("Add Data");
                System.out.println("-----------------------");
                addData(constructionList);
                break;
            case 3:
                System.out.println("-----------------------");
                System.out.println("Remove Data");
                System.out.println("-----------------------");
                break;
            case 4:
                System.out.println("-----------------------");
                System.out.println("Split data based on budget");
                System.out.println("-----------------------");
                splitData(constructionList);
                break;
            case 5:
                System.out.println("-----------------------");
                System.out.println("SEARCH AND UPDATE DATA");
                System.out.println("-----------------------");
                boolean update = getYesOrNo("Do you want to search and update too: ");
                System.out.println("Do you want to search by :");
                System.out.println("1. Project ID");
                System.out.println("2. Client Name");
                System.out.println("3. Project Location");
                System.out.print("Your option: ");
                int searchOption = userIn.nextInt();
                userIn.nextLine();
                System.out.print("Enter key word: ");
                String key = userIn.nextLine();
                findData(constructionList, searchOption, key, true, update);



                break;
            default:
                System.err.println("Wrong input");
        }

    }

    //add file
    public static void addData(LinkedList list)throws IOException {
        try {
            FileWriter fileWrite = new FileWriter("constructionData.txt", true);
            BufferedWriter bufferWrite = new BufferedWriter(fileWrite);

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
            Construction const1 = new Construction(projectID, projectCategory, projectLocation, startDate, estimatedTime, endDate, budget, projectStatus, progressStatus, clientName);

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

            boolean isAdd = getYesOrNo("\nAre you sure you want to add?");

            boolean alreadyHave = findData(list , 1,projectID, false, false);
            if(alreadyHave){
                System.err.println("File already have");
            }
            if (isAdd && !alreadyHave ) {
                list.insertAtBack(const1);
                writeListtoFile(list,"constructionData.txt");
                System.out.println("Data has successfully been added into the file.");

            } else {
                System.err.println("Error to put the file!!! ");
            }
            bufferWrite.close();
            fileWrite.close();
        } catch(Error e){
            System.err.println("File can't be read.");
        }
    }
    //file to list function
    public static LinkedList fileToList(String fileName) throws IOException{
        File constructionFile = new File(fileName) ;
        LinkedList constructionList = new LinkedList();
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
                //System.out.println("temp cons" + tempCons);
                constructionList.insertAtBack(tempCons);
                //System.out.println((Construction)constructionList.getFirst());
            }

        }catch (Error e){
            System.err.println("Can't open the file");
        }
        return constructionList;
    }
    //find data function
    public static boolean findData (LinkedList list,int field , String key , boolean display , boolean update) throws IOException{
        Object obj = list.getFirst();
        boolean found = false;
        String [] stringField = {"Project ID", "Client Name", "Project Location"};
        System.out.println("\n\nAll "+stringField[field-1] +" : " + key);
        LinkedList updateList = new LinkedList();
        int i = 0;
        while (obj != null){
            Construction temp = (Construction) obj;
            switch(field){
                case 1:
                    if(temp.getProjectID().contains(key)){
                        found = true;
                        if (display){
                            System.out.println(temp);
                        }
                    }
                    break;
                case 2:
                    String clientName = temp.getClientName();
                    clientName= clientName.toLowerCase();
                    if(clientName.contains(key.toLowerCase())){
                        found = true;
                        if (display){
                            System.out.println(temp);
                        }
                    }
                    break;
                case 3:
                    String location = temp.getProjectLocation().toLowerCase();
                    if(location.contains(key.toLowerCase())){
                        found = true;
                        if (display ){
                            System.out.println(temp);
                        }
                    }
                    break;
                default:
                    System.err.println("Wrong input !!!");
                    return false;
            }
            if(found){
                updateList.insertAtBack(temp);
            }
            obj = list.getNext();
        }
        System.out.println(!found ? "\n\nThere is no match data" : "");
        if(update){
            boolean trulyUpdate = getYesOrNo("Do you want to update the data");
            if(trulyUpdate){
                Scanner userIn = new Scanner(System.in);
                System.out.print("Project ID you want to update : ");
                String updateKey = userIn.next();
                updateList(list , updateKey );
            }
        }
        return found;
    }
    public static void splitData(LinkedList list)throws IOException{
        LinkedList listBelow = new LinkedList();
        LinkedList listAbove = new LinkedList();
        System.out.print("What is the budget you want to split: ");
        Scanner userIn = new Scanner(System.in);
        double budget = userIn.nextDouble();
        System.out.print("Name for file RM"+ budget + " above : ");
        String firstFile = userIn.next();
        System.out.print("Name for file RM" + budget + " below: ");
        String secondFile = userIn.next();

        Object obj = list.getFirst();
        boolean first = false, second = false;

        while(obj != null){
            Construction tempConst = (Construction) obj;
            if(tempConst.getBudget() >= budget){
                listAbove.insertAtBack(tempConst);
                first = true;
            }else{
                listBelow.insertAtBack(tempConst);
                second = true;
            }
            obj = list.getNext();
        }
        if(first){
            writeListtoFile(listAbove, firstFile);
        }
        if(second){
            writeListtoFile(listBelow, secondFile);
        }
    }
    public static void updateList(LinkedList list , String projectID) throws IOException{
        //P001;C;Bukit Bintang, Kuala Lumpur;2024-05-15;90;2024-08-13;150000;approve;completed;Abdul
        boolean find = false;
        try {
            Object obj = list.getFirst();

            while(obj != null){
                Construction temp = (Construction) obj;
                if(temp.getProjectID().equalsIgnoreCase(projectID)){
                    try {
                        System.out.println("\nData you want to update:\n" + temp);
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
                            temp.setProjectCategory(category);
                        }
                        if(proLoc){
                            System.out.print("New Project Location : ");
                            String location = userIn.nextLine();
                            temp.setProjectLocation(location);
                        }
                        Date newDate = new Date();
                        if(starDate){
                            System.out.println("New start date : ");
                            newDate = getDates();
                            temp.setStartDate(newDate);
                            System.out.println("New End Date: ");
                            newDate = getDates();
                            temp.setEndDate(newDate);
                        }
                        temp.setEstimatedTime(estimatedDate(temp.getStartDate(), temp.getEndDate()));
                        if (budget){
                            System.out.print("New Budget: ");
                            double newBudget = userIn.nextDouble();
                            userIn.nextLine();
                            temp.setBudget(newBudget);
                        }
                        if(progStatus){
                            System.out.print("New progress status: ");
                            String newProg = userIn.next();
                            temp.setProgressStatus(newProg);
                        }
                        if(proStatus){
                            System.out.print("New project status : ");
                            String newProStat = userIn.next();
                            temp.setProjectStatus(newProStat);
                        }
                        if(clientName){
                            System.out.print("New client name: ");
                            String newClient = userIn.nextLine();
                            temp.setClient(newClient);
                        }
                        find = true;
                    }catch (Error e){
                        System.err.println(e);
                    }
                }
                obj = list.getNext();
            }

        }catch(Error e){
            System.err.println("Have a problem !! try again later.... ");
        }
        if(!find){
            System.err.println("Cannot find project ID you want!! please try again");
        }else{
            writeListtoFile(list, "constructionData.txt");
        }

    }

    public static void writeListtoFile(LinkedList list, String fileName) throws  IOException{
        FileWriter fileWrite = new FileWriter(fileName);
        BufferedWriter bufferFile = new BufferedWriter(fileWrite);
        try{
            Object obj = list.getFirst();
            while(obj != null){
                Construction tempConst = (Construction) obj;
                String concat = tempConst.getProjectID()+ ";" + tempConst.getProjectCategory()+";"+ tempConst.getProjectLocation()+";"+
                                dateToString(tempConst.getStartDate()) + ";" + tempConst.getEstimatedTime() + ";" + dateToString(tempConst.getEndDate())+";"+
                                tempConst.getBudget() + ";" + tempConst.getProjectStatus() + ";" + tempConst.getProgressStatus() + ";" + tempConst.getClientName();
                bufferFile.write(concat);
                bufferFile.newLine();
                obj = list.getNext();
            }
            bufferFile.flush();
            fileWrite.close();
            bufferFile.close();
            System.out.println("Done update ~~ sayonara");
        }catch (Error e){
            System.err.println("Cannot update the file!!!");
        }
    }
    public static void displayList (LinkedList list){
        Object obj = list.getFirst();
        while (obj != null){
            Construction tempConst = (Construction) obj;
            System.out.println(tempConst);
            obj = list.getNext();
        }
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
        System.out.print("Day : ");
        day = userIn.nextInt();
        userIn.nextLine();
        Date tarikh = new Date(year, month, day);
        return tarikh;
    }

}