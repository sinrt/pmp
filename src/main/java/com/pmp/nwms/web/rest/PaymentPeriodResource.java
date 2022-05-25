package com.pmp.nwms.web.rest;

import com.pmp.nwms.service.PaymentPeriodService;
import com.pmp.nwms.service.dto.PaymentPeriodDTO;
import com.pmp.nwms.util.ApplicationDataStorage;
import com.pmp.nwms.util.UserUtil;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PaymentPeriod.
 */
@RestController
@RequestMapping("/api")
public class PaymentPeriodResource {

    private final Logger log = LoggerFactory.getLogger(PaymentPeriodResource.class);

    private static final String ENTITY_NAME = "paymentPeriod";

    private final PaymentPeriodService paymentPeriodService;

    @Autowired
    private ApplicationDataStorage applicationDataStorage;

    public PaymentPeriodResource(PaymentPeriodService paymentPeriodService) {
        this.paymentPeriodService = paymentPeriodService;
    }

    /**
     * POST  /payment-periods : Create a new paymentPeriod.
     *
     * @param paymentPeriodDTO the paymentPeriodDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paymentPeriodDTO, or with status 400 (Bad Request) if the paymentPeriod has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/payment-periods")
    public ResponseEntity<PaymentPeriodDTO> createPaymentPeriod(@Valid @RequestBody PaymentPeriodDTO paymentPeriodDTO) throws URISyntaxException {
        log.debug("REST request to updateClassroom PaymentPeriod : {}", paymentPeriodDTO);
        if (paymentPeriodDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentPeriod cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentPeriodDTO result = paymentPeriodService.save(paymentPeriodDTO);
        return ResponseEntity.created(new URI("/api/payment-periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payment-periods : Updates an existing paymentPeriod.
     *
     * @param paymentPeriodDTO the paymentPeriodDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paymentPeriodDTO,
     * or with status 400 (Bad Request) if the paymentPeriodDTO is not valid,
     * or with status 500 (Internal Server Error) if the paymentPeriodDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/payment-periods")
    public ResponseEntity<PaymentPeriodDTO> updatePaymentPeriod(@Valid @RequestBody PaymentPeriodDTO paymentPeriodDTO) throws URISyntaxException {
        log.debug("REST request to update PaymentPeriod : {}", paymentPeriodDTO);
        if (paymentPeriodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PaymentPeriodDTO result = paymentPeriodService.save(paymentPeriodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paymentPeriodDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payment-periods : get all the paymentPeriods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of paymentPeriods in body
     */
    @GetMapping("/payment-periods")
    public ResponseEntity<List<PaymentPeriodDTO>> getAllPaymentPeriods(Pageable pageable) {
        log.debug("REST request to get a page of PaymentPeriods");
        Page<PaymentPeriodDTO> page = paymentPeriodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment-periods");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/payment-periods/active")
    public ResponseEntity<List<PaymentPeriodDTO>> getAllActivePaymentPeriods(Pageable pageable) {
        log.debug("REST request to get a page of PaymentPeriods");
        Page<PaymentPeriodDTO> page = paymentPeriodService.getActivePaymentPeriods(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payment-periods");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /payment-periods/:id : get the "id" paymentPeriod.
     *
     * @param id the id of the paymentPeriodDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paymentPeriodDTO, or with status 404 (Not Found)
     */
    @GetMapping("/payment-periods/{id}")
    public ResponseEntity<PaymentPeriodDTO> getPaymentPeriod(@PathVariable Long id) {
        log.debug("REST request to get PaymentPeriod : {}", id);
        Optional<PaymentPeriodDTO> paymentPeriodDTO = paymentPeriodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentPeriodDTO);
    }

    @GetMapping("/payment-periods/setpaymentcodeonsession/{id}")
    public void setPaymentCodeOnSession(@PathVariable Long id) {
        String username = UserUtil.getCurrentUser().getUsername();
        applicationDataStorage.add(username + "_paymentcode", Long.toString(id));
        log.debug("REST request to get PaymentPeriod : {}", id);
    }

    /**
     * DELETE  /payment-periods/:id : delete the "id" paymentPeriod.
     *
     * @param id the id of the paymentPeriodDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/payment-periods/{id}")
    public ResponseEntity<Void> deletePaymentPeriod(@PathVariable Long id) {
        log.debug("REST request to delete PaymentPeriod : {}", id);
        paymentPeriodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
