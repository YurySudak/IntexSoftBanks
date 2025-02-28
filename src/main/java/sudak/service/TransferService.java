package sudak.service;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class TransferService {
//    private final AccountDAO accountDAO;
//    private final TransactionDAO transactionDAO;
//    private final BankDAO bankDAO;
//    private final ClientDAO clientDAO;
//    private final Connection connection;
//
//    public TransferService(AccountDAO accountDAO, TransactionDAO transactionDAO,
//                           BankDAO bankDAO, ClientDAO clientDAO, Connection connection) {
//        this.accountDAO = accountDAO;
//        this.transactionDAO = transactionDAO;
//        this.bankDAO = bankDAO;
//        this.clientDAO = clientDAO;
//        this.connection = connection;
//    }
//
//    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) throws Exception {
//        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
//            throw new Exception("Amount must be positive.");
//        }
//
//        try {
//            connection.setAutoCommit(false);
//            Account fromAccount = accountDAO.getAccountById(fromAccountId);
//            Account toAccount = accountDAO.getAccountById(toAccountId);
//
//            if (fromAccount == null || toAccount == null) {
//                throw new Exception("One or both accounts not found.");
//            }
//            if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
//                throw new Exception("Accounts must have the same currency.");
//            }
//
//            Bank fromBank = bankDAO.getBankById(fromAccount.getBankId());
//            Bank toBank = bankDAO.getBankById(toAccount.getBankId());
//            Client fromClient = clientDAO.getClientById(fromAccount.getClientId());
//
//            BigDecimal commission = BigDecimal.ZERO;
//            if (!fromBank.getId().equals(toBank.getId())) {
//                commission = fromClient.getType() == ClientType.INDIVIDUAL ?
//                        fromBank.getCommissionIndividual() : fromBank.getCommissionLegal();
//            }
//
//            BigDecimal totalDeduction = amount.add(commission);
//            if (fromAccount.getBalance().compareTo(totalDeduction) < 0) {
//                throw new Exception("Insufficient balance.");
//            }
//
//            fromAccount.setBalance(fromAccount.getBalance().subtract(totalDeduction));
//            toAccount.setBalance(toAccount.getBalance().add(amount));
//            accountDAO.updateAccount(fromAccount);
//            accountDAO.updateAccount(toAccount);
//
//            Transaction transaction = new Transaction();
//            transaction.setFromAccountId(fromAccountId);
//            transaction.setToAccountId(toAccountId);
//            transaction.setAmount(amount);
//            transaction.setCurrency(fromAccount.getCurrency());
//            transaction.setCommission(commission);
//            transactionDAO.addTransaction(transaction);
//
//            connection.commit();
//        } catch (Exception e) {
//            connection.rollback();
//            throw e;
//        } finally {
//            connection.setAutoCommit(true);
//        }
//    }
}
