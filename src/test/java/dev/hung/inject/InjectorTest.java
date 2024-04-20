package dev.hung.inject;

import static dev.hung.inject.annotation.Name.DEFAULT;
import static dev.hung.inject.annotation.Scope.SINGLETON;

public class InjectorTest {
    public static void main(String[] args) {
        testBinding();
        Injector.instance().clear();
        System.out.println("-------------------------------------");
        testBindingWithName();
        Injector.instance().clear();
        System.out.println("--------------------------------------");
        testScope();
        Injector.instance().clear();
    }

    static void testBinding() {
        PhoneSeller service = Injector.instance().get(Key.create(PhoneSeller.class));

        System.out.println("phone1 = " + service.phone1.getClass());
        System.out.println("phone2 = " + service.phone2.getClass());
        System.out.println("phone3 = " + service.phone3.getClass());
        System.out.println("phone4 = " + service.phone4.getClass());
    }

    static void testBindingWithName() {
        Injector.instance().bind(Phone.class, "iphone", IPhone.class);
        Injector.instance().bind(Phone.class, "samsung", Samsung.class);
        Injector.instance().bind(Phone.class, Pixel.class);

        PhoneSeller service = Injector.instance().get(Key.create(PhoneSeller.class));

        System.out.println("phone1 = " + service.phone1.getClass());
        System.out.println("phone2 = " + service.phone2.getClass());
        System.out.println("phone3 = " + service.phone3.getClass());
        System.out.println("phone4 = " + service.phone4.getClass());
    }

    static void testScope() {
        PhoneSeller seller1 = Injector.instance().get(Key.create(PhoneSeller.class));
        PhoneSeller seller2 = Injector.instance().get(Key.create(PhoneSeller.class));
        System.out.println("seller1 == seller2 is " + (seller1 == seller2));

        Injector.instance().bind(PhoneSeller.class, DEFAULT, PhoneSeller.class, SINGLETON);

        PhoneSeller seller3 = Injector.instance().get(Key.create(PhoneSeller.class));
        PhoneSeller seller4 = Injector.instance().get(Key.create(PhoneSeller.class));
        System.out.println("seller3 == seller4 is " + (seller3 == seller4));
    }
}