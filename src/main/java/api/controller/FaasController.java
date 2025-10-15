package api.controller;

import api.dto.common.MetricResponse;
import api.service.FaasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import api.dto.faas.FaasRequest;
import api.dto.faas.FaasResponse;
import api.model.Faas;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/faas")
@RequiredArgsConstructor
public class FaasController {
  private final FaasService faasService;

  @GetMapping
  public ResponseEntity<MetricResponse<List<FaasResponse>>> getFaas() {
    List<FaasResponse> faas = faasService.getFaas();
    List<FaasResponse> data = faas.isEmpty() ? null : faas;
    return ResponseEntity.ok(new MetricResponse<>("User FaaS functions", data));
  }

  @PostMapping
  public ResponseEntity<FaasResponse> addFaas(@RequestBody FaasRequest request) {
    Faas saved = faasService.addFaasMetric(
        request.getFuncName(),
        request.getMetricName(),
        request.getMetricValue()
    );

    FaasResponse response = new FaasResponse(
        saved.getId(),
        saved.getFuncName(),
        saved.getMetricName(),
        saved.getMetricValue(),
        saved.getTs()
    );

    return ResponseEntity.ok(response);
  }
}

