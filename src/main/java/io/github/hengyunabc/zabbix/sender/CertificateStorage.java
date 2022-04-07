package io.github.hengyunabc.zabbix.sender;

public class CertificateStorage {

    private final String jksStorage;
    private final String storagePassword;

    public CertificateStorage(String jksStorage, String storagePassword) {
        this.jksStorage = jksStorage;
        this.storagePassword = storagePassword;
    }

    public String getJksStorage() {
        return jksStorage;
    }

    public String getStoragePassword() {
        return storagePassword;
    }
}
