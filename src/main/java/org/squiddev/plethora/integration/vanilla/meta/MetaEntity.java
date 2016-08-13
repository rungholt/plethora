package org.squiddev.plethora.integration.vanilla.meta;

import com.google.common.collect.Maps;
import net.minecraft.entity.Entity;
import org.squiddev.plethora.api.meta.BasicMetaProvider;
import org.squiddev.plethora.api.meta.IMetaProvider;
import org.squiddev.plethora.utils.Helpers;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

@IMetaProvider.Inject(Entity.class)
public class MetaEntity extends BasicMetaProvider<Entity> {
	@Nonnull
	@Override
	public Map<Object, Object> getMeta(@Nonnull Entity object) {
		return getBasicProperties(object);
	}

	public static HashMap<Object, Object> getBasicProperties(@Nonnull Entity entity) {
		HashMap<Object, Object> result = Maps.newHashMap();
		result.put("id", entity.getUniqueID().toString());

		result.put("name", Helpers.getName(entity));
		result.put("displayName", entity.getName());

		result.put("motionX", entity.motionX);
		result.put("motionY", entity.motionY);
		result.put("motionZ", entity.motionZ);

		result.put("pitch", Math.toRadians(entity.rotationPitch));
		result.put("yaw", Math.toRadians(entity.rotationYaw));

		return result;
	}

}