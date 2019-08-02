Contribuindo
============

Obrigaod pelo seu interesse em ajudar nosso projeto Battlebits! Nós agradecemos seu
esforço, mas para ter certeza que a inclusão do seu patch é um processo suave, nós
pedimos que você tome nota das seguintes recomendações.

* **Siga a [convenção de códigos da Oracle](http://www.oracle.com/technetwork/java/codeconv-138413.html).**
  Utilizamos ela para evitar que o código se torne um verdadeiro frankenstein, se não tiver dessa forma
  o processo de aprovação é atrasado significantemente.
* **Use Java 8 para source e compilação.** Tenha certeza de marcar os metodos com
  ` @Override` que sobrepõe metodos de superclasses, ou que implementam
  metodos de interfaces interfaces.
* **Use apenas espaços para identação.** Nossa identação possuem 4 espaços e tabs
  são inaceitáveis.
* **Não use tags nas classes com @author.** Algumas classes antigas podem ter mas
  iremos retirá-las.
* **Tenha certeza que o código é eficiente.** Uma maneira de conseguir isso é gastando
  cerca de dez minutos para pensar o que seu codigo está fazendo e onde ele parece meio
  ineficiente. Se você teve que copiar a mesma parte de código em vários lugares,
  isto é ruim.
* **Mantenha os commits abaixo de 70 caracteres.** Para mais detalhes, coloque duas
  novas linhas depois do título e escreva!
* **Teste seu codigo.** Não estamos interessados em códigos quebrados.


Checklist
---------

Preparado para enviar? Use o checklist abaixo:

1. Possui todos os tabs trocados por quatro espaços? As identações são possuem quatro espaços?
2. Escrevi os Javadocs corretos para meus métodos públicos? Os campos @param e
   @return estão preenchidost?
3. Eu usei `git rebase` no meu pull request para o ultimo commit da branch que desejo enviar?
4. Eu combinei meus commits em uma pequena quantidade rasoável (se não um)
   de commits usando `git rebase`?
5. Eu fiz meu pull request muito longo? Pull requests devem introduzir
   pequenas mudanças por vez. Mudanças maiores devem ser discutidas em
   time antes de começar o trabalho.
6. Minhas mensagens de commit são claras?

Você deve conhecer [`git rebase`](http://learn.github.com/p/rebasing.html).
Ele permite você modificar mensagens de commit existentes, e combinar, quebrar em partes
e ajustar mudanças passadas.

Exemplo
-------

Isto é **BOM:**

    if (var.func(param1, param2)) {
        // do things
    }

Isto é **EXTREMAMENTE RUIM:**

    if(var.func( param1, param2 ))
    {
        // do things
    }
