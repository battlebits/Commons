Compilando
=========

Para compilar o Commons você deve ter o [Java Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html) com Java 8 ou mais novo.

O processo de build utiliza Gradle, que você não precisa baixar. Commons é um projeto multi-modulos com dois modulos:

* `commons-core` possui toda a API 
* `commons-bukkit` é um Bukkit plugin

## Para compilar...

### No Windows

1. Shift + botao direito na pasta com os arquivos do Commons e clique "Abrir terminal aqui".
2. `gradlew build`

### No Linux

1. No seu terminal, navegue até a pasta dos arquivos do Commons (`cd /pasta/dos/arquivos/commons`)
2. `./gradlew build`

## E então você vai encontrar...

* A API principal do Commons em **worldedit-core/build/libs**
* Commons para Bukkit em **worldedit-bukkit/build/libs**

## Outros comandos uteis

* `gradlew idea` vai gerar um modulo de [IntelliJ IDEA](http://www.jetbrains.com/idea/) para cada pasta.
* `gradlew eclipse` vai gerar um projeto de [Eclipse](https://www.eclipse.org/downloads/) para cada pasta.
