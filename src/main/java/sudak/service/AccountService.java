package sudak.service;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import sudak.model.Account;
import sudak.model.Bank;
import sudak.model.ClientType;
import sudak.model.Transaction;
import sudak.repository.AccountRepository;
import sudak.repository.BankRepository;
import sudak.repository.ClientRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class AccountService implements Service {

    private final AccountRepository accountRepository;
    private final BankRepository bankRepository;
    private final ClientRepository clientRepository;

    public Account create(Account account) {
        validateAccount(account);
        validateNewAccount(account);
        return accountRepository.save(account);
    }

    public void update(Account account) {
        validateAccount(account);
        accountRepository.update(account);
    }

    public void addMoney(Long id, BigDecimal amount) {
        Account account = getById(id);
        account.setBalance(account.getBalance().add(amount));
        update(account);
    }

    public void withdrawMoney(Long id, BigDecimal amount) {
        Account account = getById(id);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Not enough money on account");
        }
        account.setBalance(account.getBalance().subtract(amount));
        update(account);
    }

    @SneakyThrows
    public void transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive.");
        }
        if (fromAccountId.equals(toAccountId)) {
            throw new IllegalArgumentException("Cannot transfer money to the same account.");
        }
        Account fromAccount = getById(fromAccountId);
        Account toAccount = getById(toAccountId);
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Not enough money on account");
        }
        if (!fromAccount.getCurrency().equals(toAccount.getCurrency())) {
            throw new IllegalArgumentException("Currency mismatch");
        }
        BigDecimal commission = BigDecimal.ZERO;
        if (!fromAccount.getBankId().equals(toAccount.getBankId())) {
            ClientType type = clientRepository.findById(fromAccount.getClientId()).orElseThrow().getType();
            Bank bank = bankRepository.findById(fromAccount.getBankId()).orElseThrow();
            switch (type) {
                case INDIVIDUAL -> commission = bank.getCommissionIndividual();
                case LEGAL -> commission = bank.getCommissionLegal();
            }
        }
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount.add(commission)));
        if (fromAccount.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Not enough money on account");
        }
        toAccount.setBalance(toAccount.getBalance().add(amount));
        Transaction transaction = new Transaction();
        transaction.setFromAccountId(fromAccount.getId());
        transaction.setToAccountId(toAccount.getId());
        transaction.setAmount(amount);
        transaction.setCurrency(fromAccount.getCurrency());
        transaction.setCommission(commission);
        transaction.setTimestamp(LocalDateTime.now());
        accountRepository.transfer(fromAccount, toAccount, transaction);
    }

    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public void delete(Long id) {
        accountRepository.delete(id);
    }

    private void validateAccount(Account account) {
        if (account.getBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Account balance cannot be negative");
        }
        if (account.getClientId() == null) {
            throw new IllegalArgumentException("Account client id cannot be null");
        }
        if (account.getBankId() == null) {
            throw new IllegalArgumentException("Account bank id cannot be null");
        }
    }

    private void validateNewAccount(Account account) {
        if (accountRepository.findById(account.getId()).isPresent()) {
            throw new IllegalArgumentException("Account with id " + account.getId() + " already exists");
        }
    }

    public List<Transaction> getTransactions(long accountId) {
        List<Transaction> transactions = accountRepository.findTransactions(accountId);
        if(transactions.isEmpty()) {
            throw new IllegalArgumentException("No transactions found for account with id " + accountId);
        }
        return transactions;
    }
}
