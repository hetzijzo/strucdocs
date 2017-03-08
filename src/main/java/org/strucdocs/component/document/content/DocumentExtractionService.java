package org.strucdocs.component.document.content;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.springframework.stereotype.Service;
import org.strucdocs.component.document.config.CommandLineProperties;
import org.strucdocs.component.document.exception.DataExtractionException;
import org.strucdocs.model.Document;

import java.io.File;
import java.time.format.DateTimeFormatter;


@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class DocumentExtractionService {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy:MM:dd HH:mm:ssz");

    private final CommandLineProperties commandLine;

    Document extractMetadata(File file) {
        Commandline commandline = createCommandLine(commandLine.getExifCommand(),
            "-json", "-n", file.getAbsolutePath());
        CommandLineUtils.StringStreamConsumer err = new CommandLineUtils.StringStreamConsumer();
        CommandLineUtils.StringStreamConsumer out = new CommandLineUtils.StringStreamConsumer();
        runCommandLine(commandline, err, out);

        String output = out.getOutput();
        if (!output.isEmpty()) {
            try {
                JSONObject jsonObject = new JSONArray(output).getJSONObject(0);
                return Document.builder()
                    .sourceFile(jsonObject.getString("SourceFile"))
                    .filename(jsonObject.getString("FileName"))
                    .directory(jsonObject.getString("Directory"))
                    .size(jsonObject.getLong("FileSize"))
                    .pages(jsonObject.has("PageCount") ? jsonObject.getLong("PageCount") : 0)
                    .mimeType(jsonObject.has("MIMEType") ? jsonObject.getString("MIMEType") : "application/pdf")
//                    .accessDate(LocalDateTime.parse(jsonObject.getString("FileAccessDate"), DATE_TIME_FORMATTER)
//                        .toInstant(ZoneOffset.UTC))
//                    .createDate(LocalDateTime.parse(jsonObject.getString("FileCreateDate"), DATE_TIME_FORMATTER)
//                        .toInstant(ZoneOffset.UTC))
//                    .modifyDate(LocalDateTime.parse(jsonObject.getString("FileModifyDate"), DATE_TIME_FORMATTER)
//                        .toInstant(ZoneOffset.UTC))
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
        Commandline commandline = createCommandLine(commandLine.getExtractTextCommand(),
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
