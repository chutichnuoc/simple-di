package dev.hung.inject;

import dev.hung.inject.annotation.Scope;

import static dev.hung.inject.annotation.Scope.SINGLETON;

@Scope(SINGLETON)
public class Phone {
    String name;
}
