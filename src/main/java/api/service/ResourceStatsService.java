package api.service;

import api.dto.resourceStats.*;
import api.model.ResourceStats;
import api.repository.ResourceStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceStatsService {

  private final ResourceStatsRepository repository;

  private ResourceStats getOrCreate() {
    List<ResourceStats> all = repository.findAll();
    if (all.isEmpty()) {
      ResourceStats stats = new ResourceStats();
      stats.setCpu(0);
      stats.setRam(0);
      stats.setGb(0);
      stats.setCalls(0);
      repository.save(stats);
      return stats;
    }
    return all.get(0);
  }

  @Transactional
  public ResourceResponse updateResources(ResourceUpdateRequest dto) {
    ResourceStats stats = getOrCreate();
    if (dto.getCpu() != null) stats.setCpu(dto.getCpu());
    if (dto.getRam() != null) stats.setRam(dto.getRam());
    if (dto.getGb() != null) stats.setGb(dto.getGb());
    repository.save(stats);
    return mapToResponse(stats);
  }

  public ResourceResponse getAll() {
    return mapToResponse(getOrCreate());
  }

  @Transactional
  public ResourceResponse incrementCalls() {
    ResourceStats stats = getOrCreate();
    stats.setCalls(stats.getCalls() + 1);
    repository.save(stats);
    return mapToResponse(stats);
  }

  @Transactional
  public ResourceResponse updateCost(ResourceCostRequest dto) {
    ResourceStats stats = getOrCreate();
    if (dto.getCpuCost() != null) stats.setCpuCost(dto.getCpuCost());
    if (dto.getRamCost() != null) stats.setRamCost(dto.getRamCost());
    if (dto.getGbCost() != null) stats.setGbCost(dto.getGbCost());
    if (dto.getCollCost() != null) stats.setCollCost(dto.getCollCost());
    repository.save(stats);
    return mapToResponse(stats);
  }

  private ResourceResponse mapToResponse(ResourceStats stats) {
    return new ResourceResponse(
        stats.getCpu(),
        stats.getRam(),
        stats.getGb(),
        stats.getCalls(),
        stats.getCpuCost(),
        stats.getRamCost(),
        stats.getGbCost(),
        stats.getCollCost()
    );
  }
}
