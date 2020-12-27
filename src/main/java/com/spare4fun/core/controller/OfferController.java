package com.spare4fun.core.controller;

import com.spare4fun.core.dto.OfferDto;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.exception.InvalidUserException;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.OfferService;
import com.spare4fun.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        List<OfferDto> offerDtos = new ArrayList<>();
        List<Offer> offers = offerService.getAllOffersBuyer(username);

        for (Offer offer : offers) {
            offerDtos.add(
                    OfferDto
                            .builder()
                            .offerId(offer.getId())
                            .price(offer.getPrice())
                            .quantity(offer.getQuantity())
                            .message(offer.getMessage())
                            .itemId(offer.getItem().getId())
                            .sellerId(offer.getSeller().getId())
                            .buyerId(offer.getBuyer().getId())
                            .build()
            );
        }

        return offerDtos;
    }

    @GetMapping("/{offerId}")
    @ResponseBody
    public OfferDto getOfferById(@PathVariable(value = "offerId") int offerId) {

        Offer offer = offerService.getOfferById(offerId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int currentUserId = userService.loadUserByUsername(username).get().getId();

        if (currentUserId != offer.getSeller().getId() && currentUserId != offer.getBuyer().getId()) {
            throw new InvalidUserException("You don't have the authorization to get the offer");
        }
        return OfferDto
                .builder()
                .offerId(offer.getId())
                .price(offer.getPrice())
                .quantity(offer.getQuantity())
                .message(offer.getMessage())
                .itemId(offer.getItem().getId())
                .sellerName(offer.getSeller().getUsername())
                .sellerId(offer.getSeller().getId())
                .buyerName(offer.getBuyer().getUsername())
                .buyerId(offer.getBuyer().getId())
                .build();
    }

    @PostMapping("/deleteOffer/{offerId}")
    public void deleteOfferById(@PathVariable(value = "offerId") int offerId){

        Offer offer = offerService.getOfferById(offerId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int currentUserId = userService.loadUserByUsername(username).get().getId();

        //only buyer can delete the offer
        if (currentUserId != offer.getBuyer().getId()) {
            throw new InvalidUserException("You don't have the authorization to delete the offer");
        }
        offerService.deleteOfferById(offerId);
    }
}
