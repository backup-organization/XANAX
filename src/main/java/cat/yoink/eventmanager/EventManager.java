package cat.yoink.eventmanager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yoink
 * @since November 23rd 2020
 * @version 1
 */
public final class EventManager
{
    private final Map<Object, List<Method>> subscribers = new HashMap<>();

    public <E> E dispatch(final E event)
    {
        subscribers.forEach((instance, methods) -> methods.forEach(method -> {
            if (!method.getParameterTypes()[0].equals(event.getClass())) return;
            try { method.invoke(instance, event); }
            catch (final IllegalAccessException | InvocationTargetException e) { e.printStackTrace(); }
        }));

        return event;
    }

    public void addSubscriber(final Object subscriber)
    {
        final List<Method> methods = Arrays.stream(subscriber.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Listener.class))
                .sorted(Comparator.comparing(m -> -m.getAnnotation(Listener.class).value()))
                .collect(Collectors.toList());

        subscribers.put(subscriber, methods);
    }

    public void removeSubscriber(final Object subscriber)
    {
        subscribers.remove(subscriber);
    }
}
