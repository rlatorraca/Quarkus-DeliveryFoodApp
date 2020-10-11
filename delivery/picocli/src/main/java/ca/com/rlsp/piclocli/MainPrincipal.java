package ca.com.rlsp.piclocli;

import io.quarkus.picocli.runtime.annotations.TopCommand;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
/**
 * PicoCLI => serve pra fazer uma interface mais intuitiva para linhas de comando com QUARKUS
 * @author rlatorraca
 *
 */
@TopCommand // Para 2+ comandos
@Command(mixinStandardHelpOptions = true, subcommands = { HelloMethod.class, ByeMethod.class })
public class MainPrincipal {

}

@Command(name = "hi", description = "Printing user name ... ")
class HelloMethod implements Runnable {

    @Option(names = { "-n", "--name" }, description = "User Full Name (HI) ...", defaultValue = "rlatorraca")
    String nameString;

    @Override
    public void run() {
        System.out.println("HelloMethod.run() " + nameString);
    }
}

@Command(name = "bye", description = "Printing Bye and user name ... ")
class ByeMethod implements Runnable {

    @Option(names = { "-n", "--name" }, description = "User Full Name (BYE)... ", required = true)
    String nameString;

    @Override
    public void run() {
        System.out.println("ByeMethod.run() " + nameString);
    }
}
