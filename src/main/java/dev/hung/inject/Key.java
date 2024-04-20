package dev.hung.inject;

import java.util.Objects;

import static dev.hung.inject.annotation.Name.DEFAULT;

public class Key {
    private final Class<?> clazz;
    private final String name;

    private Key(Class<?> clazz, String name) {
        this.clazz = clazz;
        this.name = name;
    }

    public static Key create(Class<?> clazz, String name) {
        return new Key(clazz, name);
    }

    public static Key create(Class<?> clazz) {
        return new Key(clazz, DEFAULT);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Key key = (Key) o;
        return Objects.equals(clazz, key.clazz) && Objects.equals(name, key.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, name);
    }
}
