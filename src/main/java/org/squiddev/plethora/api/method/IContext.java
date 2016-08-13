package org.squiddev.plethora.api.method;

import dan200.computercraft.api.lua.ILuaObject;
import org.squiddev.plethora.api.reference.IReference;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

/**
 * This holds the context for a method.
 *
 * This tracks the current object and all parent/associated objects
 */
public interface IContext<T> {
	/**
	 * Get the target for this context.
	 *
	 * @return The target for this context.
	 */
	@Nonnull
	T getTarget();

	/**
	 * Get surrounding context for an object.
	 * This is generally "parent" objects: an inventory might have a tile entity in its context.
	 * This does not include the target.
	 *
	 * @param klass The type of the klass to get.
	 * @return The context object or {@code null} if it doesn't exist.
	 * @see #hasContext(Class)
	 */
	<V> V getContext(@Nonnull Class<V> klass);

	/**
	 * Check if a context exists.
	 * This is generally "parent" objects: an inventory might have a tile entity in its context.
	 * This does not include the target.
	 *
	 * @param klass The type of the klass to get.
	 * @return If this context exists. It is more performant to check if {@link #getContext(Class)} returns null.
	 * @see #getContext(Class)
	 */
	<V> boolean hasContext(@Nonnull Class<V> klass);

	/**
	 * Make a child context
	 *
	 * @param target  The child's target
	 * @param context Additional context items
	 * @return The child context
	 */
	@Nonnull
	<U> IUnbakedContext<U> makeChild(@Nonnull IReference<U> target, @Nonnull IReference<?>... context);

	/**
	 * Make a child context
	 *
	 * @param target  The child's target
	 * @param context Additional context items
	 * @return The child context
	 */
	@Nonnull
	<U> IContext<U> makeBakedChild(@Nonnull U target, @Nonnull Object... context);

	/**
	 * Include additional properties in this context
	 *
	 * @param context The additional context items
	 * @return The new context
	 */
	@Nonnull
	IUnbakedContext<T> withContext(@Nonnull IReference<?>... context);

	/**
	 * Get a lua object from this context
	 *
	 * @return The built Lua object
	 */
	@Nonnull
	ILuaObject getObject();

	/**
	 * Get the cost handler associated with this object.
	 *
	 * @return The cost handler
	 */
	@Nonnull
	ICostHandler getCostHandler();

	/**
	 * @param key The lookup for transfer location
	 * @return The valid transfer location or {@code null} if none exists.
	 */
	@Nullable
	Object getTransferLocation(@Nonnull String key);

	/**
	 * Get all primary transfer locations
	 *
	 * @return All valid locations
	 */
	@Nonnull
	Set<String> getTransferLocations();
}