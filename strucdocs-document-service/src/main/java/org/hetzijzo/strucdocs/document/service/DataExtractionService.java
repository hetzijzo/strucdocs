package org.hetzijzo.strucdocs.document.service;

import lombok.extern.slf4j.Slf4j;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.hetzijzo.strucdocs.document.CommandLineConfiguration;
import org.hetzijzo.strucdocs.document.domain.StrucdocsDocument;
import org.hetzijzo.strucdocs.document.exception.DataExtractionException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class DataExtractionService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ssz");

    private final CommandLineConfiguration commandLineConfiguration;

    public DataExtractionService(CommandLineConfiguration commandLineConfiguration) {
        this.commandLineConfiguration = commandLineConfiguration;
    }

    StrucdocsDocument extractMetadata(File file) {
        Commandline
            commandline =
            createCommandLine(commandLineConfiguration.getExifCommand(), "-json", "-n", file.getAbsolutePath());
        CommandLineUtils.StringStreamConsumer err = new CommandLineUtils.StringStreamConsumer();
        CommandLineUtils.StringStreamConsumer out = new CommandLineUtils.StringStreamConsumer();
        runCommandLine(commandline, err, out);

        String output = out.getOutput();
        if (!output.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONArray(output).getJSONObject(0);
                return StrucdocsDocument.builder()
                    .sourceFile(jsonObject.getString("SourceFile"))
                    .filename(jsonObject.getString("FileName"))
                    .directory(jsonObject.getString("Directory"))
                    .size(jsonObject.getLong("FileSize"))
                    .pages(jsonObject.getLong("PageCount"))
                    .mimeType(jsonObject.getString("MIMEType"))
                    .accessDate(LocalDateTime.parse(jsonObject.getString("FileAccessDate"), DATE_TIME_FORMATTER)
                        .toInstant(ZoneOffset.UTC))
                    .createDate(LocalDateTime.parse(jsonObject.getString("FileCreateDate"), DATE_TIME_FORMATTER)
                        .toInstant(ZoneOffset.UTC))
                    .modifyDate(LocalDateTime.parse(jsonObject.getString("FileModifyDate"), DATE_TIME_FORMATTER)
                        .toInstant(ZoneOffset.UTC))
                    .build();
            } catch (JSONException ex) {
                throw new DataExtractionException(ex);
            }
        }

        String error = err.getOutput();
        if (!error.isEmpty()) {
            log.error(error);
        }
        return null;
    }

    String extractText(File file) {
        Commandline commandline = createCommandLine(commandLineConfiguration.getExtractTextCommand(),
            "-raw", file.getAbsolutePath(), "-");
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
            CommandLineUtils.executeCommandLine(commandline, out, err, 5);
        } catch (CommandLineException ex) {
            throw new DataExtractionException(ex);
        }
    }
}
