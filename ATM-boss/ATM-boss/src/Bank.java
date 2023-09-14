import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String bankName;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.accounts = new ArrayList<Account>();
        this.users = new ArrayList<User>();
    }


    // Generate a new unique ID for user
    public String getNewUserUUID() {
        String uuid;
        Random gen = new Random();
        int length = 10;
        boolean nonUnique;

        do{
            uuid = "";
            for(int i=0; i<length; i++){
                uuid += ((Integer)gen.nextInt(10)).toString();
            }
            nonUnique = false;
            for(User u : this.users){
                if(uuid.compareTo(u.getUUID())==0){
                    nonUnique= true;
                    break;
                }
            }
        }while(nonUnique);

        return uuid;
    }
    // Generate a new unique ID for Account

    public String getNewAccountUUID() {
        String uuid;
        Random gen = new Random();
        int length = 10;
        boolean nonUnique;

        do{
            uuid = "";
            for(int i=0; i<length; i++){
                uuid += ((Integer)gen.nextInt(10)).toString();
            }
            nonUnique = false;
            for(Account acc : this.accounts){
                if(uuid.compareTo(acc.getUUID())==0){
                    nonUnique= true;
                    break;
                }
            }
        }while(nonUnique);

        return uuid;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public User addUser(String firstName, String lastName, String pin){

        // Create new user and add to list
        User newUser = new User(firstName,lastName, pin, this);
        this.users.add(newUser);

        // Create a savings account for user
        Account newAccount = new Account("Savings",newUser,this);

        // Add the new account to user's account list and bank's account list
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }
    public User userLogin(String userID, String pin){

        // Search through lists of users for userID

        for(User u : this.users){
            if(u.getUUID().compareTo(userID) ==0 && u.validatePin(pin)){
                return u;
            }
        }
        return null;
    }

    public String getName() {
        return this.bankName;
    }
}
