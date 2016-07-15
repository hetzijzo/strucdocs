package org.hetzijzo.strucdocs.document.service;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class DataExtractionService {

    private static final String EXIF_COMMAND = "C:\\Data\\Programs\\exiftool.exe";
    private static final String EXTRACT_TEXT_COMMAND = "C:\\Data\\Programs\\xpdfbin-win-3.04\\bin64\\pdftotext.exe";

    public JsonObject extractMetadata(File file) {
        Commandline commandline = createCommandLine(EXIF_COMMAND, "-json", "-n", file.getAbsolutePath());
        CommandLineUtils.StringStreamConsumer err = new CommandLineUtils.StringStreamConsumer();
        CommandLineUtils.StringStreamConsumer out = new CommandLineUtils.StringStreamConsumer();
        runCommandLine(commandline, err, out);

        String output = out.getOutput();
        if (!output.isEmpty()) {
            JsonArray arr = JsonArray.fromJson(output);
            return arr.getObject(0);
        }

        String error = err.getOutput();
        if (!error.isEmpty()) {
            log.error(error);
        }
        return null;
    }

    public String extractText(File file) {
        Commandline commandline = createCommandLine(EXTRACT_TEXT_COMMAND, "-raw", file.getAbsolutePath(), "-");
        CommandLineUtils.StringStreamConsumer err = new CommandLineUtils.StringStreamConsumer();
        CommandLineUtils.StringStreamConsumer out = new CommandLineUtils.StringStreamConsumer();
        runCommandLine(commandline, err, out);

        String output = out.getOutput();
        if (!output.isEmpty()) {
            return output;
        }

        String error = err.getOutput();
        if (!error.isEmpty()) {
            log.error(error);
        }
        return null;
    }

    private Commandline createCommandLine(String exifCommand, String... arguments) {
        Commandline commandline = new Commandline();
        commandline.setExecutable(exifCommand);
        commandline.addArguments(arguments);
        return commandline;
    }

    private void runCommandLine(Commandline commandline, CommandLineUtils.StringStreamConsumer err,
                                CommandLineUtils.StringStreamConsumer out) {
        try {
            CommandLineUtils.executeCommandLine(commandline, out, err);
        } catch (CommandLineException ex) {
            throw new DataExtractionException(ex);
        }
    }
}
