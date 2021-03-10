package by.fpmi.rest.task.sockets.data;

import by.fpmi.rest.task.entities.Contact;
import by.fpmi.rest.task.sockets.RequestType;


public class ClientRequest {

    private String address;
    private RequestType requestType;
    private Contact body;

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

    public Contact getBody() {
        return body;
    }

    public void setBody(Contact body) {
        this.body = body;
    }
}
