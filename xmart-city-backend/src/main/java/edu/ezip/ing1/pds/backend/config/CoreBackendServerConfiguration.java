package edu.ezip.ing1.pds.backend.config;

public class CoreBackendServerConfiguration {
    private int listenPort;
    public CoreBackendServerConfiguration() {
    }

    public int getListenPort() {
        return listenPort;
    }

    public void setListenPort(int listenPort) {
        this.listenPort = listenPort;
    }

    @Override
    public String toString() {
        return "CoreBackendServerConfiguration{" +
                "listenPort=" + listenPort +
                '}';
    }
}
