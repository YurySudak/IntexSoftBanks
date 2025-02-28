package sudak.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    private Long id;
    private ClientType type;
    private String name;

    public Client(ClientType type, String name) {
        this.type = type;
        this.name = name;
    }
}
