package com.pmp.nwms.web.rest;
import com.pmp.nwms.service.PurchaseLogService;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import com.pmp.nwms.service.dto.PurchaseLogDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PurchaseLog.
 */
@RestController
@RequestMapping("/api")
public class PurchaseLogResource {

    private final Logger log = LoggerFactory.getLogger(PurchaseLogResource.class);

    private static final String ENTITY_NAME = "purchaseLog";

    private final PurchaseLogService purchaseLogService;

    public PurchaseLogResource(PurchaseLogService purchaseLogService) {
        this.purchaseLogService = purchaseLogService;
    }

    /**
     * POST  /purchase-logs : Create a new purchaseLog.
     *
     * @param purchaseLogDTO the purchaseLogDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new purchaseLogDTO, or with status 400 (Bad Request) if the purchaseLog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/purchase-logs")
    public ResponseEntity<PurchaseLogDTO> createPurchaseLog(@RequestBody PurchaseLogDTO purchaseLogDTO) throws URISyntaxException {
        log.debug("REST request to save PurchaseLog : {}", purchaseLogDTO);
        if (purchaseLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new purchaseLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PurchaseLogDTO result = purchaseLogService.save(purchaseLogDTO);
        return ResponseEntity.created(new URI("/api/purchase-logs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /purchase-logs : Updates an existing purchaseLog.
     *
     * @param purchaseLogDTO the purchaseLogDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated purchaseLogDTO,
     * or with status 400 (Bad Request) if the purchaseLogDTO is not valid,
     * or with status 500 (Internal Server Error) if the purchaseLogDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/purchase-logs")
    public ResponseEntity<PurchaseLogDTO> updatePurchaseLog(@RequestBody PurchaseLogDTO purchaseLogDTO) throws URISyntaxException {
        log.debug("REST request to update PurchaseLog : {}", purchaseLogDTO);
        if (purchaseLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PurchaseLogDTO result = purchaseLogService.save(purchaseLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, purchaseLogDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /purchase-logs : get all the purchaseLogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of purchaseLogs in body
     */
    @GetMapping("/purchase-logs")
    public ResponseEntity<List<PurchaseLogDTO>> getAllPurchaseLogs(Pageable pageable) {
        log.debug("REST request to get a page of PurchaseLogs");
        Page<PurchaseLogDTO> page = purchaseLogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/purchase-logs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /purchase-logs/:id : get the "id" purchaseLog.
     *
     * @param id the id of the purchaseLogDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the purchaseLogDTO, or with status 404 (Not Found)
     */
    @GetMapping("/purchase-logs/{id}")
    public ResponseEntity<PurchaseLogDTO> getPurchaseLog(@PathVariable Long id) {
        log.debug("REST request to get PurchaseLog : {}", id);
        Optional<PurchaseLogDTO> purchaseLogDTO = purchaseLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(purchaseLogDTO);
    }

    /**
     * DELETE  /purchase-logs/:id : delete the "id" purchaseLog.
     *
     * @param id the id of the purchaseLogDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/purchase-logs/{id}")
    public ResponseEntity<Void> deletePurchaseLog(@PathVariable Long id) {
        log.debug("REST request to delete PurchaseLog : {}", id);
        purchaseLogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
