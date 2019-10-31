package br.com.battlebits.commons.bukkit.api.chat;

import lombok.Getter;
import lombok.Setter;

public class ChatAPI {

	@Getter
	@Setter
	private static ChatState chatState = ChatState.ENABLED;

	public enum ChatState {
		ENABLED, STAFF, CREATOR, DISABLED;
	}
}
