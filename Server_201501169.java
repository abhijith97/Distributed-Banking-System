import java.rmi.registry.Registry; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 
import java.util.*;

public class Server_201501169 extends Bank { 
   public Server_201501169() {} 
   public static void main(String args[]) { 
      try { 
         // Instantiating the implementation class 
         Bank obj = new Bank(); 
    
         // Exporting the object of implementation class  
         // (here we are exporting the remote object to the stub) 
         Interface_201501169 stub = (Interface_201501169) UnicastRemoteObject.exportObject(obj, 0);  
         
         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(); 
         
         registry.rebind("Interface_201501169", stub);  
         System.err.println("Server ready"); 
      } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      } 
   } 
} 


class Bank implements Interface_201501169 {  
   int trans_id = 1;
   Map<String ,Account> map=new HashMap<String,Account>();
   public Bank(){
      Account account1 = new Account("A" , "1111111111", "Premium", "999", 10000.0);
      map.put(account1.acct_n, account1);
      Account account2 = new Account("B" , "2222222222", "Basic", "888", 100000.0);
      map.put(account2.acct_n, account2);
      Account account3 = new Account("C" , "3333333333", "Premium", "777", 1000.0);
      map.put(account3.acct_n, account3);
   }

   public void printAccounts() {  
 //Traversing map  
      for(Map.Entry<String, Account> entry:map.entrySet()){    
         String key=entry.getKey();  
         Account a=entry.getValue();  
         System.out.println(key+" Details:");  
         System.out.println(a.name+" "+a.acct_n+" "+a.acct_t+" "+a.contact+" "+String.valueOf(a.balance)+" "+Arrays.toString(a.trans.toArray()));   
      }    
   }  

   public String balance(String acct_n){
      return String.valueOf(map.get(acct_n).balance);
   }

   public String deposit(String acct_n, Double amt){
      map.get(acct_n).balance += amt;
      map.get(acct_n).trans.add(new Transaction(trans_id, new Date(), "+"+Double.toString(amt),  Double.toString((map.get(acct_n).balance ))));
      trans_id+=1;
      return("DEPOSITED "+ (trans_id-1) +" "+String.valueOf(map.get(acct_n).balance));
   }
   public String withdraw(String acct_n, Double amt){
      map.get(acct_n).balance -= amt;
      map.get(acct_n).trans.add(new Transaction(trans_id, new Date(), "-"+Double.toString(amt), Double.toString((map.get(acct_n).balance ))));
      trans_id+=1;
      return("WITHDRAWN "+ (trans_id-1) +" "+ String.valueOf(map.get(acct_n).balance));
   }

   public String transaction_details(String acct_n){
      String output = "";
      Account acnt = map.get(acct_n);
      for(Transaction t: acnt.trans){
         output+=Integer.toString(t.t_id)+","+t.date.toString()+","+t.amt+","+t.balance+"\n";
      }
      return output;
   }

   public String transaction_details(String acct_n, Date start, Date end){
      Comparator<Transaction> c = new Comparator<Transaction>() {
         public int compare(Transaction t1 , Transaction t2) {
            return t1.date.compareTo(t2.date);
         }
      };
      String output = "";
      Account acnt = map.get(acct_n);
      ArrayList<Transaction> trans = acnt.trans;

      int index = Math.abs(Collections.binarySearch(trans, new Transaction(0, start, "", ""), c));
      
      for (int i = index-1; i < trans.size(); i++) {
         if(trans.get(i).date.after(end)){
            break;
         }
         output+=Integer.toString(trans.get(i).t_id)+","+trans.get(i).date.toString()+","+trans.get(i).amt+","+trans.get(i).balance+"\n";
      }
      return output;
   }
} 

class Account{
   String name;
   String acct_n;
   String acct_t;
   String contact;
   Double balance;
   ArrayList<Transaction> trans = new ArrayList<Transaction>();


   public Account(String name, String acct_n , String acct_t, String contact, Double balance){
      this.name = name;
      this.acct_n = acct_n;
      this.acct_t = acct_t;
      this.contact = contact;
      this.balance = balance;
   }
}

class Transaction{
   int t_id;
   Date date;
   String amt;
   String balance;
   public Transaction(int t_id, Date date, String amt, String balance){
      this.t_id = t_id ;
      this.date = date ;
      this.amt = amt;
      this.balance = balance;
   }
}

