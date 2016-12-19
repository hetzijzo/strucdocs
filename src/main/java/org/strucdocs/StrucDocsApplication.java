package org.strucdocs;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.EndpointMBeanExportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.strucdocs.component.artist.ArtistRepository;
import org.strucdocs.component.song.SongRepository;
import org.strucdocs.model.Artist;
import org.strucdocs.model.Song;

@SpringBootApplication(exclude = {EndpointMBeanExportAutoConfiguration.class})
@EnableJpaRepositories
public class StrucDocsApplication {

    public static void main(String[] args) {
        SpringApplication.run(StrucDocsApplication.class, args);
    }

    @Bean
    CommandLineRunner add(ArtistRepository artistRepository, SongRepository songRepository) {
        return args -> {
            Artist artist = Artist.builder()
                .name("Michael Jackson")
                .build();
            artistRepository.save(artist);

            songRepository.save(Song.builder().artist(artist).title("Thriller").build());
            songRepository.save(Song.builder().artist(artist).title("Bad").build());
        };
    }
}
