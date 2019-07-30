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
            if (CommandClass.class.isAssignableFrom(commandClass)) {
                try {
                    CommandClass commands = (CommandClass) commandClass.newInstance();
                    framework.registerCommands(commands);
                } catch (Exception e) {
                    e.printStackTrace();
                    Commons.getLogger()
                            .warning("Erro ao carregar comandos da classe " + commandClass.getSimpleName() + "!");
                }
                i++;
            }
        }
        return i;
    }

    public int loadCommandsFromPackage(File jarFile, String packageName) {
        int i = 0;
        for (Class<?> commandClass : ClassGetter.getClassesForPackageByFile(jarFile, packageName)) {
            if (CommandClass.class.isAssignableFrom(commandClass)) {
                try {
                    CommandClass commands = (CommandClass) commandClass.newInstance();
                    framework.registerCommands(commands);
                } catch (Exception e) {
                    e.printStackTrace();
                    Commons.getLogger()
                            .warning("Erro ao carregar comandos da classe " + commandClass.getSimpleName() + "!");
                }
                i++;
            }
        }
        return i;
    }
}
