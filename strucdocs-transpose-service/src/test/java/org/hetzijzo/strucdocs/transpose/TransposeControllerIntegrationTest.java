package org.hetzijzo.strucdocs.transpose;

import org.hetzijzo.strucdocs.transpose.domain.Chord;
import org.hetzijzo.strucdocs.transpose.domain.Interval;
import org.hetzijzo.strucdocs.transpose.domain.Note;
import org.hetzijzo.strucdocs.transpose.rest.TransposeRequest;
import org.hetzijzo.strucdocs.transpose.rest.TransposeResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransposeControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private ParameterizedTypeReference<Resource<TransposeResponse>> responseType =
        new ParameterizedTypeReference<Resource<TransposeResponse>>() {
        };

    @Test
    public void transposeWithSimpleChord() {
        Chord chordRequest = Chord.builder().note(Note.C).build();

        ResponseEntity<Resource<TransposeResponse>> transposeResponseEntity =
            restTemplate.exchange("/",
                HttpMethod.POST,
                new HttpEntity<>(TransposeRequest.builder().chord(chordRequest).key(-1).build()),
                responseType);

        assertThat(transposeResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(transposeResponseEntity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));

        TransposeResponse transposeResponse = transposeResponseEntity.getBody().getContent();
        assertThat(transposeResponse, notNullValue());
        assertThat(transposeResponse.getChord().toString(), equalTo("B"));
    }

    @Test
    public void transposeWithoutKey() {
        Chord chordRequest = Chord.builder().note(Note.C).build();

        ResponseEntity<Resource<TransposeResponse>> transposeResponseEntity =
            restTemplate.exchange("/",
                HttpMethod.POST,
                new HttpEntity<>(TransposeRequest.builder().chord(chordRequest).build()),
                responseType);

        assertThat(transposeResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(transposeResponseEntity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));

        TransposeResponse transposeResponse = transposeResponseEntity.getBody().getContent();
        assertThat(transposeResponse.getChord(), equalTo(transposeResponse.getOriginalChord()));
        assertThat(transposeResponse.getKey(), equalTo(0));
    }

    @Test
    public void transposeComplexChord() {
        Chord chordRequest = Chord.builder()
            .note(Note.C)
            .groundNote(Note.G)
            .addition(Interval.major)
            .addition(Interval.seventh)
            .addition(Interval.dim5)
            .build();

        ResponseEntity<Resource<TransposeResponse>> transposeResponseEntity =
            restTemplate.exchange("/",
                HttpMethod.POST,
                new HttpEntity<>(TransposeRequest.builder().chord(chordRequest).key(2).build()),
                responseType);

        assertThat(transposeResponseEntity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(transposeResponseEntity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));
        TransposeResponse response = transposeResponseEntity.getBody().getContent();
        assertThat(response.getChord().toString(), equalTo("Dmaj7b5/A"));
        assertThat(response.getOriginalChord().toString(), equalTo("Cmaj7b5/G"));
        assertThat(response.getKey(), equalTo(2));
    }

    @Test
    public void transposeNoChord() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<String> exchange =
            restTemplate.exchange("/", HttpMethod.POST, new HttpEntity<>("{}", headers), String.class);

        assertThat(exchange.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void transposeInvalidChord() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        ResponseEntity<String> exchange =
            restTemplate.exchange("/", HttpMethod.POST, new HttpEntity<>("{\"chord\": \"J\"}", headers), String.class);

        assertThat(exchange.getStatusCode(), equalTo(HttpStatus.BAD_REQUEST));
    }
}
