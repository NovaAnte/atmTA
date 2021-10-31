package test;

import main.ATM;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ATMTest {

    ATM myATM;

    @BeforeEach
    public void setUp() throws Exception {
        myATM = new ATM();
    }

    @ParameterizedTest
    @CsvSource({"100,1", "500,5", "1000,10"})

    public void withdrawHundreds_whenATMbalanceHasEnoughBills_ReturnsCorrectAmountOfBills(int money, int expected){

        myATM.initiateBalance(10, 0, 0);
        int result = myATM.withdrawHundreds(money);

        Assert.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"300,2", "500,2", "1000,2"})

    public void withdrawHundreds_whenATMbalanceHasInsufficientBills_ReturnsAvailableBills(int money, int expected){

        myATM.initiateBalance(2, 0,0);
        int result = myATM.withdrawHundreds(money);

        Assert.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"100,0", "500,1", "1000,2"})
    public void withdrawFiveHundreds_whenATMbalanceHasEnoughBills_ReturnsCorrectAmountOfBills(int money, int expected){
        myATM.initiateBalance(10, 10, 10);

        int result = myATM.withdrawFivehundreds(money);

        Assert.assertEquals(expected, result);
    }

    //500
    @ParameterizedTest
    @CsvSource({"100,0", "1500,2", "2000,2"}) // max cap på vad man kan ta ut
    public void withdrawFiveHundreds_whenATMbalanceHasInsufficientBills_ReturnsAvailableBills(int money, int expected){
        myATM.initiateBalance(0, 2, 0);

        int result = myATM.withdrawFivehundreds(money);

        Assert.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"100,0", "1500, 1", "2000, 2"})
    public void withdrawThousands_whenATMbalanceHasEnoughBills_ReturnsCorrectAmountOfBills(int money, int expected){

        myATM.initiateBalance(10, 10, 10);
        int result = myATM.withdrawThousands(money);

        Assert.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"100,0", "500,0", "3000, 2"})
    public void withdrawThousands_whenATMbalanceHasInsufficientBills_ReturnsAvailableBills(int money, int expected){
        myATM.initiateBalance(0, 0, 2);

        int result = myATM.withdrawThousands(money);

        Assert.assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvSource({"100", "500", "1000"})
    public void checkIfPossibleToWithdraw_whenATMbalanceHasEnoughBills_ReturnsTrue(int money){
        myATM.initiateBalance(2, 2, 2);

        ATM.Withdrawal result = myATM.checkIfPossibleToWithdraw(money);

        Assert.assertEquals(true, result.getPossible());
    }

    @ParameterizedTest
    @CsvSource({"200", "500", "1000"})
    public void checkIfPossibleToWithdraw_whenATMbalanceHasEnoughBills_ReturnsFalse(int money){
        myATM.initiateBalance(0, 0, 0);

        ATM.Withdrawal result = myATM.checkIfPossibleToWithdraw(money);

        Assert.assertEquals(false, result.getPossible());
    }
}
