package org.squiddev.plethora.integration.vanilla.method;

import dan200.computercraft.api.lua.LuaException;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.network.NetHandlerPlayServer;
import org.squiddev.plethora.ArgumentHelper;
import org.squiddev.plethora.api.method.IContext;
import org.squiddev.plethora.api.method.IUnbakedContext;
import org.squiddev.plethora.api.method.Method;
import org.squiddev.plethora.api.method.MethodResult;
import org.squiddev.plethora.api.module.IModule;
import org.squiddev.plethora.api.module.TargetedModuleMethod;
import org.squiddev.plethora.api.module.TargetedModuleObjectMethod;
import org.squiddev.plethora.gameplay.modules.PlethoraModules;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;

import static org.squiddev.plethora.ArgumentHelper.getNumber;

/**
 * Various methods for mobs
 */
public final class MethodsKineticEntity {
	/*
		TODO: `walk(x, y, z)` path find to position
		TODO: `shoot(x, y, z)` for skeletons with bows
		TODO: `swing()` Swing hand to dig/attack (requires fake player)
		TODO: `activate()` Right click to activate (requires fake player)
	*/

	@Method(IModule.class)
	public static final class MethodEntityLook extends TargetedModuleMethod<EntityLiving> {
		public MethodEntityLook() {
			super("look", PlethoraModules.KINETIC, EntityLiving.class);
		}

		@Nonnull
		@Override
		public MethodResult apply(@Nonnull final IUnbakedContext<IModule> context, @Nonnull Object[] args) throws LuaException {
			final double yaw = ArgumentHelper.getNumber(args, 0);
			final double pitch = ArgumentHelper.getNumber(args, 1);

			return MethodResult.nextTick(new Callable<MethodResult>() {
				@Override
				public MethodResult call() throws Exception {
					/**
					 * TODO: Support EntityPlayer
					 * @see net.minecraft.entity.player.EntityPlayerMP#playerNetServerHandler
					 * @see NetHandlerPlayServer#setPlayerLocation(double, double, double, float, float)
					 */
					EntityLiving target = context.bake().getContext(EntityLiving.class);
					target.rotationYawHead = target.rotationYaw = (float) (Math.toDegrees(yaw) % 360);
					target.rotationPitch = (float) (Math.toDegrees(pitch) % 360);
					return MethodResult.empty();
				}
			});
		}
	}

	@Method(IModule.class)
	public static final class MethodEntityCreeperExplode extends TargetedModuleObjectMethod<EntityCreeper> {
		public MethodEntityCreeperExplode() {
			super("explode", true, PlethoraModules.KINETIC, EntityCreeper.class);
		}

		@Nullable
		@Override
		public Object[] apply(@Nonnull EntityCreeper target, @Nonnull IContext<IModule> context, @Nonnull Object[] args) {
			target.explode();
			return null;
		}
	}

	@Method(IModule.class)
	public static final class MethodEntityEndermanTeleport extends TargetedModuleObjectMethod<EntityEnderman> {
		public MethodEntityEndermanTeleport() {
			super("teleport", true, PlethoraModules.KINETIC, EntityEnderman.class);
		}

		@Nullable
		@Override
		public Object[] apply(@Nonnull EntityEnderman target, @Nonnull IContext<IModule> context, @Nonnull Object[] args) throws LuaException {
			double x = getNumber(args, 0);
			double y = getNumber(args, 1);
			double z = getNumber(args, 2);

			if (x < -32 || x > 32) throw new LuaException("X coordinate out of bounds (+-32");
			if (y < -32 || y > 32) throw new LuaException("Y coordinate out of bounds (+-32");
			if (z < -32 || z > 32) throw new LuaException("Z coordinate out of bounds (+-32");

			return new Object[]{target.teleportTo(target.posX + x, target.posY + y, target.posZ + z)};
		}
	}
}