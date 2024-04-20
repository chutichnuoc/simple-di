package dev.hung.inject;

import dev.hung.inject.annotation.Inject;
import dev.hung.inject.annotation.Name;
import dev.hung.inject.annotation.Scope;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static dev.hung.inject.annotation.Name.DEFAULT;
import static dev.hung.inject.annotation.Scope.PROTOTYPE;
import static dev.hung.inject.annotation.Scope.SINGLETON;

public class Injector {
    private static final Injector instance = new Injector();

    private final Map<Key, Value<?>> mapping = new HashMap<>();

    private Injector() {}

    public static Injector instance() {
        return instance;
    }

    public <K, V extends K> void bind(Class<K> src) {
        String scope = getScope(src);
        bind(src, DEFAULT, src, scope);
    }

    public <K, V extends K> void bind(Class<K> src, Class<V> dst) {
        String scope = getScope(src);
        bind(src, DEFAULT, dst, scope);
    }

    public <K, V extends K> void bind(Class<K> src, String name, Class<V> dst) {
        String scope = getScope(src);
        bind(src, name, dst, scope);
    }

    public <K, V extends K> void bind(Class<K> src, String name, Class<V> dst, String scope) {
        Key key = Key.create(src, name);
        Value<V> value = new Value<>(dst, scope);
        mapping.put(key, value);
    }

    public void init() {
        for (var entry : mapping.entrySet()) {
            if (SINGLETON.equals(entry.getValue().getScope())) {
                get(entry.getKey());
            }
        }
    }

    public void clear() {
        mapping.clear();
    }

    public <T> T get(Key key) {
        Value<T> value = (Value<T>) instance.mapping.get(key);
        T object;
        if (value == null) { // just-in-time injection
            object = (T) createObject(key.getClazz());
        } else {
            if (SINGLETON.equals(value.getScope())) {
                if (value.getInstance() == null) {
                    object = createObject(value.getClazz());
                    value.setInstance(object);
                }
                object = value.getInstance();
            } else {
                object = createObject(value.getClazz());
            }
        }
        return object;
    }

    private <T> T createObject(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T object = constructor.newInstance();
            Class<?> current = clazz;
            while (current.getSuperclass() != null) {
                Field[] fields = current.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Inject.class)) {
                        Key key = Key.create(field.getType(), getName(field));
                        Object fieldValue = get(key);
                        field.setAccessible(true);
                        field.set(object, fieldValue);
                    }
                }
                current = current.getSuperclass();
            }
            return object;
        } catch (Exception ex) {
            System.out.println("Error instantiating " + clazz.getName());
            System.out.println(ex.getMessage());
            System.out.println(Arrays.toString(ex.getStackTrace()));
            return null;
        }
    }

    private String getScope(Class<?> clazz) {
        String scope = PROTOTYPE;
        if (clazz.isAnnotationPresent(Scope.class)) {
            scope = clazz.getAnnotation(Scope.class).value();
        }
        return scope;
    }

    private String getName(Field field) {
        if (field.isAnnotationPresent(Name.class)) {
            return field.getAnnotation(Name.class).value();
        }
        return "default";
    }
}
