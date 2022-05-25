package com.pmp.nwms.web.rest;

import com.pmp.nwms.NwmsConfig;
import com.pmp.nwms.domain.SuggestionsBox;
import com.pmp.nwms.service.SuggestionsBoxService;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SuggestionsBox.
 */
@RestController
@RequestMapping("/api")
public class SuggestionsBoxResource {

    private final Logger log = LoggerFactory.getLogger(SuggestionsBoxResource.class);

    private static final String ENTITY_NAME = "suggestionsBox";


//    private final ServerConfigService serverConfigService;

    private NwmsConfig nwmsConfig;

    private final SuggestionsBoxService suggestionsBoxService;

    public SuggestionsBoxResource(SuggestionsBoxService suggestionsBoxService, NwmsConfig nwmsConfig) {
        this.suggestionsBoxService = suggestionsBoxService;
        this.nwmsConfig = nwmsConfig;
    }

    /**
     * POST  /suggestions-boxes : Create a new suggestionsBox.
     *
     * @param suggestionsBox the suggestionsBox to create
     * @return the ResponseEntity with status 201 (Created) and with body the new suggestionsBox, or with status 400 (Bad Request) if the suggestionsBox has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/suggestions-boxes")
    public ResponseEntity<SuggestionsBox> createSuggestionsBox(@Valid @RequestBody SuggestionsBox suggestionsBox) throws URISyntaxException {
        log.debug("REST request to save SuggestionsBox : {}", suggestionsBox);
        if (suggestionsBox.getId() != null) {
            throw new BadRequestAlertException("A new suggestionsBox cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SuggestionsBox result = suggestionsBoxService.save(suggestionsBox);
        return ResponseEntity.created(new URI("/api/suggestions-boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /suggestions-boxes : Updates an existing suggestionsBox.
     *
     * @param suggestionsBox the suggestionsBox to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated suggestionsBox,
     * or with status 400 (Bad Request) if the suggestionsBox is not valid,
     * or with status 500 (Internal Server Error) if the suggestionsBox couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/suggestions-boxes")
    public ResponseEntity<SuggestionsBox> updateSuggestionsBox(@Valid @RequestBody SuggestionsBox suggestionsBox) throws URISyntaxException {
        log.debug("REST request to update SuggestionsBox : {}", suggestionsBox);
        if (suggestionsBox.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SuggestionsBox result = suggestionsBoxService.save(suggestionsBox);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, suggestionsBox.getId().toString()))
            .body(result);
    }

    /**
     * GET  /suggestions-boxes : get all the suggestionsBoxes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of suggestionsBoxes in body
     */
    @GetMapping("/suggestions-boxes")
    public ResponseEntity<List<SuggestionsBox>> getAllSuggestionsBoxes(Pageable pageable) {
        log.debug("REST request to get a page of SuggestionsBoxes");
        Page<SuggestionsBox> page = suggestionsBoxService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/suggestions-boxes");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/suggestions-boxes/file")
    public ResponseEntity<String> getFile() throws IOException {
        String imagePath = this.nwmsConfig.getRubruImageProfilePath();
        File file = new File(imagePath + "rubru.pdf");
        byte[] bytes = Files.readAllBytes(file.toPath());
        String string = Base64.getEncoder().encodeToString(bytes);
        ResponseEntity<String> response = ResponseEntity.ok(string);
        return response;
    }

    /**
     * GET  /suggestions-boxes/:id : get the "id" suggestionsBox.
     *
     * @param id the id of the suggestionsBox to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the suggestionsBox, or with status 404 (Not Found)
     */
    @GetMapping("/suggestions-boxes/{id}")
    public ResponseEntity<SuggestionsBox> getSuggestionsBox(@PathVariable Long id) {
        log.debug("REST request to get SuggestionsBox : {}", id);
        Optional<SuggestionsBox> suggestionsBox = suggestionsBoxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(suggestionsBox);
    }

    /**
     * DELETE  /suggestions-boxes/:id : delete the "id" suggestionsBox.
     *
     * @param id the id of the suggestionsBox to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/suggestions-boxes/{id}")
    public ResponseEntity<Void> deleteSuggestionsBox(@PathVariable Long id) {
        log.debug("REST request to delete SuggestionsBox : {}", id);
        suggestionsBoxService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
