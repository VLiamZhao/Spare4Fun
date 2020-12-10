package com.spare4fun.core.controller;

import com.spare4fun.core.dto.ItemDto;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.exception.InvalidUserException;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import com.spare4fun.core.dto.LocationDto;
import com.spare4fun.core.entity.Location;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.service.UserService;
import org.modelmapper.TypeMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private LocationService locationService;

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
            location = locationService.saveLocation(
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
                //.category(itemDto.getCategory())
                //.condition(itemDto.getCondition())
                .availabilityTime(itemDto.getAvailabilityTime())
                .listingPrice(itemDto.getListingPrice())
                .fixedPrice(
                        itemDto.getFixedPrice() != null && itemDto.getFixedPrice())
                .hideLocation(
                        itemDto.getHideLocation() != null && itemDto.getHideLocation())
                .location(location)
                .seller(seller)
                .build();

        // 3. itemService: call service to save item
        // business logic is all put into itemService
        item = itemService.saveItem(item);

        // 4. item
        // TODO 1: hide lcoation or not
        // TODO 2: sellerId set to current userId
        itemDto = itemDtoMapper.map(item);
        if (!item.isHideLocation()) {
            itemDto.setLocationDto(locationDtoMapper.map(item.getLocation()));
        }

        return itemDto;
    }

    @GetMapping("/{itemId}")
    @ResponseBody
    public ItemDto getItemById(@PathVariable(value = "itemId") int itemId) {

        Item item = itemService.getItemById(itemId);

        ItemDto itemDto = ItemDto
                .builder()
                .itemId(item.getId())
                .title(item.getTitle())
                .description(item.getDescription())
                .sellerId(item.getSeller().getId())
                .sellerName(item.getSeller().getUsername())
                .locationId(item.getLocation().getId())
                .hideLocation(item.isHideLocation())
                .quantity(item.getQuantity())
                .listingPrice(item.getListingPrice())
                .fixedPrice(item.isFixedPrice())
                .availabilityTime(item.getAvailabilityTime())
                .category(item.getCategory().getCategory())
                .condition(item.getCondition().getLabel())
                .build();
        return itemDto;
    }

    @PostMapping("/deleteItem/{itemId}")
    public void deleteItemById(@PathVariable(value = "itemId") int itemId){

        Item item = itemService.getItemById(itemId);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        int currentUserId = userService.loadUserByUsername(username).get().getId();

        //only seller can delete the item
        if (currentUserId != item.getSeller().getId()) {
            throw new InvalidUserException("You don't have the authorization to delete the item!");
        }

        itemService.deleteItemById(itemId);
    }
}
