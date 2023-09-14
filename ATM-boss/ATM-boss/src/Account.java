import java.util.ArrayList;

public class Account {
    private String accountName;
    private String uuid;
    private User holder;
    private ArrayList<Transaction> transactions;

    // Create new Account

    public Account(String accountName, User holder, Bank theBank) {
        this.accountName = accountName;
        this.holder = holder;

        // get new account UUID
        this.uuid = theBank.getNewAccountUUID();

        // get account transactions
        this.transactions = new ArrayList<Transaction>();

    }

    public String getUUID() {
        return this.uuid;
    }

    public String getSummaryLine() {
        // get account balance
        double balance = this.getBalance();
        if(balance>=0){
            return String.format("%s : INR %.02f : %s", this.uuid, balance, this.accountName);
        }else{
            return String.format("%s : INR (%.02f) : %s", this.uuid, balance, this.accountName);
        }
    }

    public double getBalance() {
        double balance = 0;

        for(Transaction t : this.transactions){
            balance += t.getAmount();
        }

        return balance;
    }

    public void printTransHistory() {
        System.out.printf("Transaction history for account %s\n", this.uuid);
        for(int t=this.transactions.size()-1; t>=0; t--){
            System.out.printf(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    public void addTransaction(double amount, String memo) {

        // Create new transaction

        Transaction newTrans = new Transaction(amount,this,memo);
        this.transactions.add(newTrans);
    }
}

