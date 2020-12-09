package com.spare4fun.core.controller;

import com.spare4fun.core.dto.OfferDto;
import com.spare4fun.core.dto.UserDto;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.OfferService;
import com.spare4fun.core.service.UserAuthService;
import com.spare4fun.core.service.UserService;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/offer")
public class OfferController {
    @Autowired
    private OfferService offerService;

    @Autowired
    private UserService userService;

    @Autowired
    ItemService itemService;

    //********
    //Can Zhao
    @PostMapping("/creation")
    @ResponseBody
    public OfferDto saveOffer(@RequestBody OfferDto offerDto){
        // get current user (buyer)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String buyerName = authentication.getName();

        // create the Offer object we are about to save
        Offer offer = Offer
                .builder()
                .item(itemService.getItemById(offerDto.getItemId()))
                .seller(userService.loadUserByUsername(offerDto.getSellerName()).orElse(null))
                .buyer(userService.loadUserByUsername(buyerName).orElse(null))
                .price(offerDto.getPrice())
                .quantity(offerDto.getQuantity())
                .message(offerDto.getMessage())
                .enabled(true)
                .build();

        Offer offerToBeSaved = offerService.saveOffer(offer);
        offerDto.setOfferId(offerToBeSaved.getId());
        return offerDto;
    }

    //********
    //Yuhe
    @GetMapping("/buyer/getAllOffers")
    @ResponseBody
    public List<OfferDto> getAllOffersBuyer(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Offer> offers = offerService.getAllOffersBuyer(username);
        List<OfferDto> offerDtos = new ArrayList<>();

        for (Offer offer : offers) {
            offerDtos.add(
                    OfferDto
                            .builder()
                            .build()
            );
        }
        return offerDtos;
    }

    @GetMapping("/{offerId}")
    @ResponseBody
    public OfferDto getOfferById(@PathVariable(value = "offerId") int offerId) {

        Offer offer = offerService.getOfferById(offerId);
        OfferDto offerDto = OfferDto
                                    .builder()
//                                    .buyerId(offer.getBuyer().getId())
                                    .build();
        return offerDto;
    }

    @PostMapping("/deleteOffer/{offerId}")
    public ResponseEntity<String> deleteOfferById(@PathVariable(value = "offerId") int offerId){
        offerService.deleteOfferById(offerId);
        return new ResponseEntity<String>("The offer has been successfully deleted!", HttpStatus.OK);
    }
}
