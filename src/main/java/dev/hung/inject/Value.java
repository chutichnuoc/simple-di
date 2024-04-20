package dev.hung.inject;

public class Value<T> {
    private final Class<T> clazz;
    private T instance;
    private String scope;

    public Value(Class<T> clazz, String scope) {
        this.clazz = clazz;
        this.scope = scope;
    }

    public Value(Class<T> clazz, T instance) {
        this.clazz = clazz;
        this.instance = instance;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public T getInstance() {
        return instance;
    }

    public String getScope() {
        return scope;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }
}
