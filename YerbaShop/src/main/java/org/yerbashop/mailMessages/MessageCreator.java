package org.yerbashop.mailMessages;

import org.springframework.mail.SimpleMailMessage;

public interface MessageCreator {

	SimpleMailMessage getMessage();
}
