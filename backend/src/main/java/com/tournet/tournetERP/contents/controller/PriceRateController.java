package com.tournet.tournetERP.contents.controller;

/**
 * Please explain the class!!
 *
 * @author : rubayi
 * @fileName : PriceRateController
 * @since : 2024-06-14
 */
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.contents.entity.PriceRate;
import com.tournet.tournetERP.contents.repository.PriceRateRepository;
import com.tournet.tournetERP.contents.service.PriceRateService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.transaction.Transactional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/priceRate")
public class PriceRateController {

    @Autowired
    PriceRateRepository priceRateRepository;

    @Autowired
    PriceRateService priceRateService;

    @PostMapping("/selectPriceRates")
    public ResponseEntity<?> selectPriceRates (@RequestBody PriceRate priceRateReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        List<PriceRate> priceRateList = priceRateRepository.findAllByOrderByRateUuidDesc();

        return new ResponseEntity<>(priceRateList, HttpStatus.OK);
    }

    @GetMapping("/getPriceRates/{id}")
    public ResponseEntity<?> selectPriceRates (@PathVariable long id) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        PriceRate onePriceRate = new PriceRate();

        if (storUser.isAuthenticated()) {
            onePriceRate = priceRateRepository.findFirstByRateUuid(id);
        }

        return new ResponseEntity<>(onePriceRate, HttpStatus.OK);
    }

    @PostMapping("/updatePriceRate")
    public ResponseEntity<?> updatePriceRate(@RequestBody PriceRate priceRateReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        PriceRate updatedpriceRate = new PriceRate();

        Optional<PriceRate> currentPriceRate = priceRateRepository.findByRateUuid(priceRateReq.getRateUuid());

        if (currentPriceRate.isPresent()) {

            PriceRate _priceRate = currentPriceRate.get();

            _priceRate.setRateUuid(priceRateReq.getRateUuid());
            _priceRate.setRateType(priceRateReq.getRateType());
            _priceRate.setRateCont(priceRateReq.getRateCont());

            updatedpriceRate = priceRateRepository.save(_priceRate);

        }

        return new ResponseEntity<>(updatedpriceRate, HttpStatus.OK);
    }

    @Transactional
    @GetMapping("/deletePriceRate/{id}")
    public ResponseEntity<?> deletePriceRate(@PathVariable long id) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        if (storUser.isAuthenticated()) {
            priceRateRepository.deleteByRateUuid(id);
        }
        return ResponseEntity.ok(new MessageResponse("삭제 되었습니다."));

    }


}
