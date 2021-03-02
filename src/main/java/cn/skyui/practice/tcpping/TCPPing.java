package cn.skyui.practice.tcpping;

import cn.skyui.practice.tcpping.catcher.Catcher;
import cn.skyui.practice.tcpping.pitcher.Pitcher;
import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TCPPing {

    private static final int MIN_MSG_SIZE = 50;
    private static final int MAX_MSG_SIZE = 3000;
    private static final int DEFAULT_MSG_SIZE = 300;
    private static final int DEFAULT_MPS = 1;

    private static Options options = new Options();
    private static Logger logger = LogManager.getLogger(TCPPing.class.getName());

    @SuppressWarnings("static-access")
    private static CommandLine parseCommandLine(String[] args) throws ParseException {

        // add options
        options.addOption("p", false, "Način rada kao Pitcher");
        options.addOption("c", false, "Način rada kao Catcher");

        Option port = OptionBuilder.withArgName("port").hasArg()
                .withDescription("[Pitcher] TCP socket port koji će se koristiti za connect\n" +
                        "[Catcher] TCP socket port koji će se koristiti za listen")
                .create("port");
        options.addOption(port);

        Option bind = OptionBuilder.withArgName("ip_address").hasArg().withDescription("[Catcher] TCP socket bind adresa na kojoj će biti pokrenut listen")
                .create("bind");
        options.addOption(bind);

        Option mps = OptionBuilder.withArgName("rate").hasArg().withDescription("[Pitcher] brzina slanja izražena u „messages per second“\nDefault: 1")
                .create("mps");
        options.addOption(mps);

        Option size = OptionBuilder.withArgName("size").hasArg().withDescription("[Pitcher] dužina poruke\nMinimum: 50, Maximum: 3000,	Default: 300")
                .create("size");
        options.addOption(size);

        CommandLineParser parser = new BasicParser();
        return parser.parse(options, args);
    }

    private static void Help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("TCPPing", options);
    }

    public static void main(String[] args) {

        try {
            CommandLine cmd = TCPPing.parseCommandLine(args);
            if (cmd.hasOption("p") && cmd.hasOption("port")) {
                String[] unassignedArgs = cmd.getArgs();
                if (unassignedArgs.length != 1)
                    Help();

                int mps = cmd.hasOption("mps") ? Integer.parseInt(cmd.getOptionValue("mps")) : DEFAULT_MPS;

                int size;
                if (cmd.hasOption("size")) {
                    size = Integer.parseInt(cmd.getOptionValue("size"));
                    if (!(size >= MIN_MSG_SIZE && size <= MAX_MSG_SIZE)) {
                        size = DEFAULT_MSG_SIZE;
                    }
                } else {
                    size = DEFAULT_MSG_SIZE;
                }

                new Pitcher(unassignedArgs[0], Integer.parseInt(cmd.getOptionValue("port")), mps, size).start();
            } else if (cmd.hasOption("c") && cmd.hasOption("port") && cmd.hasOption("bind")) {
                new Catcher(cmd.getOptionValue("bind"), Integer.parseInt(cmd.getOptionValue("port"))).start();
            } else {
                Help();
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            Help();
        }
    }


}
