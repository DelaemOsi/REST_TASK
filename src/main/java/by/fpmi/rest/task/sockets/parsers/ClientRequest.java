package by.fpmi.rest.task.sockets.parsers;

import by.fpmi.rest.task.entities.Contact;
import by.fpmi.rest.task.sockets.RequestType;

import java.util.Optional;

public class ClientRequest {
    private String address;
    private RequestType requestType;
    private Optional<Contact>body;

    public ClientRequest(){

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public Optional<Contact> getBody() {
        return body;
    }

    public void setBody(Optional<Contact> body) {
        this.body = body;
    }
}
