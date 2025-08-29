package io.github.ShadowOne123.Events;
import java.util.*;

public class EventBus {
    private Map<Class<? extends GameEvent>, List<GameEventListener<? extends GameEvent>>> listeners = new HashMap<>();

    public <T extends GameEvent> void register(Class<T> eventType, GameEventListener<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
    }

    public <T extends GameEvent> void unregister(Class<T> eventType, GameEventListener<T> listener) {
        List<GameEventListener<? extends GameEvent>> list = listeners.get(eventType);
        if (list != null) list.remove(listener);

    }

    public <T extends GameEvent> void emit(T event) {
        List<GameEventListener<? extends GameEvent>> list = listeners.get(event.getClass());
        if (list != null) {
            for (GameEventListener<? extends GameEvent> listener : new ArrayList<>(list)) {
                ((GameEventListener<T>)listener).onEvent(event);

            }
        }
    }

    private void reprioritize(){

    }
}
