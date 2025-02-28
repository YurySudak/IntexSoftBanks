package sudak.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import sudak.model.Bank;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BankRepository implements Repository<Bank, Long> {

    private final Connection connection;

    @SneakyThrows
    @Override
    public Bank save(Bank bank) {
        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO bank (name, commission_individual, commission_legal) VALUES (?, ?, ?)");
        preparedStatement.setString(1, bank.getName());
        preparedStatement.setBigDecimal(2, bank.getCommissionIndividual());
        preparedStatement.setBigDecimal(3, bank.getCommissionLegal());
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatement1 = connection
                .prepareStatement("SELECT * FROM bank WHERE name = ?");
        preparedStatement1.setString(1, bank.getName());
        ResultSet resultSet = preparedStatement1.executeQuery();
        resultSet.next();
        bank.setId(resultSet.getLong("id"));
        return bank;
    }

    @SneakyThrows
    @Override
    public void update(Bank bank) {
        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE bank SET name = ?, commission_individual = ?, commission_legal = ? WHERE id = ?");
        preparedStatement.setString(1, bank.getName());
        preparedStatement.setBigDecimal(2, bank.getCommissionIndividual());
        preparedStatement.setBigDecimal(3, bank.getCommissionLegal());
        preparedStatement.setLong(4, bank.getId());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    @Override
    public Optional<Bank> findById(Long id) {
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM bank WHERE id = ?");
        preparedStatement.setLong(1, id);
        return getBank(preparedStatement);
    }

    @SneakyThrows
    public Optional<Bank> findByName(String name) {
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM bank WHERE name = ?");
        preparedStatement.setString(1, name);
        return getBank(preparedStatement);
    }

    @SneakyThrows
    @Override
    public List<Bank> findAll() {
        List<Bank> banks = new ArrayList<>();
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM bank");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            banks.add(new Bank(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getBigDecimal("commission_individual"),
                    resultSet.getBigDecimal("commission_legal")
            ));
        }
        return banks;
    }

    @SneakyThrows
    @Override
    public void delete(Long id) {
        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM bank WHERE id = ?");
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }




    private static Optional<Bank> getBank(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return Optional.of(new Bank(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getBigDecimal("commission_individual"),
                    resultSet.getBigDecimal("commission_legal")
            ));
        }
        return Optional.empty();
    }
}
