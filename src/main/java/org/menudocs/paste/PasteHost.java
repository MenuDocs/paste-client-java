package org.menudocs.paste;

public enum PasteHost {
    MENUDOCS("https://paste.menudocs.org"),
    LOCAL("http://localhost:7000");

    private final String url;
    PasteHost(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
