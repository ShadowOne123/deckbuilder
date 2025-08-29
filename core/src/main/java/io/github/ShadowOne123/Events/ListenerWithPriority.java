package io.github.ShadowOne123.Events;

public class ListenerWithPriority implements Comparable<ListenerWithPriority> {

    final GameEventListener<?> listener;
    final int priority;

    public ListenerWithPriority(GameEventListener<?> listener, int priority) {
        this.listener = listener;
        this.priority = priority;
    }

    @Override
    public int compareTo(ListenerWithPriority other) {
        // Lower priority value means higher precedence (executed first)
        return Integer.compare(this.priority, other.priority);
    }

}
