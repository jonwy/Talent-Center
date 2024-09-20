package com.tujuhsembilan.app.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalentWishlistDto {

    private UUID talentWishlistId;
    private TalentsDto talent;
    private  ClientDto client;
    private Date wishlistDate;  
}
