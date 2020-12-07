package com.spare4fun.core.controller;

import com.spare4fun.core.dto.ItemDto;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.spare4fun.core.dto.LocationDto;
import com.spare4fun.core.entity.Location;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.UserService;
import org.modelmapper.TypeMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TypeMap<Item, ItemDto> itemDtoMapper;

    @Autowired
    private TypeMap<Location, LocationDto> locationDtoMapper;

    @GetMapping("/seller/getAllItems")
    @ResponseBody
    public List<ItemDto> getAllItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<ItemDto> res = new ArrayList<>();
        Optional<User> user = userService.loadUserByUsername(username);
        if (user.isEmpty()) {
            // if user doesn't exists
            return res;
        }

        List<Item> items = user.get().getItems();
        items
                .stream()
                .forEach( item -> {
                    ItemDto itemDto = itemDtoMapper.map(item);
                    if (!item.isHideLocation()) {
                        itemDto.setLocationDto(locationDtoMapper.map(item.getLocation()));
                    }
                    res.add(itemDto);
                });
        return res;
    }

    @PostMapping("/creation")
    @ResponseBody
    public ItemDto createItem(@RequestBody ItemDto itemDto) {
        /**
         * {
         *     title: ...
         *     sellerId: null
         *     listing_price: 20
         *     fixed_price: null????
         *     quantity: 10
         *     lcoationId: null
         *     locationDto: {
         *         id: null
         *         line1: "1101 110TH PL SE"
         *         ...
         *     }
         *     description:
         *     availabilityTime:
         * }
         */

        // 1. check/confirm user authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 2. map itemDto -> item
        // Item item = Item.builder().title(itemDto.getTitle()).build;
        // TODO 1: expect user with username is the seller of this item
        User seller = userService
                .loadUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("username doesn't exist")
                );

        Location location = null;
        if (itemDto.getLocationId() == null) {
            location = locationServcie.saveLocation(
                    Location
                            .builder()
                            .line1(itemDto.getLocationDto().getLine1())
                            //TODO other location attributes
                            .line2(itemDto.getLocationDto().getLine2())
                            .city(itemDto.getLocationDto().getCity())
                            .state(itemDto.getLocationDto().getState())
                            .zipcode(itemDto.getLocationDto().getZipcode())
                            .country(itemDto.getLocationDto().getCountry())
                            .build()
            );
        } else {
            location = locationService.getLocationById(itemDto.getLocationId());
        }

        Item item = Item
                .builder()
                .title(itemDto.getTitle())
                .description(itemDto.getDescription())
                .quantity(itemDto.getQuantity())
                .category(itemDto.getCategory())
                .condition(itemDto.getCondition())
                .availabilityTime(itemDto.getAvailabilityTime())
                .listingPrice(itemDto.getListingPrice())
                .fixPrice(itemDto.getFixedPrice())
                .location(location)
                .seller(seller)
                .build();

        // 3. itemService: call service to save item
        // business logic is all put into itemService
        item = itemService.saveItem(item);

        // 4. item
        // TODO 1: hide lcoation or not
        // TODO 2: sellerId set to current userId
        itemDto.setSellerId(seller.getId());
        if (item.getHideLocation() == true) {
            itemDto.setLocationId(location.getId());
            itemDto.setLocationDto(null);
        }

        return itemDto;
    }
}
