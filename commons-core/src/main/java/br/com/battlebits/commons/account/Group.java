package br.com.battlebits.commons.account;

import java.util.Optional;

public enum Group {
     DEFAULT,
     CREATOR,
     DONATOR,
     DONATORPLUS,
     BUILDER,
     DEVELOPER,
     ADMIN;

     public static Optional<Group> byId(int id) {
          for (Group group : values()) {
               if(group.ordinal() == id) {
                    return Optional.of(group);
               }
          }
          return Optional.empty();
     }

}
