package net.jamesandrew.commons.database.sql;

public class SQLResponse<T> {

    private T response;

    public SQLResponse(){}

    public SQLResponse(T response) {
        setResponse(response);
    }

    public T getResponse() {
        return response;
    }

    public boolean hasResponse() {
        return response != null;
    }

    public void setResponse(T response) {
        this.response = response;
    }

}
