package sudak.service;

import sudak.model.Bank;
import sudak.repository.BankRepository;

import java.math.BigDecimal;
import java.util.List;

public class BankService implements Service {
    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Bank create(Bank bank) {
        validateBank(bank);
        validateNewBank(bank);
        return bankRepository.save(bank);
    }

    public void update(Bank bank) {
        validateBank(bank);
        bankRepository.update(bank);
    }

    public Bank getById(Long id) {
        return bankRepository.findById(id).orElseThrow();
    }

    public List<Bank> getAll() {
        return bankRepository.findAll();
    }

    public void delete(Long id) {
        bankRepository.delete(id);
    }

    private void validateBank(Bank bank) {
        validateCommission(bank.getCommissionIndividual());
        validateCommission(bank.getCommissionLegal());
        validateBankName(bank.getName());
    }

    private void validateCommission(BigDecimal commission) {
        if (commission.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Commission cannot be negative");
        }
    }

    private void validateBankName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Bank name cannot be empty");
        }
    }

    private void validateNewBank(Bank bank) {
        if (bankRepository.findByName(bank.getName()).isPresent()) {
            throw new IllegalArgumentException("Bank name must be unique");
        }
    }
}
