package br.com.battlebits.commons.bukkit.api.input.anvil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryView;

import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.ConstructorAccessor;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.AbstractWrapper;
import com.comphenix.protocol.wrappers.BlockPosition;

import br.com.battlebits.commons.bukkit.util.reflection.Reflection;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class WrappedContainerAnvil extends AbstractWrapper {

	private static Class<?> CONTAINER_ANVIL = MinecraftReflection.getMinecraftClass("ContainerAnvil");
	private static final Class<?> PLAYER_INVENTORY = MinecraftReflection.getMinecraftClass("PlayerInventory");
	private static final Class<?> BLOCK_POSITION = MinecraftReflection.getBlockPositionClass();
	private static final Class<?> ENTITY_PLAYER = MinecraftReflection.getEntityPlayerClass();
	private static final Class<?> ENTITY_HUMAN = MinecraftReflection.getEntityHumanClass();
	private static final Class<?> WORLD = MinecraftReflection.getNmsWorldClass();

	static {

		try {
			CtClass origClazz = ClassPool.getDefault().get(CONTAINER_ANVIL.getName());
			CtClass subClass = ClassPool.getDefault()
					.makeClass("br.com.battlebits.commons.api.input.anvil.AnvilNoXPContainer", origClazz);
			CtMethod m = CtNewMethod.make("public boolean a(net.minecraft.server."
					+ MinecraftReflection.getPackageVersion() + ".EntityHuman entityhuman) {return true; }", subClass);
			subClass.addMethod(m);
			CONTAINER_ANVIL = subClass.toClass();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public WrappedContainerAnvil(Object anvilContainer) {
		super(CONTAINER_ANVIL);
		setHandle(anvilContainer);
	}

	public WrappedContainerAnvil(Player player) {
		super(CONTAINER_ANVIL);

		try {
			Method getHandle = MinecraftReflection.getCraftPlayerClass().getMethod("getHandle");
			Object entityPlayer = getHandle.invoke(player);
			Object inventory = Reflection.getField(ENTITY_PLAYER, "inventory").get(entityPlayer);
			Object world = Reflection.getField(ENTITY_PLAYER, "world").get(entityPlayer);
			Object blockPosition = BlockPosition.getConverter().getGeneric(BlockPosition.ORIGIN);
			ConstructorAccessor CREATE = Accessors.getConstructorAccessorOrNull(CONTAINER_ANVIL, PLAYER_INVENTORY,
					WORLD, BLOCK_POSITION, ENTITY_HUMAN);
			Object anvilContainer = CREATE.invoke(inventory, world, blockPosition, entityPlayer);
			setHandle(anvilContainer);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	public InventoryView getBukkitView() {
		try {
			return (InventoryView) getHandle().getClass().getMethod("getBukkitView").invoke(getHandle());
		} catch (Exception e) {
			return null;
		}
	}

}
