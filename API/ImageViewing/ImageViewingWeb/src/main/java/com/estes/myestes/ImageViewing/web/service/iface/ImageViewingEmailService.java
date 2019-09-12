package com.estes.myestes.ImageViewing.web.service.iface;

import com.estes.myestes.ImageViewing.web.dto.ImageEmail;

public interface ImageViewingEmailService {

	boolean processEmailRequest(ImageEmail imageEmail);

}
