package org.hetzijzo.strucdocs.song;


import org.hetzijzo.strucdocs.song.domain.Song;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SongRepositoryTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetSongs() throws Exception {
        ResponseEntity<Resource<Song>> responseEntity =
            restTemplate.exchange("/songs/", HttpMethod.GET, null, new ParameterizedTypeReference<Resource<Song>>() {
            });
    }
}
