package sudak.service;

import lombok.AllArgsConstructor;
import sudak.model.Account;
import sudak.model.Client;
import sudak.repository.BankRepository;
import sudak.repository.ClientRepository;

import java.util.List;

@AllArgsConstructor
public class ClientService implements Service {

    private final ClientRepository clientRepository;
    private final BankRepository bankRepository;

    public Client create(Client client) {
        validateClient(client);
        validateNewClient(client);
        return clientRepository.save(client);
    }

    public void update(Client client) {
        validateClient(client);
        clientRepository.update(client);
    }

    public Client getById(Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    public void delete(Long id) {
        clientRepository.delete(id);
    }

    public void createNewClient(Client client, Account account) {
        validateBankId(account.getBankId());
        clientRepository.createNewClient(client, account);
    }

    private void validateBankId(Long bankId) {
        bankRepository.findById(bankId).orElseThrow(() -> new IllegalArgumentException("Bank with id " + bankId + " not found"));
    }


    private void validateClient(Client client) {
        if (client.getName() == null || client.getName().isEmpty()) {
            throw new IllegalArgumentException("Client name cannot be null or empty");
        }
        if (client.getType() == null) {
            throw new IllegalArgumentException("Client type cannot be null");
        }
    }

    private void validateNewClient(Client client) {
        if (clientRepository.findByName(client.getName()).isPresent()) {
            throw new IllegalArgumentException("Client with name " + client.getName() + " already exists");
        }
    }
}
