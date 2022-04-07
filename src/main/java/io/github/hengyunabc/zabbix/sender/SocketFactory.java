package io.github.hengyunabc.zabbix.sender;

import javax.net.ssl.*;
import java.io.*;
import java.net.Socket;
import java.security.*;

public class SocketFactory {

    public static Socket createSocket() {
        return new Socket();
    }

    public static Socket createSSLSocket(CertificateStorage certificateStorage) throws IOException, GeneralSecurityException {
        if (certificateStorage == null) {
            throw new GeneralSecurityException("CertificateStorage is null");
        }
        String jksStorage = certificateStorage.getJksStorage();
        String password = certificateStorage.getStoragePassword();

        FileInputStream stream = null;
        Socket socket;
        try {
            stream = new FileInputStream(jksStorage);
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(stream, password.toCharArray());

            KeyManagerFactory keyManagerFactory =
                    KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password.toCharArray());
            KeyManager[] keyManagers = keyManagerFactory.getKeyManagers();

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, trustManagers, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            socket = sslSocketFactory.createSocket();

        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return socket;
    }
}
