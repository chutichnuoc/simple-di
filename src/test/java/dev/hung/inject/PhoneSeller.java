package dev.hung.inject;

import dev.hung.inject.annotation.Inject;
import dev.hung.inject.annotation.Name;

public class PhoneSeller {

    @Inject
    @Name("iphone")
    Phone phone1;

    @Inject
    @Name("samsung")
    Phone phone2;

    @Inject
    @Name("noname")
    Phone phone3;

    @Inject
    Phone phone4;
}
