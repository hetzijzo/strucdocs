package org.hetzijzo.strucdocs.document.rest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DocumentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void getDocuments() throws Exception {
        List documentList = restTemplate.getForObject("/", List.class);

    }

    @Test
    public void getDocumentByUuid() throws Exception {

    }

    @Test
    public void getDocumentContentByUuid() throws Exception {

    }

    @Test
    public void saveDocument() throws Exception {

        HttpEntity request = new HttpEntity(null);
        restTemplate.exchange("/", HttpMethod.POST, request, Void.class);

    }

}
