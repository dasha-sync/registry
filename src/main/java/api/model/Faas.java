package api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "faas_metrics")
@AllArgsConstructor
@NoArgsConstructor
public class Faas {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "func_name", nullable = false)
  private String funcName;

  @Column(name = "metric_name", nullable = false)
  private String metricName; // metric1 / metric2 / metric3

  @Column(name = "metric_value", nullable = false)
  private Long metricValue;

  @CreationTimestamp
  @Column(name = "ts", nullable = false, updatable = false)
  private OffsetDateTime ts;
}
