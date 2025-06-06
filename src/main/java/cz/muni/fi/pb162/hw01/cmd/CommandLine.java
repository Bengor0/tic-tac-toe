package cz.muni.fi.pb162.hw01.cmd;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

/**
 * Command Line Interface
 */
public final class CommandLine {

    private final JCommander commander;

    /**
     * Constructs new CLI instance
     *
     * @param application application object to be populated with arguments
     */
    public CommandLine(Object application) {
        commander = JCommander.newBuilder()
                .addObject(application)
                .build();

        commander.setProgramName(application.getClass().getSimpleName());
    }

    /**
     * Parses command line arguments (terminates on error)
     *
     * @param args command line arguments of the application
     */
    public void parseArguments(String[] args) {
        try {
            commander.parse(args);
        } catch (ParameterException ex) {
            System.err.println("Error: " + ex.getMessage());
            showUsage(1);
        }
    }

    /**
     * Shows usage of the application and terminates
     */
    public void showUsage() {
        showUsage(0);
    }

    private void showUsage(int status) {
        commander.usage();
        System.exit(status);
    }
}
