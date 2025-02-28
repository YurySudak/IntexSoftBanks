package sudak.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import sudak.model.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class AccountRepository implements Repository<Account, Long> {

    private final Connection connection;

    @SneakyThrows
    @Override
    public Account save(Account account) {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO account (client_id, bank_id, currency, balance) VALUES (?, ?, ?, ?)");
        preparedStatement.setString(1, account.getNumber());
        preparedStatement.setLong(1, account.getClientId());
        preparedStatement.setLong(2, account.getBankId());
        preparedStatement.setString(3, account.getCurrency());
        preparedStatement.setBigDecimal(4, account.getBalance());
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatement1 = connection.prepareStatement(
                "SELECT * FROM account WHERE number = ?");
        preparedStatement1.setString(1, account.getNumber());
        ResultSet resultSet = preparedStatement1.executeQuery();
        resultSet.next();
        account.setId(resultSet.getLong("id"));
        return account;
    }

    @SneakyThrows
    @Override
    public void update(Account account) {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE account SET client_id = ?, bank_id = ?, currency = ?, balance = ? WHERE id = ?");
        preparedStatement.setLong(1, account.getClientId());
        preparedStatement.setLong(2, account.getBankId());
        preparedStatement.setString(3, account.getCurrency());
        preparedStatement.setBigDecimal(4, account.getBalance());
        preparedStatement.setLong(5, account.getId());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    @Override
    public Optional<Account> findById(Long id) {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM account WHERE id = ?");
        preparedStatement.setLong(1, id);
        return getAccount(preparedStatement);
    }

    @SneakyThrows
    @Override
    public Optional<Account> findByName(String name) {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM account WHERE name = ?");
        preparedStatement.setString(1, name);
        return getAccount(preparedStatement);
    }

    @SneakyThrows
    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM account");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accounts.add(new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("number"),
                        resultSet.getLong("client_id"),
                        resultSet.getLong("bank_id"),
                        resultSet.getString("currency"),
                        resultSet.getBigDecimal("balance")
                ));
            }
        return accounts;
    }

    @SneakyThrows
    @Override
    public void delete(Long id) {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "DELETE FROM account WHERE id = ?");
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();

    }


    @SneakyThrows
    private Optional<Account> getAccount(PreparedStatement preparedStatement) {
        ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(new Account(
                        resultSet.getLong("id"),
                        resultSet.getString("number"),
                        resultSet.getLong("client_id"),
                        resultSet.getLong("bank_id"),
                        resultSet.getString("currency"),
                        resultSet.getBigDecimal("balance")
                ));
            }
        return Optional.empty();
    }
}
