import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String uuid;
    private byte[] pinHash;
    private ArrayList<Account> accounts;

    // Create a new user

    public User(String firstName, String lastName, String pin, Bank userBank) {
        this.firstName = firstName;
        this.lastName = lastName;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: No such algorithm");
            System.exit(1);
        }

        // generate a new uuid

        this.uuid = userBank.getNewUserUUID();

        // create empty list of accounts
        this.accounts = new ArrayList<Account>();

        System.out.printf("New user : %s %s .\n UUID : %s \n Account Created Successfully!\n\n", firstName, lastName, this.uuid);
    }

    // Add new User to Users List
    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String pin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error : An error occurred while validating PIN");
            System.exit(1);
        }

        return false;
    }

    public String getFullName() {
        String fullName="";
        fullName = this.firstName + this.lastName;
        return fullName;
    }

    public void printAccountsSummary() {
        System.out.printf("\n%s 's Accounts summary\n",this.firstName);
        for(int a=0; a<accounts.size(); a++){
            System.out.printf("%d. %s\n",a+1, this.accounts.get(a).getSummaryLine());
        }
    }

    public int getNumAccounts() {
        return this.accounts.size();
    }

    public void printAccTransHistory(int accIndex) {
        this.accounts.get(accIndex).printTransHistory();
    }

    public double getAccountBalance(int accIndex) {
        return this.accounts.get(accIndex).getBalance();
    }

    public void addAccTransaction(int accIndex, double amount, String memo) {
        this.accounts.get(accIndex).addTransaction(amount,memo);
    }

    public String getAccUUID(int toAcct) {
        return this.accounts.get(toAcct).getUUID();
    }
}
