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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

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
}
