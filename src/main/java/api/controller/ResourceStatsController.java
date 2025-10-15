package api.controller;

import api.dto.common.ApiResponse;
import api.dto.resourceStats.ResourceCostRequest;
import api.dto.resourceStats.ResourceResponse;
import api.dto.resourceStats.ResourceUpdateRequest;
import api.service.ResourceStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resource")
@RequiredArgsConstructor
public class ResourceStatsController {

  private final ResourceStatsService resourceStatsService;

  @PostMapping("/update")
  public ResponseEntity<ApiResponse<ResourceResponse>> updateResources(
      @RequestBody ResourceUpdateRequest request) {

    ResourceResponse response = resourceStatsService.updateResources(request);
    return ResponseEntity.ok(
        new ApiResponse<>("Resources updated successfully", response)
    );
  }

  @PostMapping("/increment-calls")
  public ResponseEntity<ApiResponse<ResourceResponse>> incrementCalls() {
    ResourceResponse response = resourceStatsService.incrementCalls();
    return ResponseEntity.ok(
        new ApiResponse<>("Calls incremented successfully", response)
    );
  }

  @GetMapping
  public ResponseEntity<ApiResponse<ResourceResponse>> getAll() {
    ResourceResponse response = resourceStatsService.getAll();
    return ResponseEntity.ok(
        new ApiResponse<>("Current resource statistics", response)
    );
  }

  @PutMapping("/update-costs")
  public ResponseEntity<ApiResponse<ResourceResponse>> updateCosts(
      @RequestBody ResourceCostRequest request) {

    ResourceResponse response = resourceStatsService.updateCost(request);
    return ResponseEntity.ok(
        new ApiResponse<>("Resource costs updated successfully", response)
    );
  }
}
