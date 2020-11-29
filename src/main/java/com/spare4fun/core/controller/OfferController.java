package com.spare4fun.core.controller;

import com.spare4fun.core.dto.OfferDto;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Offer;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferController {
    @Autowired
    OfferService offerService;

    @Autowired
    ItemService itemService;

    @PostMapping("/creation")
    public ResponseEntity<String> saveOffer(@RequestBody OfferDto offerDto){
        // Test dummy data
        Item item = new Item();
        itemService.saveItem(item);

//        Item item = ItemDao.getItemById(offerDto.getItemId());
//        User seller = UserDao.getUserById(offerDto.getBuyerId());
        Offer offer = new Offer();
        offer.setItem(item);
        offer.setMessage(offerDto.getMessage());
        offer.setEnabled(true);
        offer.setQuantity(offerDto.getQuantity());
        offer.setPrice(offerDto.getPrice());
//        offer.setSeller(seller);
//        User curUser = null;
//        try {
//            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//            if (principal != null) {
//                curUser =(User)principal;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if(curUser != null){
//            offer.setBuyer(curUser);
//        }else{
//            throw new RuntimeException("Cannot get current user");
//        }
        offerService.saveOffer(offer);
        return new ResponseEntity<String>("The offer has been successfully placed!", HttpStatus.OK);
    }

    //********
    //Yuhe
    @GetMapping("/getAllOffers")
    @ResponseBody
    public List<OfferDto> getAllOffers(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<Offer> res = offerService.getAllOffers();
        List<OfferDto> resOffers = new ArrayList<>();
        for (Offer offer : res) {
            //resOffers.add(/* TODO new offer dto here */ null);
            //resOffers.add(offer);
        }
        return resOffers;
    }

    @GetMapping("/getOfferById/{offerId}")
    @ResponseBody
//    public OfferDto getOfferById(@PathVariable(value = "offerId") int offerId) {
    public Offer getOfferById(@PathVariable(value = "offerId") int offerId) {

        return offerService.getOfferById(offerId);
    }

    @PostMapping("/deleteOffer/{offerId}")
    public ResponseEntity<String> deleteOffer(@PathVariable(value = "offerId") int offerId){

        offerService.deleteOffer(offerId);
        return new ResponseEntity<String>("The offer has been successfully deleted!", HttpStatus.OK);
    }
}
