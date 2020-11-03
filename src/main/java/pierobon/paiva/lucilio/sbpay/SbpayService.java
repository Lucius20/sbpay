package pierobon.paiva.lucilio.sbpay;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SbpayService {
	
		private static final String AUTH_TOKEN = "r2d2";
	
	@PostMapping(path = "/pay", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PaymentResponse> pay(
			@RequestHeader("Token") String token,
			@Valid @RequestBody(required = false) CardData cardData,
			Errors errors) {
		
		if (!AUTH_TOKEN.equals(token)) {
			return ResponseEntity.badRequest().body(new PaymentResponse("Token inválido."));
		}
		
		if (errors.hasErrors()) {
			return ResponseEntity.ok(new PaymentResponse(PaymentStatus.INVALID_CARD));
		}
		
		String cardNumber = cardData.getCardNumber();
		
		PaymentStatus status = cardNumber.startsWith("1111") ? PaymentStatus.AUTHORIZED : PaymentStatus.DENIED;
		
		return ResponseEntity.ok(new PaymentResponse(status));
	}

}
