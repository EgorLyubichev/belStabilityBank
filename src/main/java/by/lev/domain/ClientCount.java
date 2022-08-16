package by.lev.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@EqualsAndHashCode
@Builder
@ToString //(exclude = {"userName"})
@AllArgsConstructor
@NoArgsConstructor
public class ClientCount {

    private Long id;

    private Double count;

    private Timestamp registrationDate;

    private Timestamp modificationDate;

}
