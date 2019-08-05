package br.com.battlebits.commons.bukkit.api.input.anvil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import br.com.battlebits.commons.bukkit.api.input.InputHandler;
import br.com.battlebits.commons.bukkit.BukkitMain;
import br.com.battlebits.commons.bukkit.util.reflection.Reflection;

public class AnvilInputGui {
	private UUID playerUUID;
	private Inventory anvilInventory;
	private InputHandler inputHandler;
	private ItemStack searchItem;
	private boolean wasHandled;

	public AnvilInputGui(Player p, ItemStack item, InputHandler handler) {
		this.inputHandler = handler;
		this.playerUUID = p.getUniqueId();
		this.searchItem = item;
		this.wasHandled = false;
		open();
	}

	public void open() {
		try {
			Method getHandle = MinecraftReflection.getCraftPlayerClass().getMethod("getHandle");
			Object entityPlayer = getHandle.invoke(getPlayer());
			WrappedContainerAnvil container = new WrappedContainerAnvil(getPlayer());
			anvilInventory = container.getBukkitView().getTopInventory();
			anvilInventory.setItem(0, searchItem);
			int containerId = (int) Reflection.getMethod(entityPlayer.getClass(), "nextContainerCounter")
					.invoke(entityPlayer);
			PacketContainer packet = new PacketContainer(PacketType.Play.Server.OPEN_WINDOW);
			packet.getIntegers().write(0, containerId);
			packet.getIntegers().write(1, 0);
			packet.getStrings().write(0, "minecraft:anvil");
			packet.getChatComponents().write(0, WrappedChatComponent.fromText("Repairing"));
			BukkitMain.getInstance().getProtocolManager().sendServerPacket(getPlayer(), packet);

			Field f = Reflection.getField(entityPlayer.getClass(), "activeContainer");

			f.set(entityPlayer, container.getHandle());

			Object o = f.get(entityPlayer);

			Reflection.getField(o.getClass(), "windowId").set(o, containerId);
			Reflection.getMethod(o.getClass(), "addSlotListener", entityPlayer.getClass()).invoke(o, entityPlayer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void destroyThisInventory() {
		playerUUID = null;
		anvilInventory = null;
		inputHandler = null;
	}

	public UUID getPlayerUUID() {
		return playerUUID;
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(playerUUID);
	}

	public Inventory getAnvilInventory() {
		return anvilInventory;
	}

	public InputHandler getInputHandler() {
		return inputHandler;
	}

	public void setHandled(boolean b) {
		this.wasHandled = b;
	}

	public boolean wasHandled() {
		return wasHandled;
	}
}
