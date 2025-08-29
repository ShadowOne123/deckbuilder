package io.github.ShadowOne123.Events;

public class ListenerWithPriority<T extends GameEvent> implements Comparable<ListenerWithPriority<T>> {

    final GameEventListener<T> listener;

    final int priority;

    public ListenerWithPriority(GameEventListener<T> listener, int priority) {

        this.listener = listener;

        this.priority = priority;

    }

    @Override

    public int compareTo(ListenerWithPriority<T> other) {

        // Lower priority value means higher precedence (executed first)

        return Integer.compare(this.priority, other.priority);

    }

}
