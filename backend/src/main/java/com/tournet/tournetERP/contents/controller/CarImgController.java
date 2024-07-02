package com.tournet.tournetERP.contents.controller;

import java.nio.file.FileAlreadyExistsException;
import java.util.*;

import com.tournet.tournetERP.auth.dto.MessageResponse;
import com.tournet.tournetERP.common.service.FilesStorageService;
import com.tournet.tournetERP.contents.entity.CarImg;
import com.tournet.tournetERP.contents.repository.CarImgRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/carImg")
public class CarImgController {
    
    @Autowired
    CarImgRepository carImgRepository;

    @Autowired
    FilesStorageService storageService;
    
    @PostMapping("/selectCarImgs")
    public ResponseEntity<?> selectCarImgs(@RequestBody CarImg carImgReq) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        
        List<CarImg> carImgList = new ArrayList<CarImg>();

        if (storUser.isAuthenticated()) {
            carImgList = carImgRepository.findAll(carImgReq.getTrnCarUuid());
        }
        return new ResponseEntity<>(carImgList, HttpStatus.OK);
    }
    
    @Transactional
    @RequestMapping(value = "/updateCarImg", method = RequestMethod.POST, consumes = { "multipart/form-data" })
    public ResponseEntity<?> updateCarImg(@RequestParam(value = "file", required = false) MultipartFile file,
                                            @RequestParam(value = "carImgReq") String carImgReqJson) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        CarImg _carImg = new CarImg();
        if (storUser.isAuthenticated()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode carImgJsonNode;

                carImgJsonNode = objectMapper.readTree(carImgReqJson);

                CarImg updatedCarImg = new CarImg();

                updatedCarImg.setTrnImgUuid(carImgJsonNode.get("trnImgUuid").asLong());
                updatedCarImg.setTrnCarUuid(carImgJsonNode.get("trnCarUuid").asLong());

                // Optional<CarImg> currentCarImg = carImgRepository.findById(updatedCarImg.getTrnImgUuid());
                // if (currentCarImg.isPresent()) {
                //     CarImg existingCarImg = currentCarImg.get();
                //     updatedCarImg = currentCarImg.get();
                //     updatedCarImg.setImgFile(existingCarImg.getImgFile();
                // }

                String fileName = "";
                if (file != null) {
                    fileName = storageService.newSave(file);
                    updatedCarImg.setImgFile(fileName);
                }

                _carImg = carImgRepository.save(updatedCarImg);
            } catch (Exception e) {
                if (e instanceof FileAlreadyExistsException) {
                }
            }
            System.out.println(
                    "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@Car Image Updated Successfully");
            System.out.println(_carImg.getTrnImgUuid());
        }
        return new ResponseEntity<>(_carImg, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/deleteCarImg/{id}")
    public ResponseEntity<?> deleteCarImg(@PathVariable long id) {

        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();

        if (storUser.isAuthenticated()) {
            carImgRepository.deleteByTrnImgUuid(id);
        }
        return new ResponseEntity<>(new MessageResponse("Car Image Deleted Successfully"), HttpStatus.OK);

    }
    
    @PostMapping("/selectByCarImgUuid/{id}")
    public ResponseEntity<?> searchByTrnImgUuid(@PathVariable long id) {
        Authentication storUser = SecurityContextHolder.getContext().getAuthentication();
        CarImg carImg = new CarImg();

        if (storUser.isAuthenticated()) {
            carImg = carImgRepository.findByTrnImgUuid(id);
        }

        return new ResponseEntity<>(carImg, HttpStatus.OK);
    }
}
