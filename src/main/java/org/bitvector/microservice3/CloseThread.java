package org.bitvector.microservice3;

public class CloseThread extends Thread {
    private IClosable obj;

    CloseThread(IClosable obj) {
        this.obj = obj;
    }

    @Override
    public void run() {
        obj.close();
    }
}
