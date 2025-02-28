package sudak.service;

import lombok.AllArgsConstructor;
import sudak.model.Account;
import sudak.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
public class AccountService implements Service<Account, Long> {

    private final AccountRepository accountRepository;

    @Override
    public Account create(Account account) {
        validateAccount(account);
        validateNewAccount(account);
        return accountRepository.save(account);
    }

    @Override
    public void update(Account account) {
        validateAccount(account);
        accountRepository.update(account);
    }

    @Override
    public Account getById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
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
}
