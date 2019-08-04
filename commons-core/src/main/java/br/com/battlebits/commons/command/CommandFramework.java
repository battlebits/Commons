package br.com.battlebits.commons.command;

import br.com.battlebits.commons.account.Group;
import br.com.battlebits.commons.translate.TranslateTag;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface CommandFramework {

    Class<?> getJarClass();

    void registerCommands(CommandClass commandClass);

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Command {

        String name();

        Group groupToUse() default Group.DEFAULT;

        String permission() default "";

        String noPermMessageId() default TranslateTag.COMMAND_NO_PERMISSION;

        String[] aliases() default {};

        String description() default "";

        String usage() default "";

        boolean runAsync() default false;
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Completer {

        /**
         * The command that this completer completes. If it is a sub command
         * then its values would be separated by periods. ie. a command that
         * would be a subcommand of test would be 'test.subcommandname'
         *
         * @return String
         */
        String name();

        /**
         * A list of alternate names that the completer is executed under. See
         * name() for details on how names work
         *
         * @return String
         */
        String[] aliases() default {};

    }

}
