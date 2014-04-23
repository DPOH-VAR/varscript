package ru.dpohvar.varscript.event;

import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredListener;

/**
 * Contains all information about registered event
 */
public class EventRegisterResult {
    public final HandlerList handlerList;
    public final EventHandler<?> handler;
    public final Class<?> clazz;
    public final EventPriority priority;
    public final boolean ignoreCancelled;
    public final RegisteredListener registeredListener;

    public EventRegisterResult(
            HandlerList handlerList,
            Class<?> clazz,
            EventPriority priority,
            boolean ignoreCancelled,
            EventHandler<?> handler,
            RegisteredListener registeredListener
    ) {
        this.handlerList = handlerList;
        this.handler = handler;
        this.clazz = clazz;
        this.priority = priority;
        this.ignoreCancelled = ignoreCancelled;
        this.registeredListener = registeredListener;
    }
}