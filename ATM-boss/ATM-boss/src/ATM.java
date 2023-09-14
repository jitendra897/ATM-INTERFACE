import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        // initialize bank
        Bank theBank = new Bank("Bank of KLE");

        // add a user which also creates a savings account

        User aUser = theBank.addUser("Steve","Lodge","1234");

        Account newAcc = new Account("Current", aUser, theBank);

        aUser.addAccount(newAcc);
        theBank.addAccount(newAcc);

        User curUser;

        while(true){
            // Login prompt stays till successful login and returns a user
            curUser = ATM.mainMenuLoginPrompt(theBank, input);

            // Main menu for user
            ATM.printUserMenu(curUser, input);

        }
    }

    private static void printUserMenu(User curUser, Scanner input) {
        System.out.printf("-------------------------------------------------------------\n");
        System.out.printf("Welcome back %s !\n\n",curUser.getFullName());

        // Print summary for user's Accounts
        curUser.printAccountsSummary();

        int choice;
        do{
            System.out.println("Choose an action\n");
            System.out.printf("1. Show account transactions\n 2. Withdrawal\n 3. Deposit\n 4. Transfer\n 5. Logout`\n");
            System.out.printf("-------------------------------------------------------------\n");

            System.out.println("Enter choice: ");
            choice = input.nextInt();

            if(choice<1 || choice >5){
                System.out.println("Invalid choice");
            }
        }while(choice<1 || choice >5);

        // process the choice
        switch(choice){
            case 1:
                ATM.showTransactionHistory(curUser, input);
                break;
            case 2:
                ATM.withdrawFunds(curUser, input);
                break;
            case 3:
                ATM.depositFunds(curUser, input);
                break;
            case 4:
                ATM.transferFunds(curUser, input);
                break;
            case 5:
                input.nextLine();
                break;
        }

        if(choice!=5){
            ATM.printUserMenu(curUser,input);
        }
    }

    private static void withdrawFunds(User curUser, Scanner input) {
        // init
        int fromAcct;
        double amount;
        double accBal;
        String memo;

        do{
            System.out.printf("Enter the account you want to transfer from: \n");
            fromAcct = input.nextInt()-1;
            if(fromAcct<0 || fromAcct>curUser.getNumAccounts()){
                System.out.println("Invalid account to transfer from");
            }
        }while(fromAcct<0 || fromAcct>curUser.getNumAccounts());

        accBal = curUser.getAccountBalance(fromAcct);

        do{
            System.out.printf("Enter the amount to withdraw (MAX : %.02f ): \n",accBal);
            amount = input.nextDouble();
            if(amount<0){
                System.out.println("Amount must be greater than zero");
            }else if(amount >accBal){
                System.out.println("Insufficient funds");
            }
        }while(amount<0 || amount>accBal);

        input.nextLine();

        //get memo
        System.out.println("Enter a memo : \n");
        memo= input.nextLine();

        // Make withdrawal
        curUser.addAccTransaction(fromAcct,-1*amount,memo);

    }
    private static void depositFunds(User curUser, Scanner input) {
        // init
        int toAcct;
        double amount;
        String memo;

        do{
            System.out.printf("Enter the account you want to deposit to: \n");
            toAcct = input.nextInt()-1;
            if(toAcct<0 || toAcct>curUser.getNumAccounts()){
                System.out.println("Invalid account to transfer to");
            }
        }while(toAcct<0 || toAcct>curUser.getNumAccounts());

        do{
            System.out.printf("Enter the amount to deposit (MIN : INR 100 ): \n");
            amount = input.nextDouble();
            if(amount<100){
                System.out.println("Amount must be greater than INR 100");
            }
        }while(amount<100);

        input.nextLine();

        //get memo
        System.out.println("Enter a memo : \n");
        memo= input.nextLine();

        // Make withdrawal
        curUser.addAccTransaction(toAcct,amount,memo);

    }

    private static void transferFunds(User curUser, Scanner input) {
        // init
        int fromAcct;
        int toAcct;
        double amount;
        double accBal;

        // Get the acc to transfer from
        do{
            System.out.printf("Enter the account you want to transfer from: \n");
            fromAcct = input.nextInt()-1;
            if(fromAcct<0 || fromAcct>curUser.getNumAccounts()){
                System.out.println("Invalid account to transfer from");
            }
        }while(fromAcct<0 || fromAcct>curUser.getNumAccounts());

        accBal = curUser.getAccountBalance(fromAcct);

        // get the account to tranfer to
        do{
            System.out.printf("Enter the account you want to transfer to: \n");
            toAcct = input.nextInt()-1;
            if(toAcct<0 || toAcct>curUser.getNumAccounts()){
                System.out.println("Invalid account to transfer to");
            }
        }while(toAcct<0 || toAcct>curUser.getNumAccounts());

        // get amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (MAX : %.02f ): \n",accBal);
            amount = input.nextDouble();
            if(amount<0){
                System.out.println("Amount must be greater than zero");
            }else if(amount >accBal){
                System.out.println("Insufficient funds");
            }
        }while(amount<0 || amount>accBal);

        // Do the transfer
        curUser.addAccTransaction(fromAcct, -1*amount, String.format("Transfer to account %s", curUser.getAccUUID(toAcct)));
        curUser.addAccTransaction(toAcct, amount, String.format("Transfered from account %s", curUser.getAccUUID(fromAcct)));

    }

    private static void showTransactionHistory(User curUser, Scanner input) {
        int theAcc;
        do{
            System.out.printf("Select bank account (1-%d): ", curUser.getNumAccounts());
            theAcc = input.nextInt()-1;
            if(theAcc <0 || theAcc>= curUser.getNumAccounts()){
                System.out.println("Invalid Account selected\n");
            }
        }while(theAcc <0 || theAcc>= curUser.getNumAccounts());

        // Print transaction history
        curUser.printAccTransHistory(theAcc);

    }

    private static User mainMenuLoginPrompt(Bank theBank, Scanner input) {

        // init
        String userID;
        String pin;
        User authUser;

        System.out.printf("-------------------------------------------------------------\n");
        System.out.printf("Welcome to %s\n\n", theBank.getName());
        do{
            System.out.printf("Enter userID : ");
            userID = input.nextLine();
            System.out.println("Enter user PIN: ");
            pin = input.nextLine();

            // try to get User for this userID and password

            authUser = theBank.userLogin(userID,pin);

            if(authUser==null){
                System.out.println("Invalid login credentials!");
            }

        }while(authUser==null);

        return authUser;
    }
}
