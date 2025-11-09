package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the {@link StrictBankAccount} class.
 */
class TestStrictBankAccount {

    private static final int AMOUNT = 100;
    private static final int ACCEPTABLE_MESSAGE_LENGTH = 10;

    // Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    /**
     * Prepare the tests.
     */
    @BeforeEach
    public void setUp() {
        this.mRossi = new AccountHolder("Mario", "Rossi", 1);
        this.bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    /**
     * Test the initial state of the StrictBankAccount.
     */
    @Test
    public void testInitialization() {
        assertEquals(0.0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactionsCount());
        assertEquals(mRossi, bankAccount.getAccountHolder());
    }

    /**
     * Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
     */
    @Test
    public void testManagementFees() {
        bankAccount.deposit(mRossi.getUserID(), AMOUNT);
        final double feeAmount = StrictBankAccount.MANAGEMENT_FEE + bankAccount.getTransactionsCount() * StrictBankAccount.TRANSACTION_FEE;
        bankAccount.chargeManagementFees(mRossi.getUserID());
        assertEquals(AMOUNT - feeAmount , bankAccount.getBalance());
    }

    /**
     * Test that withdrawing a negative amount causes a failure.
     */
    @Test
    public void testNegativeWithdraw() {
        try {
            bankAccount.deposit(mRossi.getUserID(), AMOUNT);
            bankAccount.withdraw(mRossi.getUserID(), -AMOUNT);
            Assertions.fail("Withdrawing a negative amount was possible, but should have thrown an exception");
        } catch (final IllegalArgumentException e) {
            assertEquals(AMOUNT, bankAccount.getBalance()); // No money was withdraw, balance is consistent
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH); // A message with a decent length
        }
    }

    /**
     * Test that withdrawing more money than it is in the account is not allowed.
     */
    @Test
    public void testWithdrawingTooMuch() {
        try {
            bankAccount.deposit(mRossi.getUserID(), AMOUNT);
            bankAccount.withdraw(mRossi.getUserID(), AMOUNT * 2);
            Assertions.fail("withdrawing more money than it is in the account was possible, but should have thrown an exception");
        } catch (final IllegalArgumentException e) {
            assertEquals(AMOUNT, bankAccount.getBalance()); // No money was withdraw, balance is consistent
            assertNotNull(e.getMessage()); // Non-null message
            assertFalse(e.getMessage().isBlank()); // Not a blank or empty message
            assertTrue(e.getMessage().length() >= ACCEPTABLE_MESSAGE_LENGTH); // A message with a decent length
        }
    }
}
