package api.service;

import api.dto.faas.FaasResponse;
import api.dto.kafka.MetricsEvent;
import api.model.Faas;
import api.repository.FaasRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FaasService {
  private final FaasRepository faasRepository;

  @Transactional
  public void saveMetrics(MetricsEvent event) {
    List<Faas> metricsToSave = buildMetrics(event);
    faasRepository.saveAll(metricsToSave);
    log.debug("Saved {} metrics for func={}", metricsToSave.size(), event.getFuncName());
  }

  public List<FaasResponse> getFaas() {
    return faasRepository.findAll().stream()
        .map(entity -> new FaasResponse(
            entity.getId(),
            entity.getFuncName(),
            entity.getMetricName(),
            entity.getMetricValue(),
            entity.getTs()
        ))
        .toList();
  }

  private List<Faas> buildMetrics(MetricsEvent event) {
    return event.toMetricsMap().entrySet().stream()
        .filter(e -> e.getValue() != null && e.getValue() != 0)
        .map(e -> new Faas(null, event.getFuncName(), e.getKey(), e.getValue(), null))
        .toList();
  }

  @Transactional
  public Faas addFaasMetric(String funcName, String metricName, Long metricValue) {
    Faas metric = new Faas();
    metric.setFuncName(funcName);
    metric.setMetricName(metricName);
    metric.setMetricValue(metricValue != null ? metricValue : 0L);

    Faas saved = faasRepository.save(metric);
    log.debug("Added FaaS metric func={}, metric={}, value={}", funcName, metricName, metricValue);
    return saved;
  }
}
