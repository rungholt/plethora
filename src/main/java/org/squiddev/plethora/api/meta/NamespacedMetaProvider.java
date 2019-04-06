package org.squiddev.plethora.api.meta;

import org.squiddev.plethora.api.method.IPartialContext;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * A provider that wraps the data in a namespace.
 */
public class NamespacedMetaProvider<T> extends BaseMetaProvider<T> {
	private final String namespace;
	private final IMetaProvider<T> delegate;

	/**
	 * Create a new namespaced metadata provider
	 *
	 * @param namespace The namespace to insert into
	 * @param delegate  The provider to delegate to
	 */
	public NamespacedMetaProvider(String namespace, IMetaProvider<T> delegate) {
		super(delegate.getPriority(), delegate.getDescription());
		Objects.requireNonNull(namespace, "namespace cannot be null");
		Objects.requireNonNull(delegate, "delegate cannot be null");

		this.namespace = namespace;
		this.delegate = delegate;
	}

	@Nonnull
	@Override
	public Map<String, ?> getMeta(@Nonnull IPartialContext<T> context) {
		Map<String, ?> data = delegate.getMeta(context);
		return data.isEmpty() ? Collections.emptyMap() : Collections.singletonMap(namespace, data);
	}

	@Nonnull
	@Override
	public T getExample() {
		return delegate.getExample();
	}

	public IMetaProvider<T> getDelegate() {
		return delegate;
	}
}
