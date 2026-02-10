package hu.ps.ss.domain.pojo;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemEditable {

  private LocalDateTime modified;
  private String modifiedBy;

}
