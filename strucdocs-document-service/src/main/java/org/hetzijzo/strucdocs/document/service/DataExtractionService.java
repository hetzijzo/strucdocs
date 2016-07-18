package org.hetzijzo.strucdocs.document.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.hetzijzo.strucdocs.document.domain.StrucdocsFile;
import org.hetzijzo.strucdocs.document.exception.DataExtractionException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class DataExtractionService {

    private static final String EXIF_COMMAND = "C:\\Data\\Programs\\exiftool.exe";
    private static final String EXTRACT_TEXT_COMMAND = "C:\\Data\\Programs\\xpdfbin-win-3.04\\bin64\\pdftotext.exe";

    private final ObjectMapper objectMapper;

    public DataExtractionService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    StrucdocsFile extractMetadata(File file) {
        Commandline commandline = createCommandLine(EXIF_COMMAND, "-json", "-n", file.getAbsolutePath());
        CommandLineUtils.StringStreamConsumer err = new CommandLineUtils.StringStreamConsumer();
        CommandLineUtils.StringStreamConsumer out = new CommandLineUtils.StringStreamConsumer();
        runCommandLine(commandline, err, out);

        String output = out.getOutput();
        if (!output.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONArray(output).getJSONObject(0);
                return objectMapper.readValue(jsonObject.toString(), StrucdocsFile.class);
            } catch (JSONException | IOException ex) {
                throw new IllegalStateException(ex);
            }
        }

        String error = err.getOutput();
        if (!error.isEmpty()) {
            log.error(error);
        }
        return null;
    }
//
//    public String extractText(File file) {
//        Commandline commandline = createCommandLine(EXTRACT_TEXT_COMMAND, "-raw", file.getAbsolutePath(), "-");
//        CommandLineUtils.StringStreamConsumer err = new CommandLineUtils.StringStreamConsumer();
//        CommandLineUtils.StringStreamConsumer out = new CommandLineUtils.StringStreamConsumer();
//        runCommandLine(commandline, err, out);
//
//        String output = out.getOutput();
//        if (!output.isEmpty()) {
//            return output;
//        }
//
//        String error = err.getOutput();
//        if (!error.isEmpty()) {
//            log.error(error);
//        }
//        return null;
//    }

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
