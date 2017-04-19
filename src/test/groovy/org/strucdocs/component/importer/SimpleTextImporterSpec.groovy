package org.strucdocs.component.importer

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.strucdocs.model.Song
import spock.lang.Specification

import javax.transaction.Transactional

@ContextConfiguration
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SimpleTextImporterSpec extends Specification {

    @Autowired
    SimpleTextImporter simpleTextImporter

    def "should import song"() {
        given: "A song as text"
            InputStream inputStream = SimpleTextImporterSpec.getResourceAsStream("/song_import_1.txt")
        when: "The song is imported"
            Song song = simpleTextImporter.importSong(inputStream)
        then: "An empty list of arrays should be returned"
            song != null
    }
}
