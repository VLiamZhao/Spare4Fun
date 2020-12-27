package com.spare4fun.core;

import com.spare4fun.core.dto.*;
import com.spare4fun.core.entity.*;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.UserAuthService;
import com.spare4fun.core.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Don't use this class. Will be deprecated in next version.
 */
@Configuration
public class ConverterConfig {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public TypeMap<UserDto, User> convertUserDtoToUser() {
        return modelMapper.typeMap(UserDto.class, User.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsername(), User::setEmail);
            mapper.map(src -> src.getPassword(), User::setPassword);
        });
    }

//    @Bean
//    public TypeMap<OfferDto, Offer> convertOfferDtoToOffer() {
//        modelMapper.getConfiguration().setAmbiguityIgnored(true);
//        return modelMapper.typeMap(OfferDto.class, Offer.class).addMappings(mapper -> {
//            mapper.map(OfferDto::getMessage, Offer::setMessage);
//        });
//    }
//
//    @Bean
//    public TypeMap<Offer, OfferDto> convertOfferToOfferDto() {
////        modelMapper.getConfiguration().setAmbiguityIgnored(true);
//        return modelMapper.typeMap(Offer.class, OfferDto.class).addMappings(mapper -> {
//            mapper.map(src -> src.getSeller().getEmail(), OfferDto::setSellerName);
//            mapper.map(Offer::getMessage, OfferDto::setMessage);
//        });
//    }

    @Bean
    public TypeMap<Item, ItemDto> convertItemToItemDto() {
        return modelMapper.typeMap(Item.class, ItemDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), ItemDto::setItemId);
            mapper.map(src -> src.getTitle(), ItemDto::setTitle);
            mapper.map(src -> src.getDescription(), ItemDto::setDescription);
            mapper.map(src -> src.getSeller().getId(), ItemDto::setSellerId);
            mapper.map(src -> {
                UserInfo info = src.getSeller().getUserInfo();
                String name = String.format("%s %s",
                        info.getFirstName(),
                        info.getLastName());
                return name;
            }, ItemDto::setSellerName);
            // concrete location is not set here
            mapper.map(src -> src.getLocation().getId(), ItemDto::setLocationId);
            mapper.map(src -> src.isHideLocation(), ItemDto::setHideLocation);
            mapper.map(src -> src.getQuantity(), ItemDto::setQuantity);
            mapper.map(src -> src.getListingPrice(), ItemDto::setListingPrice);
            mapper.map(src -> src.isFixedPrice(), ItemDto::setFixedPrice);
            mapper.map(src -> src.getAvailabilityTime(), ItemDto::setAvailabilityTime);
            mapper.map(src -> src.getCategory().getCategory(), ItemDto::setCategory);
            mapper.map(src -> src.getCondition().getLabel(), ItemDto::setCondition);
        });
    }

    @Bean
    public TypeMap<Location, LocationDto> convertLocationToLocationDto() {
        return modelMapper.typeMap(Location.class, LocationDto.class).addMappings(mapper -> {
            mapper.map(Location::getId, LocationDto::setId);
            mapper.map(Location::getLine1, LocationDto::setLine1);
            mapper.map(Location::getLine2, LocationDto::setLine2);
            mapper.map(Location::getCity, LocationDto::setCity);
            mapper.map(Location::getState, LocationDto::setState);
            mapper.map(Location::getZipcode, LocationDto::setZipcode);
            mapper.map(Location::getCountry, LocationDto::setCountry);
        });
    }

    @Bean
    public TypeMap<Category, CategoryDto> convertCatogoryToCategoryDto() {
        return modelMapper.typeMap(Category.class, CategoryDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), CategoryDto::setCategoryId);
            mapper.map(src -> src.getCategory(), CategoryDto::setCategory);
        });
    }

    @Bean
    public TypeMap<ItemCondition, ItemConditionDto> convertItemConditionToItemConditionDto() {
        return modelMapper.typeMap(ItemCondition.class, ItemConditionDto.class).addMappings(mapper -> {
            mapper.map(src -> src.getId(), ItemConditionDto::setItemConditionId);
            mapper.map(src -> src.getLabel(), ItemConditionDto::setLabel);
        });
    }
}
