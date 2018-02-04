import java.rmi.Remote; 
import java.rmi.RemoteException;  
import java.util.*; 
// Creating Remote interface for our application 
public interface Interface_201501169 extends Remote {  
   void printAccounts() throws RemoteException; 
   String balance(String acct_n) throws RemoteException; 
   String deposit(String acct_n, Double amt) throws RemoteException; 
   String withdraw(String acct_n, Double amt) throws RemoteException; 
   String transaction_details(String acct_n) throws RemoteException;
   String transaction_details(String acct_n, Date start, Date end) throws RemoteException;
} 