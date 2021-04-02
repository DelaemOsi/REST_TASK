package by.fpmi.rest.task.sockets.data;

import by.fpmi.rest.task.entities.Contact;
import by.fpmi.rest.task.sockets.RequestType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ClientRequest {

    private String address;
    private RequestType requestType;
    private Contact body;
}
