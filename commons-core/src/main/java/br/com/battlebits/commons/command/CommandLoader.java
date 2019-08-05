package br.com.battlebits.commons.command;

import br.com.battlebits.commons.Commons;
import br.com.battlebits.commons.util.ClassGetter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.File;

@RequiredArgsConstructor
public class CommandLoader {

    @NonNull
    private CommandFramework framework;

    public int loadCommandsFromPackage(String packageName) {
        int i = 0;
        for (Class<?> commandClass : ClassGetter.getClassesForPackage(framework.getJarClass(), packageName)) {
            if (registerCommands(commandClass)) {
                i++;
            }
        }
        return i;
    }

    public int loadCommandsFromPackage(File jarFile, String packageName) {
        int i = 0;
        for (Class<?> commandClass : ClassGetter.getClassesForPackageByFile(jarFile, packageName)) {
            System.out.println("teste");
            if (registerCommands(commandClass)) {
                i++;
            }
        }
        return i;
    }

    private boolean registerCommands(Class<?> commandClass) {
        if (CommandClass.class.isAssignableFrom(commandClass)) {
            try {
                CommandClass commands = (CommandClass) commandClass.getDeclaredConstructor().newInstance();
                framework.registerCommands(commands);
                Commons.getLogger().info("Class " + commandClass.getSimpleName() + ".class registered");
            } catch (Exception e) {
                e.printStackTrace();
                Commons.getLogger()
                        .warning("Erro ao carregar comandos da classe " + commandClass.getSimpleName() + "!");
            }
            return true;
        }
        return false;
    }
}
