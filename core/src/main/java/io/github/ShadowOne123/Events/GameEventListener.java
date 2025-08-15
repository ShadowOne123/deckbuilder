package io.github.ShadowOne123.Events;

public interface GameEventListener<T extends GameEvent> {
    void onEvent(T event);
}
