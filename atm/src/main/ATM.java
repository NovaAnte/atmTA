package main;

import java.util.*;

public class ATM {

    private Map<Integer, Integer> atmBalance;

    public void ATMmenu() {
        initiateBalance();
        while (true) {
            System.out.println("Welcome!");
            System.out.println("Select amount to withdraw:");
            ATMwithdraw();
        }
    }

    public void ATMwithdraw() {

        boolean run = true;
        Scanner sc = new Scanner(System.in);
        while (run) {
            try {
                int amount = sc.nextInt();
                System.out.println("You have selected: " + amount);
                run = false;
                withdrawMoney(amount);
                getRemainingBills();

            } catch (InputMismatchException e) {
                System.out.println("Sorry, wrong type, try again.");
            }
        }
    }

    public void withdrawMoney(int money) {
        Withdrawal withdrawal = checkIfPossibleToWithdraw(money);
        if (withdrawal.getPossible()) {
            atmBalance.replace(100, atmBalance.get(100) - withdrawal.getHundreds());
            atmBalance.replace(500, atmBalance.get(500) - withdrawal.getFiveHundreds());
            atmBalance.replace(1000, atmBalance.get(1000) - withdrawal.getThousands());
        } else {
            System.out.println("Not possible to withdraw.");
        }
    }

    public Withdrawal checkIfPossibleToWithdraw(int money) {
        Withdrawal tryWithdrawal = new Withdrawal();

        int amountOfThousands = withdrawThousands(money);
        int remainingMoney = money - 1000 * amountOfThousands;
        if (remainingMoney == 0)
            return setWithdrawalToPossible(tryWithdrawal, amountOfThousands, 0, 0);

        int amountOfFivehundreds = withdrawFivehundreds(remainingMoney);
        remainingMoney = remainingMoney - 500 * amountOfFivehundreds;
        if (remainingMoney == 0)
            return setWithdrawalToPossible(tryWithdrawal, amountOfThousands, amountOfFivehundreds, 0);

        int amountOfHundreds = withdrawHundreds(remainingMoney);
        remainingMoney = remainingMoney - 100 * amountOfHundreds;

        if (remainingMoney < 100)
            return setWithdrawalToPossible(tryWithdrawal, amountOfThousands, amountOfFivehundreds, amountOfHundreds);

        tryWithdrawal.setPossible(false);
        return tryWithdrawal;
    }

    public int withdrawThousands(int money) {
        int thousands = money / 1000;
        int availableThousands = atmBalance.get(1000);

        if (availableThousands >= thousands) {
            return thousands;
        } else {
            return availableThousands;
        }
    }

    public int withdrawFivehundreds(int money) {
        int fiveHundreds = money / 500;
        int availableFiveHundreds = atmBalance.get(500);

        if (availableFiveHundreds >= fiveHundreds) {
            return fiveHundreds;
        } else {
            return availableFiveHundreds;
        }
    }

    public int withdrawHundreds(int money) {
        int hundreds = money / 100;
        int availableHundreds = atmBalance.get(100);

        if (availableHundreds >= hundreds) {
            return hundreds;
        } else {
            return availableHundreds;
        }
    }

    public Withdrawal setWithdrawalToPossible(Withdrawal withdrawal, int amountOfThousands, int amountOfFiveHundreds,
            int amountOfHundreds) {
        withdrawal.setHundreds(amountOfHundreds);
        withdrawal.setFiveHundreds(amountOfFiveHundreds);
        withdrawal.setThousands(amountOfThousands);
        withdrawal.setPossible(true);
        return withdrawal;
    }

    public void getRemainingBills() {
        int thousands = atmBalance.get(1000);
        int fiveHundreds = atmBalance.get(500);
        int hundreds = atmBalance.get(100);
        System.out.println("Thousands left: " + thousands);
        System.out.println("Five-hundreds left: " + fiveHundreds);
        System.out.println("Hundreds left: " + hundreds);
    }

    public void initiateBalance() {
        atmBalance = new HashMap<>();
        atmBalance.put(100, 5);
        atmBalance.put(500, 3);
        atmBalance.put(1000, 2);
        System.out.println(atmBalance);
    }

    public void initiateBalance(int hundreds, int fiveHundreds, int thousands){
        atmBalance = new HashMap<>();
        atmBalance.put(100, hundreds);
        atmBalance.put(500, fiveHundreds);
        atmBalance.put(1000, thousands);
        System.out.println(atmBalance);
    }

    public class Withdrawal {

        private int thousands;
        private int fiveHundreds;
        private int hundreds;
        private boolean possible;

        public boolean getPossible() {return possible;}

        public int getThousands() {
            return thousands;
        }

        public int getHundreds() {
            return hundreds;
        }

        public int getFiveHundreds() {
            return fiveHundreds;
        }

        public void setPossible(boolean possible) {
            this.possible = possible;
        }

        public void setThousands(int thousands) {
            this.thousands = thousands;
        }

        public void setFiveHundreds(int fiveHundreds) {
            this.fiveHundreds = fiveHundreds;
        }

        public void setHundreds(int hundreds) {
            this.hundreds = hundreds;
        }

    }

}
