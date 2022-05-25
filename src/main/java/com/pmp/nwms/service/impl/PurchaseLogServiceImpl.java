package com.pmp.nwms.service.impl;

import com.pmp.nwms.service.PurchaseLogService;
import com.pmp.nwms.domain.PurchaseLog;
import com.pmp.nwms.repository.PurchaseLogRepository;
import com.pmp.nwms.service.dto.PurchaseLogDTO;
import com.pmp.nwms.service.mapper.PurchaseLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing PurchaseLog.
 */
@Service
@Transactional
public class PurchaseLogServiceImpl implements PurchaseLogService {

    private final Logger log = LoggerFactory.getLogger(PurchaseLogServiceImpl.class);

    private final PurchaseLogRepository purchaseLogRepository;

    private final PurchaseLogMapper purchaseLogMapper;

    public PurchaseLogServiceImpl(PurchaseLogRepository purchaseLogRepository, PurchaseLogMapper purchaseLogMapper) {
        this.purchaseLogRepository = purchaseLogRepository;
        this.purchaseLogMapper = purchaseLogMapper;
    }

    /**
     * Save a purchaseLog.
     *
     * @param purchaseLogDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PurchaseLogDTO save(PurchaseLogDTO purchaseLogDTO) {
        log.debug("Request to save PurchaseLog : {}", purchaseLogDTO);
        PurchaseLog purchaseLog = purchaseLogMapper.toEntity(purchaseLogDTO);
        purchaseLog = purchaseLogRepository.save(purchaseLog);
        return purchaseLogMapper.toDto(purchaseLog);
    }

    /**
     * Get all the purchaseLogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PurchaseLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PurchaseLogs");
        return purchaseLogRepository.findAll(pageable)
            .map(purchaseLogMapper::toDto);
    }


    /**
     * Get one purchaseLog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PurchaseLogDTO> findOne(Long id) {
        log.debug("Request to get PurchaseLog : {}", id);
        return purchaseLogRepository.findById(id)
            .map(purchaseLogMapper::toDto);
    }

    /**
     * Delete the purchaseLog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PurchaseLog : {}", id);
        purchaseLogRepository.deleteById(id);
    }
}
