package com.sonda.emsysmobile.logic.model.core.attachments;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by marccio on 08-Nov-16.
 */
public class ImageDescriptionDtoTest {

    private ImageDescriptionDto mImageDescriptionDto;
    private int mId;
    private String mUser;
    private Date mDeliveryDate;
    private int mExtensionId;
    private String mImageUrl;

    @Before
    public void setUp() throws Exception {
        mId = 1;
        mUser = "user";
        mDeliveryDate = new Date(1992,1,1);
        mExtensionId = 1;
        mImageUrl = "url";
        mImageDescriptionDto = new ImageDescriptionDto();
        mImageDescriptionDto.setId(mId);
        mImageDescriptionDto.setUser(mUser);
        mImageDescriptionDto.setDeliveryDate(mDeliveryDate);
        mImageDescriptionDto.setExtensionId(mExtensionId);
        mImageDescriptionDto.setImageUrl(mImageUrl);
    }

    @Test
    public void setId() throws Exception {
        final int i = 2;
        mImageDescriptionDto.setId(i);
        assertTrue(mImageDescriptionDto.getId() == i);
    }

    @Test
    public void setUser() throws Exception {
        final String alt_user = "alt_user";
        mImageDescriptionDto.setUser(alt_user);
        assertTrue(mImageDescriptionDto.getUser().equals(alt_user));
    }

    @Test
    public void setDeliveryDate() throws Exception {
        final Date deliveryDate = new Date();
        mImageDescriptionDto.setDeliveryDate(deliveryDate);
        assertTrue(mImageDescriptionDto.getDeliveryDate().equals(deliveryDate));
    }

    @Test
    public void setExtensionId() throws Exception {
        final int extensionId = 2;
        mImageDescriptionDto.setExtensionId(extensionId);
        assertTrue(mImageDescriptionDto.getExtensionId() == extensionId);
    }

    @Test
    public void setImageUrl() throws Exception {
        final String alt_url = "alt_url";
        mImageDescriptionDto.setImageUrl(alt_url);
        assertTrue(mImageDescriptionDto.getImageUrl().equals(alt_url));
    }

}