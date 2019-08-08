package org.menudocs.paste;

class PasteImpl implements Paste {
    private final String id;
    private final String body;
    private final String url;
    private final Language language;

    PasteImpl(String id, String body, String url, Language language) {
        this.id = id;
        this.body = body;
        this.url = url;
        this.language = language;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getBody() {
        return this.body;
    }

    @Override
    public String getPasteUrl() {
        return this.url;
    }

    @Override
    public Language getLanguage() {
        return this.language;
    }
}
