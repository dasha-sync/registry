package api.controller;

import api.dto.common.ApiResponse;
import api.dto.subscription.SubscriptionAmountRequest;
import api.dto.subscription.SubscriptionResponse;
import api.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
  private final SubscriptionService subscriptionService;

  @PostMapping("/create/{cardId}")
  public ResponseEntity<ApiResponse<SubscriptionResponse>> createSubscription(
      @PathVariable Long cardId) throws Exception {
    SubscriptionResponse subscription = subscriptionService.createSubscription(cardId);
    return ResponseEntity.ok(new ApiResponse<>("Subscription created successfully", subscription));
  }

  @DeleteMapping("/cancel")
  public ResponseEntity<ApiResponse<SubscriptionResponse>> cancelSubscription() throws Exception {
    SubscriptionResponse subscription = subscriptionService.cancelSubscription();
    return ResponseEntity.ok(new ApiResponse<>("Subscription cancelled successfully", subscription));
  }

  @PutMapping("/change-payment-method/{cardId}")
  public ResponseEntity<ApiResponse<SubscriptionResponse>> updatePaymentMethod(
      @PathVariable Long cardId) throws Exception {
    SubscriptionResponse subscription = subscriptionService.updatePaymentMethod(cardId);
    return ResponseEntity.ok(new ApiResponse<>("Payment method updated successfully", subscription));
  }

  @GetMapping
  public ResponseEntity<ApiResponse<SubscriptionResponse>> getSubscription() {
    SubscriptionResponse subscription = subscriptionService.getSubscription();
    return ResponseEntity.ok(new ApiResponse<>("User subscription", subscription));
  }

  @PutMapping("/amount/overwrite")
  public ResponseEntity<ApiResponse<SubscriptionResponse>> overwriteAmount(
      @RequestBody SubscriptionAmountRequest request
  ) {
    SubscriptionResponse response = subscriptionService.overwriteAmount(request.getAmount());
    return ResponseEntity.ok(new ApiResponse<>("Subscription amount overwritten successfully", response));
  }

  @PutMapping("/amount/increase")
  public ResponseEntity<ApiResponse<SubscriptionResponse>> increaseAmount(
      @RequestBody SubscriptionAmountRequest request
  ) {
    SubscriptionResponse response = subscriptionService.increaseAmount(request.getAmount());
    return ResponseEntity.ok(new ApiResponse<>("Subscription amount increased successfully", response));
  }

  @PutMapping("/amount/reset")
  public ResponseEntity<ApiResponse<SubscriptionResponse>> resetAmount() {
    SubscriptionResponse response = subscriptionService.resetAmount();
    return ResponseEntity.ok(new ApiResponse<>("Subscription amount increased successfully", response));
  }
}
