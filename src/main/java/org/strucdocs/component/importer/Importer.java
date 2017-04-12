package org.strucdocs.component.importer;

import org.strucdocs.model.Song;

import java.io.InputStream;

public interface Importer {

    Song importSong(InputStream inputStream);

}
