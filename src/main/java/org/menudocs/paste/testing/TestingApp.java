package org.menudocs.paste.testing;

import org.menudocs.paste.PasteClient;
import org.menudocs.paste.PasteClientBuilder;
import org.menudocs.paste.PasteHost;

public class TestingApp {
    public static void main(String[] args) {
        PasteClient client = new PasteClientBuilder()
                .setPasteHost(PasteHost.LOCAL)
                .setDefaultExpiry("10m")
                .build();

        String pasteID = client.createPaste("html", "<h1>testing</h1>").execute();

        System.out.println("Paste id: " + pasteID);

        client.getPaste(pasteID).async((paste) -> {
            System.out.println(paste.getPasteUrl());
            System.out.println(paste.getBody());
        });
    }
}
