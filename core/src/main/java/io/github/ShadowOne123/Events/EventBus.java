package io.github.ShadowOne123.Events;
import java.util.*;

public class EventBus {
    private Map<Class<? extends GameEvent>, List<ListenerWithPriority>> listeners = new HashMap<>();

    public <T extends GameEvent> void register(Class<T> eventType, GameEventListener<T> listener, int priority) {

        List<ListenerWithPriority> list = listeners.computeIfAbsent(eventType, k -> new ArrayList<>());

        list.add(new ListenerWithPriority(listener, priority));

        Collections.sort(list); // Now this call is type-safe and works

    }

    public <T extends GameEvent> void unregister(Class<T> eventType, GameEventListener<T> listener) {
        List<ListenerWithPriority> list = listeners.get(eventType);
        if (list != null) list.remove(listener);
    }

    @SuppressWarnings("unchecked")

    public <T extends GameEvent> void emit(T event) {

        List<ListenerWithPriority> list = listeners.get(event.getClass());

        if (list != null) {

            for (ListenerWithPriority wrapper : new ArrayList<>(list)) {

                ((GameEventListener<T>) wrapper.listener).onEvent(event);

            }

        }

    }

}
