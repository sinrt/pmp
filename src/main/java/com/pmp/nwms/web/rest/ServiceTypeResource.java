package com.pmp.nwms.web.rest;

import com.pmp.nwms.domain.ServiceType;
import com.pmp.nwms.repository.ServiceTypeRepository;
import com.pmp.nwms.service.ServiceTypeService;
import com.pmp.nwms.web.rest.errors.BadRequestAlertException;
import com.pmp.nwms.web.rest.util.HeaderUtil;
import com.pmp.nwms.web.rest.util.PaginationUtil;
import com.pmp.nwms.service.dto.ServiceTypeDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ServiceType.
 */
@RestController
@RequestMapping("/api")
public class ServiceTypeResource {

    private final Logger log = LoggerFactory.getLogger(ServiceTypeResource.class);

    private static final String ENTITY_NAME = "serviceType";

    private final ServiceTypeService serviceTypeService;

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeResource(ServiceTypeService serviceTypeService,
                               ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeService = serviceTypeService;
        this.serviceTypeRepository = serviceTypeRepository;
    }

    /**
     * POST  /service-types : Create a new serviceType.
     *
     * @param serviceTypeDTO the serviceTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new serviceTypeDTO, or with status 400 (Bad Request) if the serviceType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/service-types")
    public ResponseEntity<ServiceTypeDTO> createServiceType(@Valid @RequestBody ServiceTypeDTO serviceTypeDTO) throws URISyntaxException {
        log.debug("REST request to updateClassroom ServiceType : {}", serviceTypeDTO);
        if (serviceTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceTypeDTO result = serviceTypeService.save(serviceTypeDTO);
        return ResponseEntity.created(new URI("/api/service-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /service-types : Updates an existing serviceType.
     *
     * @param serviceTypeDTO the serviceTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated serviceTypeDTO,
     * or with status 400 (Bad Request) if the serviceTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the serviceTypeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/service-types")
    public ResponseEntity<ServiceTypeDTO> updateServiceType(@Valid @RequestBody ServiceTypeDTO serviceTypeDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceType : {}", serviceTypeDTO);
        if (serviceTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceTypeDTO result = serviceTypeService.save(serviceTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, serviceTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /service-types : get all the serviceTypes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of serviceTypes in body
     */
    @GetMapping("/service-types")
    public ResponseEntity<List<ServiceTypeDTO>> getAllServiceTypes(Pageable pageable) {
        log.debug("REST request to get a page of ServiceTypes");
        
        if (pageable.getPageSize() != 2000) {
            Page<ServiceTypeDTO> page = serviceTypeService.findAll(pageable);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/service-types");
            return ResponseEntity.ok().headers(headers).body(page.getContent());
        } else {
            List<ServiceType> allServices = serviceTypeRepository.findActivedServiceType();
            List<ServiceTypeDTO> typeDTOS = new ArrayList<>();
            for (ServiceType type : allServices) {
                ServiceTypeDTO typeDTO = new ServiceTypeDTO(type);
                typeDTOS.add(typeDTO);
            }
            return ResponseEntity.ok().body(typeDTOS);
        }
    }

    /**
     * GET  /service-types/:id : get the "id" serviceType.
     *
     * @param id the id of the serviceTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the serviceTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/service-types/{id}")
    public ResponseEntity<ServiceTypeDTO> getServiceType(@PathVariable Long id) {
        log.debug("REST request to get ServiceType : {}", id);
        Optional<ServiceTypeDTO> serviceTypeDTO = serviceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceTypeDTO);
    }

    /**
     * DELETE  /service-types/:id : delete the "id" serviceType.
     *
     * @param id the id of the serviceTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/service-types/{id}")
    public ResponseEntity<Void> deleteServiceType(@PathVariable Long id) {
        log.debug("REST request to delete ServiceType : {}", id);
        serviceTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
