import java.rmi.registry.LocateRegistry; 
import java.rmi.registry.Registry;  
import java.util.*;   
import java.text.*;

public class Client_201501169 {  
   private Client_201501169() {}  
   public static void main(String[] args) {  
      try {  
         // Getting the registry 
         Registry registry = LocateRegistry.getRegistry(null); 

         // Looking up the registry for the remote object 
         Interface_201501169 stub = (Interface_201501169) registry.lookup("Interface_201501169"); 

         // Calling the remote method using the obtained object 

         // stub.printAccounts(); 
         // String inputStringS = "11-11-2012";
         DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         // Date startDate = dateFormat.parse(inputStringS);

         // String inputStringE = "12-11-2018";
         // // DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         // Date endDate = dateFormat.parse(inputStringE);


         // System.out.println(stub.transaction_details("1", startDate, endDate));

         Scanner scan = new Scanner(System.in);
         while(1==1){
            System.out.print(">>");
            String str = scan.nextLine();
            String[] splited = str.split("\\s+");

            if(splited[0].equals("deposit") ){
               System.out.println(stub.deposit(splited[1], Double.parseDouble(splited[2])));
            }

            else if(splited[0].equals("withdraw") ){
               System.out.println(stub.withdraw(splited[1], Double.parseDouble(splited[2])));
            }

            else if(splited[0].equals("balance") ){
               System.out.println(stub.balance(splited[1])) ;
            }

            else if(splited[0].equals("transaction_details") ){

               if(splited.length == 2){
                  String csv = stub.transaction_details(splited[1]);
                  System.out.printf("| %-5s", "ID");
                  System.out.printf("| %-28s", "TIME");
                  System.out.printf("| %-5s", "AMOUNT");
                  System.out.printf("| %-5s", "BALANCE\n");

                  String[] rows = csv.split("\n");
                  for(String row : rows) {
                     String[] fields = row.split(",");
                     for(String field : fields) {
                        System.out.printf("| %-5s", field);
                     }
                     System.out.println();
                  }
               }
            
            else{
               Date startDate = dateFormat.parse(splited[2]);
               Date endDate = dateFormat.parse(splited[3]);
               String csv = stub.transaction_details(splited[1], startDate, endDate);
               System.out.printf("| %-5s", "ID");
               System.out.printf("| %-28s", "TIME");
               System.out.printf("| %-5s", "AMOUNT");
               System.out.printf("| %-5s", "BALANCE\n");

               String[] rows = csv.split("\n");
               for(String row : rows) {
                  String[] fields = row.split(",");
                  for(String field : fields) {
                     System.out.printf("| %-5s", field);
                  }
                  System.out.println();
               }
            }
         
         }
      }
   }
         catch (Exception e) {
            System.err.println("Client exception: " + e.toString()); 
            e.printStackTrace(); 
         } 



      } 
   }  