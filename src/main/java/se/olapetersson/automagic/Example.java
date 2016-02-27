package se.olapetersson.automagic;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

@Stateless
public class Example {

    @Inject
    Event<UnaryOperator> unaryEvent;

    @Inject
    Event<Supplier> supplierEvent;

    @Inject
    Event<Supplier<String>> stringSupplierEvent;

    /*
        Why are the first three observers (the first two events) working but not the last
        typed one?
     */
    public void fireEvent() {
        UnaryOperator unaryOperator = (x) -> x + " consumed this unary";
        unaryEvent.fire(unaryOperator);

        Supplier supplier = () -> "Yes I am observing ALL";
        supplierEvent.fire(supplier);

        Supplier<String> stringSupplier = () -> "Yes I am observing STRINGS";
        stringSupplierEvent.fire(stringSupplier);

    }

    public void observeEvent(@Observes UnaryOperator unaryOperator) {
        System.out.println(unaryOperator.apply("observerEvent"));
    }

    public void secondObserver(@Observes UnaryOperator unaryOperator) {
        System.out.println(unaryOperator.apply("secondObserver"));
    }

    public void consumeSupplier(@Observes Supplier supplier) {
        System.out.println(supplier.get() + " IN GENERAL");
    }

    public void consumeStringSupplier(@Observes Supplier<String> supplier) {
        System.out.println(supplier.get());
    }
}
