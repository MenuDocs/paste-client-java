package org.menudocs.paste;

class LanguageImpl implements Language {
    private final String name;
    private final String id;

    LanguageImpl(String name, String id) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
