package com.spare4fun.core.controller;

import com.spare4fun.core.dto.ItemDto;
import com.spare4fun.core.dto.OfferDto;
import com.spare4fun.core.dto.UserDto;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.InvalidUserException;
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
    private UserService userAuthService;

    @Autowired
    ItemService itemService;

    @Autowired
    private TypeMap<OfferDto, Offer> offerMapper;

    //********
    //Can Zhao
    @PostMapping("/creation")
    public ResponseEntity<String> saveOffer(@RequestBody OfferDto offerDto){
        // get current user (buyer)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User currentUser = null;
        Optional<User> currentUserOpt = userAuthService.loadUserByUsername(username);
        if (currentUserOpt.isPresent()) {
            currentUser = currentUserOpt.get();
        }
        // check if the buyer exists
        if(currentUser == null){
            throw new RuntimeException("Cannot get buyer information!");
        }

        // create the Offer object we are about to save
        Offer offer = offerMapper.map(offerDto);
        // check if the item is valid
        Item item = itemService.getItemById(offerDto.getItemId());
        if (item == null) {
            throw new RuntimeException("Cannot get item information!");
        }
        Optional<User> sellerOpt = userAuthService.loadUserByUsername(offerDto.getSellerName());
        User seller = null;
        //check if the seller is valid
        if (sellerOpt.isPresent()) {
            seller = sellerOpt.get();
            if(seller.getEmail().equals(currentUser.getEmail())) {
                throw new RuntimeException("Buyer and seller cannot be the same person!");
            }
        }else {
            throw new RuntimeException("Cannot get seller information!");
        }
        offer.setSeller(seller);
        offer.setBuyer(currentUser);
        offer.setItem(item);
        offer.setEnabled(true);

        Offer offerToBeSaved = offerService.saveOffer(offer);
        if (offerToBeSaved.getId() == 0) {
            return new ResponseEntity<String>("Saving offer failed!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<String>("The offer has been successfully placed!", HttpStatus.OK);
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
                            .itemId(offer.getItem().getId())
                            .buyerName(offer.getBuyer().getUsername())
                            .sellerName(offer.getSeller().getUsername())
//                            .message(offer.getMessage())
//                            .price(offer.getPrice())
//                            .quantity(offer.getQuantity())
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
        int currentId = userAuthService.loadUserByUsername(username).get().getId();
        if (currentId != offer.getSeller().getId() && currentId != offer.getBuyer().getId()) {
            throw new InvalidUserException("You don't have the authorization to get the offer");
        }
        return OfferDto
                .builder()
                .itemId(offer.getItem().getId())
                .buyerName(offer.getBuyer().getUsername())
                .sellerName(offer.getSeller().getUsername())
//                            .message(offer.getMessage())
//                            .price(offer.getPrice())
//                            .quantity(offer.getQuantity())
                .build();
    }

    @PostMapping("/deleteOffer/{offerId}")
    public void deleteOfferById(@PathVariable(value = "offerId") int offerId){

        Offer offer = offerService.getOfferById(offerId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int currentId = userAuthService.loadUserByUsername(username).get().getId();
        //only buyer can delete the offer
        if (currentId != offer.getBuyer().getId()) {
            throw new InvalidUserException("You don't have the authorization to delete the offer");
        }
        offerService.deleteOfferById(offerId);
    }
}
