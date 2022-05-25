package com.pmp.nwms.web.rest;

import com.pmp.nwms.domain.DiscountsDef;
import com.pmp.nwms.security.NwmsUser;
import com.pmp.nwms.service.DiscountsDefService;
import com.pmp.nwms.service.PurchaseStatusService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DiscountsDef.
 */
@RestController
@RequestMapping("/api")
public class DiscountsDefResource {

    @Autowired
    private PurchaseStatusService purchaseStatusService;

    @Autowired
    private ApplicationDataStorage applicationDataStorage;

    private final Logger log = LoggerFactory.getLogger(DiscountsDefResource.class);

    private static final String ENTITY_NAME = "discountsDef";

    private final DiscountsDefService discountsDefService;

    public DiscountsDefResource(DiscountsDefService discountsDefService) {
        this.discountsDefService = discountsDefService;
    }

    /**
     * POST  /discounts-defs : Create a new discountsDef.
     *
     * @param discountsDef the discountsDef to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discountsDef, or with status 400 (Bad Request) if the discountsDef has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/discounts-defs")
    public ResponseEntity<DiscountsDef> createDiscountsDef(@Valid @RequestBody DiscountsDef discountsDef) throws URISyntaxException {
        log.debug("REST request to save DiscountsDef : {}", discountsDef);
        if (discountsDef.getId() != null) {
            throw new BadRequestAlertException("A new discountsDef cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscountsDef result = discountsDefService.save(discountsDef);
        return ResponseEntity.created(new URI("/api/discounts-defs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discounts-defs : Updates an existing discountsDef.
     *
     * @param discountsDef the discountsDef to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discountsDef,
     * or with status 400 (Bad Request) if the discountsDef is not valid,
     * or with status 500 (Internal Server Error) if the discountsDef couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/discounts-defs")
    public ResponseEntity<DiscountsDef> updateDiscountsDef(@Valid @RequestBody DiscountsDef discountsDef) throws URISyntaxException {
        log.debug("REST request to update DiscountsDef : {}", discountsDef);
        if (discountsDef.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiscountsDef result = discountsDefService.save(discountsDef);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, discountsDef.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discounts-defs : get all the discountsDefs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of discountsDefs in body
     */
    @GetMapping("/discounts-defs")
    public ResponseEntity<List<DiscountsDef>> getAllDiscountsDefs(Pageable pageable) {
        log.debug("REST request to get a page of DiscountsDefs");
        Page<DiscountsDef> page = discountsDefService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/discounts-defs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /discounts-defs/:id : get the "id" discountsDef.
     *
     * @param id the id of the discountsDef to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discountsDef, or with status 404 (Not Found)
     */
    @GetMapping("/discounts-defs/{id}")
    public ResponseEntity<DiscountsDef> getDiscountsDef(@PathVariable Long id) {
        log.debug("REST request to get DiscountsDef : {}", id);
        Optional<DiscountsDef> discountsDef = discountsDefService.findOne(id);
        return ResponseUtil.wrapOrNotFound(discountsDef);
    }

    @GetMapping("/discounts-defs/bycode/{code}")
    public ResponseEntity<DiscountsDef> getDiscountsDefByCode(@PathVariable String code) throws ParseException {
        log.debug("REST request to get DiscountsDef : {}", code);
        String username = UserUtil.getCurrentUser().getUsername();
        Optional<DiscountsDef> discountsDef = discountsDefService.findOneByCode(code);
        if (!discountsDef.isPresent()) {
            applicationDataStorage.remove(username + "_discountsdef");
        }else if(discountsDef.isPresent() && !discountsDef.get().getCode().equals(code)){
            applicationDataStorage.remove(username + "_discountsdef");
            discountsDef = Optional.empty();
        } else {
            NwmsUser user = UserUtil.getCurrentUser();
            int countUsedDiscount = purchaseStatusService.userPlanWithDiscount(user.getId(), code);
            if (countUsedDiscount > 0) {
                applicationDataStorage.remove(username + "_discountsdef");
                discountsDef.get().setCode("used");
                return ResponseEntity.status(450).build();
            }


            String pattern = "yyyy/MM/dd HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            String date = simpleDateFormat.format(new Date());
            Date today = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date);
            /*ZoneId z = ZoneId.of(Constants.SYSTEM_TIME_ZONE);
            Date nowDate = Date.from(today.toInstant().atZone(z);
*/

            long differenceToFinish = today.compareTo(discountsDef.get().getFinsihDate());
            long differenceToStart = today.compareTo(discountsDef.get().getStartDate());
            if (differenceToStart < 0) {
                applicationDataStorage.remove(username + "_discountsdef");
                return  ResponseEntity.status(452).body(discountsDef.get());
            }
            if (differenceToFinish > 0) {
                applicationDataStorage.remove(username + "_discountsdef");
                return  ResponseEntity.status(451).body(discountsDef.get());
            }
            applicationDataStorage.add(username + "_discountsdef", Long.toString(discountsDef.get().getId()));
        }
        return ResponseUtil.wrapOrNotFound(discountsDef);
    }

    /**
     * DELETE  /discounts-defs/:id : delete the "id" discountsDef.
     *
     * @param id the id of the discountsDef to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/discounts-defs/{id}")
    public ResponseEntity<Void> deleteDiscountsDef(@PathVariable Long id) {
        log.debug("REST request to delete DiscountsDef : {}", id);
        discountsDefService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
