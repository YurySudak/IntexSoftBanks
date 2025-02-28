package sudak.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import sudak.model.Account;
import sudak.model.Client;
import sudak.model.ClientType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ClientRepository implements Repository<Client, Long> {

    private final Connection connection;

    @SneakyThrows
    @Override
    public Client save(Client client) {
        ResultSet resultSet = insertClient(client);
        client.setId(resultSet.getLong("id"));
        return client;
    }

    @Override
    @SneakyThrows
    public void update(Client client) {
        PreparedStatement preparedStatement = connection
                .prepareStatement("UPDATE client SET name = ?, type = ? WHERE id = ?");
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, String.valueOf(client.getType()));
        preparedStatement.setLong(3, client.getId());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    @Override
    public Optional<Client> findById(Long id) {
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM client WHERE id = ?");
        preparedStatement.setLong(1, id);
        return getClient(preparedStatement);
    }

    @SneakyThrows
    @Override
    public Optional<Client> findByName(String name) {
        PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM client WHERE name = ?");
        preparedStatement.setString(1, name);
        return getClient(preparedStatement);
    }

    @SneakyThrows
    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM client");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Client client = new Client();
            client.setId(resultSet.getLong("id"));
            client.setName(resultSet.getString("name"));
            client.setType(ClientType.valueOf(resultSet.getString("type")));
            clients.add(client);
        }
        return clients;
    }

    @Override
    @SneakyThrows
    public void delete(Long id) {
        PreparedStatement preparedStatement = connection
                .prepareStatement("DELETE FROM client WHERE id = ?");
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public void createNewClient(Client client, Account account) {
        connection.setAutoCommit(false);
        try {
            ResultSet resultSet = insertClient(client);
            Long clientId = resultSet.getLong("id");
            PreparedStatement preparedStatement2 = connection
                    .prepareStatement("INSERT INTO account (client_id, bank_id, currency, number) VALUES (?, ?, ?, ?)");
            preparedStatement2.setLong(1, clientId);
            preparedStatement2.setLong(2, account.getBankId());
            preparedStatement2.setString(3, account.getCurrency());
            preparedStatement2.setString(4, account.getNumber());
            preparedStatement2.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    private ResultSet insertClient(Client client) throws SQLException {
        PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO client (name, type) VALUES (?, ?)");
        preparedStatement.setString(1, client.getName());
        preparedStatement.setString(2, String.valueOf(client.getType()));
        preparedStatement.executeUpdate();
        PreparedStatement preparedStatement1 = connection
                .prepareStatement("SELECT * FROM client WHERE name = ?");
        preparedStatement1.setString(1, client.getName());
        ResultSet resultSet = preparedStatement1.executeQuery();
        resultSet.next();
        return resultSet;
    }


    private Optional<Client> getClient(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            Client client = new Client();
            client.setId(resultSet.getLong("id"));
            client.setName(resultSet.getString("name"));
            client.setType(ClientType.valueOf(resultSet.getString("type")));
            return Optional.of(client);
        }
        return Optional.empty();
    }
}
